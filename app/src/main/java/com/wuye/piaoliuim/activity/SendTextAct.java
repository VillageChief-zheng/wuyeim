package com.wuye.piaoliuim.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.ToastUtil;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName SendTextAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/25 14:17
 */
public class SendTextAct extends BaseActivity {
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.bg_send)
    Button bgSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendtext);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.bg_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bg_send:
                sendText();
                break;
        }
    }
    private void sendText() {
        String textStr=edContent.getText().toString().trim();
        if (textStr.equals("")){
            loading("请输入内容").setOnlySure();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.TYPE,"1");
        params.put(UrlConstant.CONTENT,textStr);
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.SENDTEXT, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {

                ToastUtil.show(getBaseContext(),"发送成功");
                finish();
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
