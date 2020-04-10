package com.wuye.piaoliuim.activity;

import android.annotation.SuppressLint;
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
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.MymlAdapter;
import com.wuye.piaoliuim.bean.MyMlListData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeopleMlListAct extends BaseActivity {
    @BindView(R.id.tv_mlz)
    TextView tvMlz;
    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;
    @BindView(R.id.noDataCommL)
    LinearLayout noDataCommL;

    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 10;


    private List<MyMlListData.Res.MlListInfo> newsList = new ArrayList<>();
    MyMlListData listData;
    MymlAdapter publicAdapter;
    String uId = "";
    String mlNumber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peomeli);
        ButterKnife.bind(this);
        uId=getIntent().getStringExtra("uid");
        mlNumber = getIntent().getStringExtra("ml");
        tvMlz.setText(mlNumber);
        load(mNextRequestPage,uId);

    }


    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public void load(int page,String uId) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PAGE, page + "");
        params.put(UrlConstant.USER_ID,uId  );
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.MEILILIST, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                listData = GsonUtil.getDefaultGson().fromJson(requestEntity, MyMlListData.class);
                boolean isRefresh = mNextRequestPage == 1;
                if (listData.res.getListList().size() > 0) {
                    setAdapter(isRefresh, listData);
                } else {
                    showNoData(listData.res.getListList().size());
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
                    load(mNextRequestPage,uId);
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
}
