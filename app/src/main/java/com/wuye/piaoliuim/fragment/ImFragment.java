package com.wuye.piaoliuim.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chaychan.library.BottomBarLayout;
import com.chuange.basemodule.BaseFragement;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.component.action.PopActionClickListener;
import com.tencent.qcloud.tim.uikit.component.action.PopDialogAdapter;
import com.tencent.qcloud.tim.uikit.component.action.PopMenuAction;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationManagerKit;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.tencent.qcloud.tim.uikit.utils.PopWindowUtil;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.WuyeApplicatione;
import com.wuye.piaoliuim.activity.ChatActivity;
import com.wuye.piaoliuim.activity.SysMessageAct;
import com.wuye.piaoliuim.bean.AliPAydata;
import com.wuye.piaoliuim.bean.PayData;
import com.wuye.piaoliuim.bean.SysDatas;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.pay.WXPayUtil;
import com.wuye.piaoliuim.pay.ZFBPayUtil;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.Menu;
import com.wuye.piaoliuim.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

import static com.wuye.piaoliuim.WuyeApplicatione.getContext;

/**
 * @ClassName ImFragment
 * @Description
 * @Author VillageChief
 * @Date 2019/12/13 16:20
 */
public class ImFragment extends BaseImFragment implements ConversationManagerKit.MessageUnreadWatcher{

