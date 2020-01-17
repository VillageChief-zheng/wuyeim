package com.wuye.piaoliuim.helper;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.qcloud.tim.uikit.component.picture.imageEngine.impl.GlideEngine;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.ICustomMessageViewGroup;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.WuyeApplicatione;
import com.wuye.piaoliuim.utils.DemoLog;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

/**
 * @ClassName CustomHelloTIMUIController
 * @Description
 * @Author VillageChief
 * @Date 2020/1/2 10:46
 */
public class CustomHelloTIMUIController {

    private static final String TAG = CustomHelloTIMUIController.class.getSimpleName();
    private static final int DEFAULT_RADIUS = 1;
    public static void onDraw(ICustomMessageViewGroup parent, final CustomMessage data) {

        // 把自定义消息view添加到TUIKit内部的父容器里
        View view = LayoutInflater.from(WuyeApplicatione.instance()).inflate(R.layout.test_custom_message_layout1, null, false);
        parent.addMessageContentView(view);

        // 自定义消息view的实现，这里仅仅展示文本信息，并且实现超链接跳转
        TextView textView = view.findViewById(R.id.test_custom_message_tv);
        ImageView imageView = view.findViewById(R.id.liwu);
        final String text = "不支持的自定义消息";
         if (data == null) {
            textView.setText(text);
        } else {
            textView.setText(data.getName());
             GlideEngine.loadCornerImage(imageView, ImagUrlUtils.getImag(data.getPicUrl()), null, DEFAULT_RADIUS);

         }
        view.setClickable(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (data == null) {
//                    DemoLog.e(TAG, "Do what?");
//                    ToastUtil.toastShortMessage(text);
//                    return;
//                }
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse(data.link);
//                intent.setData(content_url);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                WuyeApplicatione.instance().startActivity(intent);
            }
        });
    }
}
