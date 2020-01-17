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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuange.basemodule.utils.ScreenUtils;
import com.chuange.basemodule.view.DialogView;
import com.google.gson.Gson;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriendResult;
import com.tencent.qcloud.tim.uikit.base.BaseFragment;
import com.tencent.qcloud.tim.uikit.component.AudioPlayer;
import com.tencent.qcloud.tim.uikit.component.NoticeLayout;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.inputmore.InputMoreActionUnit;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfoUtil;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;
import com.tencent.qcloud.tim.uikit.utils.TUIKitLog;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.wuye.piaoliuim.MainActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.WuyeApplicatione;
import com.wuye.piaoliuim.activity.BindPhone;
import com.wuye.piaoliuim.activity.JubaoAct;
import com.wuye.piaoliuim.activity.MyActivity;
import com.wuye.piaoliuim.activity.OpinionAct;
import com.wuye.piaoliuim.activity.RechangeAct;
import com.wuye.piaoliuim.activity.UserInfoAct;
import com.wuye.piaoliuim.activity.imactivity.FriendProfileActivity;
import com.wuye.piaoliuim.adapter.DialogLiwuAdapter;
import com.wuye.piaoliuim.bean.ChannelModel;
import com.wuye.piaoliuim.bean.LiwuListData;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.helper.ChatLayoutHelper;
import com.wuye.piaoliuim.helper.CustomMessage;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.AppSessionEngine;
import com.wuye.piaoliuim.utils.KeyMapDailog;
import com.wuye.piaoliuim.utils.PopupOrderPriceDetail;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.wuye.piaoliuim.WuyeApplicatione.getContext;

/**
 * @ClassName ChatFragment
 * @Description
 * @Author VillageChief
 * @Date 2020/1/2 10:33
 */
public class ChatFragment extends BaseImFragment implements  DialogView.DialogViewListener,DialogLiwuAdapter.OnCheckedChangedListener {

    private View mBaseView;
    private ChatLayout mChatLayout;
    private TitleBarLayout mTitleBar;
    private ChatInfo mChatInfo;
    private NoticeLayout mNoticeLayout;
    private MessageLayout mMessageLayout;
    private InputLayout mInputLayout;
    int type = 0;


    ArrayList<LiwuListData.Res.LiwuLiestData> list = new ArrayList<>();
    DialogLiwuAdapter dialogLiwuAdapter;

    RecyclerView recommendGv;
    TextView tvNumber, tvJinbi, tvTop, tvSend;
    KeyMapDailog dialog;

    String liwuNumber = "1";
    String liwuNUmbers = "1";

    int postione = 0;
    LiwuListData liwuListData;

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    String mId;
    private EasyPopup mCirclePop;


    PopupOrderPriceDetail popupOrderPriceDetail;
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
                    initLoad();
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
                if (messageInfo.getFromUser().equals(TIMManager.getInstance().getLoginUser())){
                    Intent intent = new Intent(getContext(), MyActivity.class);
                     startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), UserInfoAct.class);
                    intent.putExtra("id",mId);
                    startActivity(intent);
                }
 //                if (messageInfo.getId().equals(AppSessionEngine.getMyUserInfo().res.getListList().getId())){
//
//                    }else {
//
//        }
//                 ChatInfo info = new ChatInfo();
//                info.setId(messageInfo.getFromUser());
//                Intent intent = new Intent(WuyeApplicatione.instance(), FriendProfileActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra(TUIKitConstants.ProfileType.CONTENT, info);
//                WuyeApplicatione.instance().startActivity(intent);
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
  Log.i("ppppppppppppppppppp","chushihua ahahahah ");
        // TODO 通过api设置ChatLayout各种属性的样例
        ChatLayoutHelper helper = new ChatLayoutHelper(getActivity());
        helper.customizeChatLayout(mChatLayout);
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
        int[] a={50,50};
        mTitleBar.setRightIcon(R.mipmap.ic_zi_maore);
        mTitleBar.setLeftIcon(R.mipmap.ic_back);
         mNoticeLayout.setBackgroundColor(Color.parseColor("#262339"));
         mTitleBar.getMiddleTitle().setText(mChatInfo.getChatName());
           mInputLayout.disableCaptureAction(false);
