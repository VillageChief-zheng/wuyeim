package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IsAttenData extends BaseData {
    @SerializedName("data")
    public  Res res;

    public class Res implements Serializable {
    private String is_atte;

        public String getIs_atte() {
            return is_atte;
        }

        public void setIs_atte(String is_atte) {
            this.is_atte = is_atte;
        }
    }
    }
