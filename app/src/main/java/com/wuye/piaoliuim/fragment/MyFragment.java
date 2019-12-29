package com.wuye.piaoliuim.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseFragement;
import com.chuange.basemodule.utils.ViewUtils;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.activity.BlackList;
import com.wuye.piaoliuim.activity.FinsActivity;
import com.wuye.piaoliuim.activity.LiuLanAct;
import com.wuye.piaoliuim.activity.LiwuAct;
import com.wuye.piaoliuim.activity.MyActivity;
import com.wuye.piaoliuim.activity.MyLoveAct;
import com.wuye.piaoliuim.activity.OpinionAct;
import com.wuye.piaoliuim.activity.RechangeAct;
import com.wuye.piaoliuim.activity.SettingAct;
import com.wuye.piaoliuim.adapter.FragmnetMyAdapter;
import com.wuye.piaoliuim.bean.ItemBean;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
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
    private Class[] itemClass = {LiwuAct.class, RechangeAct.class, LiuLanAct.class, BlackList.class, BlackList.class, OpinionAct.class, OpinionAct.class, SettingAct.class};
    UserInfoData userInfoData;
    ImageView header;
    TextView tvname,sigeConetn,fians,jinbi,guanzhu;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.activity_my, this, false);

        initAdapter();
        getNetData();
     }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
    public void getNetData(){
        HashMap<String, String> params = new HashMap<>();
        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.GETUSERINFO, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                userInfoData= GsonUtil.getDefaultGson().fromJson(requestEntity, UserInfoData.class);
                tvname.setText(userInfoData.res.listList.getName());
                fians.setText(userInfoData.res.listList.getFans());
                guanzhu.setText(userInfoData.res.listList.getFollows());
                sigeConetn.setText(userInfoData.res.listList.getSignature());
                jinbi.setText(userInfoData.res.listList.getUser_gold());
                RequestOptions options = new RequestOptions()//圆形图片
                        .circleCrop();
                Glide.with(getBaseActivity())
                        .load(Constants.BASEURL+userInfoData.res.listList.getLitpic()).apply(options)
                        .into(header);
             }

            @Override
            public void onError(String message) {

            }
        });
    }
    private void initAdapter(){
        String[] name = getResources().getStringArray(R.array.main_account_list);
        int[] nameIcon = {R.mipmap.ic_myliwu, R.mipmap.ic_myjbi,R.mipmap.ic_myjbi,
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
        LinearLayout llFins=headerView.findViewById(R.id.ll_fins);
        LinearLayout ll_love=headerView.findViewById(R.id.ll_love);
        LinearLayout llfistTop=headerView.findViewById(R.id.rl_firsttop);
        TextView textView=headerView.findViewById(R.id.tv_myinfo);
          header=headerView.findViewById(R.id.clock);
        tvname=headerView.findViewById(R.id.tv_name);
         sigeConetn=headerView.findViewById(R.id.tv_singe);
        fians=headerView.findViewById(R.id.tv_myfans);
        jinbi=headerView.findViewById(R.id.jinbi);
        guanzhu=headerView.findViewById(R.id.tv_mylove);

        llFins.setOnClickListener(this);
        ll_love.setOnClickListener(this);
        llfistTop.setOnClickListener(this);
        textView.setOnClickListener(this);
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
            if (i==3){
                //应用评分
             }else if(i==4){
                //守则
             }else {
                startActivity(new Intent(getContext(),itemClass[i]));
            }
            }
        });

     }
    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_fins:
                startActivity(new Intent(getActivity(), FinsActivity.class));
                  break;
            case R.id.ll_love:
                startActivity(new Intent(getActivity(), MyLoveAct.class));
                  break;
            case R.id.rl_firsttop:
                startActivity(new Intent(getActivity(), RechangeAct.class));
                  break;
            case R.id.tv_myinfo:
                startActivity(new Intent(getActivity(), MyActivity.class));
                  break;
        }
    }
}
