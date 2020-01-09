package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.view.NavigationTopView;
import com.chuange.basemodule.view.SwitchButton;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.WuyeApplicatione;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.login.LoginActivity;
import com.wuye.piaoliuim.utils.AppSessionEngine;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.sql.Time;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName SettingAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/16 15:34
 */
public class SettingAct extends BaseActivity {
    @BindView(R.id.topView)
    NavigationTopView topView;
    @BindView(R.id.tv_bindphone)
    TextView tvBindphone;
    @BindView(R.id.ll_us)
    LinearLayout llUs;
    @BindView(R.id.Notifi_switch)
    SwitchButton NotifiSwitch;
    @BindView(R.id.ll_ts)
    LinearLayout llTs;
    @BindView(R.id.ll_changepsw)
    LinearLayout llChangepsw;
    @BindView(R.id.ll_yinsi)
    LinearLayout llYinsi;
    @BindView(R.id.tv_ver)
    TextView tvVer;
    @BindView(R.id.ll_ver)
    LinearLayout llVer;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.ll_clear)
    LinearLayout llClear;
    @BindView(R.id.bg_login)
    Button bgLogin;

    UserInfoData userInfoData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        userInfoData=AppSessionEngine.getMyUserInfo();
        initPhone();
    }
   private void initPhone(){
        String mobile=userInfoData.res.getListList().getPhone();
       String maskNumber = mobile.substring(0,3)+"****"+mobile.substring(7,mobile.length());
       tvBindphone.setText(maskNumber+" ");
       Log.i("ppppppppp",AppSessionEngine.getLocation());


   }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppSessionEngine.getLocation().equals("1")){
            NotifiSwitch.setChecked(true);
        }else {
            NotifiSwitch.setChecked(false);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.ll_us, R.id.ll_ts, R.id.ll_changepsw, R.id.ll_yinsi, R.id.tv_ver, R.id.tv_clear, R.id.bg_login,R.id.Notifi_switch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_us:
                startActivity(new Intent(this,ChangePhoneAct.class));
                break;
            case R.id.ll_ts:
                break;
            case R.id.ll_changepsw:
                startActivity(new Intent(this,ResetPswAct.class));
                break;
            case R.id.ll_yinsi:
                WuyeApplicatione.instance().webView("隐私协议",Constants.BASEURL+"/Public/home/agree/agreement.html");

                break;
            case R.id.tv_ver:
                break;
            case R.id.tv_clear:
                break;
            case R.id.bg_login:
                getOutLogin();

                break;
            case R.id.Notifi_switch:
                if (AppSessionEngine.getLocation().equals("1")){
                    AppSessionEngine.setLocation("2");
                    NotifiSwitch.setChecked(false);
                }else {
                    AppSessionEngine.setLocation("1");
                     NotifiSwitch.setChecked(true);
                }

                break;
        }
    }
    public void getOutLogin(){
        HashMap<String, String> params = new HashMap<>();
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.SINOUT, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                TIMManager.getInstance().logout(new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess() {
                        AppSessionEngine.clear();
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    }
                });

            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
