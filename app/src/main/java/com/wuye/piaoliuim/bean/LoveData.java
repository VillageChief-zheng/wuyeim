package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName LoveData
 * @Description
 * @Author VillageChief
 * @Date 2019/12/17 13:27
 */
public class LoveData  extends BaseData {
    @SerializedName("data")
    public Res  res;
    public class Res implements Serializable {
        @SerializedName("lists")
        public List<LoveList> listList;

        public List<LoveList> getListList() {
            return listList;
        }

        public void setListList(List<LoveList> listList) {
            this.listList = listList;
        }

        public class LoveList implements Serializable {
            public String pass_id;
            public String litpic;
            public String name;

            public String getPass_id() {
                return pass_id;
            }

            public void setPass_id(String pass_id) {
                this.pass_id = pass_id;
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
