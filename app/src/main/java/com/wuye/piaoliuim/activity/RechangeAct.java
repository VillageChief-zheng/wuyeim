package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.view.DialogView;
import com.chuange.basemodule.view.NavigationTopView;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.ChannelAdapter;
import com.wuye.piaoliuim.bean.ChannelModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName RechangeAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/16 10:55
 */
public class RechangeAct extends BaseActivity implements ChannelAdapter.OnCheckedChangedListener , DialogView.DialogViewListener {

    ChannelAdapter channelAdapter;//通道adapter
    ArrayList<ChannelModel> list = new ArrayList<>();
    @BindView(R.id.tv_myjb)
    TextView tvMyjb;
    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;
    @BindView(R.id.bt_top)
    Button btTop;
    @BindView(R.id.top)
    NavigationTopView top;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        ButterKnife.bind(this);
        initChannel();
    }

    //初始化充值通道
    public void initChannel() {
        ChannelModel channelModel = new ChannelModel(ChannelModel.ONE, "1元", "500金币", "", "1");
        ChannelModel channelModels = new ChannelModel(ChannelModel.TWO, "6元", "600金币", "", "6");
        ChannelModel channelModelss = new ChannelModel(ChannelModel.THREE, "10元", "1000金币", "+60金币", "10");
        ChannelModel channelModelsss = new ChannelModel(ChannelModel.THREE, "15元", "1500金币", "+120金币", "15");
        ChannelModel channelModelsssss = new ChannelModel(ChannelModel.THREE, "20元", "2000金", "+200金币", "20");
        ChannelModel channelModelssss = new ChannelModel(ChannelModel.THREE, "50元", "5000金币", "+500金币", "20");
        list.add(channelModel);
        list.add(channelModels);
        list.add(channelModelss);
        list.add(channelModelsss);
        list.add(channelModelsssss);
        list.add(channelModelssss);
        channelAdapter = new ChannelAdapter();
        channelAdapter.replaceAll(list);
        recommendGv.setHasFixedSize(true);
        recommendGv.setLayoutManager(new GridLayoutManager(this, 2));
        recommendGv.setAdapter(channelAdapter);
        channelAdapter.changetShowDelImage(true, 5);
        channelAdapter.setOnCheckChangedListener(this);
        top.setRightName("账单");
        top.setOnRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), MyZhangDanAct.class));
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onItemChecked(int position) {
        Log.i("ppppp", position + "");
    }

    @OnClick({ R.id.bt_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_top:
                loading(R.layout.dialog_pay, this).setOutsideClose(true).setGravity(Gravity.BOTTOM);
                break;
        }
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.pay_wx:

                break;
            case R.id.pay_zfb:

                break;
            case R.id.tv_cancle:
                cancelLoading();
                break;
        }
    }
    @Override
    public void onView(View view) {
     view.findViewById( R.id.pay_wx).setOnClickListener(this);
     view.findViewById( R.id.pay_zfb).setOnClickListener(this);
     view.findViewById( R.id.tv_cancle).setOnClickListener(this);
    }
}
