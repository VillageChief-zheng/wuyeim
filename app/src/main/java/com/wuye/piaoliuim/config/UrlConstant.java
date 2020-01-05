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
    public static String USERTOKEN = "token";
    public static String USERINFOS = "userinfo";
    public static String CHANNEL = "channel";
    public static String PLANTYPE = "platform_type";
    public static String PHONEMODEL = "phone_model";
    public static String DEVICEID = "device_id";
    public static String DEVICE_BRAND = "divece_brand";
    //渠道
    public static String QUDAOHAO = "1";
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
    public static String PHONE = "phone";
    public static String CODE="code";
    public static String SINGNINREGION = "signin_region";
    public static String V_CODE = "v_code";
    public static String USER_ID = "user_id";
    public static String FILEDATA = "filedata";
    public static String GID = "gid";
    public static String NUMLIWU = "num";
    public static String PAY_TYPE = "pay_type";
    public static String MPNYTYPE = "money_type";



    //my
    public static String USERID = "userId";
    public static String HEADPORT = "headPortrait";



    //主机(9080正式)
    public static final String WEB_BASEURL = "wss://train.odrcloud.cn:8443/";
    public static final String BASEURL = "http://piaoliu.kuaiyueread.com/";
    public static final String SocketAddress = "wss://yundr.gov.cn:8443/homeing/";

    /**
     *net
     */
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
    public static final String GETIMAGE = "Useracc/getVerifyCode";//tupian
    public static final String LOGIN = "Useracc/userPhoneAccount";//tupian
    public static final String LOGINPhoneCode = "Useracc/getLoginCode";//tupian
    public static final String FINDINDEX = "Index/getDiscoverLists";//tupian
    public static final String SENDTEXT = "Index/userSendDrift";//tupian
    public static final String UPFILE = "Index/uploadfile";//上传文件
    public static final String REMOVEBLACK = "User/removeBlack";//移除黑名单
    public static final String JUBAO = "User/userReport";//移除黑名单
    public static final String JILULIEBIAO = "User/getUserBrowLists";//浏览记录
    public static final String SENDLIWU = "User/userGiveGifts";//送礼物
    public static final String BINDPHONECODE = "Useracc/getBindCode";//绑定手机号获取验证码
    public static final String BINDPHONE = "Useracc/userBindPhone";//绑定手机号
    public static final String WECHATLOFIN = "Useracc/userWechatLogin";//微信登录
    public static final String SINOUT = "Useracc/userLoginOut";//退出登录
    public static final String PLAYLIST = "Index/getPayLevelLists";//获取充值列表
    public static final String PAYWACHATANDALI = "Payapi/activityReserve";//支付
    public static final String GETVERSION = "Index/getVersionInfo";//支付
    public static final String RENGPINGZI = "User/getGoldInsufficient";//扔瓶子
    public static final String SYSMESSAGE = "Index/getNoticeLists";//系统消息
    public static final String SIN = "Useracc/getTlv2UserSig";//系统消息
    public static final String LAOPINGZI = "User/userDeductionGold";//捞瓶子
    public static final String GETLIWULIST = "User/getGiftLists";//礼物


}
