package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.PiaoliuData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.HashMap;

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
                getNetData("1");
                break;
            case R.id.ll_yuyin:
   getNetData("2");
                break;
            case R.id.canle_tv:
                finish();
                break;
        }
    }
    public void getNetData(String type) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.TYPE,   "1");
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.RENGPINGZI, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
         if (type.equals("1")){
             startActivity(new Intent(getBaseContext(),SendTextAct.class));

         }else {
             startActivity(new Intent(getBaseContext(),SendYuyinAct.class));

         }
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
