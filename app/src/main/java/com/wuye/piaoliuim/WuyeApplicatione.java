package com.wuye.piaoliuim;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


import androidx.core.app.NotificationCompat;

import com.chuange.basemodule.BaseApplication;
import com.chuange.basemodule.BaseData;
import com.chuange.basemodule.utils.ToastUtil;
import com.chuange.basemodule.view.DialogView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.huawei.android.hms.agent.HMSAgent;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.tencent.imsdk.TIMBackgroundParam;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageOfflinePushSettings;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.imsdk.TIMOfflinePushSettings;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.imsdk.utils.IMFunc;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.vise.utils.assist.SSLUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.interceptor.HttpLogInterceptor;
import com.wuye.piaoliuim.activity.IndexAct;
import com.wuye.piaoliuim.activity.imactivity.MipushTestActivity;
import com.wuye.piaoliuim.config.AppConfig;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.helper.ConfigHelper;
import com.wuye.piaoliuim.helper.CustomAVCallUIController;
import com.wuye.piaoliuim.helper.CustomMessage;
import com.wuye.piaoliuim.thirdpush.ThirdPushTokenMgr;
import com.wuye.piaoliuim.utils.DemoLog;
import com.wuye.piaoliuim.utils.GenerateTestUserSig;
import com.wuye.piaoliuim.utils.PrivateConstants;
import com.wuye.piaoliuim.utils.WebActivity;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.bouncycastle.asn1.x509.Time;

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
    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";

//c8c077c246dcbea0e9f2e9270713af1a46404c0bc64ce99c47740d064d380d5f
//    5e12960f0cafb2b4690002d1


//       d3e4d91c1818405d1506069d0055e9d8


//    eqtuc8anbj8ugzohnt6w9rniigskrxab

private int SDKAPPID=1400302511;
    private static WuyeApplicatione instance;
    private static final String TAG = WuyeApplicatione.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        init();
