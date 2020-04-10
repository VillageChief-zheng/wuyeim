package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IsAddPicBean extends BaseData {
    @SerializedName("data")
    public  Res res;
    public class Res implements Serializable {
        public String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