// 隐藏发送文件
        mInputLayout.disableSendFileAction(true);
// 隐藏发送图片
        mInputLayout.disableSendPhotoAction(false);
// 隐藏摄像并发送
        mInputLayout.disableVideoRecordAction(false);
        //自定义一个发送礼物
        InputMoreActionUnit unit = new InputMoreActionUnit();
//        InputMoreActionUnit units = new InputMoreActionUnit();
//        InputMoreActionUnit unitss = new InputMoreActionUnit();
//        InputMoreActionUnit unitsss = new InputMoreActionUnit();

        unit.setIconResId(R.mipmap.ic_liwu_item); // 设置单元的图标
        unit.setTitleId(R.string.liwu); // 设置单元的文字标题
        unit.setOnClickListener(new View.OnClickListener() { // 定义点击事件
            @Override
            public void onClick(View v) {
//              ToastUtil.toastShortMessage("自定义的更多功能");

                initLiwu();
            }
        });
// 把定义好的单元增加到更多面板

        mInputLayout.addAction(unit);
//        mInputLayout.addAction(units);
//        mInputLayout.addAction(unitss);
//        mInputLayout.addAction(unitsss);
    }
//礼物的逻辑
    @Override
    public void onView(View view) {
        if (type == 0) {
            view.findViewById(R.id.tv_clear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TIMManager.getInstance().deleteConversationAndLocalMsgs(TIMConversationType.C2C,mChatInfo.getId());
                    getActivity().finish();

//                            mChatLayout.deletelocal(new TIMCallBack() {
//                                @Override
//                                public void onError(int i, String s) {
//                                    Log.i("ppppppp"," 失败事实上 "+s);
//
//                                }
//
//                                @Override
//                                public void onSuccess() {
//                                    Log.i("ppppppp","成功事实上 ");
//                                }
//                            });



                }
            });
            view.findViewById(R.id.tv_jubao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), JubaoAct.class);
                    intent.putExtra("uid",mId);
                    startActivity(intent);
                    cancelLoading();
                }
            });
            view.findViewById(R.id.tv_addblack).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     toBlack();

                }
            });
            view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancelLoading();

                }
            }); view.findViewById(R.id.tv_ziliao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), UserInfoAct.class);
                    intent.putExtra("id",mId);
                    startActivity(intent);

                }
            });
        } else {
            recommendGv = view.findViewById(R.id.rv_comment);
            tvNumber = view.findViewById(R.id.tv_num);
            tvJinbi = view.findViewById(R.id.tv_jb);
            tvJinbi.setText(AppSessionEngine.getMyUserInfo().res.getListList().getUser_gold());
            tvTop = view.findViewById(R.id.tv_top);
            tvSend = view.findViewById(R.id.bt_send);
            tvSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendLiwu(list.get(postione).getId(), liwuNUmbers);
                    cancelLoading();
                }
            });
            tvTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), RechangeAct.class);
                    startActivity(intent);
                    cancelLoading();
                }
            });
            tvNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    popupOrderPriceDetail=new PopupOrderPriceDetail(getActivity(),12,false);
