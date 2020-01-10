package com.wuye.piaoliuim.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.ActivityTaskManager;
import com.chuange.basemodule.utils.ToastUtil;
import com.chuange.basemodule.view.DialogView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.tencent.tauth.UiError;
import com.vise.xsnow.common.GsonUtil;
import com.wuye.piaoliuim.MainActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.activity.BindPhone;
import com.wuye.piaoliuim.activity.ResetPswAct;
import com.wuye.piaoliuim.bean.SingData;
import com.wuye.piaoliuim.bean.TokenUserInfo;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.bean.UserTokenData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.AppSessionEngine;
import com.wuye.piaoliuim.utils.DemoLog;
import com.wuye.piaoliuim.utils.FileSizeUtil;
import com.wuye.piaoliuim.utils.GenerateTestUserSig;
import com.wuye.piaoliuim.utils.ImgcodeDialog;
import com.wuye.piaoliuim.utils.LocationProvider;
import com.wuye.piaoliuim.utils.MessageEvent;
import com.wuye.piaoliuim.utils.TelNumMatch;
import com.wuye.piaoliuim.utils.postMessageWx;
import com.wuye.piaoliuim.wxapi.QQLoginManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName LoginActivity
 * @Description
 * @Author VillageChief
 * @Date 2019/12/13 16:26
 */
public class LoginActivity extends BaseActivity implements QQLoginManager.QQLoginListener {

    ImgcodeDialog imgcodeDialog;
    @BindView(R.id.qq)
    TextView qq;
    @BindView(R.id.edphone)
    EditText edphone;
    @BindView(R.id.sendcode)
    Button sendcode;
    @BindView(R.id.smcode)
    EditText smcode;
    @BindView(R.id.tv_pswlogin)
    TextView tvPswlogin;
    @BindView(R.id.bg_login)
    Button bgLogin;
    @BindView(R.id.qq_login)
    ImageView qqLogin;
    @BindView(R.id.wx_login)
    ImageView wxLogin;


    TokenUserInfo tokenUserInfo;
    private LocationManager locationManager;
    LocationProvider myLocationListener;
    private QQLoginManager qqLoginManager;

    /**
     * 微信的登录
     */
    private IWXAPI api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ///< 报名服务
        api = WXAPIFactory.createWXAPI(this, "wx4b25184c83dbca16", true);
        api.registerApp("wx4b25184c83dbca16");
        //qqzhue c
        qqLoginManager = new QQLoginManager("1110049291", this);
        //       TokenUserInfo tokenData= new TokenUserInfo() ;
//       tokenData.setToken("llll");
//       AppSessionEngine.setTokenUserInfo(tokenData);
//       TokenUserInfo tokenDatas= AppSessionEngine.getUserTokenInfo() ;
//       Log.i("ppppppp",tokenDatas.getToken());
        EventBus.getDefault().register(this);
             AppSessionEngine.setLocation("1");
        edphone.addTextChangedListener(new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

       }

