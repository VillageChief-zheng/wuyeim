package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.MainActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.ImgcodeDialog;
import com.wuye.piaoliuim.utils.TelNumMatch;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName BindPhone
 * @Description
 * @Author VillageChief
 * @Date 2019/12/31 15:21
 */
public class BindPhone extends BaseActivity {

    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.tv_tiaoguo)
    TextView tvTiaoguo;
    @BindView(R.id.qq)
    TextView qq;
    @BindView(R.id.edphone)
    EditText edphone;
    @BindView(R.id.tv_sendcode)
    TextView tvSendcode;
    @BindView(R.id.smcode)
    EditText smcode;
    @BindView(R.id.bg_login)
    Button bgLogin;

    ImgcodeDialog imgcodeDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindphone);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.im_back, R.id.tv_tiaoguo, R.id.edphone, R.id.tv_sendcode, R.id.bg_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                startActivity(new Intent(getBaseContext(), MainActivity.class));
   finish();
                break;
            case R.id.tv_tiaoguo:
                startActivity(new Intent(getBaseContext(), MainActivity.class));
              finish();
                break;
            case R.id.edphone:
                break;
            case R.id.tv_sendcode:
                //发送验证码
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
        String phoneStr = edphone.getText().toString().trim();
        String phonecode = smcode.getText().toString().trim();
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
                tvSendcode.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                tvSendcode.setText(getString(R.string.reSend));
            }
        });
    }
}
