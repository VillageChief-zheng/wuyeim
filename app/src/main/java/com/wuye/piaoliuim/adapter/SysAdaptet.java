package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.LoveData;
import com.wuye.piaoliuim.bean.SysData;
import com.wuye.piaoliuim.config.Constants;

import java.util.List;

public class SysAdaptet extends BaseQuickAdapter<SysData.Res.SysList, BaseViewHolder> {

    private Context mContext;
    SysData.Res.SysList rseckillRow;
    ImageView imageView,imLiwu;
    TextView tpNotlove;
    String iszan;
    int viedoZanNumber = 0;


    public SysAdaptet(Context context, @LayoutRes int layoutResId, @Nullable List<SysData.Res.SysList> data) {
        super(layoutResId, data);
        mContext = context;

    }
    // 日 分
    @Override
    protected void convert(BaseViewHolder helper, SysData.Res.SysList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper .setText(R.id.tv_title,rseckillRow.getTitle()).setText(R.id.tv_content,rseckillRow.getContent()).setText(R.id.tv_date,rseckillRow.getCreate_time())
        ;


    }
}
