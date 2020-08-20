package com.spring.common.vaptcha.inteface;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Index {
    @RequestMapping(value = {"/", "/click"})
    public String VaptchaIndex() {
        return "click";
    }

    @RequestMapping(value = "/embed")
    public String Embed() {
        return "embed";
    }

    @RequestMapping(value = "/invisible")
    public String Invisible() {
        return "invisible";
    }
}

