package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.BlackData;
import com.wuye.piaoliuim.config.Constants;

import java.util.List;

/**
 * @ClassName BlackListAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/16 10:08
 */
public class BlackListAdapter  extends BaseQuickAdapter<BlackData.Res.BlackList, BaseViewHolder> {

    private Context mContext;
    BlackData.Res.BlackList rseckillRow;
    ImageView imageView;
     String iszan;
    int viedoZanNumber = 0;

    public BlackListAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<BlackData.Res.BlackList> data) {
        super(layoutResId, data);
        mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, BlackData.Res.BlackList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper.setText(R.id.tv_name, rseckillRow.getName())
         ;
        imageView = helper.getView(R.id.clock);
        Glide.with(mContext)
                .load(Constants.BASEURL+rseckillRow.getLitpic())
                .into(imageView);
        helper.addOnClickListener(R.id.tv_deleteblack);


    }
}
