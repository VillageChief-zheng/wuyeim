package com.wuye.piaoliuim.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName TelNumMatch
 * @Description
 * @Author VillageChief
 * @Date 2019/12/30 16:59
 */
public class TelNumMatch {
    /*
     * 10. * 移动: 2G号段(GSM网络)有139,138,137,136,135,134,159,158,152,151,150, 11. *
     * 3G号段(TD-SCDMA网络)有157,182,183,188,187,181 147是移动TD上网卡专用号段. 联通: 12. *
     * 2G号段(GSM网络)有130,131,132,155,156 3G号段(WCDMA网络)有186,185 电信: 13. *
     * 2G号段(CDMA网络)有133,153 3G号段(CDMA网络)有189,180 14.
     */
    static String YD = "^[1]{1}(([3]{1}[4-9]{1})|([5]{1}[012789]{1})|([8]{1}[12378]{1})|([4]{1}[7]{1}))[0-9]{8}$";
    static String LT = "^[1]{1}(([3]{1}[0-2]{1})|([5]{1}[56]{1})|([8]{1}[56]{1}))[0-9]{8}$";
    static String DX = "^[1]{1}(([3]{1}[3]{1})|([5]{1}[3]{1})|([8]{1}[09]{1}))[0-9]{8}$";
    /**
     * 中国电信号码格式验证 手机段： 133,149,153,173,177,180,181,189,199,1349,1410,1700,1701,1702
     **/
    private static final String CHINA_TELECOM_PATTERN = "(?:^(?:\\+86)?1(?:33|49|53|7[37]|8[019]|99)\\d{8}$)|(?:^(?:\\+86)?1349\\d{7}$)|(?:^(?:\\+86)?1410\\d{7}$)|(?:^(?:\\+86)?170[0-2]\\d{7}$)";

    /**
     * 中国联通号码格式验证 手机段：130,131,132,145,146,155,156,166,171,175,176,185,186,1704,1707,1708,1709
     **/
    private static final String CHINA_UNICOM_PATTERN = "(?:^(?:\\+86)?1(?:3[0-2]|4[56]|5[56]|66|7[156]|8[56])\\d{8}$)|(?:^(?:\\+86)?170[47-9]\\d{7}$)";

    /**
     * 中国移动号码格式验证
     * 手机段：134,135,136,137,138,139,147,148,150,151,152,157,158,159,178,182,183,184,187,188,198,1440,1703,1705,1706
     **/
    private static final String CHINA_MOBILE_PATTERN = "(?:^(?:\\+86)?1(?:3[4-9]|4[78]|5[0-27-9]|78|8[2-478]|98)\\d{8}$)|(?:^(?:\\+86)?1440\\d{7}$)|(?:^(?:\\+86)?170[356]\\d{7}$)";

    String mobPhnNum;

    public TelNumMatch(String mobPhnNum) {
        this.mobPhnNum = mobPhnNum;
     }

    public int matchNum() {
        /**
         * 28. * flag = 1 YD 2 LT 3 DX 29.
         */
        int flag;//存储匹配结果
        // 判断手机号码是否是11位
        if (mobPhnNum.length() == 11) {
            // 判断手机号码是否符合中国移动的号码规则
            if (mobPhnNum.matches(YD)) {
                flag = 1;
            }
            // 判断手机号码是否符合中国联通的号码规则
            else if (mobPhnNum.matches(LT)) {
                flag = 2;
            }
            // 判断手机号码是否符合中国电信的号码规则
            else if (mobPhnNum.matches(DX)) {
                flag = 3;
            }
            // 都不适合，未知֪
            else {
                flag = 4;
            }
        }
        // 不是11位
        else {
            flag = 5;
        }
         return flag;
    }

    //手机号码的有效性验证
    public static boolean checkPhone(String number)
    {
        boolean flag=false;
        if(number.length()==11 && (number.matches(YD)||number.matches(LT)||number.matches(DX)))
        {
            flag=true;
        }
        return flag;
    }

    //判断手机号码是否存在
    public static boolean isExistPhoneNumber(String number)
    {
        return false;
    }

    //判断email地址是否有效
    public static boolean isEmail(String email)
    {
        String patternString="^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        return isMatcher(patternString,email);
    }

    //是否是数字和字母
    public static boolean isMatchCharOrNumber(String str)
    {
        String patternString="^[\\d|a-z|A-Z]+$";
        return isMatcher(patternString,str);
    }

    //是否匹配
    public static boolean isMatcher(String patternString,String str)
    {
        boolean isValid=false;
        CharSequence inputStr =str ;
        Pattern pattern =Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher=pattern.matcher(inputStr);
        if(matcher.matches())
        {
            isValid =true;
        }
        return isValid;
    }
    /**
     * 中国大陆手机号码校验
     *
     * @param phone
     * @return
     */
    public static boolean isValidPhoneNumber(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            if (checkChinaMobile(phone) || checkChinaUnicom(phone) || checkChinaTelecom(phone)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 中国移动手机号码校验
     *
     * @param phone
     * @return
     */
    public static boolean checkChinaMobile(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            Pattern regexp = Pattern.compile(CHINA_MOBILE_PATTERN);
            if (regexp.matcher(phone).matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 中国联通手机号码校验
     *
     * @param phone
     * @return
     */
    public static boolean checkChinaUnicom(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            Pattern regexp = Pattern.compile(CHINA_UNICOM_PATTERN);
            if (regexp.matcher(phone).matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 中国电信手机号码校验
     *
     * @param phone
     * @return
     */
    public static boolean checkChinaTelecom(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            Pattern regexp = Pattern.compile(CHINA_TELECOM_PATTERN);
            if (regexp.matcher(phone).matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏手机号中间四位
     *
     * @param phone
     * @return java.lang.String
     */
    public static String hideMiddleMobile(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return phone;
    }

}
