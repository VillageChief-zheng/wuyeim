package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MoneyData extends BaseData {
    @SerializedName("data")
    public Res  res;
    public class Res implements Serializable {
        @SerializedName("lists")
         MoneyInfo listList;

        public  MoneyInfo getListList() {
            return listList;
        }

        public void setListList( MoneyInfo listList) {
            this.listList = listList;
        }

        public class MoneyInfo implements Serializable{
            public String diam_gold;
            public String rece_gold;
             public String user_gold;

            public String getDiam_gold() {
                return diam_gold;
            }

            public void setDiam_gold(String diam_gold) {
                this.diam_gold = diam_gold;
            }

            public String getRece_gold() {
                return rece_gold;
            }

            public void setRece_gold(String rece_gold) {
                this.rece_gold = rece_gold;
            }

            public String getUser_gold() {
                return user_gold;
            }

            public void setUser_gold(String user_gold) {
                this.user_gold = user_gold;
            }
        }
     }
}
