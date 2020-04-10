package com.wuye.piaoliuim.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.ToastUtil;
import com.chuange.basemodule.view.NavigationTopView;
import com.wuye.piaoliuim.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CashTowAct extends BaseActivity {
    @BindView(R.id.topView)
    NavigationTopView topView;
    @BindView(R.id.tvbindphone)
    TextView tvbindphone;
    @BindView(R.id.tvfuzhi)
    Button tvfuzhi;
    @BindView(R.id.bt_next)
    Button btNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityrecharge_tow);
        ButterKnife.bind(this);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tvfuzhi, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvfuzhi:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText("夜色瓶子");
                ToastUtil.show(getBaseContext(),"复制成功");
                break;
            case R.id.bt_next:
                startActivity(new Intent(getBaseContext(), CashThreeAct.class));
            finish();
                break;
        }
    }
}
