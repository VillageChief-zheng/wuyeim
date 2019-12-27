package com.wuye.piaoliuim.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.LiwuFragAdapter;
import com.wuye.piaoliuim.adapter.PaihangFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName
 * @Description
 * @Author VillageChief
 * @Date 2019/12/23 15:29
 */
public class PaihangAct extends BaseActivity {

    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private PaihangFragmentAdapter adapter;
    private List<String> names;
    int postision;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paihang);
        ButterKnife.bind(this);
        postision=getIntent().getIntExtra("int",0);
        initData();
    }

    private void initData() {
        names = new ArrayList<>();
        names.add("富豪榜");
        names.add("魅力榜");
        names.add("人气榜");
        adapter = new PaihangFragmentAdapter(getSupportFragmentManager(), names);
        viewPager.setAdapter(adapter);
        xTablayout.setupWithViewPager(viewPager);
        xTablayout.getTabAt(postision).select();
        // 更新适配器数据

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
