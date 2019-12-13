package com.wuye.piaoliuim.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.chuange.basemodule.utils.LogUtils;
import com.chuange.basemodule.utils.ShareParamUtils;
import com.google.gson.Gson;
import com.wuye.piaoliuim.WuyeApplicatione;
import com.wuye.piaoliuim.bean.UserData;
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
}
