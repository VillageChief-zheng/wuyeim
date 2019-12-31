package com.wuye.piaoliuim.config;

import android.os.Environment;

import com.wuye.piaoliuim.BuildConfig;

import java.io.File;

/**
 * @ClassName Constants
 * @Description
 * @Author VillageChief
 * @Date 2019/12/6 17:33
 */
public class Constants {

    /**
     * ****************************************** 参数设置信息开始 ******************************************
     */

    public static final String areaFilePath = "/area_config.json";
    public static final String caseTypeFilePath = "/casetype_config.json";

    // SDCard路径
    //    public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "com.bmsoft.odr.mobile" + File.separator;


    public static final String SD_PATH_RESODER = SD_PATH  + "recorder_audios" + File.separator;

    // 签名图片存储路径
    public static final String SIGNIMGPATH = SD_PATH + "cache" + File.separator + "signimage" + File.separator;
    // 图片存储路径
    public static final String BASE_PATH = SD_PATH + "cache" + File.separator + "image" + File.separator;
    // 语音存储路径
    public static final String AUDIORECODER_PATH = SD_PATH + "cache" + File.separator + "audiorecoder" + File.separator;

    // 缓存图片路径
    public static final String BASE_IMAGE_CACHE = BASE_PATH;

    public static final String TAG = "rance";
    public static final String APP_ID = "wx4b25184c83dbca16";
    /**
     * 0x001-接受消息  0x002-发送消息
     **/
    public static final int CHAT_ITEM_TYPE_LEFT = 0x001;
    public static final int CHAT_ITEM_TYPE_RIGHT = 0x002;
    /**
     * 0x003-发送中  0x004-发送失败  0x005-发送成功
     **/
    public static final int CHAT_ITEM_SENDING = 0x003;
    public static final int CHAT_ITEM_SEND_ERROR = 0x004;
    public static final int CHAT_ITEM_SEND_SUCCESS = 0x005;
    public static long SPLASH_TIME = 5;
    public static long COUNT_DOWN = 60;

    //线上环境
    //主机(正式)
    public static String VersionName = "";
    public static boolean mIsDebug = false;
//    public static String BASEURL = "http://piaoliu.kuaiyueread.com/";//
    public static String BASEURL = "http://ceshi.yesepiaoliu.com/";//
    public static String RTMPPREFIXION = "ODRPxVx";
    public static String EstimateUrl = "http://zlpg.yipan.com/";
    public static String ConsultUrl = "https://robot.odrcloud.cn/";
    public static String SIGNATUREPATH = "lawCaseAttachment/";
    public static String SIGNATUREPATH_JUDICIAL = "lawSuitAttachment/";

    public static String FLFG = "https://lawsearch.odrcloud.cn/lawSearch?areas=zhejiang";//法规检索
    public static String DXAL = "https://lawsearch.odrcloud.cn/caseSearch?areas=zhejiang";//案例检索

    public static final int DEBUG = 111;

    public static void setBaseurl() {

        switch (DEBUG) {
            case 1:
                //测试
                VersionName = "（测试版）";
                mIsDebug = true;
                BASEURL = "http://39.105.150.151:8080/";
                RTMPPREFIXION = "ODRTxVx";
                EstimateUrl = "http://test.yipan.com/";
                ConsultUrl = "https://odrcloud.net:19095/";
                DXAL = "https://lawsearch-pre.odrcloud.cn/caseSearch?areas=zhejiang";
                FLFG = "https://lawsearch-pre.odrcloud.cn/lawSearch?areas=zhejiang";
                break;
            case 2:
                //线上
                VersionName = "";
                mIsDebug = false;
                BASEURL = "http://www.ishijing.cn:8080/";
                RTMPPREFIXION = "ODRPxVx";
                EstimateUrl = "http://zlpg.yipan.com/";
                ConsultUrl = "https://robot.odrcloud.cn/";
                DXAL = "https://lawsearch.odrcloud.cn/caseSearch?areas=zhejiang";
                FLFG = "https://lawsearch.odrcloud.cn/lawSearch?areas=zhejiang";
                break;
            default:
                //开发
                VersionName = "（开发版）";
                mIsDebug = true;
                BASEURL = "http://www.ishijing.cn:8080/";
//                BASEURL = "http://182.254.149.243:9214/";
                RTMPPREFIXION = "ODRTxVx";
                EstimateUrl = "http://test.yipan.com/";
                ConsultUrl = "https://odrcloud.net:19095/";
                DXAL = "https://lawsearch-pre.odrcloud.cn/caseSearch?areas=zhejiang";
                FLFG = "https://lawsearch-pre.odrcloud.cn/lawSearch?areas=zhejiang";
                break;

        }

    }

}
