package com.spring.common.exception;

/**
 * 自定义系统异常
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 16:09
 */
public class SysException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public SysException() {
        super();
    }
    public SysException(String msg) {
        super(msg);
    }
    public SysException(String msg, Throwable e) {
        super(msg, e);
    }
}
