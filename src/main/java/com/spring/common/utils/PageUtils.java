package com.spring.common.utils;

import com.github.pagehelper.PageHelper;
import com.spring.common.xss.SQLFilter;

import java.util.Map;

/**
 * 分页工具类
 *
 * @author  团子
 * @date 2018/3/2 15:44
 * @since V1.0
 */
public class PageUtils {

    public static void initPaging(Map<String, Object> params) {
        //分页参数
        Object pageNumObj = params.get("pageNumber");
        Object pageSizeObj = params.get("pageSize");
        Object sortNameObj = params.get("sortName");
        Object sortOrderObj = params.get("sortOrder");
        //默认分页数为1
        int pageNum = pageNumObj != null ? Integer.parseInt(pageNumObj.toString()) : 1;
        //默认每页大小为10
        int pageSize = pageSizeObj != null ? Integer.parseInt(pageSizeObj.toString()) : 10;
        String orderBy = "";
        if (sortNameObj != null && sortOrderObj != null) {
            orderBy = sortNameObj.toString() + " " + sortOrderObj.toString();
        }
        PageHelper.startPage(pageNum, pageSize, SQLFilter.filterInject(orderBy));
    }

    public static Map<String, Object> filterParams(Map<String, Object> params) {
        Object sortNameObj = params.get("sortName");
        Object sortOrderObj = params.get("sortOrder");
        String orderBy = "";
        if (sortNameObj != null && sortOrderObj != null) {
            orderBy = sortNameObj.toString() + " " + sortOrderObj.toString();
        }
        params.put("orderBy",SQLFilter.filterInject(orderBy));
        return params;
    }


}
