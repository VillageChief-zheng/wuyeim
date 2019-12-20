package com.wuye.piaoliuim.pay;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.wuye.piaoliuim.bean.PayOrderBean;
import com.wuye.piaoliuim.config.UrlConstant;

import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Map;

/**
 * Created by admini on 2017/12/14.
 */

public class WXPayUtil {
    private static final String TAG = "MicroMsg.SDKSample.PayActivity";
    private PayReq req;
    private IWXAPI msgApi;
    private Map<String, String> resultunifiedorder;
    private StringBuffer sb;
     PayOrderBean payOrderBean;
    public void payWX(Context context, PayOrderBean payOrderBean, IWXAPI msgApi) {

        this.msgApi = msgApi;

        req = new PayReq();
        sb = new StringBuffer();
         this.msgApi.registerApp(UrlConstant.APP_ID);
         this.payOrderBean=payOrderBean;
//		GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
//		getPrepayId.execute();
        //genProductArgs();
        genPayReq();
        sendPayReq();
    }


    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(UrlConstant.API_KEY);

        String packageSign = PayMD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }
    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(UrlConstant.API_KEY);

//        this.sb.append("sign str\n"+sb.toString()+"\n\n");
        String appSign = PayMD5.getMessageDigest(sb.toString().getBytes());
        return appSign;
    }




    private void genPayReq() {
//        req.packageValue = "Sign=WXPay";//"prepay_id="+ordernum;
//         List<NameValuePair> signParams = new LinkedList<NameValuePair>();
//        signParams.add(new BasicNameValuePair("appid", payOrderBean.res.getAppId()));
//        signParams.add(new BasicNameValuePair("noncestr", payOrderBean.res.getNonceStr()));
//        signParams.add(new BasicNameValuePair("package", req.packageValue));
//        signParams.add(new BasicNameValuePair("partnerid", payOrderBean.res.getMchId()));
//        signParams.add(new BasicNameValuePair("prepayid",payOrderBean.res.getPrepayId() ));
//        signParams.add(new BasicNameValuePair("timestamp", payOrderBean.res.getTimeStamp()));
//        req.sign = genAppSign(signParams);//genAppSign(signParams);;//genAppSign(signParams);
//         sb.append("sign\n"+req.sign+"\n\n");
        PayReq payRequest = new PayReq();
        payRequest.appId =payOrderBean.res.getAppId();
        payRequest.partnerId =  payOrderBean.res.getMchId();
        payRequest.prepayId = payOrderBean.res.getPrepayId();
        payRequest.packageValue = "Sign=WXPay";//固定值
        payRequest.nonceStr =payOrderBean.res.getNonceStr();
        payRequest.timeStamp =  payOrderBean.res.getTimeStamp();
        payRequest.sign =payOrderBean.res.getPaySign();
        msgApi.registerApp(UrlConstant.APP_ID);
        msgApi.sendReq(payRequest);
    }





    private void sendPayReq() {


    }


}
