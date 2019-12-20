package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.view.NavigationTopView;
import com.chuange.basemodule.view.SwitchButton;
import com.wuye.piaoliuim.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.ll_us, R.id.ll_ts, R.id.ll_changepsw, R.id.ll_yinsi, R.id.tv_ver, R.id.tv_clear, R.id.bg_login})
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
                break;
            case R.id.tv_ver:
                break;
            case R.id.tv_clear:
                break;
            case R.id.bg_login:
                break;
        }
    }
}
