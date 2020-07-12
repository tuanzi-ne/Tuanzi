package com.spring.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author  团子
 * @Title: UploadImgPathConfig
 * @Description: 设置上传头像虚拟路径，访问绝对路径下资源
 * @date 2018/9/15 13:11
 * @since v1.0
 */
@Configuration
public class UploadImgPathConfig implements WebMvcConfigurer {
    @Value("${upload.img.path}")
    private String uploadPath;
    @Value("${upload.img.static.url}")
    private String staticUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticUrl).addResourceLocations("file:" + uploadPath);
    }
}
