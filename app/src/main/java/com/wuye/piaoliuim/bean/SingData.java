package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

/**
 * @ClassName SingData
 * @Description
 * @Author VillageChief
 * @Date 2020/1/2 11:13
 */
public class SingData extends BaseData {
    @SerializedName("data")
    public Res res;
   public class Res{
       @SerializedName("usersig")
       public String usersig;

       public String getUsersig() {
           return usersig;
       }

       public void setUsersig(String usersig) {
           this.usersig = usersig;
       }
   }

}
