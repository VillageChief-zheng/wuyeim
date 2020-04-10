package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyMlListData extends BaseData {
    @SerializedName("data")
    public  Res res;
    public class Res implements Serializable {
        @SerializedName("lists")
        public List<MlListInfo> listList;

        public List<MlListInfo> getListList() {
            return listList;
        }

        public void setListList(List<MlListInfo> listList) {
            this.listList = listList;
        }

        public class MlListInfo{
        private String user_id;
        private String give_id;
        private String gold;
        private String litpic;
        private String name;
        public String gender;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getGive_id() {
            return give_id;
        }

        public void setGive_id(String give_id) {
            this.give_id = give_id;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
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
    }

    }
}
