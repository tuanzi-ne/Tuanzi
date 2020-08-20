package com.spring.common.vaptcha.domain;

public class HttpResp {
    private String Resp;
    private int Code;

    @Override
    public String toString() {
        return "HttpResp{" +
                "Resp='" + Resp + '\'' +
                ", Code=" + Code +
                '}';
    }

    public HttpResp(String resp, int code) {
        Resp = resp;
        Code = code;
    }

    public String getResp() {
        return Resp;
    }

    public void setResp(String resp) {
        Resp = resp;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
