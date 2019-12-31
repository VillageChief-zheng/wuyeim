package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.MainActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.login.LoginActivity;
import com.wuye.piaoliuim.utils.AppSessionEngine;

public class IndexAct extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        String userId = AppSessionEngine.getToken();
         countDown(Constants.SPLASH_TIME, new CountDownListener() {
                    @Override
                    public void onTick(long millisUntilFinished) {
                     }

                    @Override
                    public void onFinish() {


                        if (!TextUtils.isEmpty(userId)||userId!=null) {
                            goActivity();
                        } else {
                            goLogin();
                        }

                    }
                }
        );



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

}
