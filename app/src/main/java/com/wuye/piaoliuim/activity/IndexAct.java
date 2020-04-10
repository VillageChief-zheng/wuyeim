package com.wuye.piaoliuim.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.view.DialogView;
import com.vise.xsnow.common.GsonUtil;
import com.wuye.piaoliuim.MainActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.TokenUserInfo;
import com.wuye.piaoliuim.bean.VersionData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.TTAdManagerHolder;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.login.LoginActivity;
import com.wuye.piaoliuim.utils.AppSessionEngine;
import com.wuye.piaoliuim.utils.AppVersion;
import com.wuye.piaoliuim.utils.UIUtils;

import java.util.HashMap;

public class IndexAct extends BaseActivity implements DialogView.DialogViewListener {
  TextView dialogContent;
    VersionData upList;
    String verSion="1.0.0.2";
    private static final String TAG = "SplashActivity";
    private TTAdNative mTTAdNative;
    private FrameLayout mSplashContainer;
    //是否强制跳转到主页面
    private boolean mForceGoMain;

    //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
    private static final int AD_TIME_OUT = 3000;
    private String mCodeId = "887311681";
    private boolean mIsExpress = false; //是否请求模板广告

    @SuppressWarnings("RedundantCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mSplashContainer = (FrameLayout) findViewById(R.id.splash_container);

//        goActivity();
//        goToNotificationSetting(this);
        wchatLogin();
        Long l= AppVersion.getAppVersionCode(this);
        Log.i("版本号",l+"");


    }

    private void tiaozhugan(){
        String userId = AppSessionEngine.getToken();

        if (!TextUtils.isEmpty(userId)||userId!=null) {
                            goActivity();
        } else {
                            goLogin();
        }
    }
    private void inite(String verinfo){
        loading(R.layout.activity_up,this);
        dialogContent.setText(verinfo);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.dialogCancel:
                 if (upList.res.getAndroid_isdownload().equals("1")){
                finish();
                cancelLoading();
                }else {
                    tiaozhugan();
                     finish();
                     cancelLoading();
                 }
                break;
            case R.id.dialogSure:
//                if (upList.res.getAndroid_isdownload().equals("1")){
                    bySearchOpen(this);
                     cancelLoading();

                    Log.i("ppppp","去市场");
//                }else {
//                    tiaozhugan();
//                    cancelLoading();
//
//
//                }
                break;

        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public void goActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void goLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }



    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onView(View view) {
        view.findViewById(R.id.dialogCancel).setOnClickListener(this);
        view.findViewById(R.id.dialogSure).setOnClickListener(this);
        dialogContent=view.findViewById(R.id.dialogContent);
    }
    public void wchatLogin() {
        HashMap<String, String> params = new HashMap<>();
         RequestManager.getInstance().publicGettMap(this, params, UrlConstant.GETVERSION, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                  upList = GsonUtil.gson().fromJson(requestEntity, VersionData.class);
                  if (!verSion.equals(upList.res.getAndroid_version())){
                      inite(upList.res.getAndroid_download_msg());
                  }else {
//                      countDown(Constants.SPLASH_TIME, new CountDownListener() {
//                                  @Override
//                                  public void onTick(long millisUntilFinished) {
//                                  }
//
//                                  @Override
//                                  public void onFinish() {
//                          tiaozhugan();
                      mTTAdNative = TTAdManagerHolder.get().createAdNative(getBaseContext());
                      getExtraInfo();
                      loadSplashAd();
//                                  }
                              }
//                      );
//                  }

            }

            @Override
            public void onError(String message) {

            }
        });
    }
    private void getExtraInfo() {
        Intent intent = getIntent();
        if(intent == null) {
            return;
        }
        String codeId = intent.getStringExtra("splash_rit");
        if (!TextUtils.isEmpty(codeId)){
            mCodeId = codeId;
        }
        mIsExpress = intent.getBooleanExtra("is_express", false);
    }
    public static void bySearchOpen(Context context) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("market://search?q=" + "秘密漂流瓶"));
            context.startActivity(i);
        } catch (Exception e) {
            Toast.makeText(context, "您的手机没有安装Android应用市场", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void goToNotificationSetting(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            // android 8.0引导
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) {
            // android 5.0-7.0
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else {
            // 其他
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 加载开屏广告
     */
    private void loadSplashAd() {
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = null;
        if (mIsExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，请传入实际需要的大小，
            //比如：广告下方拼接logo、适配刘海屏等，需要考虑实际广告大小
            float expressViewWidth = UIUtils.getScreenWidthDp(this);
            float expressViewHeight = UIUtils.getHeight(this);
            adSlot = new AdSlot.Builder()
                    .setCodeId(mCodeId)
                    .setSupportDeepLink(true)
                    .setImageAcceptedSize(1080, 1920)
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
                    .setExpressViewAcceptedSize(expressViewWidth, expressViewHeight)
                    .build();
        } else {
            adSlot = new AdSlot.Builder()
                    .setCodeId(mCodeId)
                    .setSupportDeepLink(true)
                    .setImageAcceptedSize(1080, 1920)
                    .build();
        }

        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                Log.d(TAG, String.valueOf(message));
                 tiaozhugan();
            }

            @Override
            @MainThread
            public void onTimeout() {
                tiaozhugan();
            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                Log.d(TAG, "开屏广告请求成功");
                if (ad == null) {
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();
                if (view != null && mSplashContainer != null && !IndexAct.this.isFinishing()) {
                    mSplashContainer.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
                    mSplashContainer.addView(view);
                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                    //ad.setNotAllowSdkCountdown();
                }else {
                    tiaozhugan();
                }

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        Log.d(TAG, "onAdClicked");
                     }

                    @Override
                    public void onAdShow(View view, int type) {
                        Log.d(TAG, "onAdShow");
                     }

                    @Override
                    public void onAdSkip() {
                        Log.d(TAG, "onAdSkip");
                         tiaozhugan();

                    }

                    @Override
                    public void onAdTimeOver() {
                        Log.d(TAG, "onAdTimeOver");
                        tiaozhugan();
                    }
                });
                if(ad.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ad.setDownloadListener(new TTAppDownloadListener() {
                        boolean hasShow = false;

                        @Override
                        public void onIdle() {
                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                            if (!hasShow) {
                                 hasShow = true;
                            }
                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {

                        }
                    });
                }
            }
        }, AD_TIME_OUT);

    }

}
