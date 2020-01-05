package com.wuye.piaoliuim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.BaseFragement;
import com.chuange.basemodule.utils.ActivityTaskManager;
import com.chuange.basemodule.utils.ToastUtil;
import com.meizu.cloud.pushsdk.notification.MPushMessage;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.umeng.message.PushAgent;
import com.vise.xsnow.common.GsonUtil;
import com.wuye.piaoliuim.activity.BaseImActivity;
import com.wuye.piaoliuim.activity.EditInfoAct;
import com.wuye.piaoliuim.activity.FangdaPicAct;
import com.wuye.piaoliuim.activity.IndexAct;
import com.wuye.piaoliuim.activity.LiwuTsetAct;
import com.wuye.piaoliuim.activity.OpinionAct;
import com.wuye.piaoliuim.activity.PaihangAct;
import com.wuye.piaoliuim.activity.RechangeAct;
import com.wuye.piaoliuim.activity.SendTxtAndYuyinAct;
import com.wuye.piaoliuim.activity.SendYuyinAct;
import com.wuye.piaoliuim.activity.TestHeixiu;
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
import com.wuye.piaoliuim.utils.AppSessionEngine;
import com.wuye.piaoliuim.utils.DemoLog;
import com.wuye.piaoliuim.utils.GenerateTestUserSig;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.tencent.imsdk.TIMManager.TIM_STATUS_LOGINED;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation_bar1)
    BottomBarLayout bottomNavigationBar1;
    @BindView(R.id.main_container)
    FrameLayout mainContainer;
  int currentPositions;

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    UserInfoData userInfoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        initData();
        PushAgent.getInstance(this).onAppStart();
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_color));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigation_bar_color));
            int vis = getWindow().getDecorView().getSystemUiVisibility();
            vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            getWindow().getDecorView().setSystemUiVisibility(vis);
        }

     }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }


    private void initData() {
        mFragmentList.add(PiaoliuFragment.newInstance());
        mFragmentList.add(  FindFragment.newInstance() );
        mFragmentList.add(  FindFragment.newInstance() );
        mFragmentList.add(ImFragment.newInstance());
         mFragmentList.add(MyFragment.newInstance());
        changeFragment(0);
         bottomNavigationBar1.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int i, int currentPosition) {
                changeFragment(currentPosition);

            }
        });
        getNetData();

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
        bottomNavigationBar1.setCurrentItem(currentPositions);
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

                editor.commit();

//                Intent intent = new Intent(LoginForDevActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
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
}
