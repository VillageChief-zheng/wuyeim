package com.wuye.piaoliuim.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName ChangePhoneAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/16 15:52
 */
public class ChangePhoneAct extends BaseActivity {
    @BindView(R.id.edphone)
    EditText edphone;
    @BindView(R.id.sendcode)
    TextView sendcode;
    @BindView(R.id.smcode)
    EditText smcode;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.ll_one)
    LinearLayout llOne;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changephone);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.sendcode, R.id.bt_submit, R.id.sendcode1, R.id.bt_submit1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendcode:
                break;
            case R.id.bt_submit:
                break;
            case R.id.sendcode1:
                break;
            case R.id.bt_submit1:
                break;
        }
    }
}
