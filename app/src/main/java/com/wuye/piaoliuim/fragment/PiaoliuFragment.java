package com.wuye.piaoliuim.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseFragement;
import com.chuange.basemodule.utils.ViewUtils;
import com.chuange.basemodule.view.refresh.interfaces.RefreshLayout;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.qcloud.tim.uikit.component.AudioPlayer;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.WuyeApplicatione;
import com.wuye.piaoliuim.activity.ChatActivity;
import com.wuye.piaoliuim.adapter.PiaoliuAdapter;
import com.wuye.piaoliuim.bean.PiaoliuData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.ImagUrlUtils;
import com.wuye.piaoliuim.utils.RecyclerViewSpacesItemDecoration;

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
public class PiaoliuFragment extends BaseFragement {


    PiaoliuAdapter publicAdapter;
    PiaoliuData piaoliuData;

    private static final int PAGE_SIZE = 10;
    @ViewUtils.ViewInject(R.id.im_shaixuan)
    TextView imShaixuan;
    @ViewUtils.ViewInject(R.id.recommend_gv)
    RecyclerView recommendGv;
    @ViewUtils.ViewInject(R.id.noDataCommL)
    LinearLayout noDataCommL;
    @ViewUtils.ViewInject(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private int mNextRequestPage = 1;

    private List<PiaoliuData.Res.PiaoliuList> newsList = new ArrayList<>();
    PiaoliuData.Res.PiaoliuList imUserInfo;
    TIMManager timManager;
    List<Object> imcList = new ArrayList<>();
    int initView=1;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.fragment_piaoliu, this);
        initView=1;

        getNetData(mNextRequestPage);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
                mNextRequestPage=1;
                noDataCommL.setVisibility(View.GONE);
                recommendGv.setVisibility(View.VISIBLE);
                getNetData(mNextRequestPage);
            }


        });
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

                boolean isRefresh = mNextRequestPage == 1;
                Log.i("ppppppppp",piaoliuData.res.listList.size()+"");
                if (piaoliuData.res.listList.size() > 0) {

                     initAdapter(isRefresh, piaoliuData);

                } else {
                    showNoData(piaoliuData.res.listList.size());
                }

            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public void setAdapter(boolean isRefresh, PiaoliuData dataList) {
        if (mNextRequestPage == 1) {
            @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                    getBaseActivity(),
                    LinearLayoutManager.VERTICAL, false);
            publicAdapter = new PiaoliuAdapter(getContext(), R.layout.adapter_drift_item, dataList.res.listList);
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

    private void initAdapter(boolean isRefresh, PiaoliuData dataList) {
        if (initView == 1&&dataList.res.getListList().size()>0) {
            initView=2;
            @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                    getBaseActivity(),
                    LinearLayoutManager.VERTICAL, false);
            recommendGv.addItemDecoration(new RecyclerViewSpacesItemDecoration(3));
            recommendGv.setLayoutManager(managers);
            publicAdapter = new PiaoliuAdapter(getContext(), R.layout.adapter_drift_item, dataList.res.listList);
            Log.i("-----","第一次------");

            if (swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
        }

        publicAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getNetData(mNextRequestPage);
            }
        });
        mNextRequestPage++;
        final int size = dataList == null ? 0 : dataList.res.getListList().size();
        if (isRefresh) {

            publicAdapter.setNewData(dataList.res.getListList());
            Log.i("-----","第一次-xinde -----");

        } else {
            if (size > 0) {
                publicAdapter.addData(dataList.res.getListList());
                Log.i("-----","第一次-xinde来了 -----");

            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            publicAdapter.loadMoreEnd(isRefresh);
            publicAdapter.loadMoreComplete();
        }
        Log.i("-----","第一次-设置了 -----");

        recommendGv.setAdapter(publicAdapter);

        setAdapterLis();

    }

    public void setAdapterLis() {
        publicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //jinxing 关注
                imUserInfo = publicAdapter.getData().get(position);
                laoPingzi(publicAdapter.getData().get(position).getUser_id());
            }
        });
        publicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId()) {
                    case R.id.bofang:
                        Log.i("ssssss", "点击了播放");
                        ImageView imageView = view.findViewById(R.id.audioPlayImage);
                        playMedia(ImagUrlUtils.getImag(publicAdapter.getData().get(i).getContent()), imageView);
                        break;
                }
            }
        });
    }

    public static PiaoliuFragment newInstance() {
        return new PiaoliuFragment();
    }

    //捞瓶子扣金币
    public void laoPingzi(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.TYPE, "-3");
        params.put(UrlConstant.USER_ID, id);
        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.LAOPINGZI, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                ConversationInfo conversationInfo = new ConversationInfo();
                conversationInfo.setId(imUserInfo.getUser_id());
                imcList.add(ImagUrlUtils.getImag(imUserInfo.getLitpic()));
                conversationInfo.setIconUrlList(imcList);
                conversationInfo.setGroup(false);
                conversationInfo.setTitle(imUserInfo.getName());
                startChatActivity(conversationInfo);
            }

            @Override
            public void onError(String message) {

            }
        });
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

    private void showNoData(int size) {
        if (size > 0) {
            Log.i("ppppppppp","来了");
            noDataCommL.setVisibility(View.GONE);
            recommendGv.setVisibility(View.VISIBLE);
        } else {
            Log.i("ppppppppp","来了-------");

            noDataCommL.setVisibility(View.VISIBLE);
            recommendGv.setVisibility(View.GONE);

        }
    }

    private void playMedia(String path, ImageView audioPlayImage) {
        if (AudioPlayer.getInstance().isPlaying()) {
            AudioPlayer.getInstance().stopPlay();
            return;
        }
        if (TextUtils.isEmpty(path)) {
            ToastUtil.toastLongMessage("语音文件还未下载完成");
            return;
        }
        audioPlayImage.setImageResource(com.tencent.qcloud.tim.uikit.R.drawable.play_voice_message);
        final AnimationDrawable animationDrawable = (AnimationDrawable) audioPlayImage.getDrawable();
        animationDrawable.start();
//        msg.setCustomInt(READ);
//        unreadAudioText.setVisibility(View.GONE);
        AudioPlayer.getInstance().startPlay(path, new AudioPlayer.Callback() {
            @Override
            public void onCompletion(Boolean success) {
                audioPlayImage.post(new Runnable() {
                    @Override
                    public void run() {
                        animationDrawable.stop();
                        audioPlayImage.setImageResource(com.tencent.qcloud.tim.uikit.R.drawable.voice_msg_playing_3);
                    }
                });
            }
        });

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        mNextRequestPage = 1;
        getNetData(mNextRequestPage);
    }


}
