package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName GongPingData
 * @Description
 * @Author VillageChief
 * @Date 2020/1/16 14:45
 */
public class GongPingData    extends BaseData {
    @SerializedName("data")
    public  Res res;
    public class Res implements Serializable {
        @SerializedName("lists")
        public List<Gpist> gpistList;

        public List<Gpist> getGpistList() {
            return gpistList;
        }

        public void setGpistList(List<Gpist> gpistList) {
            this.gpistList = gpistList;
        }

        public class  Gpist  implements Serializable {
        private String id;
        private String user_id;
        private String give_id;
        private String gid;
        private String num;
        private String name;
        private String cname;
        private String litpic;

            public String getLitpic() {
                return litpic;
            }

            public void setLitpic(String litpic) {
                this.litpic = litpic;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCname() {
                return cname;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }
        }
    }
}
