package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.MainActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.TokenUserInfo;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.AppSessionEngine;
import com.wuye.piaoliuim.utils.ImgcodeDialog;
import com.wuye.piaoliuim.utils.TelNumMatch;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName ToBindPhoneAct
 * @Description
 * @Author VillageChief
 * @Date 2020/1/6 17:12
 */
public class ToBindPhoneAct extends BaseActivity {

    @BindView(R.id.edphone1)
    EditText edphone1;
    @BindView(R.id.sendcode1)
    TextView sendcode1;
    @BindView(R.id.smcode1)
    EditText smcode1;
    @BindView(R.id.bt_submit1)
    Button btSubmit1;
    @BindView(R.id.ll_tow)
    LinearLayout llTow;
    ImgcodeDialog imgcodeDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tobindphone);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.sendcode1, R.id.bt_submit1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendcode1:
                //发送验证码
                String phoneStr = edphone1.getText().toString().trim();
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
            case R.id.bt_submit1:
                bindPhone();
                break;
        }
    }

    private void getPhonecode(String tupianCode, String phoneStr) {

        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PHONE, phoneStr);
        params.put(UrlConstant.V_CODE, tupianCode);
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.BINDPHONECODE, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                countDown();
            }

            @Override
            public void onError(String message) {

            }
        });

    } private void bindPhone( ) {
        String phoneStr = edphone1.getText().toString().trim();
        String phonecode = smcode1.getText().toString().trim();
        if (!TelNumMatch.isValidPhoneNumber(phoneStr)) {
            loading("请输入正确手机号").setOnlySure();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PHONE, phoneStr);
        params.put(UrlConstant.CODE, phonecode);
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.BINDPHONECODE, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                UserInfoData userInfoData=AppSessionEngine.getMyUserInfo();
                userInfoData.res.getListList().setPhone(phoneStr);
                 AppSessionEngine.setUserInfo(userInfoData);
                  startActivity(new Intent(getBaseContext(), MainActivity.class));
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
                sendcode1.setClickable(false);
                sendcode1.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                sendcode1.setClickable(true);

                sendcode1.setText(getString(R.string.reSend));
            }
        });
    }
}
