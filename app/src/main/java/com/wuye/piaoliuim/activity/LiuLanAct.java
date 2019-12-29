package com.wuye.piaoliuim.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.view.NavigationTopView;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.LiuLanAdapter;
import com.wuye.piaoliuim.adapter.LoveAdapter;
import com.wuye.piaoliuim.bean.LiulanData;
import com.wuye.piaoliuim.bean.LoveData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiuLanAct extends BaseActivity {


    @BindView(R.id.topView)
    NavigationTopView topView;
    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;

    LiulanData listData;
    LiuLanAdapter publicAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        ButterKnife.bind(this);
        topView.setTitle("浏览记录");
        getNetData();
    }
    public void getNetData(){
        HashMap<String, String> params = new HashMap<>();
         RequestManager.getInstance().publicPostMap(this, params, UrlConstant.JILULIEBIAO, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                listData= GsonUtil.getDefaultGson().fromJson(requestEntity, LiulanData.class);
                setAdapter(listData);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
    private void setAdapter(LiulanData listData){
        publicAdapter=new LiuLanAdapter( this,R.layout.adapter_jilu_item,listData.res.listList);
        @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL, false);
        recommendGv.setLayoutManager(managers);
        recommendGv.setAdapter(publicAdapter);
    }
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
