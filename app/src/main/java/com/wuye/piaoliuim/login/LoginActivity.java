package com.wuye.piaoliuim.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.ActivityTaskManager;
import com.chuange.basemodule.utils.ToastUtil;
import com.chuange.basemodule.view.DialogView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.xsnow.common.GsonUtil;
import com.wuye.piaoliuim.MainActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.activity.BindPhone;
import com.wuye.piaoliuim.activity.ResetPswAct;
import com.wuye.piaoliuim.bean.TokenUserInfo;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.AppSessionEngine;
import com.wuye.piaoliuim.utils.ImgcodeDialog;
import com.wuye.piaoliuim.utils.MessageEvent;
import com.wuye.piaoliuim.utils.TelNumMatch;
import com.wuye.piaoliuim.utils.postMessageWx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName LoginActivity
 * @Description
 * @Author VillageChief
 * @Date 2019/12/13 16:26
 */
public class LoginActivity extends BaseActivity {

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
//       TokenUserInfo tokenData= new TokenUserInfo() ;
//       tokenData.setToken("llll");
//       AppSessionEngine.setTokenUserInfo(tokenData);
//       TokenUserInfo tokenDatas= AppSessionEngine.getUserTokenInfo() ;
//       Log.i("ppppppp",tokenDatas.getToken());
        EventBus.getDefault().register(this);

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
                loading("请绑定手机号").setListener(new DialogView.DialogOnClickListener() {
                    @Override
                    public void onDialogClick(boolean isCancel) {
                     if (isCancel)
                        return;
                    else {
                        ToastUtil.show(getBaseContext(),"绑定");
                    }
                    }
                }).setOnlySure();
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
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PHONE, phoneStr);
        params.put(UrlConstant.CODE, phoneCode);
        params.put(UrlConstant.SINGNINREGION, "");
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.LOGIN, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                TokenUserInfo tokenUserInfo = GsonUtil.gson().fromJson(requestEntity, TokenUserInfo.class);
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
        params.put(UrlConstant.SINGNINREGION, "");
        RequestManager.getInstance().publicGettMap(this, params, UrlConstant.WECHATLOFIN, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                TokenUserInfo tokenUserInfo = GsonUtil.gson().fromJson(requestEntity, TokenUserInfo.class);
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
                sendcode.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                sendcode.setText(getString(R.string.reSend));
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(postMessageWx event) {
        wchatLogin(event.wxUserInfo.getCity());
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
 }
