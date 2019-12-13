package com.wuye.piaoliuim.config;

/**
 * @ClassName AppConfig
 * @Description
 * @Author VillageChief
 * @Date 2019/12/6 17:32
 */
public class AppConfig {
         private boolean isDebug;
        private boolean isHttps;
        public String VersionName;
        public String BASEURL;
        public String SocketAddress;
        public String SocketConsultAddress;
        public String RTMPPREFIXION;
        public String ConsultUrl;//人工咨询
        public String EstimateUrl;//易判接口
        public String DXAL;//案例检索
        public String FLFG;//法规检索

        public boolean isHttps() {
            return isHttps;
        }

        public void setHttps(boolean https) {
            isHttps = https;
        }

        public boolean isDebug() {
            return isDebug;
        }

        public void setIsDebug(boolean isDebug) {
            this.isDebug = isDebug;
        }
}
