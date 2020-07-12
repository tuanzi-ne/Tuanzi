package com.spring.job.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试定时Job(演示Demo，可删除)
 *
 * @author  团子
 * @date 2018/4/18 17:31
 * @since V1.0
 */
@Component("testTask")
public class TestTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void hello(String params) {
        logger.info("带参数的方法，正在被执行，参数为：" + params);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello,参数为：" + params);
    }

    public void hello2() {
        logger.info("不带参数的方法，正在被执行");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello,无参");
    }
}
