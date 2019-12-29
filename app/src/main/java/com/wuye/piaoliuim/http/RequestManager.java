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
     *
     *
     * @paramMy
     */
    public void publicPostMap(Context context,  HashMap<String, String> params, String postUrl,  RequestListener<String> rComplete) {
        SimpleRequest request = new SimpleRequest(context, postUrl, params, rComplete);
        request.postDataAsync();
    }
    /**
     *  上传文件
     *
     * @paramMy
     */
    public void upUpFile(Context context, HashMap<String, String> params,List<File> files, String fileName , MediaType type, RequestListener<String> rComplete) {
        SimpleRequest request = new SimpleRequest(context, UrlConstant.UPFILE, params,files,fileName,type, rComplete);
        request.upFile();
    }/**
     *  更改个人信息
     *
     * @paramMy
     */
    public void upYuyin(Context context, HashMap<String, String> params,List<File> files, String fileName , MediaType type, RequestListener<String> rComplete) {
        SimpleRequest request = new SimpleRequest(context, UrlConstant.PIAOLIUYUYIN, params,files,fileName,type, rComplete);
        request.upFile();
    }
}
