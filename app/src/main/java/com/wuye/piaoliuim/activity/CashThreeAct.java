package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.ToastUtil;
import com.chuange.basemodule.view.NavigationTopView;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.CashTypeAdapter;
import com.wuye.piaoliuim.adapter.YiJIanTypeAdapter;
import com.wuye.piaoliuim.bean.ChannelModel;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CashThreeAct extends BaseActivity implements CashTypeAdapter.OnCheckedChangedListener{
    @BindView(R.id.topView)
    NavigationTopView topView;
    @BindView(R.id.tv_mlnumber)
    TextView tvMlnumber;
    @BindView(R.id.tv_jine)
    TextView tvJine;
    @BindView(R.id.tvtxjl)
    TextView tvtxjl;
    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;
    @BindView(R.id.bg_tixian)
    Button bgTixian;

    CashTypeAdapter cashTypeAdapter;//通道adapter
    ArrayList<ChannelModel> list = new ArrayList<>();
    String type = "";
    String mlNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityrecharge_three);
         ButterKnife.bind(this);
        initCash();
        mlNumber = getIntent().getStringExtra("ml");
        setDate();
    }
    private void setDate(){
        tvMlnumber.setText(mlNumber);
        tvJine.setText("约"+getMoney(mlNumber)+"元");
    }
    private int getMoney(String moneys){
        int money;
        money=  Integer.parseInt(moneys)/100;
        return money;
    }
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
    //初始化提现通道
    public void initCash() {
        ChannelModel channelModel = new ChannelModel(ChannelModel.ONE, "3元", "500金币", "3", "需1000魅力",1);
        ChannelModel channelModels = new ChannelModel(ChannelModel.ONE, "50元", "600金币", "50", "需10000魅力",1);
        ChannelModel channelModelss = new ChannelModel(ChannelModel.ONE, "600元", "1000金币", "600", "需100000魅力",1);
        ChannelModel channelModelsss = new ChannelModel(ChannelModel.ONE, "7000元", "1500金币", "100", "需1000000魅力",1);
        list.add(channelModel);
        list.add(channelModels);
        list.add(channelModelss);
        list.add(channelModelsss);
        cashTypeAdapter = new CashTypeAdapter();
        cashTypeAdapter.replaceAll(list);
        recommendGv.setHasFixedSize(true);
        recommendGv.setLayoutManager(new GridLayoutManager(this, 2));
        recommendGv.setAdapter(cashTypeAdapter);
        cashTypeAdapter.changetShowDelImage(true, 4);
        cashTypeAdapter.setOnCheckChangedListener(this);
        type = list.get(0).addJinbi + "";


    }

        @OnClick({R.id.tvtxjl, R.id.bg_tixian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvtxjl:
                startActivity(new Intent(getBaseContext(), CashHistoryAct.class));

                break;
            case R.id.bg_tixian:
                subMit();
                break;
        }
    }

    @Override
    public void onItemChecked(int position) {
        type = list.get(position).addJinbi + "";

    }
    private void subMit() {

        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.MONEY, type);
         RequestManager.getInstance().publicPostMap(this, params, UrlConstant.CACHS, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
             ToastUtil.show(getBaseContext(),"申请成功");
             finish();
             }

            @Override
            public void onError(String message) {

            }
        });

    }
}
