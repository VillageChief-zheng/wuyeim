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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.view.DialogView;
import com.chuange.basemodule.view.NavigationTopView;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.ChannelAdapter;
import com.wuye.piaoliuim.bean.ChannelModel;
import com.wuye.piaoliuim.bean.TopListData;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.pay.WXPayUtil;
import com.wuye.piaoliuim.utils.AppSessionEngine;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    ArrayList<ChannelModel> channelModels = new ArrayList<>();
    @BindView(R.id.tv_myjb)
    TextView tvMyjb;
    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;
    @BindView(R.id.bt_top)
    Button btTop;
    @BindView(R.id.top)
    NavigationTopView top;
    TopListData topListData;
   int postione=0;
     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        ButterKnife.bind(this);
        getTopList();

     }

    //初始化充值通道
    public void initChannel(TopListData topListData) {
//        ChannelModel channelModel = new ChannelModel(ChannelModel.ONE, "1元", "500金币", "", "1",1);
//        ChannelModel channelModels = new ChannelModel(ChannelModel.TWO, "6元", "600金币", "", "6",1);
//        ChannelModel channelModelss = new ChannelModel(ChannelModel.THREE, "10元", "1000金币", "+60金币", "10",1);
//        ChannelModel channelModelsss = new ChannelModel(ChannelModel.THREE, "15元", "1500金币", "+120金币", "15",1);
//        ChannelModel channelModelsssss = new ChannelModel(ChannelModel.THREE, "20元", "2000金", "+200金币", "20",1);
//        ChannelModel channelModelssss = new ChannelModel(ChannelModel.THREE, "50元", "5000金币", "+500金币", "20",1);
//        channelModels.add(channelModel);
//        channelModels.add(channelModels);
//        list.add(channelModelss);
//        list.add(channelModelsss);
//        list.add(channelModelsssss);
//        list.add(channelModelssss);
        getAdapterList(topListData);
         channelAdapter = new ChannelAdapter();
        channelAdapter.replaceAll(channelModels);
        recommendGv.setHasFixedSize(true);
        recommendGv.setLayoutManager(new GridLayoutManager(this, 2));
        recommendGv.setAdapter(channelAdapter);
        channelAdapter.changetShowDelImage(true, 5);
        channelAdapter.setOnCheckChangedListener(this);
        top.setRightName("账单");
        tvMyjb.setText( AppSessionEngine.getMyUserInfo().res.getListList().getUser_gold());
        postione=0;
        top.setOnRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), MyZhangDanAct.class));
            }
        });
    }
  private void getTopList(){
           HashMap<String, String> params = new HashMap<>();
          RequestManager.getInstance().publicPostMap(this, params, UrlConstant.PLAYLIST, new RequestListener<String>() {
              @Override
              public void onComplete(String requestEntity) {
                  topListData =GsonUtil.getDefaultGson().fromJson(requestEntity, TopListData.class);
                  initChannel(topListData);

              }

              @Override
              public void onError(String message) {

              }
          });

  }
  private void getAdapterList(TopListData topListData){
      channelModels=new ArrayList<>();
      for (int i=0;i<topListData.res.getPuList().size();i++){
          TopListData.Res.TopList topList=topListData.res.getPuList().get(i);
//          ChannelModel channelModelss = new ChannelModel(ChannelModel.THREE, "10元", "1000金币", "+60金币", "10",1);
          if (i==0){
              channelModels.add(new ChannelModel(ChannelModel.ONE,getMoney(topList.getMoney())+"元",topList.getGold()+"金币","",topList.getMoney(),topList.getId()));
           }else {
              if (topList.getGive_gold().equals("0")){
                  channelModels.add(new ChannelModel(ChannelModel.THREE,getMoney(topList.getMoney())+"元",topList.getGold()+"金币","",topList.getMoney(),topList.getId()));

              }else {
                  channelModels.add(new ChannelModel(ChannelModel.THREE,getMoney(topList.getMoney())+"元",topList.getGold()+"金币","+"+topList.getGive_gold()+"金币",topList.getMoney(),topList.getId()));

              }

          }
      }
   }
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onItemChecked(int position) {
        postione=position;
        Log.i("ppppp", position + "");
    }

    @OnClick({ R.id.bt_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_top:
                Intent intent=new Intent(this,SubMitPay.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("money",channelModels.get(postione));
                intent.putExtras(bundle);
                startActivity(intent);
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
    private int getMoney(String moneys){
        int money;
        money=  Integer.parseInt(moneys)/100;
        return money;
    }
}
