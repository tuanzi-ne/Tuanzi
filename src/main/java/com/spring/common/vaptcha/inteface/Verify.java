package com.spring.common.vaptcha.inteface;

import com.spring.common.vaptcha.constant.Constant;
import com.spring.common.vaptcha.domain.HttpResp;
import com.spring.common.vaptcha.domain.SecondVerify;
import com.spring.common.vaptcha.sdk.Vaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class Verify {
    private Vaptcha vaptcha = Vaptcha.getInstance(Constant.SecretKey, Constant.Vid, Constant.Scene);

    /**
     * 二次验证接口
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ResponseBody
    public Object VaptchaVerify(@RequestBody com.spring.common.vaptcha.domain.Verify verify, HttpServletRequest request) {

        String token = verify.getToken();
        SecondVerify result = vaptcha.Verify(request, token);
        if (result.getSuccess() == Constant.VerifySuccess) {
            // 二次验证成功
            // 执行后续逻辑 比如:登录 注册
            return new HttpResp("verify success", 200);
        } else {
            // 二次验证失败
            // 前端重新人机验证
            return new HttpResp("verify fail", 400);
        }
    }
}
