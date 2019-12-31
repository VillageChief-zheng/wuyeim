package com.wuye.piaoliuim.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.ToastUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.AliPAydata;
import com.wuye.piaoliuim.bean.ChannelModel;
import com.wuye.piaoliuim.bean.PayData;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.pay.WXPayUtil;
import com.wuye.piaoliuim.pay.ZFBPayUtil;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wuye.piaoliuim.WuyeApplicatione.getContext;

/**
 * @ClassName SubMitPay
 * @Description
 * @Author VillageChief
 * @Date 2019/12/31 9:37
 */
public class SubMitPay extends BaseActivity {

    @BindView(R.id.zfblogo)
    ImageView zfblogo;
    @BindView(R.id.zfb)
    ImageView zfb;
    @BindView(R.id.wxlogo)
    ImageView wxlogo;
    @BindView(R.id.wchat)
    ImageView wchat;
    @BindView(R.id.bt_submit)
    Button btSubmit;

  ChannelModel channelModel;
    PayData payData;
    int paytype=1;//微信
    AliPAydata aliPAydata;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitpay);
        ButterKnife.bind(this);
    channelModel= (ChannelModel) getIntent().getSerializableExtra("money");
    }

    private void initPay(){
        btSubmit.setText("微信支付"+getMoney(channelModel.jine)+"元");
    }
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.zfb, R.id.wchat, R.id.bt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zfb:
                btSubmit.setText("支付宝支付"+getMoney(channelModel.jine)+"元");
                paytype=2;

                break;
            case R.id.wchat:
                btSubmit.setText("微信支付"+getMoney(channelModel.jine)+"元");
                paytype=1;
                break;
            case R.id.bt_submit:
                topPay(paytype);
                break;
        }
    }
    private void topPay(int type){
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PAY_TYPE,type+"");
        params.put(UrlConstant.MPNYTYPE,channelModel.imgSrc+"");
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.PAYWACHATANDALI, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                if (paytype == 1) {
                    payData= GsonUtil.getDefaultGson().fromJson(requestEntity, PayData.class);
                    final IWXAPI msgApi = WXAPIFactory.createWXAPI(getBaseContext(), null);
                    WXPayUtil pay = new WXPayUtil();
                    pay.payWX(getContext(), payData, msgApi);
                }else {
                    aliPAydata= GsonUtil.getDefaultGson().fromJson(requestEntity, AliPAydata.class);
                    ZFBPayUtil zfb = new ZFBPayUtil();
                    zfb.payZFB(getContext(), aliPAydata.aliPayData); // 调用
                }

             }

            @Override
            public void onError(String message) {

            }
        });
    }

    private int getMoney(String moneys){
        int money;
        money=  Integer.parseInt(moneys)/100;
        return money;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if (event.message.equals("shuaxin")){
            ToastUtil.show(getContext(),"成功跳转");
        }
    }
}
