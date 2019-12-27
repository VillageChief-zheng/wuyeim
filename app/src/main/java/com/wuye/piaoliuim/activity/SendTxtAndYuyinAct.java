package com.wuye.piaoliuim.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName SendTxtAndYuyinAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/25 14:12
 */
public class SendTxtAndYuyinAct extends BaseActivity {
    @BindView(R.id.ll_wz)
    LinearLayout llWz;
    @BindView(R.id.ll_yuyin)
    LinearLayout llYuyin;
    @BindView(R.id.canle_tv)
    ImageView canleTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmain);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.ll_wz, R.id.ll_yuyin, R.id.canle_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_wz:
                break;
            case R.id.ll_yuyin:
                break;
            case R.id.canle_tv:
                break;
        }
    }
}
