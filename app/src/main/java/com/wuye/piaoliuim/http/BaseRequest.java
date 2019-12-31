package com.wuye.piaoliuim.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;

import com.blankj.utilcode.util.StringUtils;
import com.chuange.basemodule.utils.LogUtils;
import com.vise.log.ViseLog;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.wuye.piaoliuim.BuildConfig;
import com.wuye.piaoliuim.R;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @ClassName BaseRequest
 * @Description
 * @Author VillageChief
 * @Date 2019/12/6 17:45
 */
public abstract class BaseRequest<T> {

    private static final String TAG = "BaseRequest";
    protected Context mContext;
    protected RequestListener<T> rComplete;
    protected String deviceId;
    protected String deviceType;
    protected String version;
    private static final int initialTimeoutMs = 5 * 1000;
    private static final int GET_DEFAULT_MAX_RETRIES = 1;
    private static final int POST_DEFAULT_MAX_RETRIES = 0;
    private ProgressDialog mProgressDialog = null;
    private String message = "正在请求网络，请稍后...";
    private boolean isShowDialog = true;
    private boolean needToJson = true;

    public BaseRequest(Context context) {
        mContext = context;
    }

    public BaseRequest(Context context, boolean isShowDialog) {
        mContext = context;
        this.isShowDialog = isShowDialog;

    }

    public BaseRequest(Context context, boolean isShowDialog, boolean needToJson) {
        mContext = context;
        this.isShowDialog = isShowDialog;
        this.needToJson = needToJson;
    }

    public abstract RequestParams makeParams();

    public abstract void parseData(String jsonTxt);

    public abstract void parseError(String message);

    /**
     * @getData 异步获取数据 -GET
     * @Exception 异常对象
     */
    public void getDataAsync() {
        RequestParams params = makeParams();
        if (params != null) {
            if (!mContext.equals(mContext.getApplicationContext())) {
                if (isShowDialog) {
                    showProgressDialog(mContext, message);
                }
            }
            getAsync(params);
        }
    }

