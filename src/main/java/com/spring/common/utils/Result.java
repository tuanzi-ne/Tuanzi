package com.spring.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 结果集
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 18:22
 */
public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "code";
    private static final String MSG = "msg";
    private static final Integer NORMAL_CODE = 1;
    private static final Integer ERROR_CODE = 0;

    private Result() { }

    public static Result build() {
        Result rs = new Result();
        //code默认为0
        rs.put(CODE,NORMAL_CODE);
        return rs;
    }
    /**
     * 添加正常消息
     * @param msg 消息内容
     * @return Result
     */
    public static Result info(String msg) {
        Result rs = build();
        rs.put(MSG, msg);
        return rs;
    }
    /**
     * 添加数据
     * @param key value
     * @return Result
     */
    public static Result data(String key, Object value) {
        Result rs = build();
        rs.put(key, value);
        return rs;
    }

    /**
     * 批量添加数据
     * @param map value
     * @return Result
     */
    public static Result datas(Map<String, Object> map) {
        Result rs = build();
        rs.putAll(map);
        return rs;
    }

    /**
     * 添加错误消息
     * @param msg 消息内容
     * @return Result
     */
    public static Result error(String msg) {
        Result rs = build();
        rs.put(CODE, ERROR_CODE);
        rs.put(MSG, msg);
        return rs;
    }

}
