package com.wuye.piaoliuim.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @ClassName GlodFragmentAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/17 14:58
 */
public class GlodFragmentAdapter  extends FragmentPagerAdapter {
    private List<String> mList;
    public GlodFragmentAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.mList = list;
    }
    @Override
    public Fragment getItem(int position) {//加载的Fragment
        if (position == 0) {
            return new AddGlodFragment();

        } else  {
            return new SendGlodFragment();
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
