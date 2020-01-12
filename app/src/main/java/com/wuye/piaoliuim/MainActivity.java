package com.wuye.piaoliuim;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.BaseFragement;
import com.chuange.basemodule.utils.ActivityTaskManager;
import com.chuange.basemodule.utils.ToastUtil;
import com.chuange.basemodule.view.DialogView;
import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.meizu.cloud.pushsdk.notification.MPushMessage;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushSettings;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.utils.IMFunc;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationManagerKit;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.inapp.IUmengInAppMsgCloseCallback;
import com.umeng.message.inapp.InAppMessageManager;
import com.vise.xsnow.common.GsonUtil;
//import com.vivo.push.IPushActionListener;
//import com.vivo.push.PushClient;
import com.wuye.piaoliuim.activity.BaseImActivity;
import com.wuye.piaoliuim.activity.BindPhone;
import com.wuye.piaoliuim.activity.EditInfoAct;
import com.wuye.piaoliuim.activity.FangdaPicAct;
import com.wuye.piaoliuim.activity.IndexAct;
import com.wuye.piaoliuim.activity.LiwuTsetAct;
import com.wuye.piaoliuim.activity.OpinionAct;
import com.wuye.piaoliuim.activity.PaihangAct;
import com.wuye.piaoliuim.activity.PayJiegAct;
import com.wuye.piaoliuim.activity.RechangeAct;
import com.wuye.piaoliuim.activity.SendTxtAndYuyinAct;
import com.wuye.piaoliuim.activity.SendYuyinAct;
import com.wuye.piaoliuim.activity.TestHeixiu;
import com.wuye.piaoliuim.activity.ToBindPhoneAct;
import com.wuye.piaoliuim.activity.UserInfoAct;
import com.wuye.piaoliuim.bean.SingData;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.fragment.FindFragment;
import com.wuye.piaoliuim.fragment.ImFragment;
import com.wuye.piaoliuim.fragment.MyFragment;
import com.wuye.piaoliuim.fragment.PiaoliuFragment;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.login.LoginActivity;
import com.wuye.piaoliuim.thirdpush.ThirdPushTokenMgr;
import com.wuye.piaoliuim.utils.AppSessionEngine;
import com.wuye.piaoliuim.utils.DemoLog;
import com.wuye.piaoliuim.utils.GenerateTestUserSig;
import com.wuye.piaoliuim.utils.ImagUrlUtils;
import com.wuye.piaoliuim.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.tencent.imsdk.TIMManager.TIM_STATUS_LOGINED;

public class MainActivity extends BaseActivity implements ConversationManagerKit.MessageUnreadWatcher{

    @BindView(R.id.bottom_navigation_bar1)
    BottomBarLayout bottomNavigationBar1;
    @BindView(R.id.main_container)
    FrameLayout mainContainer;
  int currentPositions;

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    UserInfoData userInfoData;

    String isBindPhone="";
    private PushAgent mPushAgent;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQ_PERMISSION_CODE = 0x100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        EventBus.getDefault().register(this);

        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.onAppStart();
//        checkPermission(this);
        setAlias();
         getNetData();

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_color));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigation_bar_color));
            int vis = getWindow().getDecorView().getSystemUiVisibility();
            vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            getWindow().getDecorView().setSystemUiVisibility(vis);
        }
       prepareThirdPushToken();
        initData();
     }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }


    private void initData() {
//        Log.i("是不是绑定了手机号",AppSessionEngine.getUserTokenInfo().getIs_binding());
         mFragmentList.add(PiaoliuFragment.newInstance());
        mFragmentList.add(  FindFragment.newInstance() );
        mFragmentList.add(  FindFragment.newInstance() );
        mFragmentList.add(ImFragment.newInstance());
         mFragmentList.add(MyFragment.newInstance());
        changeFragment(3);
        changeFragment(0);
         bottomNavigationBar1.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int i, int currentPosition) {
                if (AppSessionEngine.getMyUserInfo().res.getListList().getPhone().equals("")){
                    loading("请绑定手机号").setListener(new DialogView.DialogOnClickListener() {
                        @Override
                        public void onDialogClick(boolean isCancel) {
                            if (isCancel)
                                return;
                            else {
                                startActivity(new Intent(getBaseContext(), ToBindPhoneAct.class));
                            }
                        }
                    }).setOnlySure();
                }else {
                    changeFragment(currentPosition);

                }

            }
        });

    }


    private void changeFragment(int currentPosition) {
        if (currentPosition==2){
//        startActivity(new Intent(this, TestHeixiu.class));
        startActivity(new Intent(this, SendTxtAndYuyinAct.class));
         }else {
            currentPositions=currentPosition;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, mFragmentList.get(currentPosition));
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
         if (AppSessionEngine.getMyUserInfo()!=null){
             bottomNavigationBar1.setCurrentItem(currentPositions);

         }

//        MobclickAgent.onResume(this);

     }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
