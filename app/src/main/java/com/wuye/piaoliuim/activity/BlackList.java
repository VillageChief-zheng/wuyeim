package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.BlackListAdapter;
import com.wuye.piaoliuim.bean.BlackData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.http.RequestParams;
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

    public void getNetData(int page){
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PAGE,page+"");
        params.put(UrlConstant.TOKEN,"");
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.BLACKLIST, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                blackData= GsonUtil.getDefaultGson().fromJson(requestEntity,BlackData.class);
                setAdapter(blackData);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public void setAdapter(BlackData  dataList){
        if (mNextRequestPage==1){
             publicAdapter=new BlackListAdapter( this,R.layout.adapter_black_item,dataList.res.blackLists);
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
    public void setAdapterLis(){
        publicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
