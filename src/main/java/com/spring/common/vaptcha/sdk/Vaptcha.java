package com.spring.common.vaptcha.sdk;


import com.google.gson.Gson;
import com.spring.common.vaptcha.constant.Constant;
import com.spring.common.vaptcha.domain.*;
import com.spring.common.vaptcha.utils.Common;
import com.spring.common.vaptcha.utils.HttpClientUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Vaptcha sdk
 */
public class Vaptcha {
    // 验证单元key
    private String SecretKey;
    // 验证单元id
    private String Vid;
    // 场景值
    private String Scene;

    private Vaptcha() {
    }

    private Vaptcha(String secretKey, String vid, String scene) {
        SecretKey = secretKey;
        Vid = vid;
        Scene = scene;
    }

    private static volatile Vaptcha vaptcha;

    public static Vaptcha getInstance(String secretKey, String vid, String scene) {
        if (vaptcha == null) {
            synchronized (Vaptcha.class) {
                if (vaptcha == null) {
                    vaptcha = new Vaptcha(secretKey, vid, scene);
                }
            }
        }
        return vaptcha;
    }

    // -----------------------public------------------------

    /**
     * 二次验证
     *
     * @param token mode(7)+knock(32)+uuid(32)
     */
    public SecondVerify Verify(HttpServletRequest request, String token) {
        if (token.length() < 7) {
            return new SecondVerify(Constant.VerifyFail, "验证失败", 0);
        }
        // 根据token前7位判断验证模式
        String mode = token.substring(0, 7);
        if (Constant.OfflineMode.equals(mode)) {
            // 离线模式
            // token 中间32位为knock
            String knock = token.substring(7, 39);
            HttpSession session = request.getSession();
            // 根据截取出来的knock从session中获取token
            String sessionToken = (String) session.getAttribute(knock);
            SecondVerify secondVerify = OfflineVerify(token, sessionToken);
            // 二次验证成功则从session中移除
            if (secondVerify.getSuccess() == Constant.VerifySuccess) {
                session.removeAttribute(knock);
            }
            return secondVerify;
        } else {
            // 正常模式
            String ipAddress = Common.GetIpAddress(request);
            return Verify(ipAddress, token);
        }
    }

    /**
     * 离线模式
     */
    public Object Offline(HttpServletRequest request, String offlineAction, String callback, String vid, String knock, String userCode) {
        HttpSession session = request.getSession();
        if (Constant.ActionGet.equals(offlineAction)) {
            // 从session中获取offlineKey
            // 获取失败则调用 GetOfflineKey(vid)获取并存入session
            String offlineKey = (String) session.getAttribute(vid);
            if (offlineKey == null || "".equals(offlineKey)) {
                GetResp offlineData = GetOfflineKey(vid);
                if (0 == offlineData.getOfflineState()) {
                    return "VAPTCHA未进入离线模式";
                } else {
                    session.setAttribute(vid, offlineData.getOfflineKey());
                    offlineKey = offlineData.getOfflineKey();
                }
            }
            // 获取验证图
            Image image = GetImage(knock, offlineKey);
            // 生成imgData并以knock为key存入session
            // key:knock value:unix+imgId
            String timestamp = String.valueOf(Common.GetTimeStamp());
            String imgData = timestamp + image.getImgId();
            session.setAttribute(image.getKnock(), imgData);
            // 拼接并返回
            String res = new Gson().toJson(image);
            String[] resultArray = new String[]{callback, "(", res, ")"};
            return Common.StrAppend(resultArray);
        } else {
            // imgData=unix(10)+imgId(32)
            // 根据knock从session中获取imgData 获取成功则移除掉 一个knock只能使用一次
            String imgData = (String) session.getAttribute(knock);
            session.removeAttribute(knock);
            if (imgData == null) {
                VerifyResp verify = new VerifyResp();
                verify.setCode(Constant.ValidateFail);
                verify.setMsg("knock过期");
                String result = new Gson().toJson(verify);
                String[] resultArray = new String[]{callback, "(", result, ")"};
                return Common.StrAppend(resultArray);
            }
            String imgId = imgData.substring(10);
            GetResp offlineKey = GetOfflineKey(vid);
            VerifyResp verify = Validate(imgId, userCode, offlineKey.getOfflineKey());
            if (Constant.ValidateSuccess.equals(verify.getCode())) {
                // 校验成功则生成token并存入session
                // session中只存uid 返回前端时拼接 offline+knock+uid
                String uuid = Common.GetUUID();
                session.setAttribute(knock, uuid);
                String respToken = Constant.OfflineMode + knock + uuid;
                verify.setToken(respToken);
            }
            String result = new Gson().toJson(verify);
            String[] resultArray = new String[]{callback, "(", result, ")"};
            return Common.StrAppend(resultArray);
        }
    }

