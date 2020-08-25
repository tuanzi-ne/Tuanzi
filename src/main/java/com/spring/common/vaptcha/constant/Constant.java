package com.spring.common.vaptcha.constant;

public class Constant {
    public static final String Char = "0123456789abcdef";
    public static final String ChannelUrl = "https://channel2.vaptcha.com/";
    public static final String ValidateUrl = "https://offline.vaptcha.com/";
    public static final String VerifyUrl = "https://0.vaptcha.com/verify";
    // 手势验证
    public static final String ValidateSuccess = "0103";
    public static final String ValidateFail = "0104";
    // 二次验证
    public static final int VerifySuccess = 1;
    public static final int VerifyFail = 0;
    public static final String ActionGet = "get";
    public static final String OfflineMode = "offline";
    //-----------验证单元相关信息 请自行替换----------------
    // 验证单元key
    public static final String SecretKey = "验证单元key";
    // 验证单元id
    public static final String Vid = "验证单元id";
    // 场景值
    public static final String Scene = "1";
}
