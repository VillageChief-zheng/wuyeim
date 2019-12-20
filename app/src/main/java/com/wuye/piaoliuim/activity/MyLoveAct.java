package com.wuye.piaoliuim.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.FinsAdapter;
import com.wuye.piaoliuim.adapter.LoveAdapter;
import com.wuye.piaoliuim.bean.FinsData;
import com.wuye.piaoliuim.bean.LoveData;
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
 * @ClassName MyLoveAct
 * @Description 我的关注
 * @Author VillageChief
 * @Date 2019/12/17 11:22
 */
public class MyLoveAct extends BaseActivity {

    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;

    private static final int PAGE_SIZE = 10;

    private int mNextRequestPage = 1;

    private List<FinsData.Res.FinsList> newsList = new ArrayList<>();
    LoveData listData;
    LoveAdapter publicAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        ButterKnife.bind(this);
        getNetData(mNextRequestPage);
    }

    public void getNetData(int page){
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PASSID,page+"");
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.MYFOLLW, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                listData= GsonUtil.getDefaultGson().fromJson(requestEntity,LoveData.class);
                setAdapter(listData);
            }

            @Override
            public void onError(String message) {

            }
        });
    } public void cancelLove(int postione,String id){
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PASSID,id );
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.CANCELMYFOLLW, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
             publicAdapter.remove(postione);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public void setAdapter(LoveData dataList){
        if (mNextRequestPage==1){
            publicAdapter=new LoveAdapter( this,R.layout.adapter_mylove_item,dataList.res.listList);
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
        publicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //jinxing 关注
                int itemViewId = view.getId();
              String   passId=publicAdapter.getData().get(position).pass_id;
                 switch (itemViewId) {
                    case R.id.tv_deleteblove:
                        cancelLove(position,passId );
                        break;
            }
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
