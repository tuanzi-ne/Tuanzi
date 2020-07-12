package com.spring.common.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @Title: AbstractLogging
 * @Description: 抽象日志类
 * @author  团子
 * @date 2018/8/26 9:28
 * @since v1.0
 */
public abstract class AbstractLogging {
    protected Logger log = LoggerFactory.getLogger(getClass());
}
