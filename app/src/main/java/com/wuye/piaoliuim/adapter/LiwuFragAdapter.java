package com.wuye.piaoliuim.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wuye.piaoliuim.fragment.LiwuInFragment;
import com.wuye.piaoliuim.fragment.LiwuSendFragment;

import java.util.List;

/**
 * @ClassName LiwuFragAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/16 16:42
 */
public class LiwuFragAdapter  extends FragmentPagerAdapter {
    private List<String> mList;
    public LiwuFragAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.mList = list;
    }
    @Override
    public Fragment getItem(int position) {//加载的Fragment
        if (position == 0) {
            return new LiwuInFragment();

        } else  {
            return new LiwuSendFragment();
        }

    }
    @Override
    public int getCount() {//标签页数
        return mList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {//标签标题
        return mList.get(position);
    }

}
