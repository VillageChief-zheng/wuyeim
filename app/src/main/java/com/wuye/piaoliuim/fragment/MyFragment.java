package com.wuye.piaoliuim.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseFragement;
import com.chuange.basemodule.utils.ViewUtils;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.FragmnetMyAdapter;
import com.wuye.piaoliuim.bean.ItemBean;
import com.wuye.piaoliuim.utils.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName MyFragment
 * @Description
 * @Author VillageChief
 * @Date 2019/12/13 15:46
 */
public class MyFragment extends BaseFragement {

    @ViewUtils.ViewInject(R.id.recommend_gv)
    RecyclerView recommendGv;

    View headerView;
   FragmnetMyAdapter fragmnetMyAdapter;
//    private Class[] itemClass = {MyInvestActivity.class, FundRecordActivity.class, RedPacketActivity.class, IncreaseCouponActivity.class, ClaimsTransferredActivity.class, AutomaticBidActivity.class};

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.activity_my, this, false);
        initAdapter();
     }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
    private void initAdapter(){
        String[] name = getResources().getStringArray(R.array.main_account_list);
        int[] nameIcon = {R.mipmap.ic_myliwu, R.mipmap.ic_myjbi,
                R.mipmap.ic_myyy, R.mipmap.ic_myheimd, R.mipmap.ic_mysz, R.mipmap.ic_myfk, R.mipmap.ic_myset};
        List<ItemBean> accountDataList = new ArrayList<>();
        int length = name.length;
        for (int i = 0; i < length; i++) {
            ItemBean accountData = new ItemBean();
            accountData.icon = nameIcon[i];
            accountData.name = name[i];
            accountDataList.add(accountData);
        }
        headerView = getLayoutInflater().inflate(R.layout.adapter_myinfo_header, null);
        @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                getBaseActivity(),
                LinearLayoutManager.VERTICAL, false);
        recommendGv.addItemDecoration(new RecyclerViewSpacesItemDecoration(5));
        recommendGv.setLayoutManager(managers);
        fragmnetMyAdapter=new FragmnetMyAdapter(getBaseActivity(),R.layout.adapter_my_item,accountDataList);
        fragmnetMyAdapter.addHeaderView(headerView);
        recommendGv.setAdapter(fragmnetMyAdapter);
        fragmnetMyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }
        });

     }
    public static MyFragment newInstance() {
        return new MyFragment();
    }

}
