package com.wuye.piaoliuim.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.utils.RoundImageView;
import com.wuye.piaoliuim.utils.WhewView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName TestHeixiu
 * @Description
 * @Author VillageChief
 * @Date 2019/12/31 13:52
 */
public class TestHeixiu extends BaseActivity {


    @BindView(R.id.wv)
    WhewView wv;
    @BindView(R.id.my_photo)
    RoundImageView myPhoto;
    @BindView(R.id.byt)
    Button byt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heixiu);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.wv, R.id.my_photo,R.id.byt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wv:
                break;
                case R.id.byt:
                     wv.chongzhi();
                break;
            case R.id.my_photo:
                if (wv.isStarting()) {
//如果动画正在运行就停止，否则就继续执行
                         wv.stop();

                } else {
// 执行动画
                    wv.start();
                }
                break;
        }
    }
}
