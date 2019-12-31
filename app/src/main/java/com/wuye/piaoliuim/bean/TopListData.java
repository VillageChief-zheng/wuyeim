package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName TopListData
 * @Description
 * @Author VillageChief
 * @Date 2019/12/30 17:09
 */
public class TopListData extends BaseData {
    @SerializedName("data")
    public  Res res;
    public class Res implements Serializable {
        @SerializedName("lists")
        List<TopList> puList;

        public List<TopList> getPuList() {
            return puList;
        }

        public void setPuList(List<TopList> puList) {
            this.puList = puList;
        }

        public  class TopList implements Serializable{
            public int id;
            public String money;
            public String gold;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getGold() {
                return gold;
            }

            public void setGold(String gold) {
                this.gold = gold;
            }
        }
    }
}
