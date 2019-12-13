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

    //student
    public static String TYPE = "type";
    public static String PAGE = "page";
    public static String POLICYTYPE = "policyType";
    public static String VIDEOID = "videoId";
    public static String CLICKTYPE = "clickType";
    public static String COMMENTID = "commentId";
    public static String COMMENT = "content";
    public static String COLUMNTYPE = "columnType";
    public static String INITID = "initiatorId";
    public static String INITNAME = "initiator";
    public static String INITHEAD = "initiatorHead";
    public static String INITPIENT = "recipient";//收件人
    public static String INITPIENTID = "recipientId";//收件人
    public static String COPIED = "copied";//收件人
    public static String COPIEDID = "copiedId";//收件人
    public static String OFFICEHEAD = "officialHead";//收件人
    public static String OFFICEHEADCOMTENT = "officialContent";//收件人
    public static String ACCESSORY = "accessory";//收件人
    public static String ACCESSORYNAME = "accessoryName";//收件人
    public static String SUBMITTYPE = "submitType";//收件人
    public static String OFFICETIME = "officialTime";//收件人
    public static String KEY = "key";//
    public static String ATTRIID = "attributeId";//
    public static String ATTRINAME = "attributeName";//
    public static String TOPICENAME = "topicName";//
    public static String TOPICONTENT = "topicContent";//
    //service
    public static String SERVICEID = "serviceId";
    public static String DUESTYPE = "duesType";
    public static String BUILDINTYPEID = "buildingTypeId";
    public static String PUBLICCITYTYPE = "publicityType";
    public static String ASSESSID = "assessId";
    public static String MEETINTYPE = "meetingType";
    public static String FILE = "file";
    public static String METTINGID = "meetingId";
    public static String METTINGHEAD = "meetingHead";
    public static String SUMMARHEAD = "summaryHead";
    public static String SUMMARCONTENT = "content";
    public static String SUMMARACC = "accessory";
    public static String SUMMARPIC = "pictures";
    public static String SUMMARPICS = "picture";
    public static String BOXTYPE = "boxType";
    public static String BOXHEAD = "boxHead";
    public static String BOXTIME = "boxTime";
    public static String BOXUSER = "boxUser";
    public static String BOXCONTENT = "boxContent";
    public static String POVERTYHEADER = "povertyHead";
    public static String POVERTYNAME = "userName";
    public static String POVERTYCONTENT = "content";
    public static String POVERTYTIME = "time";
    public static String ARETYPE = "publicType";
    public static String AREBELONID = "belongId";
    public static String PAGENUMBER = "pageNumber";
    public static String PAGESIZE = "pageSize";

    //pay
    public static String PAYPROID = "productId";
    public static String PAYTYPE = "productType";
    public static String PAYPTION = "description";
    public static String PAYPICE = "totalPrice";



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
    public static final String HOMELIST = "index/indexPage";//首页
    public static final String USERINFO = "user/findUserById";//获取党员信息
    public static final String UP_USERINFO = "user/updateUserInfo";//更改个人信息
    public static final String QUERY_INTEGRA = "user/findMyIntegral";//获取积分
    public static final String SENDCODE = "user/sendCode";//发送短信验证码
    public static final String RESETPSW = "user/resetPassword";//重置密码
    public static final String STUDY_BANNER = "study/findBanner";//学习banner
    public static final String STUDY_NEWS = "study/findHotNews";//学习--新闻
    public static final String SERVICE_BRLIST = "serve/branch/findBranchList";//组织概况
    public static final String SERVICE_NOTICE = "serve/notice/findNotice";//任免公告
    public static final String SERVICE_PUBLICHOME = "serve/public/findPublicPage"; //三务公开首页
    public static final String SERVICE_PUBLICAREA = "serve/public/findPublicArea"; //三务公开地址
    public static final String SERVICE_BIRTHDAY = "serve/political/findPoliticalBirthday"; //政治生日
    public static final String SERVICE_DUES = "serve/dues/findPartyDuesList"; //党费缴纳
    public static final String SERVICE_FINDDW = "index/findPartyCommittee"; //党委
    public static final String SERVICE_FINDJW = "index/findDisciplineCommittee"; //纪委
    public static final String SERVICE_PARK = "index/findPartyBuild"; //园区
    public static final String SERVICE_PARKLIST = "index/findPartyBuildByType"; //园区信息
    public static final String SERVICE_PARKINLIST = "index/findPartyInnerBuild"; //党内建设
    public static final String SERVICE_PARKINLISTFORTYPE = "index/findPartyInnerBuildByType"; //党内建设类型列表
    public static final String SERVICE_PUBLICLIST = "serve/publicity/findPublicityList"; //党内公示
    public static final String SERVICE_PARTYBUILDNES = "serve/new/findNewParty"; //党建要闻
    public static final String SERVICE_FINDFORMLIST = "serve/inform/findInformList"; //通知公告
    public static final String SERVICE_REPORTLIST = "serve/think/findThinkList"; //思想汇报
    public static final String SERVICE_ASSESSLIST = "assess/findPartyAssessList"; //党员考核
    public static final String SERVICE_ASSESSBYID = "assess/findPartyAssessById"; //党员考核详细
    public static final String SERVICE_ASSESSUSERLIST = "assess/findByAssessUser"; //党员考核人员列表
    public static final String SERVICE_PLAYLIST = "serve/active/findAllActivity"; //所有活动列表
    public static final String SERVICE_MYPLAYLIST = "serve/active/findMyActivity"; //参与活动列表
    public static final String SERVICE_MEETING = "serve/meeting/findRecentMeeting"; //三会一课
    public static final String SERVICE_MYMEETING = "serve/meeting/findMyMeeting"; //我的会议
    public static final String SERVICE_SUMMLIST = "serve/meeting/findSummaryList"; //我的纪要列表
    public static final String SERVICE_UPFILE = "serve/meeting/uploadSummaryFile"; //上传会议文件
    public static final String SERVICE_UPJY = "serve/meeting/insertSummary"; //上传纪要
    public static final String STUDENT_POLICY = "study/findPolicyList"; //党章党规
    public static final String SERVICE_ADDMEETINGLIST = "serve/meeting/findMeetingHeadList"; //选择会议
    public static final String SERVICE_RECIVEOFFIC = "serve/official/findReceiveOfficial"; //我的收件箱
    public static final String SERVICE_FINDOFFICE = "serve/official/findSponsorOfficial"; //我发起的
    public static final String SERVICE_FINDDRAOFFICE = "serve/official/findDraftsOfficial"; //草稿箱
    public static final String SERVICE_UPMAILFILE = "serve/mail/uploadMailFile"; //书记信箱附件
    public static final String SERVICE_SUBMITBOXMAIL = "serve/mail/submitMail"; //提交书记信箱
    public static final String SERVICE_FINDMAILLIST = "serve/mail/findMailList"; //书记信箱列表
    public static final String SERVICE_VOTELIST = "serve/vote/findVoteList"; //民主投票
    public static final String SERVICE_FINDHOTNEWS = "study/findHotNews"; //热门新闻
    public static final String STUDY_VIDEOLIST = "study/videoPage"; //视频列表
    public static final String FINDVIDEOBYID = "study/findVideoById"; //视频详细
    public static final String VIDEOLIKE = "study/videoLike"; //视频点赞接口
    public static final String VIDEOCOMMENT = "study/findVideoComment"; //视频评论接口
    public static final String VIDEOCOMMENTLIKE = "study/videoCommentLike"; //视频评论点赞
    public static final String VIDEOCOMMENPOST = "study/videoComment"; //视频评论
    public static final String FINDFANFU = "study/corruption/findCorruptionList"; //反腐
    public static final String ALLPEOPLE = "serve/official/findAllOfficialUser"; //反腐
    public static final String OFFICIALFILE = "serve/official/uploadOfficialFile"; //上传文件
    public static final String SUBMITOFFICE = "serve/official/submitOfficial"; //上传文件http://39.105.150.151:8080/
    public static final String FINDNOTIC = "serve/findNotice"; //专题
    public static final String POVERLIST = "serve/poverty/findPovertyList"; //扶贫
    public static final String POVERLISTPOLICE = "serve/poverty/findPolicyList"; //扶贫政策
    public static final String POVERUPFILE = "serve/poverty/uploadPovertyFile"; //扶贫政策http://39.105.150.151:8080/
    public static final String POVERUPFILEPOST = "serve/poverty/publishPoverty"; //发起扶贫
    public static final String WXPAY = "pay/wxpay"; //
    public static final String ALIPAY = "serve/dues/alipay"; //
    public static final String PLANLIST = "serve/plan/findWorkPlanList"; //工作计划
    public static final String FINDTPOIC = "discuss/findTpoicByTypeAndKey"; //工作计划
    public static final String YPOCTYPE = "discuss/findTpoicTypeList"; //工作计划
    public static final String ADDHUATI = "discuss/publishTpoic"; //添加话题
    public static final String THREELIST = "serve/public/findPublicList"; //
    public static final String SEARCHNAME = "index/findUserByKey"; //搜索党员
    public static final String SEARCHOFFICE = "serve/official/findOfficialByKey"; //搜索信箱

}
