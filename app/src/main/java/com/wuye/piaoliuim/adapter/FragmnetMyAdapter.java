package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chuange.basemodule.utils.DateUtils;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.ItemBean;

import java.util.List;

/**
 * @ClassName FragmnetMyAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/13 15:50
 */
public class FragmnetMyAdapter extends BaseQuickAdapter<ItemBean, BaseViewHolder> {

    public FragmnetMyAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<ItemBean> data) {
        super(layoutResId, data);
        mContext = context;

    }
    @Override
    protected void convert(BaseViewHolder helper, ItemBean itemBean) {
        helper.setText(R.id.tv_ziti,itemBean.name );
        ImageView imageView=helper.getView(R.id.im_ic);
        Glide.with(mContext)
                .load(itemBean.icon)
                .into(imageView);
      }
}
