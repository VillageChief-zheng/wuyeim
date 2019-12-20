package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.LiwuData;

import java.util.List;

/**
 * @ClassName LiwuAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/17 9:53
 */
public class LiwuAdapter  extends BaseQuickAdapter<LiwuData.Res.LiwuList, BaseViewHolder> {

    private Context mContext;
    LiwuData.Res.LiwuList rseckillRow;
    ImageView imageView,imLiwu;
    String iszan;
    int viedoZanNumber = 0;

    public LiwuAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<LiwuData.Res.LiwuList> data) {
        super(layoutResId, data);
        mContext = context;

    }
 // 日 分
    @Override
    protected void convert(BaseViewHolder helper, LiwuData.Res.LiwuList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper.setText(R.id.tv_riqi, rseckillRow.getName()).setText(R.id.tv_riqiyear,rseckillRow.getName())
        .setText(R.id.tv_name,rseckillRow.getName())
        .setText(R.id.tv_number,rseckillRow.getNum())
        ;
        imageView = helper.getView(R.id.clock);
        imLiwu = helper.getView(R.id.im_liwu);
        RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(mContext)
                .load(rseckillRow.getLitpic()).apply(options)
                .into(imageView);
        Glide.with(mContext)
                .load(rseckillRow.getLitpic())
                .into(imLiwu);
        helper.addOnClickListener(R.id.tv_deleteblack);


    }
}
