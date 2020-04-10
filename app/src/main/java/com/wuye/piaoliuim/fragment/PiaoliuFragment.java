package com.wuye.piaoliuim.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTInteractionAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chuange.basemodule.BaseFragement;
import com.chuange.basemodule.utils.ViewUtils;
import com.chuange.basemodule.view.DialogView;
import com.chuange.basemodule.view.refresh.interfaces.RefreshLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.qcloud.tim.uikit.component.AudioPlayer;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.WuyeApplicatione;
import com.wuye.piaoliuim.activity.ChatActivity;
import com.wuye.piaoliuim.activity.FinsActivity;
import com.wuye.piaoliuim.activity.RechangeAct;
import com.wuye.piaoliuim.adapter.PiaoliuAdapter;
import com.wuye.piaoliuim.bean.MoneyData;
import com.wuye.piaoliuim.bean.PiaoliuData;
import com.wuye.piaoliuim.bean.RenWuData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.TTAdManagerHolder;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.ImagUrlUtils;
import com.wuye.piaoliuim.utils.PeterTimeOne;
import com.wuye.piaoliuim.utils.PeterTimeThree;
import com.wuye.piaoliuim.utils.PeterTimeTow;
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
public class PiaoliuFragment extends BaseFragement  implements DialogView.DialogViewListener{


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
    @ViewUtils.ViewInject(R.id.renwu)
    FloatingActionButton renwu;

    private int mNextRequestPage = 1;

    private List<PiaoliuData.Res.PiaoliuList> newsList = new ArrayList<>();
    PiaoliuData.Res.PiaoliuList imUserInfo;
    TIMManager timManager;
    List<Object> imcList = new ArrayList<>();
    int initView=1;
    int shuaxin=2;
    RenWuData renWuData;
    MoneyData moneyData;
    PeterTimeOne timer = null;
    PeterTimeTow timerTow = null;
    PeterTimeThree timerThree = null;
    private boolean mIsExpress = false; //是否请求模板广告
    private static final String TAG = "RewardVideoActivity";
    private TTAdNative mTTAdNative;
    private TTRewardVideoAd mttRewardVideoAd;

