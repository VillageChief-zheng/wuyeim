package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VersionData extends BaseData {
    @SerializedName("data")
    public  UpList res;

        public  class UpList implements Serializable {

        private String android_version;
        private String android_download_msg;
        private String android_download_url;
        private String android_isdownload;
        private String create_time;

            public String getAndroid_version() {
                return android_version;
            }

            public void setAndroid_version(String android_version) {
                this.android_version = android_version;
            }

            public String getAndroid_download_msg() {
                return android_download_msg;
            }

            public void setAndroid_download_msg(String android_download_msg) {
                this.android_download_msg = android_download_msg;
            }

            public String getAndroid_download_url() {
                return android_download_url;
            }

            public void setAndroid_download_url(String android_download_url) {
                this.android_download_url = android_download_url;
            }

            public String getAndroid_isdownload() {
                return android_isdownload;
            }

            public void setAndroid_isdownload(String android_isdownload) {
                this.android_isdownload = android_isdownload;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }

}