//                    popupOrderPriceDetail.showUp(tvNumber);
//                    showTipPopupWindow4(tvNumber);
//                    showLiwuNum();
                    //暂时注释 是那个弹框输入
                    dialog = new KeyMapDailog("", new KeyMapDailog.SendBackListener() {
                        @Override
                        public void sendBack(String inputText) {
                            //TODO  点击发表后业务逻辑
                            liwuNumber = inputText;
                            liwuNUmbers = liwuNumber;
                            tvNumber.setText("X" + liwuNumber);
                            Log.i("ssssss", inputText);
                            dialog.hideSoftkeyboard();
                            dialog.dismiss();

                        }
                    });
                    dialog.show(getFragmentManager(), "kk");

                }
            });
            initRec();
        }
    }

    private void sendLiwu(String gid, String number) {
        HashMap<String, String> params = new HashMap<>();
         params.put(UrlConstant.USER_ID, mChatInfo.getId());
        params.put(UrlConstant.GID, gid);
        params.put(UrlConstant.NUMLIWU, number);
        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.SENDLIWU, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {

//                Uri uri = resourceIdToUris(getContext(), list.get(postione).imgSrc);
//                MessageInfo info = MessageInfoUtil.buildTextMessage("送您"+number+"个"+ list.get(postione).data);
//                mChatLayout.sendMessage(info, false);
                Gson gson = new Gson();
                CustomMessage customMessage = new CustomMessage();
                customMessage.setName("送您"+number+"个"+ list.get(postione).getName());
                customMessage.setPicUrl(list.get(postione).getLitpic());
                 String data = gson.toJson(customMessage);
                MessageInfo info = MessageInfoUtil.buildCustomMessage(data);
                mChatLayout.sendMessage(info, false);
                Toast toast = showToastFree("1",equNAme( list.get(postione).getName()));
                toast.setDuration(Toast.LENGTH_LONG);
                 getNetData();
            }

            @Override
            public void onError(String message) {

            }
        });

    }

    private void initRec() {
        getLiwu();
//        ChannelModel channelModel = new ChannelModel(ChannelModel.ONE, "饮料", "10金币", "1", "10", R.mipmap.liwu_yin);
//        ChannelModel channelModels = new ChannelModel(ChannelModel.ONE, "包包", "88金币", "2", "88", R.mipmap.liwu_bao);
//        ChannelModel channelModelss = new ChannelModel(ChannelModel.ONE, "蛋糕", "188金币", "3", "188", R.mipmap.liwu_dan);
//        ChannelModel channelModelsss = new ChannelModel(ChannelModel.ONE, "水晶鞋", "520金币", "4", "520", R.mipmap.liwu_xie);
//        ChannelModel channelModel1 = new ChannelModel(ChannelModel.ONE, "红唇", "999金币", "5", "999", R.mipmap.liwu_chun);
//        ChannelModel channelModel2 = new ChannelModel(ChannelModel.ONE, "钻戒", "1314金币", "6", "1314", R.mipmap.liwu_zuan);
//        ChannelModel channelModel3 = new ChannelModel(ChannelModel.ONE, "一箭穿心", "5200金币", "7", "5200", R.mipmap.liwu_xin);
//        ChannelModel channelModel4 = new ChannelModel(ChannelModel.ONE, "城堡", "9999金币", "8", "9999", R.mipmap.liwu_cheng);
//        list.add(channelModel);
//        list.add(channelModels);
//        list.add(channelModelss);
//        list.add(channelModelsss);
//        list.add(channelModel1);
//        list.add(channelModel2);
//        list.add(channelModel3);
//        list.add(channelModel4);


    }

    @Override
    public void onItemChecked(int position) {
        postione = position;

    }



    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    private static Uri resourceIdToUris(Context context,int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);//自己本地的图片可以是drawabe/mipmap
        Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "", ""));
        return imageUri;
    }
    private void addBlack() {
        String[] idStringList = mId.split(",");

        List<String> idList = new ArrayList<>();
        for (String id : idStringList) {
            idList.add(id);
        }

        TIMFriendshipManager.getInstance().addBlackList(idList, new TIMValueCallBack<List<TIMFriendResult>>() {
            @Override
            public void onError(int i, String s) {
                TUIKitLog.e("TAG", "addBlackList err code = " + i + ", desc = " + s);
                ToastUtil.toastShortMessage("Error code = " + i + ", desc = " + s);
            }

            @Override
            public void onSuccess(List<TIMFriendResult> timFriendResults) {
                ToastUtil.toastShortMessage("成功加入黑名单");
                getActivity().finish();

                TUIKitLog.i("TAG", "addBlackList success");
            }
        });
    }
    //加入黑名单
    public void toBlack() {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.BLICKID, mId);
        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.ADDBLACk, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
             addBlack();
            }

            @Override
            public void onError(String message) {

            }
        });
    }
 private void showLiwuNum(){
     mCirclePop = EasyPopup.create()
             .setContentView(getContext(), R.layout.layout_circle_comment)
             //是否允许点击PopupWindow之外的地方消失
             .setFocusAndOutsideEnable(true)
             .apply();
     TextView tvOne=mCirclePop.findViewById(R.id.tv_num_one);
     TextView tvTen=mCirclePop.findViewById(R.id.tv_num_ten);
     TextView tvBai=mCirclePop.findViewById(R.id.tv_num_bai);
      mCirclePop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
      mCirclePop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
     mCirclePop.showAtAnchorView(tvSend, YGravity.ABOVE, XGravity.CENTER, 0, 0);

     tvOne.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             liwuNumber = "1";
               liwuNUmbers = liwuNumber;
               tvNumber.setText("X" + liwuNumber);
              mCirclePop.dismiss();
         }
     });

     tvTen.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             liwuNumber = "10";
             liwuNUmbers = liwuNumber;
             tvNumber.setText("X" + liwuNumber);
              mCirclePop.dismiss();
         }
     });
     tvBai.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             liwuNumber = "100";
             liwuNUmbers = liwuNumber;
             tvNumber.setText("X" + liwuNumber);
             mCirclePop.dismiss();

         }
     });
 }
    public PopupWindow showTipPopupWindow4(final View anchorView) {
        final View contentView = createPopupContentView(anchorView.getContext());
        final int pos[] = new int[2];
        anchorView.getLocationOnScreen(pos);
        int windowHeight = ScreenUtils.getScreenHeight(getContext()) - pos[1];
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, windowHeight, true);
        // anchorView 下面的空间不够，Window显示时会显示在 anchorView 上面
        popupWindow.showAsDropDown(anchorView);
        return popupWindow;
    }
    private View createPopupContentView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = new LinearLayout(context);
        View mView = inflater.inflate(R.layout.layout_circle_comment, linearLayout);
 //        contentView.setOnClickListener(mClickContentCancelListener);
        return mView;
    }
    public void getNetData(){
        HashMap<String, String> params = new HashMap<>();
        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.GETUSERINFO, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
          UserInfoData      userInfoData= com.wuye.piaoliuim.utils.GsonUtil.getDefaultGson().fromJson(requestEntity, UserInfoData.class);
                AppSessionEngine.setUserInfo(userInfoData);

            }

            @Override
            public void onError(String message) {

            }
        });
    }
   public void getLiwu(){
       HashMap<String, String> params = new HashMap<>();
       RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.GETLIWULIST, new RequestListener<String>() {
           @Override
           public void onComplete(String requestEntity) {
             liwuListData= com.wuye.piaoliuim.utils.GsonUtil.getDefaultGson().fromJson(requestEntity, LiwuListData.class);
             Log.i("pppppppppppppppp",liwuListData.res.getLiwudaList().size()+"");
               initViewLiwu(liwuListData);
           }

           @Override
           public void onError(String message) {

           }
       });

   }
   private void initViewLiwu(LiwuListData liwuListDatas){
       list.addAll(liwuListDatas.res.getLiwudaList());
       dialogLiwuAdapter = new DialogLiwuAdapter(getContext());
       postione=0;
       dialogLiwuAdapter.replaceAll(list);
       recommendGv.setHasFixedSize(true);
       recommendGv.setLayoutManager(new GridLayoutManager(getContext(), 4));
       recommendGv.setAdapter(dialogLiwuAdapter);
       dialogLiwuAdapter.changetShowDelImage(true, 8);
       dialogLiwuAdapter.setOnCheckChangedListener(this);

   }
    public Toast showToastFree(String str, int resID) {
        Toast toast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
        RelativeLayout toastView = (RelativeLayout) RelativeLayout.inflate(getContext(), R.layout.toast_hor_view, null);
        ImageView iv = toastView.findViewById(R.id.im_liwu);
        iv.setImageResource(resID);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(toastView);
        toast.show();
        return toast;

    }
    public int equNAme(String name){
        int picImag=R.mipmap.liwu_yin;
        if (name.equals("饮料")){
            picImag= R.mipmap.liwu_yin;
        }else if (name.equals("包包")){
            picImag=  R.mipmap.liwu_bao;
        }else if (name.equals("蛋糕")){
            picImag=  R.mipmap.liwu_dan;
        }else if (name.equals("水晶鞋")){
            picImag=  R.mipmap.liwu_xie;
        }else if (name.equals("红唇")){
            picImag=  R.mipmap.liwu_chun;
        }else if (name.equals("钻戒")){
            picImag=  R.mipmap.liwu_zuan;
        }else if (name.equals("一箭穿心")){
            picImag=  R.mipmap.liwu_xin;
        }else if (name.equals("城堡")){
            picImag=  R.mipmap.liwu_cheng;
        }

        return picImag;
    }
 }
