package com.spring.common.vaptcha.domain;

import com.google.gson.annotations.SerializedName;

public class GetResp {
    private String api;
    private int state;
    @SerializedName("offline_state")
    private int offlineState;
    @SerializedName("offline_key")
    private String offlineKey;

    @Override
    public String toString() {
        return "GetResp{" +
                "api='" + api + '\'' +
                ", state=" + state +
                ", offlineState=" + offlineState +
                ", offlineKey='" + offlineKey + '\'' +
                '}';
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOfflineState() {
        return offlineState;
    }

    public void setOfflineState(int offlineState) {
        this.offlineState = offlineState;
    }

    public String getOfflineKey() {
        return offlineKey;
    }

    public void setOfflineKey(String offlineKey) {
        this.offlineKey = offlineKey;
    }

    public GetResp(String api, int state, int offlineState, String offlineKey) {
        this.api = api;
        this.state = state;
        this.offlineState = offlineState;
        this.offlineKey = offlineKey;
    }

    public GetResp() {
    }
}
