package com.wuye.piaoliuim.config;

/**
 * @ClassName UrlConstant
 * @Description
 * @Author VillageChief
 * @Date 2019/12/6 17:34
 */
public class UrlConstant {
    /**
     * the net error code
     */
    public static String LOGIN_OUT_CODE = "800001";
    public static final int REQUEST_CODE_PICK_FILE = 800;
    public static final int REQUEST_CODE_PICK_FILECC = 700;
    public static final int PEOPELIST = 900;
    public static final String APP_ID = "wx50dcfe13487b008f";
    public static final String API_KEY = "smqnbiHKeKsf0nZP7BTea0YtPL7GDLjP";
    public static final String MCH_ID = "1561549991";
    public static final String WEBURL = "http://39.105.150.151:8859/#/";


    /**
     * SharePreference with params
     */
    public static String GUIDE = "guide";
    public static String PINDAOID = "pindao";
    public static String GUIDE_HOME = "guideHome";
    public static String GUIDE_LEND = "guideLend";
    public static String GUIDE_ACCOUNT = "guideAccount";
    public static String USER = "user";
    public static String TITLE = "title";
    public static String SOURCE = "source";
    public static String IS_BACK = "isBack";
    public static String IS_WEB_TITLE = "isWebTitle";
    public static String URL = "url";

    /**
     * the net params
     */
    //    login
    public static String TELEPHONE = "telephone";
    public static String PASSWORD = "password";
    public static String USER_MOBILE = "useMobile";
    public static String SMCODE = "code";
    //main
    public static String GARTYPE = "gardenPartyTypeId";
    public static String NAMEKEY = "key";
    public static String OFFFTYPE = "officialType";

    //web View
    public static String JFGZ = "jfgz";
    public static String SXHB = "sxhb";
    public static String ID = "id";
    public static String TZGG = "tzgg";
    public static String GZJH = "gzjh";
    public static String DJYW = "djyw";
    public static String DNGS = "dngs";
    public static String SWGK = "swgk";
    public static String HOMEPAGE = "homepage";
    public static String TESTONLIN = "onlinetest";
    public static String DJINFO = "djinfo";
    public static String YQDJINFO = "yqdjinfo";
    public static String GWSF = "gwsfinfo";
    public static String DZZINFO = "dzzinfo";

    //net
    public static String TYPE = "type";
    public static String PAGE = "page";
    public static String TOKEN = "token";
    public static String CONTENT = "content";
    public static String PASSID = "pass_id";
    public static String NAME = "name";
    public static String LITPIC = "litpic";
    public static String OLDLITPIC = "oldlitpic";
    public static String SINNTURE = "signature";
    public static String GENDER = "gender";
    public static String AGE = "age";
    public static String BLICKID = "blick_id";
    public static String JUBAOID = "report_id";
    public static String DELLITPIC = "dellitpic";
    public static String FILENAME = "filename";



    //my
    public static String USERID = "userId";
    public static String HEADPORT = "headPortrait";



    //主机(9080正式)
    public static final String WEB_BASEURL = "wss://train.odrcloud.cn:8443/";
    public static final String BASEURL = "http://39.105.150.151:8080/";
    public static final String SocketAddress = "wss://yundr.gov.cn:8443/homeing/";

    /**
     *net
     */
    public static final String LOGIN = "user/userLogin";//登录
    public static final String BLACKLIST = "User/getBlickUser";//黑名单
    public static final String USEROPINION = "User/userOpinion";//用户反馈
    public static final String GIFTLISTS = "User/getUserGiftLists";//礼物
    public static final String FINSLIST = "User/getUserFansLists";//粉丝
    public static final String MYFOLLW = "User/getUserFollowLists";//我的关注
    public static final String CANCELMYFOLLW = "User/userCancelFollow";//取消我的关注
    public static final String MYGOLDLIST = "User/getUserGoldLists";//账单
    public static final String UPDATEUSERINFO = "User/updateUserInfos";//修改用户信息
    public static final String GETUSERINFO = "User/getUserInfos";//获取我的用户信息
    public static final String GETOTHERUSERINFO = "User/getUserDetails";//获取其他的用户信息
    public static final String ADDBLACk = "User/addBlack";//加入黑名单
    public static final String ADDFOLLOW = "User/userFollow";//关注
    public static final String DELUSERIMG = "User/delUserAlbum";//删除
    public static final String MAINFIND = "Index/getFindLists";//发现页
    public static final String PIAOLIULIST = "Index/getDriftlists";//漂流瓶列表
    public static final String PIAOLIUYUYIN = "Index/saveFileVideo";//语音


}
