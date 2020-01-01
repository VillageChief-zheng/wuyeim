package com.wuye.piaoliuim.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.view.DialogView;
import com.vise.xsnow.common.GsonUtil;
import com.wuye.piaoliuim.MainActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.TokenUserInfo;
import com.wuye.piaoliuim.bean.VersionData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.login.LoginActivity;
import com.wuye.piaoliuim.utils.AppSessionEngine;

import java.util.HashMap;

public class IndexAct extends BaseActivity implements DialogView.DialogViewListener {
  TextView dialogContent;
    VersionData upList;
    String verSion="1.0.0.1";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
//        goActivity();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        wchatLogin();




    }
    private void tiaozhugan(){
        String userId = AppSessionEngine.getToken();

        if (!TextUtils.isEmpty(userId)||userId!=null) {
                            goActivity();
        } else {
                            goLogin();
        }
    }
    private void inite(String verinfo){
        loading(R.layout.activity_up,this);
        dialogContent.setText(verinfo);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.dialogCancel:
                 if (upList.res.getAndroid_isdownload().equals("1")){
                finish();
                cancelLoading();
                }else {
                    tiaozhugan();
                     cancelLoading();

                 }
                break;
            case R.id.dialogSure:
                if (upList.res.getAndroid_isdownload().equals("1")){
                    cancelLoading();

                    Log.i("ppppp","去市场");
                }else {
                    tiaozhugan();
                    cancelLoading();


                }
                break;

        }
    }

    public void goActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void goLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }



    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onView(View view) {
        view.findViewById(R.id.dialogCancel).setOnClickListener(this);
        view.findViewById(R.id.dialogSure).setOnClickListener(this);
        dialogContent=view.findViewById(R.id.dialogContent);
    }
    public void wchatLogin() {
        HashMap<String, String> params = new HashMap<>();
         RequestManager.getInstance().publicGettMap(this, params, UrlConstant.GETVERSION, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                  upList = GsonUtil.gson().fromJson(requestEntity, VersionData.class);
                  if (!verSion.equals(upList.res.getAndroid_version())){
                      inite(upList.res.getAndroid_download_msg());
                  }else {
                      countDown(Constants.SPLASH_TIME, new CountDownListener() {
                                  @Override
                                  public void onTick(long millisUntilFinished) {
                                  }

                                  @Override
                                  public void onFinish() {
                       tiaozhugan();
                                  }
                              }
                      );
                  }

            }

            @Override
            public void onError(String message) {

            }
        });
    }

}
