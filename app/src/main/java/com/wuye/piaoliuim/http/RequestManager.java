package com.wuye.piaoliuim.http;

import android.content.Context;
import android.text.TextUtils;

import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.utils.AppSessionEngine;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;

/**
 * @ClassName RequestManager
 * @Description
 * @Author VillageChief
 * @Date 2019/12/6 17:43
 */
public class RequestManager {

    private static RequestManager instance;

    public RequestManager() {

    }

    public static RequestManager getInstance() {
        if (instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }

    public void errorResult(Context mContext, String resultStr) {
        try {
            JSONObject root = new JSONObject(resultStr);
            int returnCode = root.optInt("code");
            if (returnCode == 201) {
                String message = root.optString("message");
                if (!TextUtils.isEmpty(message)) {
//                    ToastUtils.showShort(message);
                } else {
//                    ToastUtils.showShort(mContext.getResources().getString(R.string.ser_asse));
                }
            } else if (returnCode == 202) {
                String message = root.optString("message");
                if (!TextUtils.isEmpty(message)) {
//                    ToastUtils.showShort(message);
                } else {
//                    ToastUtils.showShort(mContext.getResources().getString(R.string.haven_regist));
                }
            } else if (returnCode == 401 || returnCode == 402 || returnCode == 403) {
                String message = root.optString("message");
//                if(!TextUtils.isEmpty(message)) {
//                    rComplete.onError(message);
//                } else {
                if (returnCode == 403) {
//                    new DialogUtils().showDialogNoBackCancel(mContext, "提示", "登录异常,请重新登录!", "重新登录", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
////                            UserUtil.logOut((Activity) mContext);
//                        }
//                    });
                } else if (returnCode == 402) {
//                    ToastUtils.showShort(mContext.getResources().getString(R.string.accent_overtime));
//                    UserUtil.logOut((Activity) mContext);
                } else {
//                    ToastUtils.showShort(mContext.getResources().getString(R.string.accent_error));
//                    UserUtil.logOut((Activity) mContext);
                }
//                }
            } else if (returnCode == 500) {
                String message = root.optString("message");
                if (!TextUtils.isEmpty(message)) {
//                    ToastUtils.showShort(message);
                } else {
//                    ToastUtils.showShort(mContext.getResources().getString(R.string.unknow));
                }
            } else {
                String message = root.optString("message");
                if (!TextUtils.isEmpty(message)) {
//                    ToastUtils.showShort(message);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
//            rComplete.onError(e.getMessage());
        }
    }
    /**
     * 用户登录
     *
     * @param userName  用户名
     * @param passWord  密码
     * @param rComplete
     */
    public void login(Context context, String userName, String passWord, RequestListener<String> rComplete) {

        HashMap<String, String> params = new HashMap<>();
        params.put("telephone", userName);
        params.put("password",passWord );
        SimpleRequest request = new SimpleRequest(context, UrlConstant.LOGIN, params, rComplete);
        request.postDataAsync();

    }
    /**
     * 用户获取个人中心
     *
     * @param userId  用户id
     * @param rComplete
     */
    public void getMyhome(Context context, String userId, RequestListener<String> rComplete) {

        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.USERID, userId);
        SimpleRequest request = new SimpleRequest(context, UrlConstant.USERINFO, params, rComplete);
        request.postDataAsync();

    }
    /**
     * 用户获取个人中心
     *
     * @param rComplete
     */
    public void getBirList(Context context,  RequestListener<String> rComplete) {

        HashMap<String, String> params = new HashMap<>();
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_BIRTHDAY, params, rComplete);
        request.postDataAsync();

    }
    /**
     * 用户党内建设类型列表
     *
     * @param rComplete
     */
    public void getInPartyList(Context context,  RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_PARKINLIST, params, rComplete);
        request.postDataAsync();

    }
    /**
     * 用户党内建设详细类型列表
     *
     * @param rComplete
     */
    public void getInPartyListForType(Context context,String typeId,String page,  RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.BUILDINTYPEID,typeId);
        params.put(UrlConstant.PAGE,page);
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_PARKINLISTFORTYPE, params, rComplete);
        request.postDataAsync();
    }
    /**
     * 党内公示列表
     *
     * @param rComplete
     */
    public void getInPartyListForNotice(Context context,String typeId,   RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PUBLICCITYTYPE,typeId);
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_PUBLICLIST, params, rComplete);
        request.postDataAsync();
    }  /**
     * 党内公示列表
     *
     * @param rComplete
     */
    public void getInPartyBuildnews(Context context,  RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_PARTYBUILDNES, params, rComplete);
        request.postDataAsync();
    }
    /**
     * 通知公告
     *
     * @param rComplete
     */
    public void getNoticAndAnnoun(Context context,  RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_FINDFORMLIST, params, rComplete);
        request.postDataAsync();
    }
    /**
     * 思想汇报列表
     *
     * @param rComplete
     */
    public void getReportList(Context context,  RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_REPORTLIST, params, rComplete);
        request.postDataAsync();
    }
    /**
     * 党员考评
     *
     * @param
     */
    public void getEvaList(Context context, String type , RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.USERID, AppSessionEngine.getUseId());
        params.put(UrlConstant.TYPE, type);
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_ASSESSLIST, params, rComplete);
        request.postDataAsync();
    }
    /**
     * 党员考评详情
     *
     * @param
     */
    public void getEvaInfo(Context context, String assessid , RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.ASSESSID, assessid);
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_ASSESSBYID, params, rComplete);
        request.postDataAsync();
    }
    /**
     * 被考评人员列表
     *
     * @param
     */
    public void getEvaPeoList(Context context, String assessid , RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.ASSESSID, assessid);
        params.put(UrlConstant.USERID, AppSessionEngine.getUseId());
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_ASSESSUSERLIST, params, rComplete);
        request.postDataAsync();
    }
    /**
     * 被考评人员列表
     *
     * @param
     */
    public void getExaList(Context context, String assessid , RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.ASSESSID, assessid);
        params.put(UrlConstant.USERID, AppSessionEngine.getUseId());
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_ASSESSUSERLIST, params, rComplete);
        request.postDataAsync();
    }
    /**
     * 全部活动
     *
     * @param
     */
    public void getPlayList(Context context,   RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.USERID,AppSessionEngine.getUseId());
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_PLAYLIST, params, rComplete);
        request.postDataAsync();
    }
    /**
     *   参与的活动
     *
     * @paramMy
     */
    public void getMyPlayList(Context context,   RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.USERID,AppSessionEngine.getUseId());
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_MYPLAYLIST, params, rComplete);
        request.postDataAsync();
    }
    /**
     *   三会一课列表
     *
     * @paramMy
     */
    public void getMeetingList(Context context,   RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_MEETING, params, rComplete);
        request.postDataAsync();
    }
    /**
     *   我的会议
     *
     * @paramMy
     */
    public void getMyMeetingList(Context context, String type,  RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.USERID,AppSessionEngine.getUseId());
        params.put(UrlConstant.MEETINTYPE,type);
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_MYMEETING, params, rComplete);
        request.postDataAsync();
    }
    /**
     *  会议纪要
     *
     * @paramMy
     */
    public void getMyMeetingSum(Context context,    RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.USERID,AppSessionEngine.getUseId());
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_SUMMLIST, params, rComplete);
        request.postDataAsync();
    }

    /**
     *  会议纪要附件
     *
     * @paramMy
     */
    public void upFile(Context context, HashMap<String, String> params, List<File> files, String fileName , MediaType type, RequestListener<String> rComplete) {
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_UPFILE, params,files,fileName,type, rComplete);
        request.upFile();
    }
    public void upHuaTiFile(Context context, HashMap<String, String> params,List<File> files, String fileName , MediaType type, RequestListener<String> rComplete) {
        SimpleRequest request = new SimpleRequest(context, UrlConstant.ADDHUATI, params,files,fileName,type, rComplete);
        request.upFile();
    }
    /**
     *  会议纪要附件
     *
     * @paramMy
     */
    public void upFileMail(Context context, HashMap<String, String> params,List<File> files, String fileName , MediaType type, RequestListener<String> rComplete) {
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_UPMAILFILE, params,files,fileName,type, rComplete);
        request.upFile();
    }  /**
     *  会议纪要附件
     *
     * @paramMy
     */
    public void upFileFupin(Context context, HashMap<String, String> params,List<File> files, String fileName , MediaType type, RequestListener<String> rComplete) {
        SimpleRequest request = new SimpleRequest(context, UrlConstant.POVERUPFILE, params,files,fileName,type, rComplete);
        request.upFile();
    }
    /**
     *  会议纪要
     *
     * @paramMy
     */
    public void subMitAddmeting(Context context, HashMap<String, String> params,List<File> files, String fileName , MediaType type, RequestListener<String> rComplete) {
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_UPJY, params,files,fileName,type, rComplete);
        request.upFile();
    } /**
     *  更新个人信息
     *
     * @paramMy
     */
    public void updateMyInfo(Context context, HashMap<String, String> params,List<File> files, String fileName , MediaType type, RequestListener<String> rComplete) {
        SimpleRequest request = new SimpleRequest(context, UrlConstant.UP_USERINFO, params,files,fileName,type, rComplete);
        request.upFile();
    }
    /**
     *  会议纪要
     *
     * @paramMy
     */
    public void subMitBoxMail(Context context, HashMap<String, String> params,List<File> files, String fileName , MediaType type, RequestListener<String> rComplete) {
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_SUBMITBOXMAIL, params,files,fileName,type, rComplete);
        request.upFile();
    }/**
     *  会议纪要
     *
     * @paramMy
     */
    public void subMitPover(Context context, HashMap<String, String> params,List<File> files, String fileName , MediaType type, RequestListener<String> rComplete) {
        SimpleRequest request = new SimpleRequest(context, UrlConstant.POVERUPFILEPOST, params,files,fileName,type, rComplete);
        request.upFile();
    }
    /**
     *  支部概况
     *
     * @paramMy
     */
    public void branchList(Context context,    RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_BRLIST, params, rComplete);
        request.postDataAsync();
    }
    /**
     *  支部概况
     *
     * @paramMy
     */
    public void parklistForyq(Context context, String type,String number,   RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.GARTYPE,type);
        params.put(UrlConstant.PAGE,number);
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_PARKLIST, params, rComplete);

        request.postDataAsync();
    }/**
     *  党章 党规 党纪
     *
     * @paramMy
     */
    public void parkPolicList(Context context, String type,   RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.POLICYTYPE,type);
        SimpleRequest request = new SimpleRequest(context, UrlConstant.STUDENT_POLICY, params, rComplete);
        request.postDataAsync();
    }
    /**
     *  党章 党规 党纪
     *
     * @paramMy
     */
    public void selectMeeting(Context context,     RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.USERID,AppSessionEngine.getUseId());
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_ADDMEETINGLIST, params, rComplete);
        request.postDataAsync();
    }
    /**
     *  党章 党规 党纪
     *
     * @paramMy
     */
    public void getOfficial(Context context,  String url,   RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.USERID,AppSessionEngine.getUseId());
        SimpleRequest request = new SimpleRequest(context, url, params, rComplete);
        request.postDataAsync();
    }
    /**
     *  书记信箱列表
     *
     * @paramMy
     */
    public void findBossMailList(Context context,     RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_FINDMAILLIST, params, rComplete);
        request.postDataAsync();
    }
    /**
     *  民主投票
     *
     * @paramMy
     */
    public void findVotest(Context context,     RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.USERID, AppSessionEngine.getUseId());
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_VOTELIST, params, rComplete);
        request.postDataAsync();
    }
    /**
     * 获取党费
     *
     * @paramMy
     */
    public void partyFeiList(Context context,  String type,  RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.DUESTYPE,type);
        params.put(UrlConstant.USERID,AppSessionEngine.getUseId());
        SimpleRequest request = new SimpleRequest(context,  UrlConstant.SERVICE_DUES, params, rComplete);
        request.postDataAsync();
    }
    /**
     * 三务公开
     *
     * @paramMy
     */
    public void threePublic(Context context, String serviceId,   RequestListener<String> rComplete) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.SERVICEID,serviceId);
        SimpleRequest request = new SimpleRequest(context, UrlConstant.SERVICE_PUBLICHOME, params, rComplete);
        request.postDataAsync();
    }
    /**
     * 三务公开
     *
     * @paramMy
     */
    public void publicPostMap(Context context,  HashMap<String, String> params, String postUrl,  RequestListener<String> rComplete) {
        SimpleRequest request = new SimpleRequest(context, postUrl, params, rComplete);
        request.postDataAsync();
    }
    /**
     *  公文上传文件
     *
     * @paramMy
     */
    public void docupFile(Context context, HashMap<String, String> params,List<File> files, String fileName , MediaType type, RequestListener<String> rComplete) {
        SimpleRequest request = new SimpleRequest(context, UrlConstant.OFFICIALFILE, params,files,fileName,type, rComplete);
        request.upFile();
    }
}