    /**
     * get异步方法
     *
     * @param params
     */
    protected void getAsync(final RequestParams params) {
        try {
//            String token = MyApplication.getInstance().getToken();
            //debug模式  打印参数和请求地址  方便调试
            if (BuildConfig.APP_DEBUG == 0) {
                Log.e("", "╔═══════════════════════════════════════════════════════════════════════════════════════════════════");
                Log.e("地址GET", "║   " + params.getUrl());
                for (Map.Entry entry : params.getParams().entrySet()) {
                    Log.e("参数", " ║    " + entry.getKey() );
                }

                Log.e("", "╚═══════════════════════════════════════════════════════════════════════════════════════════════════");
            }
            ViseHttp.GET(params.getUrl()).addParams(params.getParams())
//                    .addHeader("token", token).addHeader("version", MyApplication.getInstance().getVersonName())
                    .addHeader("mobileType", "android")
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            dismissProgressDialog();
                            parseData(data);
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            dismissProgressDialog();
                            ViseLog.i("RequestError", "errCode:" + errCode + ",   errMsg:" + errMsg);
                            parseError(errMsg);
                        }
                    });

        } catch (Exception e) {
            dismissProgressDialog();
            parseError(mContext.getResources()
                    .getString(R.string.generic_error));
            ViseLog.e("NETERROR getAsync", "Current URL is:" + params.getUrl());
            e.printStackTrace();
        }
    }

    /**
     * @postData 业务层的post异步请求数据。
     * @Exception 异常对象
     */
    public void postDataAsync() {
        RequestParams params = makeParams();
        if (params != null) {
            if (!mContext.equals(mContext.getApplicationContext())) {
                if (isShowDialog) {
                    showProgressDialog(mContext, message);
                }
            }
            postAsync(params);
        }
    }
    /**
     * @postData 业务层的post异步请求数据。
     * @Exception 异常对象
     */
    public void upFile() {
        RequestParams params = makeParams();
        if (params != null) {
            if (!mContext.equals(mContext.getApplicationContext())) {
                if (isShowDialog) {
                    showProgressDialog(mContext, message);
                }
            }
            upFilepostAsync(params);
        }
    }


    /**
     * post异步方法
     *
     * @param params
     */
    protected void postAsync(final RequestParams params) {
        try {
//            String token = MyApplication.getInstance().getToken();
            String json = "";
            if(needToJson) {
//                json = GsonUtil.gson().toJson(params.getParams()).replace("\\\"", "\"").replace("\"{", "{").replace("}\"", "}").replace("\"[", "[").replace("]\"", "]").replace("\"null\"", "null");
                StringBuffer param = new StringBuffer();
                int i = 0;
                for (String key : params.getParams().keySet()) {
                    if (i == 0)
                        param.append("?");
                    else
                        param.append("&");
                    param.append(key).append("=").append(params.getParams().get(key));
                    i++;
                }
                json+=param;
            } else {
                json = params.getParamsJson();
            }

            FormBody.Builder formBody = new FormBody.Builder();
            if (!params.getParams().isEmpty()) {
                for (Map.Entry<String, String> entry : params.getParams().entrySet()) {
                    formBody.add(entry.getKey(), entry.getValue());
                }
            }
            RequestBody form = formBody.build();

            //debug模式  打印参数和请求地址  方便调试
            if (BuildConfig.APP_DEBUG != 2) {
                LogUtils.e("json格式查看    " + json);
                Log.e("请求开始", "╔═══════════════════════════════════════════════════════════════════════════════════════════════════");
                Log.e("地址", "║   " + ViseHttp.CONFIG().getBaseUrl() + params.getUrl());
                if(StringUtils.isEmpty(params.getParamsJson())) {
                    for (Map.Entry entry : params.getParams().entrySet()) {
                        Log.e("参数", " ║    " + entry.getKey() + "    ====   " + entry.getValue());
                    }
                } else {
                    Log.e("参数", " ║    " + json);
                }

                Log.e("请求结束", "╚═══════════════════════════════════════════════════════════════════════════════════════════════════");
            }
            ViseHttp.POST(params.getUrl()).setRequestBody(form)
//                    .setJson(json)//.addHeader("version", MyApplication.getInstance().getVersonName())
//                    .addHeader("mobileType", "android")
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            dismissProgressDialog();
                            parseData(data);
                        }
                        //ddHeader("token", token)
                        @Override
                        public void onFail(int errCode, String errMsg) {
                            dismissProgressDialog();
                            ViseLog.i("RequestError", "errCode:" + errCode + ",   errMsg:" + errMsg);
                            parseError(errMsg);
                        }
                    });
        } catch (Exception e) {
            dismissProgressDialog();
            parseError("网络异常,请稍后再试！");
            ViseLog.e("NETERROR postAsync", "Current URL is:" + params.getUrl());
            ViseLog.e("NETERROR postAsync", mContext.getResources()
                    .getString(R.string.generic_error));
            e.printStackTrace();
        }
    }

    /**
     * 显示网络请求进度条
     *
     * @param context
     * @param message
     */
    protected void showProgressDialog(final Context context, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            mProgressDialog.setCancelable(false);
        } else {
            mProgressDialog.dismiss();
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mProgressDialog.dismiss();
                    ((Activity) context).onBackPressed();
                }
                return false;
            }
        });
    }

    /**
     * 取消网络请求进度条
     */
    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }



    protected void upFilepostAsync(final RequestParams params ) {
        try {
//
            MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
            multipartBodyBuilder.setType(MultipartBody.FORM);
                 for (Map.Entry entry : params.getParams().entrySet()) {
                    Log.e("参数", " ║    " + entry.getKey() + "    ====   " + entry.getValue());
                }

            //遍历map中所有参数到builder
            if (params != null){
                for (String key : params.getParams().keySet()) {
                    multipartBodyBuilder.addFormDataPart(key, params.getParams().get(key));
                }
            }
            //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
            if (params.getFiles() != null){

                for (File file : params.getFiles()) {

                    multipartBodyBuilder.addFormDataPart(params.getFileName(), file.getName(), RequestBody.create(params.getType(), file));
                }
            }

            //构建请求体
            RequestBody requestBody = multipartBodyBuilder.build();


            ViseHttp.POST(params.getUrl()).setRequestBody(requestBody)
//                    .setJson(json)//.addHeader("version", MyApplication.getInstance().getVersonName())
//                    .addHeader("mobileType", "android")
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            dismissProgressDialog();
                            parseData(data);
                        }
                        //ddHeader("token", token)
                        @Override
                        public void onFail(int errCode, String errMsg) {
                            dismissProgressDialog();
                            ViseLog.i("RequestError", "errCode:" + errCode + ",   errMsg:" + errMsg);
                            parseError(errMsg);
                        }
                    });
        } catch (Exception e) {
            dismissProgressDialog();
            parseError("网络异常,请稍后再试！");
            ViseLog.e("NETERROR postAsync", "Current URL is:" + params.getUrl());
            ViseLog.e("NETERROR postAsync", mContext.getResources()
                    .getString(R.string.generic_error));
            e.printStackTrace();
        }
    }

}
