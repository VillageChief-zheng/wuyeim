package com.wuye.piaoliuim.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.view.DialogView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.LiwuAdapter;
import com.wuye.piaoliuim.adapter.MymlAdapter;
import com.wuye.piaoliuim.bean.CashUserData;
import com.wuye.piaoliuim.bean.LiwuData;
import com.wuye.piaoliuim.bean.MyMlListData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.AppSessionEngine;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.postMessageWx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MymlListAct extends BaseActivity {

    @BindView(R.id.tv_mlnumber)
    TextView tvMlnumber;
    @BindView(R.id.tv_jine)
    TextView tvJine;
    @BindView(R.id.tvtx)
    TextView tvtx;
    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;
    @BindView(R.id.noDataCommL)
    LinearLayout noDataCommL;

    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 10;


    private List<MyMlListData.Res.MlListInfo> newsList = new ArrayList<>();
    MyMlListData listData;
    MymlAdapter publicAdapter;
    CashUserData cashUserData;
    String mlNumber;
    /**
     * 微信的登录
     */
    private IWXAPI api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymlz);
         ButterKnife.bind(this);
        mlNumber = getIntent().getStringExtra("ml");
        setDate();
        load(mNextRequestPage);
        api = WXAPIFactory.createWXAPI(this, "wx4b25184c83dbca16", true);
        api.registerApp("wx4b25184c83dbca16");
        EventBus.getDefault().register(this);

    }
    private void setDate(){
        tvMlnumber.setText(mlNumber);
        tvJine.setText("约"+getMoney(mlNumber)+"元");
    }

    private int getMoney(String moneys){
        int money;
        money=  Integer.parseInt(moneys)/100;
        return money;
    }
    private int getMoneys(String moneys){
        int money;
        money=  Integer.parseInt(moneys);
        return money;
    }
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
    private void wChatLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }
    @OnClick({R.id.tvtx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvtx:
                if (getMoneys(mlNumber)>=1000){
                    getUserCash();
                }else {
                    loading("魅力值满1000即可提现").setOnlySure();
                    return;

                }
                break;
        }
    }

    public void load(int page) {
        HashMap<String, String> params = new HashMap<>();
         params.put(UrlConstant.PAGE, page + "");
        params.put(UrlConstant.USER_ID, AppSessionEngine.getMyUserInfo().res.getListList().getId());

        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.MEILILIST, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                listData = GsonUtil.getDefaultGson().fromJson(requestEntity, MyMlListData.class);
                boolean isRefresh = mNextRequestPage == 1;
                if (listData.res.getListList().size()>0){
                    setAdapter(isRefresh, listData);
                }else {
                    showNoData(listData.res.getListList().size());
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }
    public void getUserCash() {
        HashMap<String, String> params = new HashMap<>();
         RequestManager.getInstance().publicPostMap(this, params, UrlConstant.GETCASHINFO, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                cashUserData = GsonUtil.getDefaultGson().fromJson(requestEntity, CashUserData.class);
                if (cashUserData.res.getListList().getUnionid().equals("")){
                    tankuang("请先绑定微信",1);
                }else if(cashUserData.res.getListList().getPhone().equals("")){
                    tankuang("请先绑定手机号",2);

                }else if(cashUserData.res.getListList().getReal_name().equals("")){
                    tankuang("请实名账户",3);

                }else if(cashUserData.res.getListList().getCash_openid().equals("")){
                    startActivity(new Intent(getBaseContext(), CashTowAct.class));

                }else {
                    Intent intent = new Intent(getBaseContext(), CashThreeAct.class);
                    intent.putExtra("ml", mlNumber);//userInfoData.res.listList.getRece_gold()
                    startActivity(intent);

                }
            }

            @Override
            public void onError(String message) {

            }
        });

    }


    public void setAdapter(boolean isRefresh, MyMlListData listData) {
        if (mNextRequestPage == 1) {
            publicAdapter = new MymlAdapter(this, R.layout.adapter_ml_item, listData.res.listList);
            @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                   this,
                    LinearLayoutManager.VERTICAL, false);
            recommendGv.setLayoutManager(managers);
            recommendGv.setAdapter(publicAdapter);
            publicAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    load(mNextRequestPage);
                }
            });
        }
        mNextRequestPage++;
        final int size = listData == null ? 0 : listData.res.listList.size();
        if (isRefresh) {
            publicAdapter.setNewData(listData.res.listList);
        } else {
            if (size > 0) {
                publicAdapter.addData(listData.res.listList);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            publicAdapter.loadMoreEnd(isRefresh);
        } else {
            publicAdapter.loadMoreComplete();
        }
        setAdapterLis();

    }

    public void setAdapterLis() {
        publicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }
    private void showNoData(int size) {
        if (size > 0) {
            Log.i("ppppppppp", "来了");
            noDataCommL.setVisibility(View.GONE);
            recommendGv.setVisibility(View.VISIBLE);
        } else {
            Log.i("ppppppppp", "来了-------");

            noDataCommL.setVisibility(View.VISIBLE);
            recommendGv.setVisibility(View.GONE);

        }
    }
    public void tankuang(String titleName,int type){
        loading(titleName).setListener(new DialogView.DialogOnClickListener() {
            @Override
            public void onDialogClick(boolean isCancel) {
                if (isCancel)
                    return;
                else {
                    if (type==1) {
                        UrlConstant.GETUNID=2;
                         wChatLogin();
                    }else if  (type==2){
                        startActivity(new Intent(getBaseContext(), ToBindPhoneAct.class));
                    }else if  (type==3){
                        Intent intent = new Intent(getBaseContext(), CashOneAct.class);
                        intent.putExtra("phone",cashUserData.res.getListList().getPhone());//userInfoData.res.listList.getRece_gold()
                        startActivity(intent);
                     }

                }
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                cancelLoading();
            }
        });
    }
    //绑定微信
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(postMessageWx event) {
        bindWx(event.wxUserInfo.getCity());

    }

    private void bindWx(String unIonId){
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.UNIONID,unIonId);
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.BINDWX, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                ToastUtil.toastShortMessage("绑定成功，请提现!");

            }
            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
