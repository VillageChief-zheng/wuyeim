package com.wuye.piaoliuim.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName SendTextAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/25 14:17
 */
public class SendTextAct extends BaseActivity {
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.bg_send)
    Button bgSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendtext);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.bg_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bg_send:
                break;
        }
    }
}
