package com.wuye.piaoliuim.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.BlackListAdapter;
import com.wuye.piaoliuim.adapter.FinsAdapter;
import com.wuye.piaoliuim.bean.BlackData;
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

/**
 * @ClassName FinsActivity
 * @Description
 * @Author VillageChief
 * @Date 2019/12/17 10:28
 */
public class FinsActivity  extends BaseActivity {

    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;

    private static final int PAGE_SIZE = 10;

    private int mNextRequestPage = 1;

    private List<FinsData.Res.FinsList> newsList = new ArrayList<>();
    FinsData listData;
    FinsAdapter publicAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fins);
        ButterKnife.bind(this);
        getNetData(mNextRequestPage);
    }

    public void getNetData(int page){
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PAGE,page+"");
         RequestManager.getInstance().publicPostMap(this, params, UrlConstant.FINSLIST, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                listData= GsonUtil.getDefaultGson().fromJson(requestEntity,FinsData.class);
                setAdapter(listData);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public void setAdapter(FinsData  dataList){
        if (mNextRequestPage==1){
            publicAdapter=new FinsAdapter( this,R.layout.adapter_fins_item,dataList.res.listList);
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
        setAdapterLis();

    }
    public void setAdapterLis(){
        publicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                int itemViewId = view.getId();
                String   passId=publicAdapter.getData().get(position).getUser_id();
                FinsData.Res.FinsList finsData=publicAdapter.getData().get(position);
                finsData.setIs_follow("1");
                switch (itemViewId) {
                    case R.id.tv_love:
                 toLove(position,finsData);
                        break;
                }
            }
        });
    }
    private void toLove(int postione,FinsData.Res.FinsList finsData){
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PASSID,finsData.getUser_id() );
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.ADDFOLLOW, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                publicAdapter.notifyItemChanged(postione,finsData);
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
}
