package com.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**  
 * 启动类
 * @author  团子
 * @date 2018/8/4 14:03
 * @since v1.0
 */
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.spring.*.dao")
@SpringBootApplication
@EnableCaching
public class TuanziApplication {
	public static void main(String[] args) {
		SpringApplication.run(TuanziApplication.class, args);
	}
}
