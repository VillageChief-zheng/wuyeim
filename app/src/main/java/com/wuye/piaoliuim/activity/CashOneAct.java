package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.ToastUtil;
import com.chuange.basemodule.view.NavigationTopView;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CashOneAct extends BaseActivity {
    @BindView(R.id.topView)
    NavigationTopView topView;
    @BindView(R.id.tvbindphone)
    TextView tvbindphone;
    @BindView(R.id.edphone)
    EditText edphone;
    @BindView(R.id.edidcard)
    EditText edidcard;
    @BindView(R.id.bg_login)
    Button bgLogin;

    String phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityrecharge_one);
        ButterKnife.bind(this);
        phone = getIntent().getStringExtra("phone");
         tvbindphone.setText("已绑定手机号："+phone);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({  R.id.bg_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.bg_login:
                submit();
                break;
        }
    }
    private void submit(){
        String content = edphone.getText().toString().trim();
        String edCard = edidcard.getText().toString().trim();
        if (content.equals("")) {
            loading("请输入姓名").setOnlySure();
            return;
        }
        if (edCard.toString().length()<18) {
            loading("请输入身份证号").setOnlySure();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
         params.put(UrlConstant.REALNAME, content);
         params.put(UrlConstant.IDCARD, edCard);
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.BINDCARD, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                ToastUtil.show(getBaseContext(),"实名成功请提现!");
                finish();
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
