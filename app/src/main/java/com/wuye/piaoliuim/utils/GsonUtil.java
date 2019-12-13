package com.wuye.piaoliuim.utils;

import com.google.gson.Gson;

/**
 * @ClassName GsonUtil
 * @Description
 * @Author VillageChief
 * @Date 2019/12/6 17:50
 */
public class GsonUtil {
    public static Gson defaultGson;

    public static Gson getDefaultGson() {
        if (defaultGson == null) {
            defaultGson = new Gson();
        }
        return defaultGson;
    }
}