    private View mBaseView;
    private ConversationLayout mConversationLayout;
    private ListView mConversationPopList;
    private PopDialogAdapter mConversationPopAdapter;
    private PopupWindow mConversationPopWindow;
    private List<PopMenuAction> mConversationPopActions = new ArrayList<>();
    private Menu mMenu;
    TitleBarLayout titleBarLayout;//title
    TextView tvKefu;
    LinearLayout sysAct;
    BottomBarLayout  bottom_navigation_bar2;
    String sms="";
     @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.conversation_fragment, container, false);
        initView();
         getSMs();
         return mBaseView;
    }

    public void initView() {
        // 从布局文件中获取会话列表面板
        mConversationLayout = mBaseView.findViewById(R.id.conversation_layout);
        mMenu = new Menu(getActivity(), (TitleBarLayout) mConversationLayout.getTitleBar(), Menu.MENU_TYPE_CONVERSATION);
        // 会话列表面板的默认UI和交互初始化
        mConversationLayout.initDefault();
         // 通过API设置ConversataonLayout各种属性的样例，开发者可以打开注释，体验效果
//        ConversationLayoutHelper.customizeConversation(mConversationLayout);
        mConversationLayout.getConversationList().setOnItemClickListener(new ConversationListLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ConversationInfo conversationInfo) {
                //此处为demo的实现逻辑，更根据会话类型跳转到相关界面，开发者可根据自己的应用场景灵活实现
                 startChatActivity(conversationInfo);
            }
        });
        mConversationLayout.getConversationList().setOnItemLongClickListener(new ConversationListLayout.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, int position, ConversationInfo conversationInfo) {
                startPopShow(view, position, conversationInfo);
            }
        });
        initTitleAction();
        initPopMenuAction();
        setTitle();

        Log.i("ppppppppppppppp","加入了监听");
        ConversationManagerKit.getInstance().addUnreadWatcher(this);

    }

    private void initTitleAction() {
         mConversationLayout.getTitleBar().setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMenu.isShowing()) {
                    mMenu.hide();
                } else {
                    mMenu.show();
                }
            }
        });
        mConversationLayout.getConversationList().setItemAvatarRadius(5);
    }

    private void initPopMenuAction() {

        // 设置长按conversation显示PopAction
        List<PopMenuAction> conversationPopActions = new ArrayList<PopMenuAction>();
        PopMenuAction action = new PopMenuAction();
        action.setActionName(getResources().getString(R.string.zhiding));
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int position, Object data) {
                mConversationLayout.setConversationTop(position, (ConversationInfo) data);
            }
        });
        conversationPopActions.add(action);
        action = new PopMenuAction();
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int position, Object data) {
                mConversationLayout.deleteConversation(position, (ConversationInfo) data);
            }
        });
        action.setActionName(getResources().getString(R.string.shanchu));
        conversationPopActions.add(action);
        mConversationPopActions.clear();
        mConversationPopActions.addAll(conversationPopActions);
    }

    /**
     * 长按会话item弹框
     *
     * @param index            会话序列号
     * @param conversationInfo 会话数据对象
     * @param locationX        长按时X坐标
     * @param locationY        长按时Y坐标
     */
    private void showItemPopMenu(final int index, final ConversationInfo conversationInfo, float locationX, float locationY) {
        if (mConversationPopActions == null || mConversationPopActions.size() == 0)
            return;
        View itemPop = LayoutInflater.from(getActivity()).inflate(R.layout.pop_menu_layout, null);
        mConversationPopList = itemPop.findViewById(R.id.pop_menu_list);
        mConversationPopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopMenuAction action = mConversationPopActions.get(position);
                if (action.getActionClickListener() != null) {
                    action.getActionClickListener().onActionClick(index, conversationInfo);
                }
                mConversationPopWindow.dismiss();
            }
        });

        for (int i = 0; i < mConversationPopActions.size(); i++) {
            PopMenuAction action = mConversationPopActions.get(i);
            if (conversationInfo.isTop()) {
                if (action.getActionName().equals(getResources().getString(R.string.zhiding))) {
                    action.setActionName(getResources().getString(R.string.quxiaozhiding));
                }
            } else {
                if (action.getActionName().equals(getResources().getString(R.string.quxiaozhiding))) {
                    action.setActionName(getResources().getString(R.string.zhiding));
                }

            }
        }
        mConversationPopAdapter = new PopDialogAdapter();
        mConversationPopList.setAdapter(mConversationPopAdapter);
        mConversationPopAdapter.setDataSource(mConversationPopActions);
        mConversationPopWindow = PopWindowUtil.popupWindow(itemPop, mBaseView, (int) locationX, (int) locationY);
        mBaseView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mConversationPopWindow.dismiss();
            }
        }, 10000); // 10s后无操作自动消失
    }

    private void startPopShow(View view, int position, ConversationInfo info) {
        showItemPopMenu(position, info, view.getX(), view.getY() + view.getHeight() / 2);
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

    public static ImFragment newInstance() {
         return new ImFragment();
    }
    private void setTitle(){
        titleBarLayout=mConversationLayout.getTitleBar();
             tvKefu=mBaseView.findViewById(R.id.kfu);
        sysAct=mBaseView.findViewById(R.id.syssm);
        bottom_navigation_bar2=mBaseView.findViewById(R.id.bottom_navigation_bar2);
        titleBarLayout.setVisibility(View.GONE);
         tvKefu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ConversationInfo conversationInfo=new ConversationInfo();
               conversationInfo.setId("50001");
               conversationInfo.setGroup(false);
               conversationInfo.setTitle("客服");
               startChatActivity(conversationInfo);
           }
       });
       sysAct.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (sms.equals("")||sms.equals("0")){
                   bottom_navigation_bar2.hideNotify(0);
                   startActivity(new Intent(getContext(), SysMessageAct.class));
                }else {
                   topSMs();
               }
           }
       });

    }
    private void getSMs() {
        HashMap<String, String> params = new HashMap<>();

        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.SYSTEMSM, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                SysDatas  sysDatas=GsonUtil.getDefaultGson().fromJson(requestEntity, SysDatas.class);
                sms=sysDatas.res.getIs_true();
                if (sysDatas.res.getIs_true().equals("0")){
                    bottom_navigation_bar2.hideNotify(0);
                }else {
                    bottom_navigation_bar2.showNotify(0);
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }
    private void topSMs() {
        HashMap<String, String> params = new HashMap<>();

        RequestManager.getInstance().publicPostMap(getContext(), params, UrlConstant.SYSTEMSMs, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                bottom_navigation_bar2.hideNotify(0);
                startActivity(new Intent(getContext(), SysMessageAct.class));
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {

        super.onResume();
         initView();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void updateUnread(int count) {
        EventBus.getDefault().post(new MessageEvent(count+""));

        Log.i("000000000消息开始接受啊啊啊啊","ssssssssssssssss"+count);

    }
}
