package com.wuye.piaoliuim;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;

import androidx.multidex.MultiDex;

import com.chuange.basemodule.BaseApplication;
import com.chuange.basemodule.BaseData;
import com.chuange.basemodule.view.DialogView;
import com.vise.utils.assist.SSLUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.interceptor.HttpLogInterceptor;
import com.wuye.piaoliuim.config.AppConfig;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.utils.WebActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @ClassName WuyeApplicatione
 * @Description
 * @Author VillageChief
 * @Date 2019/12/6 17:30
 */
public class WuyeApplicatione extends BaseApplication {
    private static Context mContext;//上下文

    public static WuyeApplicatione etdApplication;
    private List<Activity> activityList = new ArrayList<>();

//c8c077c246dcbea0e9f2e9270713af1a46404c0bc64ce99c47740d064d380d5f
private int SDKAPPID=1400302511;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
//        ViewTarget.setTagId(R.id.iv_newsimg);
        mContext = getApplicationContext();
//        TUIKitConfigs configs = TUIKitt.getConfigs();
//        configs.setSdkConfig(new TIMSdkConfig(SDKAPPID));
//        configs.setCustomFaceConfig(new CustomFaceConfig());
//        configs.setGeneralConfig(new GeneralConfig());
//
//        TUIKit.init(this, SDKAPPID, configs);
        MultiDex.install(this);

    }
    public void init(){
        etdApplication = this;
        AppConfig mAppConfig=new AppConfig();
//        mAppConfig.setIsDebug(SyncStateContract.Constants.mIsDebug);
        mAppConfig.BASEURL = Constants.BASEURL;
        mAppConfig.VersionName = Constants.VersionName;
        mAppConfig.RTMPPREFIXION = Constants.RTMPPREFIXION;
        mAppConfig.EstimateUrl = Constants.EstimateUrl;
        mAppConfig.ConsultUrl = Constants.ConsultUrl;
        mAppConfig.DXAL = Constants.DXAL;
        mAppConfig.FLFG = Constants.FLFG;
        ViseHttp.init(this);
        ViseHttp.CONFIG()
                //配置请求主机地址
                .baseUrl(mAppConfig.BASEURL)
                //配置全局请求头
                .globalHeaders(new HashMap<String, String>())
                //配置全局请求参数
                .globalParams(new HashMap<String, String>())
                //配置读取超时时间，单位秒
                .readTimeout(30)
                //配置写入超时时间，单位秒
                .writeTimeout(30)
                //配置连接超时时间，单位秒
                .connectTimeout(30)
                //配置请求失败重试次数
                .retryCount(3)
                //配置请求失败重试间隔时间，单位毫秒
                .retryDelayMillis(1000)
                //配置是否使用cookie
                .setCookie(true)
                //配置自定义cookie
//                .apiCookie(new ApiCookie(this))
                //配置是否使用OkHttp的默认缓存
                .setHttpCache(true)
                //配置OkHttp缓存路径
//                .setHttpCacheDirectory(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR))
                //配置自定义OkHttp缓存
//                .httpCache(new Cache(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置自定义离线缓存
//                .cacheOffline(new Cache(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置自定义在线缓存
//                .cacheOnline(new Cache(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置开启Gzip请求方式，需要服务器支持
//                .postGzipInterceptor()
                //配置应用级拦截器
                .interceptor(new HttpLogInterceptor()
                        .setLevel(HttpLogInterceptor.Level.BODY))
                //配置网络拦截器
//                .networkInterceptor(new NoCacheInterceptor())
                //配置转换工厂
                .converterFactory(GsonConverterFactory.create())
                //配置适配器工厂
                .callAdapterFactory(RxJava2CallAdapterFactory.create())
                //配置请求工厂
//                .callFactory(new Call.Factory() {
//                    @Override
//                    public Call newCall(Request request) {
//                        return null;
//                    }
//                })
                //配置连接池
//                .connectionPool(new ConnectionPool())
                //配置主机证书验证
//                .hostnameVerifier(new SSLUtil.UnSafeHostnameVerifier(mAppConfig.BASEURL))
                //配置SSL证书验证
                .SSLSocketFactory(SSLUtil.getSslSocketFactory(null, null, null))
        //配置主机代理
//                .proxy(new Proxy(Proxy.Type.HTTP, new SocketAddress() {}))
        ;
//         RxHttp.setDebug();
        //或者，调试模式下会有日志输出

    }

    @Override
    public void newGlobalError(final Context context, final BaseData baseData) {
        if (context == null) return;
        loading(context, baseData.info).setListener(new DialogView.DialogOnClickListener() {
            @Override
            public void onDialogClick(boolean isCancel) {
                if (isCancel) return;
//                if (baseData.code.equals("30001")) {
//                 } else if (baseData.code.equals("60001")) {
//                 } else if (baseData.code.equals("800000") || baseData.code.equals("800001")) {
//                    clear();
//                 } else {
//                    LogUtils.e("info:", baseData.info);
//                }
            }
        }).setOnlySure();
    }

    public void pushActivity(Activity activity) {
        activityList.add(activity);
    }

    public void clearActivity() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
    }


    public void webView(String title, String url) {
        webView(title, url, 0);
    }

    public void webView(String title, String url, boolean isBack) {
        webView(title, url, 0, isBack);
    }

    public void webView(String title, String url, int source) {
        webView(title, url, source, true);
    }

    public void webView(String title, String url, int source, boolean isBack) {
        Intent intent = new Intent();
        intent.putExtra(UrlConstant.TITLE, title);
        intent.putExtra(UrlConstant.URL, url);
        intent.putExtra(UrlConstant.SOURCE, source);
        intent.putExtra(UrlConstant.IS_BACK, isBack);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(this, WebActivity.class);
        startActivity(intent);
    }

    public void cleanJump(Class _class, int toSource) {
        List<Activity> activitys = getActivitiesByApplication();
        if (activitys == null) return;
        for (Activity activity : activitys) {
            activity.finish();
        }

        Intent intent = new Intent();
        intent.setClass(this, _class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public List<Activity> getActivitiesByApplication() {
        List<Activity> list = new ArrayList<>();
        try {
            Class<Application> applicationClass = Application.class;
            Field mLoadedApkField = applicationClass.getDeclaredField("mLoadedApk");
            mLoadedApkField.setAccessible(true);
            Object mLoadedApk = mLoadedApkField.get(this);
            Class<?> mLoadedApkClass = mLoadedApk.getClass();
            Field mActivityThreadField = mLoadedApkClass.getDeclaredField("mActivityThread");
            mActivityThreadField.setAccessible(true);
            Object mActivityThread = mActivityThreadField.get(mLoadedApk);
            Class<?> mActivityThreadClass = mActivityThread.getClass();
            Field mActivitiesField = mActivityThreadClass.getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Object mActivities = mActivitiesField.get(mActivityThread);
            // 注意这里一定写成Map，低版本这里用的是HashMap，高版本用的是ArrayMap
            if (mActivities instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<Object, Object> arrayMap = (Map<Object, Object>) mActivities;
                for (Map.Entry<Object, Object> entry : arrayMap.entrySet()) {
                    Object value = entry.getValue();
                    Class<?> activityClientRecordClass = value.getClass();
                    Field activityField = activityClientRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Object object = activityField.get(value);
                    list.add((Activity) object);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }

    /**
     * @param useId
     * @param sessionId
     */
    public void pushDevices(String useId, String sessionId) {
//        JPushInterface.setAlias(this, 0, useId);
//        HashMap<String, String> params = new HashMap<>();
//        params.put(Constant.USE_ID, useId);
//        params.put("registrationId", JPushInterface.getRegistrationID(this));//registrationId: '160a3797c822f08d3d5',
//        params.put(Constant.SESSION_ID, sessionId);
//        request(this, Constant.PUSH_DEVICES, RequestType.POST, CacheType.NETWORK_THEN_CACHE, params, BaseData.class, null, this, false);
    }
    public static Context getContext() {
        return mContext;
    }



    public void clear() {
    }

}