//         ConversationManagerKit.getInstance().addUnreadWatcher(this);

    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                ActivityTaskManager.getInstance().finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void getNetData(){
        HashMap<String, String> params = new HashMap<>();
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.GETUSERINFO, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                userInfoData= com.wuye.piaoliuim.utils.GsonUtil.getDefaultGson().fromJson(requestEntity, UserInfoData.class);
                isBindPhone=userInfoData.res.getListList().getPhone();

                AppSessionEngine.setUserInfo(userInfoData);
                if (TIMManager.getInstance().getLoginStatus()==TIM_STATUS_LOGINED){
                    Log.i("------","已经");


                }else {
                    singSure();

                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public void singSure( ) {
        HashMap<String, String> params = new HashMap<>();
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.SIN, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                SingData singData = com.vise.xsnow.common.GsonUtil.gson().fromJson(requestEntity, SingData.class);
                      imLogin(userInfoData.res.getListList().getId(),singData.res.getUsersig());


            }


            @Override
            public void onError(String message) {
                AppSessionEngine.clear();
            }
        });
    }
    private void imLogin(String token,String userSig){
        TUIKit.login(token, userSig, new IUIKitCallBack() {
            @Override
            public void onError(String module, final int code, final String desc) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ToastUtil.show(getBaseContext(),"IM登录失败请退出重新登录"+desc+code);
                        Log.i("++======+++",desc+"desc"+code);
                    }
                });
                DemoLog.i("TAG", "imLogin errorCode = " + code + ", errorInfo = " + desc);
            }

            @Override
            public void onSuccess(Object data) {
                SharedPreferences shareInfo = getSharedPreferences(Constants.USERINFO, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shareInfo.edit();
                editor.putBoolean(Constants.AUTO_LOGIN, true);
                setImMyImaege(ImagUrlUtils.getImag(userInfoData.res.getListList().getLitpic()));
                Log.i("初始化头像",ImagUrlUtils.getImag(userInfoData.res.getListList().getLitpic()));
                updateProfile(userInfoData.res.getListList().getName());
               AppSessionEngine.setIm(userSig);
                editor.commit();

                setLixian();
//                Intent intent = new Intent(LoginForDevActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
    }
     private void setLixian(){
        TIMOfflinePushSettings settings = new TIMOfflinePushSettings();
//开启离线推送
        settings.setEnabled(true);

//设置收到 C2C 离线消息时的提示声音，以把声音文件放在 res/raw 文件夹下为例
//        settings.setC2cMsgRemindSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dudulu));
//设置收到群离线消息时的提示声音，以把声音文件放在 res/raw 文件夹下为例
//        settings.setGroupMsgRemindSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dudulu));
         TIMManager.getInstance().setOfflinePushSettings(settings);
     }


    private void setImMyImaege(String mIconUrl){
        HashMap<String, Object> hashMap = new HashMap<>();
// 头像，mIconUrl 就是您上传头像后的 URL，可以参考 Demo 中的随机头像作为示例
        if (!TextUtils.isEmpty(mIconUrl)) {
            hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_FACEURL, mIconUrl);
        }
        TIMFriendshipManager.getInstance().modifySelfProfile(hashMap, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                DemoLog.d("初始化头像","初始化头像失败");
            }
            @Override
            public void onSuccess() {
            }
        });
    }
    private void setAlias(){
        mPushAgent.setAlias(AppSessionEngine.getToken(), "app", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {

            }
        });
        InAppMessageManager.getInstance(this).showCardMessage(this, "main",
                new IUmengInAppMsgCloseCallback() {
                    @Override
                    public void onClose() {
                        Log.i("Ppppppp","关闭");
                     }

                });
    }
    private void updateProfile(String name) {
        HashMap<String, Object> hashMap = new HashMap<>();


        // 昵称
        hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_NICK, name);



        TIMFriendshipManager.getInstance().modifySelfProfile(hashMap, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                DemoLog.e("sdsdsd", "modifySelfProfile err code = " + i + ", desc = " + s);
            }

            @Override
            public void onSuccess() {
                DemoLog.i("ooooo", "modifySelfProfile success");
            }
        });
    }

    @Override
    public void updateUnread(int count) {

     }

    @Override
    protected void onStop() {
        super.onStop();
//        ConversationManagerKit.getInstance().destroyConversation();

    }
    private void prepareThirdPushToken() {
        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();

        if (ThirdPushTokenMgr.USER_GOOGLE_FCM) {
            return;
        }
        if (IMFunc.isBrandHuawei()) {
            // 华为离线推送
            HMSAgent.connect(this, new ConnectHandler() {
                @Override
                public void onConnect(int rst) {
                    DemoLog.i(TAG, "huawei push HMS connect end:" + rst);
                }
            });
            getHuaWeiPushToken();
        }
        if (IMFunc.isBrandVivo()) {
            // vivo离线推送
//            PushClient.getInstance(getApplicationContext()).turnOnPush(new IPushActionListener() {
//                @Override
//                public void onStateChanged(int state) {
//                    if (state == 0) {
//                        String regId = PushClient.getInstance(getApplicationContext()).getRegId();
//                        DemoLog.i(TAG, "vivopush open vivo push success regId = " + regId);
//                        ThirdPushTokenMgr.getInstance().setThirdPushToken(regId);
//                        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();
//                    } else {
//                        // 根据vivo推送文档说明，state = 101 表示该vivo机型或者版本不支持vivo推送，链接：https://dev.vivo.com.cn/documentCenter/doc/156
//                        DemoLog.i(TAG, "vivopush open vivo push fail state = " + state);
//                    }
//                }
//            });
        }
    }
    private void getHuaWeiPushToken() {
        HMSAgent.Push.getToken(new GetTokenHandler() {
            @Override
            public void onResult(int rtnCode) {
                DemoLog.i(TAG, "huawei push get token result code: " + rtnCode);
            }
        });
    }
    //权限检查
    public static boolean checkPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                String[] permissionsArray = permissions.toArray(new String[1]);
                ActivityCompat.requestPermissions(activity,
                        permissionsArray,
                        REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
             String unreadStr = "" + event.message;

            if (!unreadStr .equals("0") ) {

                bottomNavigationBar1.setMsg(3,unreadStr);

            } else {
                bottomNavigationBar1.hideMsg(3);

            }
//            if (count > 100) {
//                unreadStr = "99+";
//                bottomNavigationBar1.setMsg(3,unreadStr);
//
//            }
         }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
