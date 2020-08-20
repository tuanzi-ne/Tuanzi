package com.spring.common.vaptcha.domain;

public class Offline {
    private String knock;
    private String callback;
    private String offline_action;
    private String vid;
    private String v;

    @Override
    public String toString() {
        return "Offline{" +
                "knock='" + knock + '\'' +
                ", callback='" + callback + '\'' +
                ", offlineAction='" + offline_action + '\'' +
                ", vid='" + vid + '\'' +
                ", v='" + v + '\'' +
                '}';
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getKnock() {
        return knock;
    }

    public void setKnock(String knock) {
        this.knock = knock;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getOffline_action() {
        return offline_action;
    }

    public void setOffline_action(String offline_action) {
        this.offline_action = offline_action;
    }
}
