package com.wuye.piaoliuim.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuange.basemodule.BaseFragement;
import com.chuange.basemodule.utils.ViewUtils;
import com.chuange.basemodule.view.DialogView;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.FragmnetMyAdapter;
import com.wuye.piaoliuim.adapter.FuhaoBangAdapter;
import com.wuye.piaoliuim.bean.FindData;
import com.wuye.piaoliuim.bean.PiaoliuData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.RecyclerViewSpacesItemDecoration;

import java.util.HashMap;

import butterknife.BindView;

/**
 * @ClassName FuhaoFragment
 * @Description
 * @Author VillageChief
 * @Date 2019/12/23 16:15
 */
public class FuhaoFragment extends BaseFragement implements DialogView.DialogViewListener {
    @ViewUtils.ViewInject(R.id.recommend_gv)
    RecyclerView recommendGv;

    FindData findData;
    View headerView;
    FuhaoBangAdapter fuhaoBangAdapter;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.fragment_main_paihang, this, false);
        getList();
    }
    public void getList(){
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.TYPE,  "1");
        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.MAINFIND, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                findData = GsonUtil.getDefaultGson().fromJson(requestEntity, FindData.class);
                setAdapter(findData);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    private void setAdapter(FindData findData){
        headerView = getLayoutInflater().inflate(R.layout.fragment_fuhao, null);
        TextView tvName1=headerView.findViewById(R.id.one_name);
        TextView tvName2=headerView.findViewById(R.id.tow_name);
        TextView tvName3=headerView.findViewById(R.id.threename);

        ImageView imageView1=headerView.findViewById(R.id.img_one);
        ImageView imageView2=headerView.findViewById(R.id.img_tow);
        ImageView imageView3=headerView.findViewById(R.id.img_three);
        RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(getBaseActivity())
                .load( Constants.BASEURL+findData.res.getPublicLists().get(0).getLitpic()).apply(options)
                .into(imageView1);
        Glide.with(getBaseActivity())
                .load( Constants.BASEURL+findData.res.getPublicLists().get(1).getLitpic()).apply(options)
                .into(imageView2);
        Glide.with(getBaseActivity())
                .load( Constants.BASEURL+findData.res.getPublicLists().get(2).getLitpic()).apply(options)
                .into(imageView3);
        tvName1.setText(findData.res.getPublicLists().get(0).getName());
        tvName2.setText(findData.res.getPublicLists().get(1).getName());
        tvName3.setText(findData.res.getPublicLists().get(2).getName());
        findData.res.getPublicLists().remove(0);
        findData.res.getPublicLists().remove(0);
        findData.res.getPublicLists().remove(0);
           @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                getBaseActivity(),
                LinearLayoutManager.VERTICAL, false);
        recommendGv.addItemDecoration(new RecyclerViewSpacesItemDecoration(5));
        recommendGv.setLayoutManager(managers);
        fuhaoBangAdapter=new FuhaoBangAdapter(getBaseActivity(),R.layout.adapter_bangdan_item,findData.res.getPublicLists());
        fuhaoBangAdapter.addHeaderView(headerView);
        recommendGv.setAdapter(fuhaoBangAdapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
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
        }
    }

    @Override
    public void onView(View view) {
        view.findViewById(R.id.tv_nv).setOnClickListener(this);
        view.findViewById(R.id.tv_nan).setOnClickListener(this);
        view.findViewById(R.id.tv_all).setOnClickListener(this);
    }
}
