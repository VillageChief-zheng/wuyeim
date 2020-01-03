package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.fragment.ChatFragment;
import com.wuye.piaoliuim.fragment.KefuChatFragment;
import com.wuye.piaoliuim.utils.DemoLog;

/**
 * @ClassName ChatActivity
 * @Description
 * @Author VillageChief
 * @Date 2020/1/2 10:29
 */
public class ChatActivity extends BaseImActivity{

    private static final String TAG = ChatActivity.class.getSimpleName();

    private ChatFragment mChatFragment;
    private KefuChatFragment kefuChatFragment;
    private ChatInfo mChatInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        chat(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        DemoLog.i(TAG, "onNewIntent");
        super.onNewIntent(intent);
        chat(intent);
    }

    @Override
    protected void onResume() {
        DemoLog.i(TAG, "onResume");
        super.onResume();
    }

    private void chat(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            startSplashActivity();
        } else {
            mChatInfo = (ChatInfo) bundle.getSerializable(Constants.CHAT_INFO);
            if (mChatInfo == null) {
                startSplashActivity();
                return;
            }
            if (mChatInfo.getId().equals("100")){
                kefuChatFragment = new KefuChatFragment();
                kefuChatFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.empty_view, kefuChatFragment).commitAllowingStateLoss();
            }else {
                mChatFragment = new ChatFragment();
                mChatFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.empty_view, mChatFragment).commitAllowingStateLoss();
            }
//            getFragmentManager().beginTransaction().replace().commitAllowingStateLoss();
        }
    }

    private void startSplashActivity() {
        Intent intent = new Intent(ChatActivity.this, IndexAct.class);
        startActivity(intent);
        finish();
    }
}
