package com.wuye.piaoliuim.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseFragement;
import com.chuange.basemodule.utils.ViewUtils;
import com.chuange.basemodule.view.refresh.interfaces.RefreshLayout;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.activity.UserInfoAct;
import com.wuye.piaoliuim.adapter.FinsAdapter;
import com.wuye.piaoliuim.adapter.PiaoliuAdapter;
import com.wuye.piaoliuim.bean.FinsData;
import com.wuye.piaoliuim.bean.PiaoliuData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName PiaoliuFragment
 * @Description
 * @Author VillageChief
 * @Date 2019/12/13 16:19
 */
public class PiaoliuFragment extends BaseFragement   {


    PiaoliuAdapter publicAdapter;
    PiaoliuData piaoliuData;

    private static final int PAGE_SIZE = 10;
    @ViewUtils.ViewInject(R.id.im_shaixuan)
    TextView imShaixuan;
    @ViewUtils.ViewInject(R.id.recommend_gv)
    RecyclerView recommendGv;

    private int mNextRequestPage = 1;

    private List<PiaoliuData.Res.PiaoliuList> newsList = new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.fragment_piaoliu, this, false);
        getNetData(mNextRequestPage);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public void getNetData(int page) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PAGE, page + "");
        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.PIAOLIULIST, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                piaoliuData = GsonUtil.getDefaultGson().fromJson(requestEntity, PiaoliuData.class);
                boolean isRefresh =mNextRequestPage ==1;
                setAdapter(isRefresh,piaoliuData);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
    public void setAdapter(boolean isRefresh,PiaoliuData dataList){
        if (mNextRequestPage==1){
             @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                    getBaseActivity(),
                    LinearLayoutManager.VERTICAL, false);
            publicAdapter=new PiaoliuAdapter( getContext(),R.layout.adapter_drift_item,dataList.res.listList);
            recommendGv.setLayoutManager(managers);

            recommendGv.setAdapter(publicAdapter);
            publicAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    getNetData(mNextRequestPage);
                }
            });
        publicAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

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

        }
        });
    }

    public static PiaoliuFragment newInstance() {
        return new PiaoliuFragment();
    }


}
