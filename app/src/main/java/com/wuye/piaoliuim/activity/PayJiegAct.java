package com.wuye.piaoliuim.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.AppSessionEngine;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName PayJiegAct
 * @Description
 * @Author VillageChief
 * @Date 2020/1/6 10:29
 */
public class PayJiegAct extends BaseActivity {
    @BindView(R.id.bt_finsh)
    Button btFinsh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_result);
        ButterKnife.bind(this);
        getNetData();
        btFinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
    public void getNetData(){
        HashMap<String, String> params = new HashMap<>();
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.GETUSERINFO, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                UserInfoData   userInfoData= GsonUtil.getDefaultGson().fromJson(requestEntity, UserInfoData.class);

                AppSessionEngine.setUserInfo(userInfoData);
                Log.i("-----充值后金币",AppSessionEngine.getMyUserInfo().res.getListList().getUser_gold());
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
