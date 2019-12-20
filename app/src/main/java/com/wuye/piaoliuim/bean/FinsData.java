package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName FinsData
 * @Description
 * @Author VillageChief
 * @Date 2019/12/17 10:44
 */
public class FinsData  extends BaseData {
    @SerializedName("data")
    public Res  res;
    public class Res implements Serializable {
        @SerializedName("lists")
        public List<FinsList> listList;

        public List<FinsList> getListList() {
            return listList;
        }

        public void setListList(List<FinsList> listList) {
            this.listList = listList;
        }

        public class FinsList implements Serializable {
     public  String user_id;
     public  String is_follow;
     public  String litpic;
     public  String name;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getIs_follow() {
                return is_follow;
            }

            public void setIs_follow(String is_follow) {
                this.is_follow = is_follow;
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
        }
        }
}
