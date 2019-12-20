package com.wuye.piaoliuim.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.view.NavigationTopView;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.GlodFragmentAdapter;
import com.wuye.piaoliuim.adapter.LiwuFragAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName MyZhangDanAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/17 14:51
 */
public class MyZhangDanAct extends BaseActivity {

    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.topView)
    NavigationTopView topView;
    private GlodFragmentAdapter adapter;
    private List<String> names;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liwu);
        ButterKnife.bind(this);
        initData();

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    private void initData() {
        topView.setTitle("账单");
        names = new ArrayList<>();
        names.add("收入");
        names.add("支出");
        adapter = new GlodFragmentAdapter(getSupportFragmentManager(), names);
        viewPager.setAdapter(adapter);
        xTablayout.setupWithViewPager(viewPager);

        // 更新适配器数据

    }
}
