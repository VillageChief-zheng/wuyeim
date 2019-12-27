package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName FindData
 * @Description
 * @Author VillageChief
 * @Date 2019/12/23 11:19
 */
public class FindData extends BaseData {
    @SerializedName("data")
    public  Res res;
    public class Res implements Serializable {
        @SerializedName("lists")
        public List<FIndList> publicLists;

        public List<FIndList> getPublicLists() {
            return publicLists;
        }

        public void setPublicLists(List<FIndList> publicLists) {
            this.publicLists = publicLists;
        }

        public class FIndList implements Serializable {
            public String user_id;
            public String total_rece_gold;
            public String send_gold;
            public String fans;
            public String litpic;
            public String name;
            public String gender;
            public String online;
            public String signature;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getTotal_rece_gold() {
                return total_rece_gold;
            }

            public void setTotal_rece_gold(String total_rece_gold) {
                this.total_rece_gold = total_rece_gold;
            }

            public String getSend_gold() {
                return send_gold;
            }

            public void setSend_gold(String send_gold) {
                this.send_gold = send_gold;
            }

            public String getFans() {
                return fans;
            }

            public void setFans(String fans) {
                this.fans = fans;
            }

            public String getLitpic() {
                return litpic;
            }

            public void setLitpic(String litpic) {
                this.litpic = litpic;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getOnline() {
                return online;
            }

            public void setOnline(String online) {
                this.online = online;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }
        }
        }
}
