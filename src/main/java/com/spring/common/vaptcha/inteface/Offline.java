package com.spring.common.vaptcha.inteface;


import com.spring.common.vaptcha.constant.Constant;
import com.spring.common.vaptcha.domain.VerifyResp;
import com.spring.common.vaptcha.sdk.Vaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Offline {
    private Vaptcha vaptcha = Vaptcha.getInstance(Constant.SecretKey, Constant.Vid, Constant.Scene);

    /**
     * 离线模式交互接口
     */
    @RequestMapping(value = "/offline")
    @ResponseBody
    public Object VaptchaOffline(com.spring.common.vaptcha.domain.Offline offline, HttpServletRequest request) {
        if (offline == null) {
            return new VerifyResp(Constant.ValidateFail, Constant.ValidateFail, "");
        }
        String callback = offline.getCallback().trim();
        if ("".equals(callback)) {
            return new VerifyResp(Constant.ValidateFail, Constant.ValidateFail, "");
        }
        String offlineAction = offline.getOffline_action();
        String knock = offline.getKnock();
        String vid = offline.getVid();
        String userCode = offline.getV();

        // 测试离线模式时vid传"offline"
        // http://channel.vaptcha.com/config/offline
        vid = "offline";
        return vaptcha.Offline(request, offlineAction, callback, vid, knock, userCode);
    }

}
