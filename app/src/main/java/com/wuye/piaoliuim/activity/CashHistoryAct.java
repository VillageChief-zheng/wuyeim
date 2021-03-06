package com.wuye.piaoliuim.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.CashHistAdapter;
import com.wuye.piaoliuim.adapter.FinsAdapter;
import com.wuye.piaoliuim.bean.CashListData;
import com.wuye.piaoliuim.bean.FinsData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CashHistoryAct extends BaseActivity {
    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;
    @BindView(R.id.noDataCommL)
    LinearLayout noDataCommL;


    private static final int PAGE_SIZE = 10;

    private int mNextRequestPage = 1;

    private List<FinsData.Res.FinsList> newsList = new ArrayList<>();
    CashListData  listData;
    CashHistAdapter publicAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashhistory);
        ButterKnife.bind(this);
        getNetData(mNextRequestPage);

    }
    public void getNetData(int page) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PAGE, page + "");
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.CASHLIST, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                listData = GsonUtil.getDefaultGson().fromJson(requestEntity, CashListData.class);
                if (listData.res.getBlackLists().size()>0){
                    boolean isRefresh =mNextRequestPage ==1;

                    setAdapter(isRefresh,listData);
                }else {
                    showNoData(listData.res.getBlackLists().size());
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }
    public void setAdapter(boolean isRefresh,CashListData dataList) {
        if (mNextRequestPage == 1) {
            publicAdapter = new CashHistAdapter(this, R.layout.adapter_cash_item, dataList.res.getBlackLists());
            @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                    this,
                    LinearLayoutManager.VERTICAL, false);
            recommendGv.setLayoutManager(managers);
            recommendGv.setAdapter(publicAdapter);
            publicAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    getNetData(mNextRequestPage);
                }
            });
        }
        mNextRequestPage++;
        final int size = dataList == null ? 0 : dataList.res.getBlackLists().size();
        if (isRefresh) {
            publicAdapter.setNewData(dataList.res.getBlackLists());
        } else {
            if (size > 0) {
                publicAdapter.addData(dataList.res.getBlackLists());
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            publicAdapter.loadMoreEnd(isRefresh);
        } else {
            publicAdapter.loadMoreComplete();
        }

    }
    private void showNoData(int size) {
        if (size> 0) {
            noDataCommL.setVisibility(View.GONE);
            recommendGv.setVisibility(View.VISIBLE);
        } else {

            noDataCommL.setVisibility(View.VISIBLE);
            recommendGv.setVisibility(View.GONE);

        }
    }
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
