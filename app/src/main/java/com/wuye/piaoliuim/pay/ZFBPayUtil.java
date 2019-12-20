package com.wuye.piaoliuim.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.wuye.piaoliuim.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName ZFBPayUtil
 * @Description
 * @Author VillageChief
 * @Date 2019/12/20 14:45
 */
public class ZFBPayUtil {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    // 商户PID
    public static final String PARTNER = "2088921321370581";
    // 商户收款账号
//    public static final String SELLER = "2018010301535568";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE ="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKEMYaJ3CpeJaZxnAjW+ozy2GPm77FCS2SIRUptugZLCgL5m46Vxk5Cz+wkw0S5/w+AeaxbUPKLytaS4xIPVgLMHRWfXflJBCgb4LB7Bz6TKUpULOTEL4F1l7Tr89YGfEViGZBdmYsaWpFQ3Mj8lYQHEDQL1kCmVygbti9XEAoy3AgMBAAECgYAxBaDOoGfD+tr/9ccRbwcnWtK9afukBx87JIS9tWSRtM0VqCIRYUIm4PJAOPDjs5Z/KSM13sLaYwDxtCB1dM7otGb5OFGnAHKPn0peWTbZ3AKHV2rqM9wHUFK/ix/ZUtkk5GZJae6yeUM1gSS8yY92lFdL4Syhp+HFJv70TJrBaQJBAMxTYyQh7gDXLbUOLVVlFbs/8KGMHioK47BGPHQnFywdZT+rwb+NdKffChaFxKluyPil1PDNbJnRifUm2GDCYZsCQQDJxxkGHusyy0ZeLml955mVYAgpiOqAH2B51pRozy4WeW1B1BgaZ0LrPuiEdTi9AT9HD44anwBS5OEbZppZs9EVAkBUkwOLdjvkvyydL+QV38SSAFJjG/N0ZZN/aGMnlPKjgPti0n5dAnlR7yFm1zHjDAdF3KZ6kh0swi3/ene//VWXAkAqW8+XB1qcuSu2jcu7kKkfQJ+2CHYiG3QnX7KcJhIoyaj0DtBYiqDzvasWU31rwB1Gj69nS0XIISBYnjt85VDZAkAkndlSaMx3x7gbomJkvgClDstdd0qaxu7u97kYmNrSeoVgEhTBWY5Bi8O9H7mvCAX8j3pJ5iQDvS3ooolrso2r";
//    public static final String RSA_PRIVATE= "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOb6O6ebFlEjko3VtRyTZtNs8XuZdYZxnJ4cATwpQTmnuT+6ouUSH+7eQ/J1ISS55hr4ztMJzXeZ9YC2H+QWcpE4oITLuAskUZWr86/3Rpb1huyn2ADU2V2oImmWDcSFpr4vPbyYXX80wEP3dsiRCw2WoOxUfEcadYNyvFEYzo4ZAgMBAAECgYEAs+8P9dudIQxjKNboxiAWJHJkFHNR2LA83/bVB3bj9vQwHfNY3ral5cGEkSap8SBYn35gMIscYyWu2NwIL/lEuV2Rknrm5I9si+Q64AV5u8X7iQIVAiM+LvygxmwRCVRw7cFPTdgndu7Ku7KDI96zkKTjh2hC7iygMBnlndEo8OECQQD8WqJHyBxTcDEy8LR/iq25oXmc0snXDgh6IHDIVl8/IBa4y6+t/Gxgph7Htd+W656prfW/cc4F2As/eta9fIR1AkEA6lCI8pX9NJRGp164/xTJy7W35Ctkf/lQDRCg4O8T0NlhCmmCpNq5mlGr0HAEeeuHUBdGeJaM2/ETBXJRk5jelQJBAKH8O7cACM62cZ5VTEeiaSmDMWEtHUriQybBtCOk7bbDjlJIzRhNs/PGLnPblw51aH9anyiokKrHp1C0NSukxhECQQC936MKCTcVC26xpBV/RuY7eDba47rd5TbUT3w+qh3BqNV1LYjbdA7hGXuEPbGy5oRRB3torn7Yn+MND9VBKKh9AkASyR7/6qe/vHPAJGkYkomUkWhP4a6gIY+1q0LclgeoIRvgefI4g+TIA8e3lxKuspxHzO20Inww7WE65615WxNY";
//            "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDOQbWn6FJgmVkXB1H8DIl8CzoIN+qQv9ZNFKnztsu166XqJIYpsZmta65b0CD8wpgZd7cqB7KQlL3m8wQU8/ipAL3ufWL3exbCeJoXINj4pjY8BYdj08biNLkH6V+PS1naBLf8hpbfadzB4Pa84BpsmZvCLQ0FlIdow56KVZHHhfZphTvcKBDeSilC69XUpEeEgAO9FipEFOgM8nRwH7J1RIAkeJvkxYo4uV7HNKsczVfkWH+LWxnuWto1UPgb7wLGoNy0WylacF98EDU4fvRZdhheFM4XsICxpmo1usgILTP0oPMHM5L8T5ZoS28Llt2V/9P+Md4BHV1CvTzR/JKvAgMBAAECggEBAJkhD8zpHr+5GfuO479NT4VYkG0plmyBAImZndPjErqtiTCcjT7HLGEkYP6pNtRxZJsD1mjN7jSJObQNGCq2NxbCBkDZtnAmYm9fOoeRS4TPYdSOBkaHJFEs83J8QQYR21ORzUceZUTZQ5xteMIhX3zhTlKLP++0phORqFLr1J9b8LDm5xJ7VJOZj7WGYzK84NdI+//7nUzoQh89iuZp/tCWJjCZhXde/nj6hpkHvshWdhfcKzvd7xiDCQxnStTga/3NpAtyq2CPRRzR4cWX53zuwUi58K3M2berLm5y06jEcP/AkQdAzsQwMLUW7xacwMMmQvjeQhNgt693uLQbkYECgYEA5xWHhrPsWotGRN8J8snr17+wnQdJ1gPXJ4lizC8wqIKQMRonEgK7qB4gdEd7PoTMbZaawxOsdOqM313hsJyci9nMwHOs0QdLZ+DlL6Cs4vo2finuE7mpgXKMh8qfRnIPkeUnDcJ9qe5P6ypC/yC+CH5JIAGg2q3N0yS/KnQ/UL0CgYEA5H7iY/Sd1Q6AC18i1IOw12A+gVlTSdoDljb04Dbj5jLo4yqlb92jO80EgP/GbnICcFTiggCZdP8p0Ks8pbfVcs86zb8WnJR/mi8KXcb/CW71MbGGT2oUFwvkZXC9tyZczY+YHPTPuQtPcs/hsOtfvRndkDalaW48CPrOokGvFdsCgYEAuTp0q/DE378L0GDH5KkeNo4cz4HsGXRynepuGyUoZ6pgUjuYXcN7EEM12H8DtxVTl/REkWHPttfbm5/xLFQc16z5hroyKEskklue2hJ9AmN2EqMhhwYnbO+Y3a/5A8qh2FxKdakUeYXg4au6kC56pOw1SUaE3US72yd1ZF5R8jECgYAiC7nl1Nvzk/6GMz24Vorx/Hgzw8CCP4bLShMAmY0pjqpetssBuQ+KaIjkQ7goWrp8TFmXr0kuQprTacBWnOaj/bawU2mmGQZOTfYIdQ8pM6w7JE1z5cVqja4RY1QRRxkK0/uBTQTqeIH1BH6HYvrX7VOzGkFZTUCKwfwp9wmEgwKBgQDHBRbCucSjZlMI+szFSMXmtHYZuBwUz83NYOz3UyMpWd2okYtgthEvKmO0Ufd8dimFEhuMs9ZjjZeYzEWknWxmygMWcDEIy1a4oU/X5wz4NR1ZMRzuoMGM3BcMOyX7Q5sRqqKnuDbJgvnPRfbjrhH0JWz7a52FVddeCfxyFkaVlQ==";//
    //       "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOb6O6ebFlEjko3VtRyTZtNs8XuZdYZxnJ4cATwpQTmnuT+6ouUSH+7eQ/J1ISS55hr4ztMJzXeZ9YC2H+QWcpE4oITLuAskUZWr86/3Rpb1huyn2ADU2V2oImmWDcSFpr4vPbyYXX80wEP3dsiRCw2WoOxUfEcadYNyvFEYzo4ZAgMBAAECgYEAs+8P9dudIQxjKNboxiAWJHJkFHNR2LA83/bVB3bj9vQwHfNY3ral5cGEkSap8SBYn35gMIscYyWu2NwIL/lEuV2Rknrm5I9si+Q64AV5u8X7iQIVAiM+LvygxmwRCVRw7cFPTdgndu7Ku7KDI96zkKTjh2hC7iygMBnlndEo8OECQQD8WqJHyBxTcDEy8LR/iq25oXmc0snXDgh6IHDIVl8/IBa4y6+t/Gxgph7Htd+W656prfW/cc4F2As/eta9fIR1AkEA6lCI8pX9NJRGp164/xTJy7W35Ctkf/lQDRCg4O8T0NlhCmmCpNq5mlGr0HAEeeuHUBdGeJaM2/ETBXJRk5jelQJBAKH8O7cACM62cZ5VTEeiaSmDMWEtHUriQybBtCOk7bbDjlJIzRhNs/PGLnPblw51aH9anyiokKrHp1C0NSukxhECQQC936MKCTcVC26xpBV/RuY7eDba47rd5TbUT3w+qh3BqNV1LYjbdA7hGXuEPbGy5oRRB3torn7Yn+MND9VBKKh9AkASyR7/6qe/vHPAJGkYkomUkWhP4a6gIY+1q0LclgeoIRvgefI4g+TIA8e3lxKuspxHzO20Inww7WE65615WxNY";