    //-------------------------private------------------------

    /**
     * 获取离线验证图
     *
     * @return ImageId knock等信息
     */
    private Image GetImage(String knock, String offlineKey) {
        if (offlineKey == null || "".equals(offlineKey)) {
            return new Image(Constant.ValidateFail, "", "", "离线key获取失败", "");
        }
        String randomStr = Common.GetRandomStr();
        String imgId = Common.MD5Encode(offlineKey + randomStr);
        if (knock == null || "".equals(knock)) {
            knock = Common.GetUUID();
        }
        return new Image(Constant.ValidateSuccess, imgId, knock, "", offlineKey);
    }

    /**
     * 根据vid获取offlineKey
     *
     * @param vid 验证单元ID
     * @return offlineKey
     */
    private GetResp GetOfflineKey(String vid) {
        try {
            List<NameValuePair> parametersBody = new ArrayList<>();
            HttpResp httpResp = HttpClientUtil.getRequest(Constant.ChannelUrl + "config/" + vid, parametersBody);
            Gson gson = new Gson();
            return gson.fromJson(httpResp.getResp(), GetResp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new GetResp();
    }

    /**
     * @param imgId      MD5(offlineKey + randomStr)
     * @param v          用户绘制特征点
     * @param offlineKey 离线key
     */
    private VerifyResp Validate(String imgId, String v, String offlineKey) {
        String url = Common.MD5Encode(v + imgId);
        String fullUrl = Constant.ValidateUrl + offlineKey + "/" + url;
        try {
            List<NameValuePair> parametersBody = new ArrayList<>();
            HttpResp httpResp = HttpClientUtil.getRequest(fullUrl, parametersBody);
            if (200 == httpResp.getCode()) {
                return new VerifyResp(Constant.ValidateSuccess, "", "");
            } else {
                return new VerifyResp(Constant.ValidateFail, Constant.ValidateFail, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new VerifyResp(Constant.ValidateFail, Constant.ValidateFail, "");
    }

    /**
     * 离线模式二次校验
     *
     * @param reqToken     前端请求的token offline(7)+knock(32)+uuid(32)
     * @param sessionToken 后端session中存的token uuid(32)
     */
    private SecondVerify OfflineVerify(String reqToken, String sessionToken) {
        if (reqToken == null || "".equals(reqToken) || sessionToken == null || "".equals(sessionToken)) {
            return new SecondVerify(Constant.VerifyFail, "验证失败", 0);
        }

        String uid = reqToken.substring(39);
        if (uid.equals(sessionToken)) {
            return new SecondVerify(Constant.VerifySuccess, "验证通过", 100);
        } else {
            return new SecondVerify(Constant.VerifyFail, "验证失败", 0);
        }
    }

    /**
     * 正常模式二次校验
     *
     * @param token 前端回传token
     * @param ip    用户ip
     */
    private SecondVerify Verify(String ip, String token) {
        List<NameValuePair> parametersBody = new ArrayList<>();
        parametersBody.add(new BasicNameValuePair("id", this.Vid));
        parametersBody.add(new BasicNameValuePair("secretkey", this.SecretKey));
        parametersBody.add(new BasicNameValuePair("scene", this.Scene));
        parametersBody.add(new BasicNameValuePair("ip", ip));
        parametersBody.add(new BasicNameValuePair("token", token));
        try {
            String result = HttpClientUtil.postForm(Constant.VerifyUrl, parametersBody);
            Gson gson = new Gson();
            return gson.fromJson(result, SecondVerify.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SecondVerify();
    }


}