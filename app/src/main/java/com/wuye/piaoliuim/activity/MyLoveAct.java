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
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.WuyeApplicatione;
import com.wuye.piaoliuim.adapter.LoveAdapter;
import com.wuye.piaoliuim.bean.FinsData;
import com.wuye.piaoliuim.bean.LoveData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

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
    @BindView(R.id.noDataCommL)
    LinearLayout noDataCommL;

    private int mNextRequestPage = 1;

    private List<FinsData.Res.FinsList> newsList = new ArrayList<>();
    LoveData listData;
    LoveAdapter publicAdapter;
    List<Object> imcList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        ButterKnife.bind(this);
        getNetData(mNextRequestPage);
    }

    public void getNetData(int page) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PAGE, page + "");
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.MYFOLLW, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                listData = GsonUtil.getDefaultGson().fromJson(requestEntity, LoveData.class);
                boolean isRefresh =mNextRequestPage ==1;
                if (listData.res.listList.size()>0){

                 setAdapter(isRefresh,listData);
            }else {
                    showNoData(listData.res.listList.size());
                 }

            }
            @Override
            public void onError(String message) {

            }
        });
    }

    public void cancelLove(final int postione, String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PASSID, id);
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

    public void setAdapter(boolean isRefresh,LoveData dataList) {
        if (mNextRequestPage == 1) {
            publicAdapter = new LoveAdapter(this, R.layout.adapter_mylove_item, dataList.res.listList);
            @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                    this,
                    LinearLayoutManager.VERTICAL, false);
            recommendGv.setLayoutManager(managers);
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

    public void setAdapterLis() {
        publicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                Intent intent=new Intent(getBaseContext(), UserInfoAct.class);
                intent.putExtra("id",publicAdapter.getData().get(position).getPass_id());
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
        publicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                int itemViewId = view.getId();
                String passId = publicAdapter.getData().get(position).pass_id;
                switch (itemViewId) {
                    case R.id.tv_deleteblove:
                        cancelLove(position, passId);
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
    private void showNoData(int size) {
        if (size > 0) {
            noDataCommL.setVisibility(View.GONE);
            recommendGv.setVisibility(View.VISIBLE);
        } else {

            noDataCommL.setVisibility(View.VISIBLE);
            recommendGv.setVisibility(View.GONE);

        }
    }
    private void startChatActivity(ConversationInfo conversationInfo) {
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(conversationInfo.isGroup() ? TIMConversationType.Group : TIMConversationType.C2C);
        chatInfo.setId(conversationInfo.getId());
        chatInfo.setChatName(conversationInfo.getTitle());
        Intent intent = new Intent(WuyeApplicatione.getContext(), ChatActivity.class);
        intent.putExtra(Constants.CHAT_INFO, chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        WuyeApplicatione.instance().startActivity(intent);
    }
}
