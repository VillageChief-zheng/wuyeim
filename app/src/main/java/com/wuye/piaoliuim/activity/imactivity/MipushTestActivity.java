package com.wuye.piaoliuim.activity.imactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.umeng.message.UmengNotifyClickActivity;
import com.wuye.piaoliuim.R;

import org.android.agoo.common.AgooConstants;

/**
 * @ClassName MipushTestActivity
 * @Description
 * @Author VillageChief
 * @Date 2020/1/7 13:23
 */
public class MipushTestActivity extends UmengNotifyClickActivity {
    private static String TAG = MipushTestActivity.class.getName();
    private TextView mipushTextView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.pushact_main);
        mipushTextView = (TextView) findViewById(R.id.text);
    }
    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);
        final String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        if (!TextUtils.isEmpty(body)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mipushTextView.setText(body);
                }
            });
        }
    }
}
