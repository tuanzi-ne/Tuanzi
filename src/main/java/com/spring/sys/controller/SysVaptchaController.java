/*
 *
 *      Copyright (c) 2018-2025, tuanzi All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the tuanzi developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: tuanzi (1766285184@qq.com)
 *
 */

package com.spring.sys.controller;

import com.spring.common.vaptcha.constant.Constant;
import com.spring.common.vaptcha.domain.VerifyResp;
import com.spring.common.vaptcha.sdk.Vaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 离线模式交互接口
 * @Author tuanzi
 * @Date 2020/8/20 4:33 下午
 * @Version 1.0
 */
@Controller
public class SysVaptchaController {


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
