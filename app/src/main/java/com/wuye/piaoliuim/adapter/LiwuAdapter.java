package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.LiwuData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

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
    TextView tvName;

    public LiwuAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<LiwuData.Res.LiwuList> data) {
        super(layoutResId, data);
        mContext = context;

    }
 // 日 分
    @Override
    protected void convert(BaseViewHolder helper, LiwuData.Res.LiwuList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper.setText(R.id.tv_riqi, rseckillRow.getCreate_time()).setText(R.id.tv_riqiyear,rseckillRow.getName())
         .setText(R.id.tv_number,"x"+rseckillRow.getNum())
        ;
        tvName=helper.getView(R.id.tv_name);
        if (rseckillRow.getGender().equals("1")) {
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_nan);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(drawable, null, null, null);
        } else if (rseckillRow.getGender().equals("2")) {
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_nv);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(drawable, null, null, null);
         }
        tvName.setText(rseckillRow.getName());

        imageView = helper.getView(R.id.clock);
        imLiwu = helper.getView(R.id.im_liwu);
        RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(mContext)
                .load(ImagUrlUtils.getImag(rseckillRow.getLitpic())).apply(options)
                .into(imageView);
        Glide.with(mContext)
                .load(Constants.BASEURL+rseckillRow.getG_litpic())
                .into(imLiwu);


    }
}
