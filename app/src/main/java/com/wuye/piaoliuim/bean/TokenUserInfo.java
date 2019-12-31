package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TokenUserInfo extends BaseData  {
    @SerializedName("token")
    public String token;
    @SerializedName("is_binding")
    public String is_binding;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIs_binding() {
        return is_binding;
    }

    public void setIs_binding(String is_binding) {
        this.is_binding = is_binding;
    }
}
