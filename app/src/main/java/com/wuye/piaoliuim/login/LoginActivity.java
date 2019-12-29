package com.wuye.piaoliuim.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseData;
import com.vise.xsnow.common.GsonUtil;
import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.MainActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.TokenUserInfo;
import com.wuye.piaoliuim.bean.UserTokenData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.AppSessionEngine;
 import com.wuye.piaoliuim.utils.PhoneUtile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ClassName LoginActivity
 * @Description
 * @Author VillageChief
 * @Date 2019/12/13 16:26
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.qq)
    TextView qq;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.tuxinghuoqu)
    Button tuxinghuoqu;
    @BindView(R.id.edphone)
    EditText edphone;
    @BindView(R.id.sendcode)
    TextView sendcode;
    @BindView(R.id.smcode)
    EditText smcode;
    @BindView(R.id.tv_pswlogin)
    TextView tvPswlogin;
    @BindView(R.id.bg_login)
    Button bgLogin;
    @BindView(R.id.qq_login)
    ImageView qqLogin;
    @BindView(R.id.wx_login)
    ImageView wxLogin;
    @BindView(R.id.tupiancode)
    EditText tupiancode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

//       TokenUserInfo tokenData= new TokenUserInfo() ;
//       tokenData.setToken("llll");
//       AppSessionEngine.setTokenUserInfo(tokenData);
//       TokenUserInfo tokenDatas= AppSessionEngine.getUserTokenInfo() ;
//       Log.i("ppppppp",tokenDatas.getToken());

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tuxinghuoqu, R.id.sendcode, R.id.bg_login, R.id.qq_login, R.id.wx_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tuxinghuoqu:
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request
                                        .Builder()
                                        .url("http://piaoliu.kuaiyueread.com/Useracc/getVerifyCode")//要访问的链接
                                        .build();

                                Call call = client.newCall(request);

                                call.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                     }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {

                                        InputStream is = response.body().byteStream(); //获取 字节输入流
                                        final Bitmap bitmap = BitmapFactory.decodeStream(is); // 把获取到的 数据 转换成 Bitmap 类型的
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                image.setImageBitmap(bitmap);

                                            }
                                        });

                                    }
                                });
                            }
                        }
                ).start();
                break;
            case R.id.sendcode:
                getPhonecode();
                break;
            case R.id.bg_login:
                logIn();
                break;
            case R.id.qq_login:
                break;
            case R.id.wx_login:
                break;
        }
    }
   private void getPhonecode(){
       String tupianCode=tupiancode.getText().toString().trim();
       String phoneStr=edphone.getText().toString().trim();

       HashMap<String, String> params = new HashMap<>();
       params.put(UrlConstant.PHONE,phoneStr);
       params.put(UrlConstant.V_CODE,tupianCode);
       RequestManager.getInstance().publicPostMap(this, params, UrlConstant.LOGINPhoneCode, new RequestListener<String>() {
           @Override
           public void onComplete(String requestEntity) {

           }

           @Override
           public void onError(String message) {

           }
       });

   }
    public void logIn() {
        String phoneStr=edphone.getText().toString().trim();
        String phoneCode=smcode.getText().toString().trim();
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PHONE,phoneStr);
        params.put(UrlConstant.CODE,phoneCode);
        params.put(UrlConstant.SINGNINREGION,"");
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.LOGIN, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                TokenUserInfo tokenUserInfo = GsonUtil.gson().fromJson(requestEntity, TokenUserInfo.class);
                 AppSessionEngine.setTokenUserInfo(tokenUserInfo);
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