       @Override
       public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

       }

       @SuppressLint("ResourceAsColor")
       @Override
       public void afterTextChanged(Editable editable) {
           if (editable.length() > 0) {
               bgLogin.setBackgroundResource(R.drawable.fillet_grbg);
               bgLogin.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.white));
           } else {
               bgLogin.setBackgroundResource(R.drawable.loginbuttonbg);
               bgLogin.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.notinput));

           }
       }
   });
   smcode.addTextChangedListener(new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

       }

       @Override
       public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

       }

       @SuppressLint("ResourceAsColor")
       @Override
       public void afterTextChanged(Editable editable) {
           if (editable.length() > 0) {
               bgLogin.setBackgroundResource(R.drawable.fillet_grbg);
               bgLogin.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.white));
           } else {
               bgLogin.setBackgroundResource(R.drawable.loginbuttonbg);
               bgLogin.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.notinput));

           }
       }
   });
     }
   private void wChatLogin(){
       SendAuth.Req req = new SendAuth.Req();
       req.scope = "snsapi_userinfo";
       req.state = "wechat_sdk_demo_test";
       api.sendReq(req);
   }
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.sendcode, R.id.bg_login, R.id.qq_login, R.id.wx_login,R.id.tv_pswlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.sendcode:
//                getPhonecode();
                String phoneStr = edphone.getText().toString().trim();
                if (!TelNumMatch.isValidPhoneNumber(phoneStr)) {
                    loading("请输入正确手机号").setOnlySure();
                    return;
                }
                imgcodeDialog = new ImgcodeDialog("", new ImgcodeDialog.SendBackListener() {
                    @Override
                    public void sendBack(String inputText) {
                        imgcodeDialog.hideSoftkeyboard();
                        imgcodeDialog.dismiss();
                        getPhonecode(inputText, phoneStr);

                    }
                });
                imgcodeDialog.show(getSupportFragmentManager(), "kk");
                break;
            case R.id.bg_login:
                logIn();
                break;
            case R.id.qq_login:
                qqLoginManager.launchQQLogin();
                break;
            case R.id.wx_login:
                wChatLogin();
                break;
            case R.id.tv_pswlogin:
                startActivity(new Intent(this, PswLogin.class));
                break;
        }
    }

    private void getPhonecode(String tupianCode, String phoneStr) {

        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PHONE, phoneStr);
        params.put(UrlConstant.V_CODE, tupianCode);
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.LOGINPhoneCode, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                countDown();
            }

            @Override
            public void onError(String message) {

            }
        });

    }

    public void logIn() {

        String phoneStr = edphone.getText().toString().trim();
        String phoneCode = smcode.getText().toString().trim();
         if (!TelNumMatch.isValidPhoneNumber(phoneStr)) {
            loading("请输入正确手机号").setOnlySure();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PHONE, phoneStr);
        params.put(UrlConstant.CODE, phoneCode);
        if (getLocatione().equals("")){
            params.put(UrlConstant.SINGNINREGION, "");

        }else {
            params.put(UrlConstant.SINGNINREGION, getLocatione());

        }        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.LOGIN, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                  tokenUserInfo = GsonUtil.gson().fromJson(requestEntity, TokenUserInfo.class);
                AppSessionEngine.setTokenUserInfo(tokenUserInfo);
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
             }

            @Override
            public void onError(String message) {

            }
        });
    } public void wchatLogin(String code) {
           HashMap<String, String> params = new HashMap<>();
         params.put(UrlConstant.CODE, code);
         if (getLocatione().equals("")){
             params.put(UrlConstant.SINGNINREGION, "");

         }else {
             params.put(UrlConstant.SINGNINREGION, getLocatione());

         }
        RequestManager.getInstance().publicGettMap(this, params, UrlConstant.WECHATLOFIN, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                  tokenUserInfo = GsonUtil.gson().fromJson(requestEntity, TokenUserInfo.class);
                AppSessionEngine.setTokenUserInfo(tokenUserInfo);
                startActivity(new Intent(getBaseContext(), BindPhone.class));
                finish();
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void countDown() {

        countDown(Constants.COUNT_DOWN, new CountDownListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                sendcode.setClickable(false);

                sendcode.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                sendcode.setClickable(true);
                sendcode.setText(getString(R.string.reSend));
            }
        });
    }
    //微信登录
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(postMessageWx event) {
        if(event.wxUserInfo.getCountry().equals("wx")){
            wchatLogin(event.wxUserInfo.getCity());

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
    public void qqLogin(String token,String opendid) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.QQTOKEN, token);
        params.put(UrlConstant.QQOPENID, opendid);
        if (getLocatione().equals("")){
            params.put(UrlConstant.SINGNINREGION, "");

        }else {
            params.put(UrlConstant.SINGNINREGION, getLocatione());

        }
        Log.i("--------",getLocatione());
        RequestManager.getInstance().publicGettMap(this, params, UrlConstant.QQLOIN, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                tokenUserInfo = GsonUtil.gson().fromJson(requestEntity, TokenUserInfo.class);
                AppSessionEngine.setTokenUserInfo(tokenUserInfo);
//                startActivity(new Intent(getBaseContext(), BindPhone.class));
                finish();
            }

            @Override
            public void onError(String message) {

            }
        });
    }
@SuppressLint("MissingPermission")
private String getLocatione(){
     if (LocationProvider.getInstance().getLocations(this).equals("")||LocationProvider.getInstance().getLocations(this)==null){
        return "";
    }else {
        return LocationProvider.getInstance().getLocations(this);
    }
 }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 回调
        super.onActivityResult(requestCode, resultCode, data);
        qqLoginManager.onActivityResultData(requestCode, resultCode, data);
    }
    @Override
    public void onQQLoginSuccess(JSONObject jsonObject) {
        try {
            String  openID = jsonObject.getString("openid");
            String access_token = jsonObject.getString("access_token");
//            Log.i("pppppp",openID+"]]]"+access_token);
        qqLogin(access_token,openID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onQQLoginCancel() {
   ToastUtil.show(this,"取消qq登录");
    }

    @Override
    public void onQQLoginError(UiError uiError) {

    }
}
