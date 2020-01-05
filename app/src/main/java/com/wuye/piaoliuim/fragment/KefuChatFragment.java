package com.wuye.piaoliuim.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuange.basemodule.view.DialogView;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriendResult;
import com.tencent.qcloud.tim.uikit.component.AudioPlayer;
import com.tencent.qcloud.tim.uikit.component.NoticeLayout;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.inputmore.InputMoreActionUnit;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;
import com.tencent.qcloud.tim.uikit.utils.TUIKitLog;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.WuyeApplicatione;
import com.wuye.piaoliuim.activity.JubaoAct;
import com.wuye.piaoliuim.activity.imactivity.FriendProfileActivity;
import com.wuye.piaoliuim.adapter.DialogLiwuAdapter;
import com.wuye.piaoliuim.bean.ChannelModel;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.helper.ChatLayoutHelper;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.KeyMapDailog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName KefuChatFragment
 * @Description
 * @Author VillageChief
 * @Date 2020/1/3 10:55
 */
public class KefuChatFragment extends BaseImFragment implements  DialogView.DialogViewListener, DialogLiwuAdapter.OnCheckedChangedListener {

    private View mBaseView;
    private ChatLayout mChatLayout;
    private TitleBarLayout mTitleBar;
    private ChatInfo mChatInfo;
    private NoticeLayout mNoticeLayout;
    private MessageLayout mMessageLayout;
    private InputLayout mInputLayout;
    int type = 0;


    ArrayList<ChannelModel> list = new ArrayList<>();
    DialogLiwuAdapter dialogLiwuAdapter;

    RecyclerView recommendGv;
    TextView tvNumber, tvJinbi, tvTop, tvSend;
    KeyMapDailog dialog;

    String liwuNumber = "1";
    String liwuNUmbers = "1";

