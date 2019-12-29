package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName LiwuData
 * @Description
 * @Author VillageChief
 * @Date 2019/12/17 9:35
 */
public class LiwuData extends BaseData {
    @SerializedName("data")
    public Res  res;
    public class Res implements Serializable {
        @SerializedName("lists")
        public List<LiwuList> listList;

        public List<LiwuList> getListList() {
            return listList;
        }

        public void setListList(List<LiwuList> listList) {
            this.listList = listList;
        }

        public class LiwuList implements Serializable {
         public String user_id;
         public String give_id;
         public String gid;
         public String num;
         public String litpic;
         public String name;
         public String gender;
         public String g_litpic;
         public String create_time;

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

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
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

            public String getG_litpic() {
                return g_litpic;
            }

            public void setG_litpic(String g_litpic) {
                this.g_litpic = g_litpic;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
        }
}
