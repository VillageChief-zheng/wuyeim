package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LiulanData  extends BaseData {
    @SerializedName("data")
    public  Res res;
    public class Res implements Serializable {
        @SerializedName("lists")
        public List<JIluList> listList;

        public List<JIluList> getListList() {
            return listList;
        }

        public void setListList(List<JIluList> listList) {
            this.listList = listList;
        }

        public class JIluList implements Serializable {
            public String brow_id;
            public String litpic;
            public String name;
            public String gender;
            public String create_time;

            public String getBrow_id() {
                return brow_id;
            }

            public void setBrow_id(String brow_id) {
                this.brow_id = brow_id;
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

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
