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
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriendResult;
import com.tencent.qcloud.tim.uikit.utils.TUIKitLog;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.BlackListAdapter;
import com.wuye.piaoliuim.bean.BlackData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName BlackList
 * @Description
 * @Author VillageChief
 * @Date 2019/12/16 9:51
 */
public class BlackList extends BaseActivity {

    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;

    private static final int PAGE_SIZE = 10;
    @BindView(R.id.noDataCommL)
    LinearLayout noDataCommL;

    private int mNextRequestPage = 1;

    private List<BlackData.Res.BlackList> newsList = new ArrayList<>();
    BlackData blackData;
    BlackListAdapter publicAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        ButterKnife.bind(this);
        getNetData(mNextRequestPage);
    }

    public void getNetData(int page) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PAGE, page + "");
        params.put(UrlConstant.TOKEN, "");
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.BLACKLIST, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                blackData = GsonUtil.getDefaultGson().fromJson(requestEntity, BlackData.class);
                boolean isRefresh =mNextRequestPage ==1;

                if (blackData.res.getBlackLists().size()>0){
                    setAdapter(isRefresh,blackData);

                }else {
                    showNoData(blackData.res.getBlackLists().size());

                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public void setAdapter(boolean isRefresh,BlackData dataList) {
        if (mNextRequestPage == 1) {
            publicAdapter = new BlackListAdapter(this, R.layout.adapter_black_item, dataList.res.blackLists);
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
        final int size = dataList == null ? 0 : dataList.res.blackLists.size();
        if (isRefresh) {
            publicAdapter.setNewData(dataList.res.blackLists);
        } else {
            if (size > 0) {
                publicAdapter.addData(dataList.res.blackLists);
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
        publicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                String passId = publicAdapter.getData().get(position).getBlick_id();
                switch (view.getId()) {
                    case R.id.tv_deleteblack:
                        removeBlack(position, passId);
                        break;
                }
            }
        });
    }

    private void removeBlack(int postione, String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.BLICKID, id);
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.REMOVEBLACK, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                publicAdapter.remove(postione);
                deleteBlack(id);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    private void deleteBlack(String mId) {
        String[] idStringList = mId.split(",");

        List<String> idList = new ArrayList<>();
        for (String id : idStringList) {
            idList.add(id);
        }
        TIMFriendshipManager.getInstance().deleteBlackList(idList, new TIMValueCallBack<List<TIMFriendResult>>() {
            @Override
            public void onError(int i, String s) {
                TUIKitLog.e("TAG", "deleteBlackList err code = " + i + ", desc = " + s);
                ToastUtil.toastShortMessage("Error code = " + i + ", desc = " + s);
            }

            @Override
            public void onSuccess(List<TIMFriendResult> timFriendResults) {
                TUIKitLog.i("TAG", "deleteBlackList success");
            }
        });
    }
    private void showNoData(int size) {
        if (size > 0) {
            noDataCommL.setVisibility(View.GONE);
            recommendGv.setVisibility(View.VISIBLE);
        } else {

            noDataCommL.setVisibility(View.VISIBLE);
            recommendGv.setVisibility(View.GONE);

        }
    }
}
