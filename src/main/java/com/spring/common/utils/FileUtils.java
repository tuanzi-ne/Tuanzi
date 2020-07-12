package com.spring.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Title: FileUtils
 * @Description: 文件工具类
 * @author  团子
 * @date 2018/9/2 21:01
 * @since v1.0
 */
public class FileUtils {
    /**
     * 时间戳+随机数，避免重复
     * @param num
     * @return
     */
    public static String genFileSuffix(int num) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        // 转换为字符串
        String formatDate = format.format(new Date());
        // 随机生成文件编号
        StringBuffer randomCode = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            randomCode.append(Integer.toString(random.nextInt(1000), 36));
        }
        return formatDate + randomCode.toString();
    }

}
