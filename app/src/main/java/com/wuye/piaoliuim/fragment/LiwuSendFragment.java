package com.wuye.piaoliuim.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseFragement;
import com.chuange.basemodule.utils.ViewUtils;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.LiwuAdapter;
import com.wuye.piaoliuim.bean.LiwuData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName LiwuSendFragment
 * @Description
 * @Author VillageChief
 * @Date 2019/12/16 16:43
 */
public class LiwuSendFragment extends BaseFragement {

    @ViewUtils.ViewInject(R.id.recommend_gv)
    RecyclerView recommendGv;


    private static final int PAGE_SIZE = 10;
    @ViewUtils.ViewInject(R.id.noDataCommL)
    LinearLayout noDataCommL;

    private int mNextRequestPage = 1;


    private List<LiwuData.Res.LiwuList> newsList = new ArrayList<>();
    LiwuData listData;
    LiwuAdapter publicAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.fragment_liwu, this, false);
        load(mNextRequestPage);
    }

    public void load(int page) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.TYPE, "2");
        params.put(UrlConstant.PAGE, page + "");
        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.GIFTLISTS, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                listData = GsonUtil.getDefaultGson().fromJson(requestEntity, LiwuData.class);
                boolean isRefresh = mNextRequestPage == 1;

                if (listData.res.getListList().size()>0){
                    setAdapter(isRefresh, listData);
                }else {
                    showNoData(listData.res.getListList().size());
                }            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public void setAdapter(boolean isRefresh, LiwuData listData) {
        if (mNextRequestPage == 1) {
            publicAdapter = new LiwuAdapter(getContext(), R.layout.adapter_gift_item, listData.res.listList);
            @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                    getBaseActivity(),
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

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
    private void showNoData(int size) {
        if (size > 0) {
            Log.i("ppppppppp","来了");
            noDataCommL.setVisibility(View.GONE);
            recommendGv.setVisibility(View.VISIBLE);
        } else {
            Log.i("ppppppppp","来了-------");

            noDataCommL.setVisibility(View.VISIBLE);
            recommendGv.setVisibility(View.GONE);

        }
    }
}
