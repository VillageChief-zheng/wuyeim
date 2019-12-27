package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PiaoliuData
 * @Description
 * @Author VillageChief
 * @Date 2019/12/23 13:38
 */
public class PiaoliuData extends BaseData {
    @SerializedName("data")
    public  Res res;
    public class Res implements Serializable {
        @SerializedName("lists")
        public List<PiaoliuList> listList;

        public List<PiaoliuList> getListList() {
            return listList;
        }

        public void setListList(List<PiaoliuList> listList) {
            this.listList = listList;
        }

        public class PiaoliuList implements Serializable {
       private String user_id;
       private String type;
       private String content;
       private String litpic;
       private String name;
       private String gender;
       private String online;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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
        }
        }
}
