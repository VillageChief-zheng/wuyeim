package com.wuye.piaoliuim.wxapi;

import android.app.Activity;
import android.os.Bundle;
 import android.util.Log;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.chuange.basemodule.utils.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wuye.piaoliuim.bean.AccessToken;
import com.wuye.piaoliuim.bean.WXUserInfo;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.MessageEvent;
import com.wuye.piaoliuim.utils.postMessageWx;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hl on 2018/3/19.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";
    private static final int RETURN_MSG_TYPE_LOGIN = 1;  ///< 登录
    private static final int RETURN_MSG_TYPE_SHARE = 2;  ///< 分享
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ///< 这句没有写,是不能执行回调的方法的
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.e(TAG, "onReq" + baseReq.getType());
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.e(TAG, "onResp:------>");
        Log.e(TAG, "error_code:---->" + baseResp.errCode);
        int type = baseResp.getType(); //类型：分享还是登录

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ///< 用户拒绝授权
                finish();
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ///< 用户取消
                String message = "";
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    message = "取消了微信登录";
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    message = "取消了微信分享";
                }
                ToastUtil.show(this,message);
                finish();
                break;
            case BaseResp.ErrCode.ERR_OK:
                if (type == RETURN_MSG_TYPE_LOGIN) {
                     ///< 用户换取access_token的code，仅在ErrCode为0时有效
                    String code = ((SendAuth.Resp) baseResp).code;
                    ///< 这里拿到了这个code，去做2次网络请求获取access_token和用户个人信息
                    WXUserInfo wxUserInfo = new WXUserInfo();
                    wxUserInfo.setCity(code);
                    wxUserInfo.setCountry("wx");
                    ///< 发送广播到登录界面，把数据带过去; 可用EventBus
                    EventBus.getDefault().post(new postMessageWx(wxUserInfo ));
                     finish();
//                    getAccessToken(code);
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    ///< "微信分享成功"
                    finish();
                }
                break;
        }
    }

    /**
     * @param code 根据code再去获取AccessToken
     */
    private void getAccessToken(String code) {
        //        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        ///< Post方式也可以...
        //        RequestBody body = new FormBody.Builder()
        //                .add("appid", "替换为你的appid")
        //                .add("secret", "替换为你的app密钥")
        //                .add("code", code)
        //                .add("grant_type", "authorization_code")
        //                .build();
        url += "?appid=" + "wx4b25184c83dbca16" + "&secret=45a746222c61f51c5e038cbf865809a2"
                + "&code=" + code + "&grant_type=authorization_code";
        final Request request = new Request.Builder()
                .url(url)
                //.post(body)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                finish();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                AccessToken accessToken= GsonUtil.getDefaultGson().fromJson(json,AccessToken.class);
                Log.i("---------",accessToken.getAccess_token()+"XXXXXXXXXXX"+accessToken.getRefresh_token());
                 getUserInfo(accessToken.getAccess_token(), accessToken.getOpenid());
            }
        });
    }

    /**
     * @param access_token 根据access_token再去获取UserInfo
     */
    private void getUserInfo(String access_token, String openid) {
        //        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
         String url = "https://api.weixin.qq.com/sns/userinfo";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("access_token", access_token)
                .add("openid", openid)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                finish();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                WXUserInfo wxUserInfo = GsonUtil.getDefaultGson().fromJson(json,WXUserInfo.class);
                wxUserInfo.setCity(access_token);
                ///< 发送广播到登录界面，把数据带过去; 可用EventBus
                EventBus.getDefault().post(new postMessageWx(wxUserInfo ));

                finish();
            }
        });
    }

}
