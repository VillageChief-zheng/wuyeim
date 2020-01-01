package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SysData extends BaseData {
    @SerializedName("data")
    public Res  res;
    public class Res implements Serializable {
        @SerializedName("lists")
        public List<SysList> listList;

        public List<SysList> getListList() {
            return listList;
        }

        public void setListList(List<SysList> listList) {
            this.listList = listList;
        }

        public class SysList implements Serializable {
        private String title;
        private String content;
        private String create_time;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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
