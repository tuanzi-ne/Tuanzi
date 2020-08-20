package com.spring.common.vaptcha.domain;

public class SecondVerify {
    private int success;
    private String msg;
    private int score;

    @Override
    public String toString() {
        return "SecondVerifyResp{" +
                "code=" + success +
                ", msg='" + msg + '\'' +
                ", score=" + score +
                '}';
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public SecondVerify(int success, String msg, int score) {
        this.success = success;
        this.msg = msg;
        this.score = score;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SecondVerify() {
    }

    public SecondVerify(int success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
