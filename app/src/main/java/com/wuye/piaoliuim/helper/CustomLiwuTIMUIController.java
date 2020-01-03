package com.wuye.piaoliuim.helper;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.ICustomMessageViewGroup;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.WuyeApplicatione;
import com.wuye.piaoliuim.utils.DemoLog;

/**
 * @ClassName CustomLiwuTIMUIController
 * @Description
 * @Author VillageChief
 * @Date 2020/1/2 17:28
 */
public class CustomLiwuTIMUIController {

    private static final String TAG = CustomLiwuTIMUIController.class.getSimpleName();

    public static void onDraw(ICustomMessageViewGroup parent, final CustomMessage data) {

        // 把自定义消息view添加到TUIKit内部的父容器里
        View view = LayoutInflater.from(WuyeApplicatione.instance()).inflate(R.layout.img_message_layout, null, false);
        parent.addMessageContentView(view);

        // 自定义消息view的实现，这里仅仅展示文本信息，并且实现超链接跳转
        ImageView imageView = view.findViewById(R.id.image_message);
        final String text = "不支持的自定义消息";
        if (data == null) {

//            textView.setText(text);
        } else {
//            textView.setText(data.text);
        }

    }
}
