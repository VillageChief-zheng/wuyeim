package com.wuye.piaoliuim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.BaseFragement;
import com.chuange.basemodule.utils.ActivityTaskManager;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.wuye.piaoliuim.activity.EditInfoAct;
import com.wuye.piaoliuim.activity.FangdaPicAct;
import com.wuye.piaoliuim.activity.LiwuTsetAct;
import com.wuye.piaoliuim.activity.OpinionAct;
import com.wuye.piaoliuim.activity.PaihangAct;
import com.wuye.piaoliuim.activity.RechangeAct;
import com.wuye.piaoliuim.activity.SendTxtAndYuyinAct;
import com.wuye.piaoliuim.activity.SendYuyinAct;
import com.wuye.piaoliuim.activity.TestHeixiu;
import com.wuye.piaoliuim.activity.UserInfoAct;
import com.wuye.piaoliuim.fragment.FindFragment;
import com.wuye.piaoliuim.fragment.ImFragment;
import com.wuye.piaoliuim.fragment.MyFragment;
import com.wuye.piaoliuim.fragment.PiaoliuFragment;
import com.wuye.piaoliuim.login.LoginActivity;
import com.wuye.piaoliuim.utils.AppSessionEngine;
import com.wuye.piaoliuim.utils.GenerateTestUserSig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation_bar1)
    BottomBarLayout bottomNavigationBar1;
    @BindView(R.id.main_container)
    FrameLayout mainContainer;
  int currentPositions;

    private FragmentManager mFragmentManager;
    private List<BaseFragement> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        initData();
    }



    private void initData() {
        mFragmentList.add(PiaoliuFragment.newInstance());
        mFragmentList.add(  FindFragment.newInstance() );
        mFragmentList.add(  FindFragment.newInstance() );
        mFragmentList.add(ImFragment.newInstance());
         mFragmentList.add(MyFragment.newInstance());
        changeFragment(0);
         bottomNavigationBar1.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int i, int currentPosition) {
                changeFragment(currentPosition);

            }
        });
    }

    private void changeFragment(int currentPosition) {
        if (currentPosition==2){
//        startActivity(new Intent(this, TestHeixiu.class));
//        startActivity(new Intent(this, SendTxtAndYuyinAct.class));
            login("1", GenerateTestUserSig.genTestUserSig("1"));
        }else {
            currentPositions=currentPosition;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, mFragmentList.get(currentPosition));
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationBar1.setCurrentItem(currentPositions);
     }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                ActivityTaskManager.getInstance().finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
    public void login(String identifier,String userSig){

         // identifier为用户名，userSig 为用户登录凭证
        TIMManager.getInstance().login(identifier, userSig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.e("pppp", "login failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess() {
                Log.e("p[[p", "login succ");
            }
        });
    }
}