    int postione = 0;

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    String mId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.chat_fragment, container, false);
        return mBaseView;
    }

    private void initView() {
        //从布局文件中获取聊天面板组件
        mChatLayout = mBaseView.findViewById(R.id.chat_layout);

        //单聊组件的默认UI和交互初始化
        mChatLayout.initDefault();

        /*
         * 需要聊天的基本信息
         */
        mChatLayout.setChatInfo(mChatInfo);
        mId=mChatInfo.getId();
        //获取单聊面板的标题栏
        mTitleBar = mChatLayout.getTitleBar();
        mNoticeLayout = mChatLayout.getNoticeLayout();
        mMessageLayout = mChatLayout.getMessageLayout();
        mInputLayout = mChatLayout.getInputLayout();
        //单聊面板标记栏返回按钮点击事件，这里需要开发者自行控制
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        if (mChatInfo.getType() == TIMConversationType.C2C) {
            mTitleBar.setOnRightClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(WuyeApplicatione.instance(), FriendProfileActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra(TUIKitConstants.ProfileType.CONTENT, mChatInfo);
//                    WuyeApplicatione.instance().startActivity(intent);
//                    initLoad();
                }
            });
        }
        mChatLayout.getMessageLayout().setOnItemClickListener(new MessageLayout.OnItemClickListener() {
            @Override
            public void onMessageLongClick(View view, int position, MessageInfo messageInfo) {
                //因为adapter中第一条为加载条目，位置需减1
                mChatLayout.getMessageLayout().showItemPopMenu(position - 1, messageInfo, view);
            }

            @Override
            public void onUserIconClick(View view, int position, MessageInfo messageInfo) {
                if (null == messageInfo) {
                    return;
                }
                ChatInfo info = new ChatInfo();
                info.setId(messageInfo.getFromUser());
                Intent intent = new Intent(WuyeApplicatione.instance(), FriendProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(TUIKitConstants.ProfileType.CONTENT, info);
                WuyeApplicatione.instance().startActivity(intent);
            }
        });
        initBg();
    }

    private void initLoad() {
        type = 0;
        loading(R.layout.dialog_im_more, this).setOutsideClose(true).setGravity(Gravity.BOTTOM);

    }

    private void initLiwu() {
        type = 2;
        loading(R.layout.dialog_liwu, this).setOutsideClose(true).setGravity(Gravity.BOTTOM);

    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle bundle = getArguments();
        mChatInfo = (ChatInfo) bundle.getSerializable(Constants.CHAT_INFO);
        if (mChatInfo == null) {
            return;
        }
        initView();

        // TODO 通过api设置ChatLayout各种属性的样例 自定义消息
//        ChatLayoutHelper helper = new ChatLayoutHelper(getActivity());
//        helper.customizeChatLayout(mChatLayout);
    }

    @Override
    public void onPause() {
        super.onPause();
        AudioPlayer.getInstance().stopPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatLayout != null) {
            mChatLayout.exitChat();
        }
    }

    private void initBg() {
        mTitleBar.getRightIcon().setVisibility(View.GONE);
         mNoticeLayout.setBackgroundColor(Color.parseColor("#262339"));
        mTitleBar.setBackgroundColor(Color.parseColor("#262339"));
        mTitleBar.getMiddleTitle().setText(mChatInfo.getChatName());
          //自定义一个发送礼物

    }

    @Override
    public void onView(View view) {
//        if (type == 0) {
//            view.findViewById(R.id.tv_clear).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.i("ppppppChatIm", "清空");
//
//                }
//            });
//            view.findViewById(R.id.tv_jubao).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getContext(), JubaoAct.class);
//                    intent.putExtra("uid",mId);
//                    startActivity(intent);
//                    cancelLoading();
//                }
//            });
//            view.findViewById(R.id.tv_addblack).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toBlack();
//
//                }
//            });
//            view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    cancelLoading();
//
//                }
//            });
//        } else {
//            recommendGv = view.findViewById(R.id.rv_comment);
//            tvNumber = view.findViewById(R.id.tv_num);
//            tvJinbi = view.findViewById(R.id.tv_jb);
//            tvTop = view.findViewById(R.id.tv_top);
//            tvSend = view.findViewById(R.id.bt_send);
//            tvSend.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    sendLiwu(list.get(postione).addJinbi, liwuNUmbers);
//                    cancelLoading();
//                }
//            });
//            tvTop.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
//            tvNumber.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog = new KeyMapDailog("", new KeyMapDailog.SendBackListener() {
//                        @Override
//                        public void sendBack(String inputText) {
//                            //TODO  点击发表后业务逻辑
//                            liwuNumber = inputText;
//                            liwuNUmbers = liwuNumber;
//                            tvNumber.setText("X" + liwuNumber);
//                            Log.i("ssssss", inputText);
//                            dialog.hideSoftkeyboard();
//                            dialog.dismiss();
//
//                        }
//                    });
//                    dialog.show(getFragmentManager(), "kk");
//                }
//            });
//            initRec();
//        }
    }

//    private void sendLiwu(String gid, String number) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put(UrlConstant.TYPE, "1");
//        params.put(UrlConstant.USER_ID, mChatInfo.getId());
//        params.put(UrlConstant.GID, gid);
//        params.put(UrlConstant.NUMLIWU, number);
//        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.SENDLIWU, new RequestListener<String>() {
//            @Override
//            public void onComplete(String requestEntity) {
//                Toast toast = showToastFree("1", list.get(postione).imgSrc);
//                toast.setDuration(Toast.LENGTH_LONG);
////                Uri uri = resourceIdToUris(getContext(), list.get(postione).imgSrc);
//                Log.i("ppppppppp", "发送礼物数据");
////                MessageInfo info = MessageInfoUtil.buildImageMessage(uri, false);
////                mChatLayout.sendMessage(info, false);
//            }
//
//            @Override
//            public void onError(String message) {
//
//            }
//        });
//
//    }