  //激励视频广告
    private String shipinId="945114010";
    private String laoyixingID="945115768";
    private String laozaixianId="945115768";
    private int zxOryx=0;
    private TTNativeExpressAd mTTAd;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.fragment_piaoliu, this);
        initView=1;
        getNetData(mNextRequestPage,"1","");
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
                mNextRequestPage=1;
                shuaxin=1;
                noDataCommL.setVisibility(View.GONE);
                recommendGv.setVisibility(View.VISIBLE);
                getNetData(mNextRequestPage,"1","");
            }


        });
        getBaseActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(getContext());
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(WuyeApplicatione.getContext());

    }

    public void getNetData(int page,String type,String zxOryx) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PAGE, page + "");
        params.put(UrlConstant.TYPE, type + "");
        params.put(UrlConstant.Y_TYPE, zxOryx);
        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.PIAOLIULIST, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                piaoliuData = GsonUtil.getDefaultGson().fromJson(requestEntity, PiaoliuData.class);

                boolean isRefresh = mNextRequestPage == 1;
                 if (shuaxin==1){
                    shuaxin=2;
                    showNoData(piaoliuData.res.listList.size());
                }
                if (initView==1) {
                     if (piaoliuData.res.listList.size() > 0) {
                         initAdapter(isRefresh, piaoliuData);
                     } else {
                        showNoData(piaoliuData.res.listList.size());
                    }
                }else {
                    initAdapter(isRefresh, piaoliuData);

                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }



    private void initAdapter(boolean isRefresh, PiaoliuData dataList) {
        if (initView == 1) {
            initView=2;
            @SuppressLint("WrongConstant") RecyclerView.LayoutManager managers = new LinearLayoutManager(
                    getBaseActivity(),
                    LinearLayoutManager.VERTICAL, false);
            recommendGv.addItemDecoration(new RecyclerViewSpacesItemDecoration(3));
            recommendGv.setLayoutManager(managers);
            publicAdapter = new PiaoliuAdapter(getContext(), R.layout.adapter_drift_item, dataList.res.listList);
            Log.i("-----","第一次------");
            recommendGv.setAdapter(publicAdapter);

            if (swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
            publicAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    getNetData(mNextRequestPage,"2","");
                }
            });
        }


        mNextRequestPage++;
        final int size = dataList == null ? 0 : dataList.res.getListList().size();
        if (isRefresh) {
            publicAdapter.setNewData(dataList.res.getListList());
        } else {
            if (size > 0) {
                publicAdapter.addData(dataList.res.getListList());
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            publicAdapter.loadMoreEnd(isRefresh);
        } else {
            publicAdapter.loadMoreComplete();
        }
        setAdapterLis();
      renwu.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //任务管理
              showRenwu();
          }
      });
    }


    public void setAdapterLis() {
        publicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //jinxing 关注
                imUserInfo = publicAdapter.getData().get(position);
                if (publicAdapter.getData().get(position).getType().equals("1")) {
                    laoPingzi(publicAdapter.getData().get(position).getUser_id(),publicAdapter.getData().get(position).getContent(),publicAdapter.getData().get(position).getId());

                }else {
                    laoPingzi(publicAdapter.getData().get(position).getUser_id(),"",publicAdapter.getData().get(position).getId());

                }
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
    public void laoPingzi(String id,String content,String pingziId) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.TYPE, "-3");
        params.put(UrlConstant.USER_ID, id);
        params.put(UrlConstant.CONTENT, content);
        params.put(UrlConstant.ID, pingziId);
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
        getNetData(mNextRequestPage,"1","");
    }

 private void showRenwu(){

     HashMap<String, String> params = new HashMap<>();
     RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.GETZHANGHU, new RequestListener<String>() {
         @Override
         public void onComplete(String requestEntity) {
             moneyData= GsonUtil.getDefaultGson().fromJson(requestEntity, MoneyData.class);

             getRenwu();
         }

         @Override
         public void onError(String message) {

         }
     });
 }

 private void  getRenwu(){
     HashMap<String, String> params = new HashMap<>();
     RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.GETQIANDAO, new RequestListener<String>() {
         @Override
         public void onComplete(String requestEntity) {
             renWuData= GsonUtil.getDefaultGson().fromJson(requestEntity, RenWuData.class);
             showLoad();
             loadAd(shipinId, TTAdConstant.VERTICAL);

         }

         @Override
         public void onError(String message) {

         }
     });
 }
   private void showLoad(){
       loading(R.layout.dialog_renwu, this).setOutsideClose(false);

   }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.go_gg:
                if (mttRewardVideoAd != null) {
                    //step6:在获取到广告后展示,强烈建议在onRewardVideoCached回调后，展示广告，提升播放体验
                    //该方法直接展示广告
//                    mttRewardVideoAd.showRewardVideoAd(RewardVideoActivity.this);

                    //展示广告，并传入广告展示的场景
                    mttRewardVideoAd.showRewardVideoAd(getBaseActivity(), TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
                    mttRewardVideoAd = null;
                } else {

                }
                cancelLoading();
                 break;
                 case R.id.go_lpz:
                      loadExpressAd(laoyixingID, 300, 300);

                     zxOryx=1;
                     cancelLoading();
                     break;
                 case R.id.go_lzx:
                      zxOryx=2;
                     loadExpressAd(laozaixianId, 300, 300);
                     cancelLoading();
                     break;
                 case R.id.go_qd:
                     qiandao();
                     cancelLoading();
                 break;
                 case R.id.img_gbs:
                     if (timer != null) {
                         timer.cancel();
                     }
                     if (timerTow != null) {
                     timerTow.cancel();
                     } if (timerThree != null) {
                     timerThree.cancel();
                     }
                      cancelLoading();
                 break;
                 case R.id.bg_buyjinbi:
                     startActivity(new Intent(getActivity(), RechangeAct.class));
                     cancelLoading();
                     break;
        }

        }
    private void  qiandao(){
        HashMap<String, String> params = new HashMap<>();
        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.QIANDAO, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
         ToastUtil.toastShortMessage("签到成功");
            }

            @Override
            public void onError(String message) {

            }
        });
    }
    @Override
    public void onView(View view) {
       Button gg= view.findViewById(R.id.go_gg);
       Button ggs= view.findViewById(R.id.go_ggs);
       Button lpz= view.findViewById(R.id.go_lpz);
       Button lpzs= view.findViewById(R.id.go_lpzs);
       Button lzx= view.findViewById(R.id.go_lzx);
       Button lzxs= view.findViewById(R.id.go_lzxs);
       Button qd= view.findViewById(R.id.go_qd);
       Button qds= view.findViewById(R.id.go_qds);
       Button buyJInbi= view.findViewById(R.id.bg_buyjinbi);
       ImageView gbImg= view.findViewById(R.id.img_gbs);
        gg.setOnClickListener(this);
        lpz.setOnClickListener(this);
        lzx.setOnClickListener(this);
        qd.setOnClickListener(this);
        gbImg.setOnClickListener(this);
        buyJInbi.setOnClickListener(this);

       TextView jinbi= view.findViewById(R.id.myjinbi);
       Integer shipinInter= Integer.parseInt(renWuData.res.getType1());
       Integer laopingziInter= Integer.parseInt(renWuData.res.getType2());
       Integer laoZaixian= Integer.parseInt(renWuData.res.getType3());
       if (renWuData.res.getSign().equals("0")){
           qd.setVisibility(View.VISIBLE);
           qds.setVisibility(View.GONE);
       }else {
           qds.setVisibility(View.VISIBLE);
           qd.setVisibility(View.GONE);
       }if (shipinInter<=0){
           gg.setVisibility(View.VISIBLE);
           ggs.setVisibility(View.GONE);
       }else {
            ggs.setVisibility(View.VISIBLE);
            gg.setVisibility(View.GONE);
            timer = new PeterTimeOne(shipinInter*1000, 1000, ggs,gg);
            timer.start();
       }if (laopingziInter<=0){
           lpz.setVisibility(View.VISIBLE);
           lpzs.setVisibility(View.GONE);
       }else {
            lpzs.setVisibility(View.VISIBLE);
            lpz.setVisibility(View.GONE);
            timerTow = new PeterTimeTow(laopingziInter*1000, 1000, lpzs,lpz);
            timerTow.start();
       }if (laoZaixian<=0){
           lzx.setVisibility(View.VISIBLE);
           lzxs.setVisibility(View.GONE);
       }else {
            lzxs.setVisibility(View.VISIBLE);
            lzx.setVisibility(View.GONE);
            timerThree = new PeterTimeThree(laoZaixian*1000, 1000, lzxs,lzx);
            timerThree.start();
       }
       jinbi.setText(" 当前金币："+moneyData.res.getListList().getUser_gold());

     }
     private void addJInbi(String type){
         HashMap<String, String> params = new HashMap<>();
         params.put(UrlConstant.TYPE,type);
         RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.ADDJINBI, new RequestListener<String>() {
             @Override
             public void onComplete(String requestEntity) {

             }

             @Override
             public void onError(String message) {

             }
         });
     }
    private boolean mHasShowDownloadActive = false;

    private void loadAd(final String codeId, int orientation) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot;
        if (mIsExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeId)
                    .setSupportDeepLink(true)
                    .setRewardName("金币") //奖励的名称
                    .setRewardAmount(3)  //奖励的数量
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
                    .setExpressViewAcceptedSize(500,500)
                    .setUserID("user123")//用户id,必传参数
                    .setMediaExtra("media_extra") //附加参数，可选
                    .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                    .build();
        } else {
            //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeId)
                    .setSupportDeepLink(true)
                    .setRewardName("金币") //奖励的名称
                    .setRewardAmount(3)  //奖励的数量
                    .setUserID("user123")//用户id,必传参数
                    .setMediaExtra("media_extra") //附加参数，可选
                    .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                    .build();
        }
        //step5:请求广告
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "onError: " + code + ", " + String.valueOf(message));
             }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                Log.e(TAG, "onRewardVideoCached");
             }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                Log.e(TAG, "onRewardVideoAdLoad");

                 mttRewardVideoAd = ad;
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                     }

                    @Override
                    public void onAdVideoBarClick() {
                     }

                    @Override
                    public void onAdClose() {
                        Log.i("pppppppp","关闭关闭");
                     }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                                        addJInbi("1");//视频广告

                    }

                    @Override
                    public void onVideoError() {
                     }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                     }

                    @Override
                    public void onSkippedVideo() {
                     }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        mHasShowDownloadActive = false;
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadActive==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);

                        if (!mHasShowDownloadActive) {
                            mHasShowDownloadActive = true;
                         }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadPaused===totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                     }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFailed==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                     }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFinished==totalBytes=" + totalBytes + ",fileName=" + fileName + ",appName=" + appName);
                     }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        Log.d("DML", "onInstalled==" + ",fileName=" + fileName + ",appName=" + appName);
                     }
                });
            }
        });
    }
    /**
     * 加载插屏广告
     */
    private void loadExpressAd(String codeId, int expressViewWidth, int expressViewHeight) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(expressViewWidth, expressViewHeight) //期望模板广告view的size,单位dp
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
             }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    return;
                }
                mTTAd = ads.get(0);
                bindAdListener(mTTAd);
//                startTime = System.currentTimeMillis();
                mTTAd.render();
            }
        });
    }


    private void bindAdListener(TTNativeExpressAd ad) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.AdInteractionListener() {
            @Override
            public void onAdDismiss() {
                mNextRequestPage=1;
                if (zxOryx==1){
                    getNetData(mNextRequestPage,"1","1");
                    addJInbi("2");
                }else if (zxOryx==2){
                    getNetData(mNextRequestPage,"1","2");
                    addJInbi("3");

                }
             }

            @Override
            public void onAdClicked(View view, int type) {
             }

            @Override
            public void onAdShow(View view, int type) {
             }

            @Override
            public void onRenderFail(View view, String msg, int code) {
              }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                 //返回view的宽高 单位 dp
                 mTTAd.showInteractionExpressAd(getBaseActivity());

            }
        });

        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
             }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;
                 }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
             }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
             }

            @Override
            public void onInstalled(String fileName, String appName) {
             }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
             }
        });
    }

}
