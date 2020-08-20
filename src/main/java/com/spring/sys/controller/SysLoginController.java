package com.spring.sys.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.spring.common.annotation.SysLog;
import com.spring.common.mvc.BaseController;
import com.spring.common.shiro.ShiroUtils;
import com.spring.common.utils.Result;
import com.spring.common.vaptcha.constant.Constant;
import com.spring.common.vaptcha.domain.SecondVerify;
import com.spring.common.vaptcha.sdk.Vaptcha;
import com.spring.sys.pojo.SysResInfo;
import com.spring.sys.service.SysResService;
import com.spring.sys.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;


/**
 * 系统登录
 *
 * @author  团子
 * @date 2018/3/5 14:37
 * @since V1.0
 */
@Controller
public class SysLoginController extends BaseController{
    @Autowired
    private Producer captchaProducer;
    @Autowired
    SysResService sysResService;
    @Autowired
    SysUserService sysUserService;

    private Vaptcha vaptcha = Vaptcha.getInstance(Constant.SecretKey, Constant.Vid, Constant.Scene);

    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = captchaProducer.createText();
        //生成图片验证码
        BufferedImage image = captchaProducer.createImage(text);
        //保存到session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登录管理
     */
    @SysLog("登录系统")
    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody com.spring.common.vaptcha.domain.Verify verify, HttpServletRequest request) {
//        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//        if (!captcha.equalsIgnoreCase(kaptcha)) {
//            return Result.error("验证码错误!");
//        }

        String token = verify.getToken();
        SecondVerify result = vaptcha.Verify(request, token);
        if (result.getSuccess() == Constant.VerifySuccess) {
            // 二次验证成功
            // 执行后续逻辑 比如:登录 注册
            try {
                Subject subject = ShiroUtils.getSubject();
                if (!subject.isAuthenticated()) {
                    subject.login(new UsernamePasswordToken(verify.getUsername(), verify.getPassword()));
                }
            } catch (UnknownAccountException e) {
                return Result.error("账号不存在!");
            } catch (IncorrectCredentialsException e) {
                return Result.error("密码错误!");
            } catch (LockedAccountException e) {
                return Result.error("账号被锁定,请联系管理员!");
            } catch (AuthenticationException e) {
                return Result.error("账户验证失败!");
            }
            return Result.build();

        } else {
            // 二次验证失败
            // 前端重新人机验证
            return Result.error("验证失败");
        }


    }

    /**
     * 退出系统管理,重定向到登录页面
     */
    @SysLog("退出系统")
    @GetMapping("/logout")
    public String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }

    /**
     * 管理主页
     *
     * @return admin
     */
    @GetMapping("/admin")
    public String admin(Model model) {
        List<SysResInfo> navs = sysResService.getNav(getUserId());
        String skin = sysUserService.getParamValueByKey("sys.default.skin", com.spring.common.utils.Constants.SkinTheme.DEFAULT.getValue());
        model.addAttribute("navs", navs);
        model.addAttribute("username", getUser().getUsername());
        model.addAttribute("defaultSkin", skin);
        return "admin";
    }

    /**
     * 首页
     *
     * @return main
     */
    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
