package com.wuye.piaoliuim.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.chuange.basemodule.utils.LogUtils;
import com.chuange.basemodule.utils.ShareParamUtils;
import com.google.gson.Gson;
import com.wuye.piaoliuim.WuyeApplicatione;
import com.wuye.piaoliuim.bean.TokenUserInfo;
import com.wuye.piaoliuim.bean.UserData;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.UrlConstant;

/**
 * @ClassName AppSessionEngine
 * @Description
 * @Author VillageChief
 * @Date 2019/12/6 17:38
 */
public class AppSessionEngine {


    private static Gson defaultGson;

    static String getString(String SPKey) {
        SharedPreferences sp = ShareParamUtils.getSharedPreferences(WuyeApplicatione.etdApplication);
        String result = sp.getString(SPKey, null);
        return result;
    }

    static void setString(String param, String SPKey) {
        ShareParamUtils.getEdit(WuyeApplicatione.etdApplication).putString(SPKey, param)
                .apply();
    }

    public static UserData getUserInfo() {
        String userInfo = getString(UrlConstant.USER);
        if (userInfo == null) {
            return null;
        }
        try {
            return getDefaultGson().fromJson(userInfo, UserData.class);
        } catch (Exception e) {
            return null;
        }
    }


    public static void setUserInfo(UserData userInfo) {
        if (userInfo == null) return;
        try {
            String json = getDefaultGson().toJson(userInfo);
            if (!TextUtils.isEmpty(json)) {
                setString(json, UrlConstant.USER);
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }
    public static void setTokenUserInfo(TokenUserInfo userInfo) {
        if (userInfo == null) return;
        try {
            String json = getDefaultGson().toJson(userInfo);
            if (!TextUtils.isEmpty(json)) {
                setString(json, UrlConstant.USERTOKEN);
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }
    public static TokenUserInfo getUserTokenInfo() {
        String userInfo = getString(UrlConstant.USERTOKEN);
        if (userInfo == null) {
            return null;
        }
        try {
            return getDefaultGson().fromJson(userInfo, TokenUserInfo.class);
        } catch (Exception e) {
            return null;
        }
    }
    public static String getToken() {
        TokenUserInfo tokenUserInfo = AppSessionEngine.getUserTokenInfo();
        if (tokenUserInfo == null || tokenUserInfo.getToken() == null) {
            return null;
        }
        return  tokenUserInfo.getToken() + "";
    }
    public static String getUseId() {
        UserData userInfo = AppSessionEngine.getUserInfo();
        if (userInfo == null || userInfo.res == null || userInfo.res.user.getUserId() == null) {
            return null;
        }
        return  userInfo.res.user.getUserId()  + "";
    }

//
//    public static String getSessionId() {
//        UserData userData = getUserInfo();
//        return (null != userData && !TextUtils.isEmpty(userData.res.sessionId)) ? userData.res.sessionId : null;
//    }

    public static String getMobile() {
        UserData userData = getUserInfo();
        return (null != userData && !TextUtils.isEmpty(userData.res.user.getTelephone())) ? userData.res.user.getTelephone() : null;
    }

    public static String getUserLoginName(){
        UserData userData = getUserInfo();
        return (null != userData && !TextUtils.isEmpty(userData.res.user.getUserName())) ? userData.res.user.getUserName() : null;
    }

    public static String getUserName(){
        UserData userData = getUserInfo();
        return (null != userData && !TextUtils.isEmpty(userData.res.user.getUserName())) ? userData.res.user.getUserName() : null;
    }

    public static void clear() {
        ShareParamUtils.getSharedPreferences(WuyeApplicatione.etdApplication).edit().clear().apply();
    }

    public static Gson getDefaultGson() {
        if (defaultGson == null) {
            defaultGson = new Gson();
        }
        return defaultGson;
    }
    public static void setUserInfo(UserInfoData userInfo) {
        if (userInfo == null) return;
        try {
            String json = getDefaultGson().toJson(userInfo);
            if (!TextUtils.isEmpty(json)) {
                setString(json, UrlConstant.USERINFOS);
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }
    public static UserInfoData getMyUserInfo() {
        String userInfo = getString(UrlConstant.USERINFOS);
        if (userInfo == null) {
            return null;
        }
        try {
            return getDefaultGson().fromJson(userInfo, UserInfoData.class);
        } catch (Exception e) {
            return null;
        }
    }
    public static String getPhone() {
        UserInfoData tokenUserInfo = AppSessionEngine.getMyUserInfo();
        if (tokenUserInfo==null){
            return "";
        }else {
            return (null != tokenUserInfo && !TextUtils.isEmpty(tokenUserInfo.res.getListList().getPhone())) ? tokenUserInfo.res.getListList().getPhone() : null;

        }

    }public static void setPhone(String phone) {
        UserInfoData userInfoData=getMyUserInfo();
         userInfoData.res.getListList().setPhone(phone);
         setUserInfo(userInfoData);
    }
    public static void setLocation(String date) {
        if (date == null) return;
                 setString(date, UrlConstant.LOCATIONE);
            }
    public static String getLocation() {
         String bb=getString(UrlConstant.LOCATIONE);
         if (bb==null||bb.equals("")){
             return "";
         }
         return bb;
     }
    public static void setIm(String im) {
         setString(im,UrlConstant.TENCENIM);
    }
    public static String getIm() {
      String name=   getString(UrlConstant.TENCENIM);
       return name;
    }
}
