package com.spring.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * @Title: UploadImgConfig
 * @Description: 头像上传参数配置
 * @author  团子
 * @date 2018/9/15 15:04
 * @since v1.0
 */
@Configuration
public class UploadImgConfig {

    @Value("${upload.img.path}")
    private String uploadPath;

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        File file = new File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 使用绝对路径
        factory.setLocation(uploadPath);
        // 文件最大值
        factory.setMaxFileSize("2MB");
        // 设置总上传数据总大小
        factory.setMaxRequestSize("3MB");
        return factory.createMultipartConfig();
    }
}
