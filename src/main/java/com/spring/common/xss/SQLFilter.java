package com.spring.common.xss;

import com.spring.common.exception.SysException;
import org.apache.commons.lang.StringUtils;


/**
 * SQL注入过滤
 *
 * @author  团子
 * @date 2018/3/2 15:54
 * @since V1.0
 */
public class SQLFilter {

    /**
     * SQL注入过滤
     *
     * @param sql 待验证字符串
     */
    public static String filterInject(String sql) {
        if (StringUtils.isBlank(sql)) {
            return null;
        }
        //去掉'|"|;|\字符
        sql = sql.replace("'", "")
                .replace("\"", "")
                .replace(";", "")
                .replace("\\", "")
                .toLowerCase();
        //非法字符
        final String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop"};

        //判断是否包含非法字符
        for (String keyword : keywords) {
            if (sql.indexOf(keyword) > 0) {
                throw new SysException("SQL包含非法字符");
            }
        }
        return sql;
    }
}
