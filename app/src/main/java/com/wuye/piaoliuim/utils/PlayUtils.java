package com.wuye.piaoliuim.utils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @ClassName PlayUtils
 * @Description
 * @Author VillageChief
 * @Date 2019/12/26 16:35
 */
public class PlayUtils  {
    public static String getAllTime(int allTime) {
        SimpleDateFormat sdf;
         if (allTime <= 0) {
            allTime = 0;
        }
        int hour = allTime / (3600 * 1000);
        if (hour < 0 || hour == 0) {
            sdf = new SimpleDateFormat("mm:ss"); // 转换时间
        } else {
            sdf = new SimpleDateFormat("HH:mm:ss"); // 转换时间
        }
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+00:00")); // 设置中国时区
        String result = sdf.format(allTime); // 将时分秒转换成String类型
        return result;
    }
 }
