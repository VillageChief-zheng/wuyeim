package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName LiwuListData
 * @Description
 * @Author VillageChief
 * @Date 2020/1/16 9:14
 */
public class LiwuListData extends BaseData {
    @SerializedName("data")
    public  Res res;
    public class Res implements Serializable {
        @SerializedName("lists")
        List<LiwuLiestData> liwudaList;

        public List<LiwuLiestData> getLiwudaList() {
            return liwudaList;
        }

        public void setLiwudaList(List<LiwuLiestData> liwudaList) {
            this.liwudaList = liwudaList;
        }

        public class LiwuLiestData{
            public String  id;
            public String   litpic;
            public String      name;
            public String    gold;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getGold() {
                return gold;
            }

            public void setGold(String gold) {
                this.gold = gold;
            }
        }


    }
}
