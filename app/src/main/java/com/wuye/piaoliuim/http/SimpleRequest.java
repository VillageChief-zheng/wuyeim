package com.wuye.piaoliuim.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.chuange.basemodule.BaseData;
 import com.chuange.basemodule.utils.ToastUtil;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.utils.AppSessionEngine;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.PhoneUtile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;

/**
 * @ClassName SimpleRequest
 * @Description
 * @Author VillageChief
 * @Date 2019/12/6 17:45
 */
public class SimpleRequest  extends BaseRequest<String> {

    private HashMap<String, String> params;
    private String url;
    private String paramsJson;
    private List<File> files  ;
    private String fileName;
    private MediaType type;
    /**
     * 传入参数的构造
     */
    public SimpleRequest(Context context, String url, HashMap<String, String> params, RequestListener<String> rComplete) {
        super(context);
        this.rComplete = rComplete;
        this.params = params;
        this.url = url;
    }
    /**
     * 传入参数的构造
     */
    public SimpleRequest(Context context, String url, HashMap<String, String> params, List<File> files, String fileName , MediaType type, RequestListener<String> rComplete) {
        super(context);
        this.rComplete = rComplete;
        this.params = params;
        this.url = url;
        this.fileName=fileName;
        this.files=files;
        this.type=type;
    }
    /**
     * 传入参数的构造
     */
    public SimpleRequest(Context context, String url, String paramsJson, RequestListener<String> rComplete, boolean isShowDialog, boolean needToJson) {
        super(context, isShowDialog, needToJson);
        this.rComplete = rComplete;
        this.paramsJson = paramsJson;
        this.url = url;
    }

    /**
     * 传入参数的构造
     */
    public SimpleRequest(Context context, String url, HashMap<String, String> params, RequestListener<String> rComplete, boolean isShowDialog) {
        super(context, isShowDialog);
        this.rComplete = rComplete;
        this.params = params;
        this.url = url;
    }

    @Override
    public RequestParams makeParams() {
        RequestParams pas = new RequestParams();
        params.put(UrlConstant.CHANNEL,"1");
        params.put(UrlConstant.PLANTYPE,"1");
        params.put(UrlConstant.PHONEMODEL, PhoneUtile.getModel());
        params.put(UrlConstant.DEVICEID,"1");
        params.put(UrlConstant.DEVICE_BRAND,PhoneUtile.getDeviceBrand());
        if (AppSessionEngine.getToken()!=null){
            params.put(UrlConstant.TOKEN,AppSessionEngine.getToken());

        }
        pas.setUrl(url);
        pas.setParamsJson(paramsJson);
        pas.setParams(params);
        pas.setFileName(fileName);
        pas.setFiles(files);
        pas.setType(type);
        return pas;
    }

    @Override
    public void parseData(String jsonTxt) {
        Log.i("返回结果++++++++",jsonTxt);

        try {
            JSONObject root = new JSONObject(jsonTxt);
            Log.i("返回结果++++++++",jsonTxt);
            BaseData baseData= GsonUtil.getDefaultGson().fromJson(jsonTxt,BaseData.class);
            int returnCode = root.optInt("code");
            if (baseData.code.equals("200")) {
//                if (root.has("data")) {
                    String result = root.optString("data");
                    rComplete.onComplete(jsonTxt);
//                } else {
//                    rComplete.onComplete("");
//                }
            } else {
                String message = baseData.info;
                if (!TextUtils.isEmpty(message)) {
                    rComplete.onError(message);
                } else {
                    rComplete.onError("");
                }
                ToastUtil.show(mContext,message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return;

    }

    @Override
    public void parseError(String message) {
        rComplete.onError(message);
        ToastUtil.show(mContext,message);

    }
}
