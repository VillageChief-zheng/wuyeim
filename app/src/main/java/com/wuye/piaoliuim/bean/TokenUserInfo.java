package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TokenUserInfo extends BaseData  {
    @SerializedName("token")
    public String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
