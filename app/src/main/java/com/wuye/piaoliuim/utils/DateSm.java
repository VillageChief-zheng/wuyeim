package com.wuye.piaoliuim.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DateSm
 * @Description
 * @Author VillageChief
 * @Date 2020/1/6 10:35
 */
public class DateSm {
    public static String toMMdd(String dateStr,String type)  {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");//需要转化成的时间格式
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date1 = null;
        try {
            date1 = df1.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str1 = df1.format(date1);
        String str2 = df2.format(date1);
        if (type.equals("1"))
            return str1;
        else
            return str2;

    }
    /**
     * Date转String
     *
     * @return
     */
    public static String getDateAndMin(  Long time1,String type) {
        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time1 * 1000));
        String s_Data = result1.substring(5, 10); // 年份
        String s_Minn = result1.substring(10, result1.length()); // 年份
        if (type.equals("1")){
            return  s_Data;

        }else {
            return s_Minn;
        }
    }
}
