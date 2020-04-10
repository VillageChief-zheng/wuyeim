package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CashUserData  extends BaseData {
    @SerializedName("data")
    public Res  res;
    public class Res implements Serializable {
        @SerializedName("lists")
        CashList listList;

        public  CashList getListList() {
            return listList;
        }

        public void setListList( CashList  listList) {
            this.listList = listList;
        }

        public class CashList implements Serializable{
            public  String unionid;
            public  String phone;
            public  String cash_openid;
            public  String real_name;
            public  String idcard;

            public String getUnionid() {
                return unionid;
            }

            public void setUnionid(String unionid) {
                this.unionid = unionid;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getCash_openid() {
                return cash_openid;
            }

            public void setCash_openid(String cash_openid) {
                this.cash_openid = cash_openid;
            }

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }

            public String getIdcard() {
                return idcard;
            }

            public void setIdcard(String idcard) {
                this.idcard = idcard;
            }
        }

    }
}
