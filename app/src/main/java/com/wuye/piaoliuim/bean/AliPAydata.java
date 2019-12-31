package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @ClassName AliPAydata
 * @Description
 * @Author VillageChief
 * @Date 2019/12/31 10:02
 */
public class AliPAydata  extends BaseData{
        @SerializedName("data")
        public  String aliPayData;

    public String getAliPayData() {
        return aliPayData;
    }

    public void setAliPayData(String aliPayData) {
        this.aliPayData = aliPayData;
    }
}
