package com.spring.common.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**  
 * @Title: KaptchaConfig
 * @Description: 生成验证码配置
 * @author  团子
 * @date 2018/7/29 9:47
 * @since v1.0
 */
@Configuration
public class KaptchaConfig {

    @Bean("captchaProducer")
    public DefaultKaptcha captchaProducer() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.image.width", "105");
        properties.put("kaptcha.image.height", "40");
        properties.put("kaptcha.textproducer.font.color", "204,51,51");
        properties.put("kaptcha.textproducer.font.size", "33");
        properties.put("kaptcha.textproducer.char.length", "4");
        properties.put("kaptcha.textproducer.char.space", "4");
        properties.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
