package com.spring.common.vaptcha.domain;

public class VerifyResp {
    private String code;
    private String msg;
    private String token;

    @Override
    public String toString() {
        return "VerifyResp{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public VerifyResp() {
    }

    public VerifyResp(String code, String msg, String token) {
        this.code = code;
        this.msg = msg;
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