    public static final String SELLER = "2088921321370581";
    // 商户私钥，pkcs8格式
    public static final String RSA2_PRIVATE ="";

    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    private Context context;
    private String price,name,ordernum,urlback;
//    public void payZFB(Context context,String price,String name,String ordernum,String urlback) {
//        this.context = context;
////        this.afterpay=(DoSomeThing) context;
//        this.price=price;
//        this.name=name;
//        this.ordernum=ordernum;
//        this.urlback=urlback;
//        this.context=context;
//         goToZFB();
//    }

    public void payZFB(Context context,String ordernum) {
        this.context = context;
        this.ordernum=ordernum;
        goToZFB2();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
//                    PayResult payResult = new PayResult(( String) msg.obj);
                            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        EventBus.getDefault().post(new MessageEvent("shuaxin"));

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Log.i("zhifu",payResult.getMemo()+"===="+payResult.getResult()+"[[[["+payResult.getResultStatus());
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(context,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(context,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    public void goToZFB2(){
        final String orderInfo = ordernum;   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) context);
                Map<String, String> result = alipay.payV2(orderInfo,true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }




    /**
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     *
     */
    public void check(View v) {
        Runnable checkRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask((Activity) context);
                // 调用查询接口，获取查询结果
//                boolean isExist = payTask.checkAccountIfExist();

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = true;
                mHandler.sendMessage(msg);
            }
        };

        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();

    }

    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask((Activity) context);
        String version = payTask.getVersion();
        // Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    public String getOrderInfo(String subject, String body, String price,String urlback) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号ordernum
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
        //传  orderNum  后台返回 充值第二步请求 getOutTradeNo()

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";
        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + urlback + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
//    public String sign(String content) {
//        return SignUtils.signs(content,RSA_PRIVATE);
//    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }
    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
//        Intent intent = new Intent(this, H5PayDemoActivity.class);
//        Bundle extras = new Bundle();
        /**
         * url 是要测试的网站，在 Demo App 中会使用 H5PayDemoActivity 内的 WebView 打开。
         *
         * 可以填写任一支持支付宝支付的网站（如淘宝或一号店），在网站中下订单并唤起支付宝；
         * 或者直接填写由支付宝文档提供的“网站 Demo”生成的订单地址
         * （如 https://mclient.alipay.com/h5Continue.htm?h5_route_token=303ff0894cd4dccf591b089761dexxxx）
         * 进行测试。
         *
         * H5PayDemoActivity 中的 MyWebViewClient.shouldOverrideUrlLoading() 实现了拦截 URL 唤起支付宝，
         * 可以参考它实现自定义的 URL 拦截逻辑。
         */
//        String url = "http://m.taobao.com";
//        extras.putString("url", url);
//        intent.putExtras(extras);
//        startActivity(intent);
    }
}
