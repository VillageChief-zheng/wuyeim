package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @ClassName SysDatas
 * @Description
 * @Author VillageChief
 * @Date 2020/1/9 15:45
 */
public class SysDatas extends BaseData {
    @SerializedName("data")
    public Res res;
    public class Res implements Serializable{
     public String is_true;

        public String getIs_true() {
            return is_true;
        }

        public void setIs_true(String is_true) {
            this.is_true = is_true;
        }
    }
}
