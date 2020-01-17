package com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.qcloud.tim.uikit.R;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.utils.CustomMessage;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;

public class MessageCustomHolder extends MessageContentHolder implements ICustomMessageViewGroup {

    private MessageInfo mMessageInfo;
    private int mPosition;

    private TextView msgBodyText;

    public MessageCustomHolder(View itemView) {
        super(itemView);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.message_adapter_content_text;
    }

    @Override
    public void initVariableViews() {
        msgBodyText = rootView.findViewById(R.id.msg_body_tv);
    }

    @Override
    public void layoutViews(MessageInfo msg, int position) {
        mMessageInfo = msg;
        mPosition = position;
        super.layoutViews(msg, position);
    }

    @Override
    public void layoutVariableViews(MessageInfo msg, int position) {
        // 获取到自定义消息的json数据
        Log.i("pppppppppppppp",position+"");
        if (!(msg.getElement() instanceof TIMCustomElem)) {
            return;
        }
        TIMCustomElem elem = (TIMCustomElem) msg.getElement();
        // 自定义的json数据，需要解析成bean实例
        CustomMessage data = null;
        try {
            data = new Gson().fromJson(new String(elem.getData()), CustomMessage.class);
        } catch (Exception e) {
         }
         msgBodyText.setVisibility(View.VISIBLE);
        msgBodyText.setText(Html.fromHtml(TUIKitConstants.covert2HTMLString("[系统消息,对方送您礼物]")));
        if (properties.getChatContextFontSize() != 0) {
            msgBodyText.setTextSize(properties.getChatContextFontSize());
        }
        if (msg.isSelf()) {
            if (properties.getRightChatContentFontColor() != 0) {
                msgBodyText.setTextColor(properties.getRightChatContentFontColor());
            }
        } else {
            if (properties.getLeftChatContentFontColor() != 0) {
                msgBodyText.setTextColor(properties.getLeftChatContentFontColor());
            }
        }
    }

    private void hideAll() {
        for (int i = 0; i < ((RelativeLayout) rootView).getChildCount(); i++) {
            ((RelativeLayout) rootView).getChildAt(i).setVisibility(View.GONE);
        }
    }

    @Override
    public void addMessageItemView(View view) {
        hideAll();
        if (view != null) {
            ((RelativeLayout) rootView).removeView(view);
            ((RelativeLayout) rootView).addView(view);
        }
    }

    @Override
    public void addMessageContentView(View view) {
        // item有可能被复用，因为不能确定是否存在其他自定义view，这里把所有的view都隐藏之后重新layout
        hideAll();
        super.layoutViews(mMessageInfo, mPosition);

        if (view != null) {
            for (int i = 0; i < msgContentFrame.getChildCount(); i++) {
                msgContentFrame.getChildAt(i).setVisibility(View.GONE);
            }
            msgContentFrame.removeView(view);
            msgContentFrame.addView(view);
        }
    }

}
