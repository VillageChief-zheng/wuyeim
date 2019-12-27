package com.wuye.piaoliuim.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wuye.piaoliuim.fragment.FuhaoFragment;
import com.wuye.piaoliuim.fragment.LiwuInFragment;
import com.wuye.piaoliuim.fragment.LiwuSendFragment;
import com.wuye.piaoliuim.fragment.MeiliFragment;
import com.wuye.piaoliuim.fragment.RenqiFragment;

import java.util.List;

/**
 * @ClassName PaihangFragmentAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/23 16:15
 */
public class PaihangFragmentAdapter  extends FragmentPagerAdapter {
    private List<String> mList;
    public PaihangFragmentAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.mList = list;
    }
    @Override
    public Fragment getItem(int position) {//加载的Fragment
        if (position == 0) {
            return new FuhaoFragment();

        } else  if (position == 1){
            return new MeiliFragment();
        }else {
            return new RenqiFragment();

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
