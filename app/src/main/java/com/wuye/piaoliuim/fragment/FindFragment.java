package com.wuye.piaoliuim.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseFragement;
import com.chuange.basemodule.utils.ViewUtils;
import com.chuange.basemodule.view.DialogView;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.activity.PaihangAct;
import com.wuye.piaoliuim.activity.UserInfoAct;
import com.wuye.piaoliuim.adapter.FindAdapter;
import com.wuye.piaoliuim.bean.FindData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.RecyclerViewSpacesItemDecoration;

import java.util.HashMap;

import butterknife.BindView;

/**
 * @ClassName FindFragment
 * @Description
 * @Author VillageChief
 * @Date 2019/12/13 16:19
 */
public class FindFragment extends BaseFragement implements DialogView.DialogViewListener {

    View headerView;
    @ViewUtils.ViewInject(R.id.im_shaixuan)
    TextView imShaixuan;
    @ViewUtils.ViewInject(R.id.recommend_gv)
    RecyclerView recommendGv;
    @ViewUtils.ViewInject(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private static final int PAGE_SIZE = 10;


    FindAdapter fragmnetMyAdapter;
    FindData findData;
    @ViewUtils.ViewInject(R.id.noDataCommL)
    LinearLayout noDataCommL;
    private int mNextRequestPage = 1;

    String sexStr = "0";
    int initView = 1;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.activity_find, this, false);
        imShaixuan.setOnClickListener(this);
        initView = 1;
        getNetData(mNextRequestPage);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
                noDataCommL.setVisibility(View.GONE);
                swipeRefresh.setVisibility(View.VISIBLE);
                mNextRequestPage = 1;
                getNetData(mNextRequestPage);
            }


        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public void getNetData(int page) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PAGE, page + "");
        params.put(UrlConstant.GENDER, sexStr);
        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.FINDINDEX, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                findData = GsonUtil.getDefaultGson().fromJson(requestEntity, FindData.class);
                boolean isRefresh = mNextRequestPage == 1;
                if (findData.res.getPublicLists().size() > 0) {

                    initAdapter(isRefresh, findData);
                } else {
                    showNoData(findData.res.getPublicLists().size());
                }

            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void initAdapter(boolean isRefresh, FindData findData) {
        if (initView == 1) {
            initView = 2;
            headerView = getLayoutInflater().inflate(R.layout.header_find, null);
            LinearLayout llFuhao = headerView.findViewById(R.id.ll_fuhao);
            LinearLayout llMl = headerView.findViewById(R.id.ll_ml);
            LinearLayout llRq = headerView.findViewById(R.id.ll_rqi);
            llFuhao.setOnClickListener(this);
            llMl.setOnClickListener(this);
            llRq.setOnClickListener(this);
            @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                    getBaseActivity(),
                    LinearLayoutManager.VERTICAL, false);
            recommendGv.addItemDecoration(new RecyclerViewSpacesItemDecoration(3));
            recommendGv.setLayoutManager(managers);
            fragmnetMyAdapter = new FindAdapter(getBaseActivity(), R.layout.adapter_finds_item, findData.res.publicLists);
            fragmnetMyAdapter.addHeaderView(headerView);
            recommendGv.setAdapter(fragmnetMyAdapter);
            fragmnetMyAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    getNetData(mNextRequestPage);
                }
            });
            if (swipeRefresh.isRefreshing()) {
                Log.i("-----", "取消刷新");
                swipeRefresh.setRefreshing(false);
            }
        }

        mNextRequestPage++;
        final int size = findData == null ? 0 : findData.res.getPublicLists().size();
        if (isRefresh) {
            fragmnetMyAdapter.setNewData(findData.res.getPublicLists());
        } else {
            if (size > 0) {
                fragmnetMyAdapter.addData(findData.res.getPublicLists());
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            fragmnetMyAdapter.loadMoreEnd(isRefresh);
        } else {
            fragmnetMyAdapter.loadMoreComplete();
        }
        setAdapterLis();

    }

    public void setAdapterLis() {
        fragmnetMyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //jinxing 关注
                Intent intent = new Intent(getContext(), UserInfoAct.class);
                intent.putExtra("id", fragmnetMyAdapter.getData().get(position).getUser_id());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent(getContext(), PaihangAct.class);
        switch (v.getId()) {
            case R.id.im_shaixuan:
                loading(R.layout.dialog_find_sx, this).setOutsideClose(true).setGravity(Gravity.BOTTOM);
                break;
            case R.id.tv_all:
                mNextRequestPage = 1;
                imShaixuan.setText("全部");
                sexStr = "0";
                getNetData(mNextRequestPage);

                cancelLoading();
                break;
            case R.id.tv_nan:
                imShaixuan.setText("男生");

                mNextRequestPage = 1;
                sexStr = "1";
                getNetData(mNextRequestPage);
                cancelLoading();
                break;
            case R.id.tv_nv:
                imShaixuan.setText("女生");

                mNextRequestPage = 1;
                sexStr = "2";
                getNetData(mNextRequestPage);

                cancelLoading();
                break;
            case R.id.ll_fuhao:
                intent.putExtra("int", 0);
                startActivity(intent);
                break;
            case R.id.ll_ml:
                intent.putExtra("int", 1);
                startActivity(intent);
                break;
            case R.id.ll_rqi:
                intent.putExtra("int", 2);
                startActivity(intent);

                break;
        }
    }
    private void showNoData(int size) {
        if (size > 0) {
            noDataCommL.setVisibility(View.GONE);
            swipeRefresh.setVisibility(View.VISIBLE);
        } else {

            noDataCommL.setVisibility(View.VISIBLE);
            swipeRefresh.setVisibility(View.GONE);

        }
    }
    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    public void onView(View view) {
        view.findViewById(R.id.tv_nv).setOnClickListener(this);
        view.findViewById(R.id.tv_nan).setOnClickListener(this);
        view.findViewById(R.id.tv_all).setOnClickListener(this);
    }
}