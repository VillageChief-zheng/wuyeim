package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName BlackData
 * @Description
 * @Author VillageChief
 * @Date 2019/12/16 9:57
 */
public class BlackData extends BaseData {
    @SerializedName("data")
    public Res  res;
public class Res implements Serializable{
    @SerializedName("lists")
    public List<BlackList> blackLists;

    public List<BlackList> getBlackLists() {
        return blackLists;
    }

    public void setBlackLists(List<BlackList> blackLists) {
        this.blackLists = blackLists;
    }

    public class BlackList implements Serializable{
    public String blick_id;
    public String litpic;
    public String name;

        public String getBlick_id() {
            return blick_id;
        }

        public void setBlick_id(String blick_id) {
            this.blick_id = blick_id;
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
