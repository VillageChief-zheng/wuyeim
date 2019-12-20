package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName GlodData
 * @Description
 * @Author VillageChief
 * @Date 2019/12/17 15:18
 */
public class GlodData  extends BaseData {
    @SerializedName("data")
    public  Res res;
    public class Res implements Serializable {
        @SerializedName("lists")
        public List<GlodList> listList;

        public List<GlodList> getListList() {
            return listList;
        }

        public void setListList(List<GlodList> listList) {
            this.listList = listList;
        }

        public class GlodList implements Serializable {
            public  String gold;
            public  String type;
            public  String create_time;

            public String getGold() {
                return gold;
            }

            public void setGold(String gold) {
                this.gold = gold;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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
