package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CashListData extends BaseData {
    @SerializedName("data")
    public  Res res;
    public class Res implements Serializable {
        @SerializedName("lists")
        public List<CashList> blackLists;

        public List<CashList> getBlackLists() {
            return blackLists;
        }

        public void setBlackLists(List<CashList> blackLists) {
            this.blackLists = blackLists;
        }

        public class CashList implements Serializable {
            public String money;
            public String status;
            public String create_time;

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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
