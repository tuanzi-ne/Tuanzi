package com.spring.common.vaptcha.utils;

import com.spring.common.vaptcha.constant.Constant;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;
import java.util.UUID;

public class Common {
    /**
     * 获取4位随机字符串
     */
    public static String GetRandomStr() {
        Random r = new Random();
        String[] split = Constant.Char.split("");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String s = split[r.nextInt(16)];
            sb.append(s);
        }
        return sb.toString();
    }

    public static String MD5Encode(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String GetUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    public static String StrAppend(String[] s) {
        StringBuilder sb = new StringBuilder();
        for (String value : s) {
            sb.append(value);
        }
        return sb.toString();
    }

    // 获取11位时间戳
    public static long GetTimeStamp() {
        return Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0))).toInstant().
                getEpochSecond();
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     */
    public static String GetIpAddress(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String s : ips) {
                if (!("unknown".equalsIgnoreCase(s))) {
                    ip = s;
                    break;
                }
            }
        }
        return ip;
    }
}
