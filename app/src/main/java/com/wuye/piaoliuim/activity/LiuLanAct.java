package com.wuye.piaoliuim.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.view.NavigationTopView;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.LiuLanAdapter;
import com.wuye.piaoliuim.bean.LiulanData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiuLanAct extends BaseActivity {


    @BindView(R.id.topView)
    NavigationTopView topView;
    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;

    LiulanData listData;
    LiuLanAdapter publicAdapter;
    @BindView(R.id.noDataCommL)
    LinearLayout noDataCommL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        ButterKnife.bind(this);
        topView.setTitle("谁看过我");
        getNetData();
    }

    public void getNetData() {
        HashMap<String, String> params = new HashMap<>();
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.JILULIEBIAO, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                listData = GsonUtil.getDefaultGson().fromJson(requestEntity, LiulanData.class);
                if (listData.res.listList.size()>0){
                    setAdapter(listData);
                }else {
                    showNoData(listData.res.listList.size());
                }

            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void setAdapter(LiulanData listData) {
        publicAdapter = new LiuLanAdapter(this, R.layout.adapter_jilu_item, listData.res.listList);
        @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL, false);
        recommendGv.setLayoutManager(managers);
        recommendGv.setAdapter(publicAdapter);
        setAdapterLis();
    }

    public void setAdapterLis() {
        publicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                Intent intent = new Intent(getBaseContext(), UserInfoAct.class);
                intent.putExtra("id", publicAdapter.getData().get(position).getBrow_id());
                startActivity(intent);
//                ConversationInfo conversationInfo = new ConversationInfo();
//                conversationInfo.setId(publicAdapter.getData().get(i).getPass_id());
//                imcList.add(ImagUrlUtils.getImag(publicAdapter.getData().get(i).getLitpic()));
//                conversationInfo.setIconUrlList(imcList);
//                conversationInfo.setGroup(false);
//                conversationInfo.setTitle(publicAdapter.getData().get(i).getName());
//                startChatActivity(conversationInfo);
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
