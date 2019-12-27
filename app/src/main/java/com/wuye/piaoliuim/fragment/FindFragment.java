package com.wuye.piaoliuim.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuange.basemodule.BaseFragement;
import com.chuange.basemodule.utils.ViewUtils;
import com.chuange.basemodule.view.DialogView;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.activity.PaihangAct;
import com.wuye.piaoliuim.adapter.FindAdapter;
import com.wuye.piaoliuim.bean.FindData;
import com.wuye.piaoliuim.utils.RecyclerViewSpacesItemDecoration;

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



    FindAdapter fragmnetMyAdapter;
    FindData findData;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.activity_find, this, false);
        imShaixuan.setOnClickListener(this);
        initAdapter(findData);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }


    private void initAdapter(FindData findData) {
        headerView = getLayoutInflater().inflate(R.layout.header_find, null);
        LinearLayout llFuhao=headerView.findViewById(R.id.ll_fuhao);
        LinearLayout llMl=headerView.findViewById(R.id.ll_ml);
        LinearLayout llRq=headerView.findViewById(R.id.ll_rqi);
        llFuhao.setOnClickListener(this);
        llMl.setOnClickListener(this);
        llRq.setOnClickListener(this);
        @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                getBaseActivity(),
                LinearLayoutManager.VERTICAL, false);
        recommendGv.addItemDecoration(new RecyclerViewSpacesItemDecoration(5));
        recommendGv.setLayoutManager(managers);
        fragmnetMyAdapter = new FindAdapter(getBaseActivity(), R.layout.adapter_my_item, findData.res.publicLists);
        fragmnetMyAdapter.addHeaderView(headerView);
        recommendGv.setAdapter(fragmnetMyAdapter);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent=new Intent(getContext(), PaihangAct.class);
        switch (v.getId()) {
            case R.id.im_shaixuan:
                loading(R.layout.dialog_find_sx, this).setOutsideClose(true).setGravity(Gravity.BOTTOM);
                break;
            case R.id.tv_all:
                cancelLoading();
                break;
            case R.id.tv_nan:
                cancelLoading();
                break;
            case R.id.tv_nv:
                cancelLoading();
                break;
            case R.id.ll_fuhao:
                intent.putExtra("int",0);
                startActivity(intent);
                break;
            case R.id.ll_ml:
                intent.putExtra("int",1);
                startActivity(intent);
                 break;
            case R.id.ll_rqi:
                intent.putExtra("int",2);
                startActivity(intent);

                break;
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