//        ViewTarget.setTagId(R.id.iv_newsimg);
        mContext = getApplicationContext();

        instance=this;
         initIm();
         initUmengSdk();

    }
    private void initUmengSdk() {
        UMConfigure.init(this, "5e12960f0cafb2b4690002d1", "oppo",
                UMConfigure.DEVICE_TYPE_PHONE, "d3e4d91c1818405d1506069d0055e9d8");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

        UMConfigure.setLogEnabled(true);

//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.setNotificaitonOnForeground(false);

        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));

                Log.i(TAG, "友盟注册成功：deviceToken：-------->  " + deviceToken);

            }

            @Override
            public void onFailure(String s, String s1) {
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
                Log.e(TAG, "友盟注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
        mPushAgent.setDisplayNotificationNumber(5);
         Log.e(TAG, "注册：-------->  " + "s:" + mPushAgent.getRegistrationId());
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            public void dealWithCustomAction(Context context, UMessage msg) {
                Intent intent=new Intent(context, MipushTestActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

    }
    public static WuyeApplicatione instance() {
        return instance;
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


    class StatisticActivityLifecycleCallback implements ActivityLifecycleCallbacks {
        private int foregroundActivities = 0;
        private boolean isChangingConfiguration;
        private IMEventListener mIMEventListener = new IMEventListener() {
            @Override
            public void onNewMessages(List<TIMMessage> msgs) {
                if (CustomMessage.convert2VideoCallData(msgs) != null) {
                    // 会弹出接电话的对话框，不再需要通知
                    return;
                }
                for (TIMMessage msg : msgs) {
                    // 小米手机需要在设置里面把demo的"后台弹出权限"打开才能点击Notification跳转。TIMOfflinePushNotification后续不再维护，如有需要，建议应用自己调用系统api弹通知栏消息。
//                    TIMOfflinePushNotification notification = new TIMOfflinePushNotification(WuyeApplicatione.this, msg);
//                    notification.doNotify(WuyeApplicatione.this, R.drawable.default_user_icon);
                    Log.i("腾讯im 我是新消息通知","pppppppp"+msg);
                    notifsy(msg);
                }
            }
        };

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            Log.i("腾讯", "onActivityCreated bundle: " + bundle);
            if (bundle != null) { // 若bundle不为空则程序异常结束
                // 重启整个程序
                ToastUtil.show(getContext(),"Im聊天信息登出请重新登录");
                Intent intent = new Intent(activity, IndexAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {
            foregroundActivities++;
            if (foregroundActivities == 1 && !isChangingConfiguration) {
                // 应用切到前台
                Log.i("腾讯", "application enter foreground");
                TIMManager.getInstance().doForeground(new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        Log.i("腾讯", "doForeground err = " + code + ", desc = " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        Log.i("腾讯", "doForeground success");
                    }
                });
                TUIKit.removeIMEventListener(mIMEventListener);
            }
            isChangingConfiguration = false;
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            foregroundActivities--;
            if (foregroundActivities == 0) {
                // 应用切到后台
                Log.i("腾讯", "application enter background");
                int unReadCount = 0;
                List<TIMConversation> conversationList = TIMManager.getInstance().getConversationList();
                for (TIMConversation timConversation : conversationList) {
                    unReadCount += timConversation.getUnreadMessageNum();
                }
                TIMBackgroundParam param = new TIMBackgroundParam();
                param.setC2cUnread(unReadCount);
                TIMManager.getInstance().doBackground(param, new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        Log.i("腾讯", "doBackground err = " + code + ", desc = " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        Log.i("腾讯", "doBackground success");
                    }
                });
                // 应用退到后台，消息转化为系统通知
                TUIKit.addIMEventListener(mIMEventListener);
            }
            isChangingConfiguration = activity.isChangingConfigurations();
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
  private void initIm(){
      if (SessionWrapper.isMainProcess(getApplicationContext())) {
          /**
           * TUIKit的初始化函数
           *
           * @param context  应用的上下文，一般为对应应用的ApplicationContext
           * @param sdkAppID 您在腾讯云注册应用时分配的sdkAppID
           * @param configs  TUIKit的相关配置项，一般使用默认即可，需特殊配置参考API文档
           */
          TUIKit.init(this, GenerateTestUserSig.SDKAPPID, new ConfigHelper().getConfigs());

          if (ThirdPushTokenMgr.USER_GOOGLE_FCM) {
              FirebaseInstanceId.getInstance().getInstanceId()
                      .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                          @Override
                          public void onComplete(Task<InstanceIdResult> task) {
                              if (!task.isSuccessful()) {
                                  DemoLog.w(TAG, "getInstanceId failed exception = " + task.getException());
                                  return;
                              }

                              // Get new Instance ID token
                              String token = task.getResult().getToken();
                              DemoLog.i(TAG, "google fcm getToken = " + token);

                              ThirdPushTokenMgr.getInstance().setThirdPushToken(token);
                          }
                      });
          } else if (IMFunc.isBrandXiaoMi()) {
              // 小米离线推送
//              MiPushClient.registerPush(this, PrivateConstants.XM_PUSH_APPID, PrivateConstants.XM_PUSH_APPKEY);
          }
          else if (IMFunc.isBrandHuawei()) {
              // 华为离线推送
              HMSAgent.init(this);
          } else if (MzSystemUtils.isBrandMeizu(this)) {
              // 魅族离线推送
              PushManager.register(this, PrivateConstants.MZ_PUSH_APPID, PrivateConstants.MZ_PUSH_APPKEY);
          }
          else if (IMFunc.isBrandVivo()) {
              // vivo离线推送
//              PushClient.getInstance(getApplicationContext()).initialize();
          }

          registerActivityLifecycleCallbacks(new StatisticActivityLifecycleCallback());
      }
//        if (BuildConfig.DEBUG) {
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                return;
//            }
//            LeakCanary.install(this);
//        }
      CustomAVCallUIController.getInstance().onCreate();
      IMEventListener imEventListener = new IMEventListener() {
          @Override
          public void onNewMessages(List<TIMMessage> msgs) {
              DemoLog.i(TAG, "onNewMessages"+msgs.size());
              CustomAVCallUIController.getInstance().onNewMessage(msgs);
          }
      };
      TUIKit.addIMEventListener(imEventListener);
       TIMManager.getInstance().getOfflinePushSettings(new TIMValueCallBack<TIMOfflinePushSettings>() {
          @Override
          public void onError(int i, String s) {
              Log.e("sss", "get offline push setting error " + s);
          }

          @Override
          public void onSuccess(TIMOfflinePushSettings timOfflinePushSettings) {
              Log.e("sss", "get offline push setting success "+ timOfflinePushSettings.isEnabled()+"" );
//                timOfflinePushSettings.isEnabled();
          }
      });
  }
  private void notifsy(TIMMessage msg){
      NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
      NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
      Intent notificationIntent = new Intent(mContext, MainActivity.class);
      notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
      PendingIntent intent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);
      mBuilder.setContentTitle(msg.getSenderNickname())//设置通知栏标题
              .setContentText( "您有一条消息")
              .setContentIntent(intent) //设置通知栏单击意图
//              .setNumber(5) //设置通知集合的数量
              .setTicker(msg.getSenderNickname()+":"+msg.getCustomStr()) //通知首次出现在通知栏，带上升动画效果的
              .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
              .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用 defaults 属性，可以组合
              .setSmallIcon(R.mipmap.app_ico);//设置通知小 ICON
      Notification notify = mBuilder.build();
      notify.flags |= Notification.FLAG_AUTO_CANCEL;
      mNotificationManager.notify(1, notify);
  }
}
