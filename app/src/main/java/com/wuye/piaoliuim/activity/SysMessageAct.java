package com.wuye.piaoliuim.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.FinsAdapter;
import com.wuye.piaoliuim.adapter.SysAdaptet;
import com.wuye.piaoliuim.bean.FinsData;
import com.wuye.piaoliuim.bean.SysData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SysMessageAct extends BaseActivity {

    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;
    @BindView(R.id.commTipsTxt)
    TextView commTipsTxt;
    @BindView(R.id.noDataCommL)
    LinearLayout noDataCommL;

    private static final int PAGE_SIZE = 10;

    private int mNextRequestPage = 1;

    private List<SysData.Res.SysList> newsList = new ArrayList<>();
    SysData listData;
    SysAdaptet publicAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys);
        ButterKnife.bind(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getNetData(mNextRequestPage);
    }
    public void getNetData(int page) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PAGE, page + "");
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.SYSMESSAGE, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                listData = GsonUtil.getDefaultGson().fromJson(requestEntity, SysData.class);
                if (listData.res.listList.size()>0){
                    boolean isRefresh =mNextRequestPage ==1;

                    setAdapter(isRefresh,listData);
                }else {
                    showNoData(listData.res.listList.size());
                }
            }

            @Override
            public void onError(String message) {

            }
        });
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

    public void setAdapter(boolean isRefresh,SysData dataList) {
        if (mNextRequestPage == 1) {
            publicAdapter = new SysAdaptet(this, R.layout.adapter_sys_item, dataList.res.listList);
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
        final int size = dataList == null ? 0 : dataList.res.listList.size();
        if (isRefresh) {
            publicAdapter.setNewData(dataList.res.listList);
        } else {
            if (size > 0) {
                publicAdapter.addData(dataList.res.listList);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            publicAdapter.loadMoreEnd(isRefresh);
        } else {
            publicAdapter.loadMoreComplete();
        }

    }
}
