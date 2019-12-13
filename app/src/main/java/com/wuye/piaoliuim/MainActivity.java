package com.wuye.piaoliuim;

import android.content.Intent;
import android.os.Bundle;
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
import com.wuye.piaoliuim.fragment.FindFragment;
import com.wuye.piaoliuim.fragment.ImFragment;
import com.wuye.piaoliuim.fragment.MyFragment;
import com.wuye.piaoliuim.fragment.PiaoliuFragment;
import com.wuye.piaoliuim.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation_bar1)
    BottomBarLayout bottomNavigationBar1;
    @BindView(R.id.main_container)
    FrameLayout mainContainer;


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
        startActivity(new Intent(this, LoginActivity.class));
        }else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, mFragmentList.get(currentPosition));
            transaction.commit();
        }
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
}
