package com.wuye.piaoliuim.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

public class PhoneUtile {
    public static String getModel() {
        //shouji xinghap
        return android.os.Build.MODEL;
    }
    public static String getDeviceBrand() {
        //厂商
        return android.os.Build.BRAND;
    }
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }
}
