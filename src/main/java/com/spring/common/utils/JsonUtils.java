package com.spring.common.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonUtils {
    public static void outputJson(Object obj, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().print(JSON.toJSONString(obj));
    }
}
