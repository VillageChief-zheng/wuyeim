package com.wuye.piaoliuim.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseFragement;
import com.chuange.basemodule.utils.ViewUtils;
import com.chuange.basemodule.view.DialogView;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.activity.UserInfoAct;
import com.wuye.piaoliuim.adapter.FuhaoBangAdapter;
import com.wuye.piaoliuim.bean.FindData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.RecyclerViewSpacesItemDecoration;

import java.util.HashMap;

import butterknife.BindView;

/**
 * @ClassName RenqiFragment
 * @Description
 * @Author VillageChief
 * @Date 2019/12/23 16:55
 */
public class RenqiFragment extends BaseFragement implements DialogView.DialogViewListener {
    @ViewUtils.ViewInject(R.id.recommend_gv)
    RecyclerView recommendGv;

    FindData findData;
    View headerView;
    FuhaoBangAdapter fuhaoBangAdapter;
    String id1, id2, id3;
    @ViewUtils.ViewInject(R.id.noDataCommL)
    LinearLayout noDataCommL;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.fragment_main_paihang, this, false);
        getList();
    }

    public void getList() {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.TYPE, "3");
        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.MAINFIND, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                findData = GsonUtil.getDefaultGson().fromJson(requestEntity, FindData.class);
                 if (findData.res.getPublicLists().size() > 0) {
                    setAdapter(findData);
                } else {
                    showNoData(findData.res.getPublicLists().size());
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    private void setAdapter(FindData findData) {
        headerView = getLayoutInflater().inflate(R.layout.fragment_rq, null);
        TextView tvName1 = headerView.findViewById(R.id.one_name);
        TextView tvName2 = headerView.findViewById(R.id.tow_name);
        TextView tvName3 = headerView.findViewById(R.id.threename);
        ImageView imageView1 = headerView.findViewById(R.id.img_one);
        ImageView imageView2 = headerView.findViewById(R.id.img_tow);
        ImageView imageView3 = headerView.findViewById(R.id.img_three);
        TextView tvqiehuan = headerView.findViewById(R.id.tv_qiehuan);
        RelativeLayout userThree = headerView.findViewById(R.id.rl_three);
        RelativeLayout userOne = headerView.findViewById(R.id.rl_one);
        RelativeLayout userTow = headerView.findViewById(R.id.rl_tow);
        userOne.setOnClickListener(this);
        userThree.setOnClickListener(this);
        userTow.setOnClickListener(this);
        tvqiehuan.setOnClickListener(this);
        RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(getBaseActivity())
                .load(Constants.BASEURL + findData.res.getPublicLists().get(0).getLitpic()).apply(options)
                .into(imageView1);
        Glide.with(getBaseActivity())
                .load(Constants.BASEURL + findData.res.getPublicLists().get(1).getLitpic()).apply(options)
                .into(imageView2);
        Glide.with(getBaseActivity())
                .load(Constants.BASEURL + findData.res.getPublicLists().get(2).getLitpic()).apply(options)
                .into(imageView3);
        tvName1.setText(findData.res.getPublicLists().get(0).getName());
        tvName2.setText(findData.res.getPublicLists().get(1).getName());
        tvName3.setText(findData.res.getPublicLists().get(2).getName());
        id1 = findData.res.getPublicLists().get(0).getUser_id();
        id2 = findData.res.getPublicLists().get(1).getUser_id();
        id3 = findData.res.getPublicLists().get(2).getUser_id();
        findData.res.getPublicLists().remove(0);
        findData.res.getPublicLists().remove(0);
        findData.res.getPublicLists().remove(0);
        @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                getBaseActivity(),
                LinearLayoutManager.VERTICAL, false);
        recommendGv.addItemDecoration(new RecyclerViewSpacesItemDecoration(5));
        recommendGv.setLayoutManager(managers);
        fuhaoBangAdapter = new FuhaoBangAdapter(getBaseActivity(), R.layout.adapter_bangdan_item, findData.res.getPublicLists());
        fuhaoBangAdapter.addHeaderView(headerView);
        recommendGv.setAdapter(fuhaoBangAdapter);
        fuhaoBangAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                Intent intent = new Intent(getContext(), UserInfoAct.class);
                intent.putExtra("id", fuhaoBangAdapter.getData().get(position).getUser_id());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_qiehuan:
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
            case R.id.rl_three:
                startID(id3);
                break;
            case R.id.rl_tow:
                startID(id2);
                break;
            case R.id.rl_one:
                startID(id1);
                break;
        }
    }

    @Override
    public void onView(View view) {
        view.findViewById(R.id.tv_nv).setOnClickListener(this);
        view.findViewById(R.id.tv_nan).setOnClickListener(this);
        view.findViewById(R.id.tv_all).setOnClickListener(this);
    }

    private void startID(String id) {
        Intent intent = new Intent(getContext(), UserInfoAct.class);
        intent.putExtra("id", id);
        startActivity(intent);
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