//    private void initRec() {
//        ChannelModel channelModel = new ChannelModel(ChannelModel.ONE, "饮料", "10金币", "1", "10", R.mipmap.liwu_yin);
//        ChannelModel channelModels = new ChannelModel(ChannelModel.ONE, "包包", "20金币", "2", "20", R.mipmap.liwu_bao);
//        ChannelModel channelModelss = new ChannelModel(ChannelModel.ONE, "蛋糕", "30金币", "3", "30", R.mipmap.liwu_dan);
//        ChannelModel channelModelsss = new ChannelModel(ChannelModel.ONE, "水晶鞋", "50金币", "4", "50", R.mipmap.liwu_xie);
//        ChannelModel channelModel1 = new ChannelModel(ChannelModel.ONE, "红唇", "50金币", "5", "50", R.mipmap.liwu_chun);
//        ChannelModel channelModel2 = new ChannelModel(ChannelModel.ONE, "钻戒", "100金币", "6", "100", R.mipmap.liwu_zuan);
//        ChannelModel channelModel3 = new ChannelModel(ChannelModel.ONE, "一箭穿心", "200金币", "7", "200", R.mipmap.liwu_xin);
//        ChannelModel channelModel4 = new ChannelModel(ChannelModel.ONE, "城堡", "500金币", "8", "500", R.mipmap.liwu_cheng);
//        list.add(channelModel);
//        list.add(channelModels);
//        list.add(channelModelss);
//        list.add(channelModelsss);
//        list.add(channelModel1);
//        list.add(channelModel2);
//        list.add(channelModel3);
//        list.add(channelModel4);
//        dialogLiwuAdapter = new DialogLiwuAdapter(getContext());
//        dialogLiwuAdapter.replaceAll(list);
//        recommendGv.setHasFixedSize(true);
//        recommendGv.setLayoutManager(new GridLayoutManager(getContext(), 4));
//        recommendGv.setAdapter(dialogLiwuAdapter);
//        dialogLiwuAdapter.changetShowDelImage(true, 8);
//        dialogLiwuAdapter.setOnCheckChangedListener(this);
//
//    }

    @Override
    public void onItemChecked(int position) {
        postione = position;

    }

//    public Toast showToastFree(String str, int resID) {
//        Toast toast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
//        RelativeLayout toastView = (RelativeLayout) RelativeLayout.inflate(getContext(), R.layout.toast_hor_view, null);
//        ImageView iv = toastView.findViewById(R.id.im_liwu);
//        iv.setImageResource(resID);
//
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.setView(toastView);
//        toast.show();
//        return toast;
//
//    }

    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    private static Uri resourceIdToUris(Context context,int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);//自己本地的图片可以是drawabe/mipmap
        Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "", ""));
        return imageUri;
    }
//    private void addBlack() {
//        String[] idStringList = mId.split(",");
//
//        List<String> idList = new ArrayList<>();
//        for (String id : idStringList) {
//            idList.add(id);
//        }
//
//        TIMFriendshipManager.getInstance().addBlackList(idList, new TIMValueCallBack<List<TIMFriendResult>>() {
//            @Override
//            public void onError(int i, String s) {
//                TUIKitLog.e("TAG", "addBlackList err code = " + i + ", desc = " + s);
//                ToastUtil.toastShortMessage("Error code = " + i + ", desc = " + s);
//            }
//
//            @Override
//            public void onSuccess(List<TIMFriendResult> timFriendResults) {
//                ToastUtil.toastShortMessage("成功加入黑名单");
//                getActivity().finish();
//
//                TUIKitLog.i("TAG", "addBlackList success");
//            }
//        });
//    }
    //加入黑名单
//    public void toBlack() {
//        HashMap<String, String> params = new HashMap<>();
//        params.put(UrlConstant.BLICKID, mId);
//        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.ADDBLACk, new RequestListener<String>() {
//            @Override
//            public void onComplete(String requestEntity) {
//                addBlack();
//            }
//
//            @Override
//            public void onError(String message) {
//
//            }
//        });
//    }

}
