package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.BlackData;
import com.wuye.piaoliuim.bean.GlodData;

import java.util.List;

/**
 * @ClassName GlodAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/17 15:20
 */
public class GlodAdapter  extends BaseQuickAdapter<GlodData.Res.GlodList, BaseViewHolder> {

    private Context mContext;
    GlodData.Res.GlodList rseckillRow;
    TextView type;
     String iszan;
    int viedoZanNumber = 0;

    public GlodAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<GlodData.Res.GlodList> data) {
        super(layoutResId, data);
        mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, GlodData.Res.GlodList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper.setText(R.id.tv_riqi, rseckillRow.getCreate_time()).setText(R.id.tv_riqiyear, rseckillRow.getCreate_time())
        .setText(R.id.tv_number, rseckillRow.getGold()).setText(R.id.tv_name,"")
        ;

    }
 }
