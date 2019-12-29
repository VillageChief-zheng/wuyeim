package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName UserInfoData
 * @Description
 * @Author VillageChief
 * @Date 2019/12/18 14:08
 */
public class UserInfoData extends BaseData {
    @SerializedName("data")
    public  Res res;
    public class Res implements Serializable {
        @SerializedName("lists")
        public  UserInfoList  listList;

        public UserInfoList getListList() {
            return listList;
        }

        public void setListList(UserInfoList listList) {
            this.listList = listList;
        }

        public class UserInfoList implements Serializable {
            public String id;
            public String name;//用户昵称
            public String litpic;//用户头像
            public String user_imgs;//用户相册 多个以","分割
            public String signature;//用户相册 多个以","分割
            public String gender;//用户相册 多个以","分割
            public String age;//用户相册 多个以","分割
            public String fans;//用户相册 多个以","分割
            public String follows;//用户相册 多个以","分割
            @SerializedName("is_follow")
            public String is_follow ;//用户相册 多个以","分割
            public String user_gold ;//用户相册 多个以","分割

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLitpic() {
                return litpic;
            }

            public void setLitpic(String litpic) {
                this.litpic = litpic;
            }

            public String getUser_imgs() {
                return user_imgs;
            }

            public void setUser_imgs(String user_imgs) {
                this.user_imgs = user_imgs;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getFans() {
                return fans;
            }

            public void setFans(String fans) {
                this.fans = fans;
            }

            public String getFollows() {
                return follows;
            }

            public void setFollows(String follows) {
                this.follows = follows;
            }

            public String getIs_follow() {
                return is_follow;
            }

            public void setIs_follow(String is_follow) {
                this.is_follow = is_follow;
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
