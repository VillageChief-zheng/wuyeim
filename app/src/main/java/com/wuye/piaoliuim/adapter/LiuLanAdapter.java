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
import com.chuange.basemodule.utils.DateUtils;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.LiulanData;
import com.wuye.piaoliuim.bean.LiwuData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

import java.util.List;

public class LiuLanAdapter extends BaseQuickAdapter<LiulanData.Res.JIluList, BaseViewHolder> {

    private Context mContext;
    LiulanData.Res.JIluList rseckillRow;
    ImageView imageView,imLiwu;
    String iszan;
    int viedoZanNumber = 0;
    TextView tvName;

    public LiuLanAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<LiulanData.Res.JIluList> data) {
        super(layoutResId, data);
        mContext = context;

    }
    // 日 分
    @Override
    protected void convert(BaseViewHolder helper, LiulanData.Res.JIluList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper.setText(R.id.tv_riqi,  DateUtils.getDateAndMin( Long.parseLong(rseckillRow.getCreate_time()),"1" )).setText(R.id.tv_riqiyear,  DateUtils.getDateAndMin( Long.parseLong(rseckillRow.getCreate_time()),"2" ))

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
         RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(mContext)
                .load(ImagUrlUtils.getImag(rseckillRow.getLitpic())).apply(options)
                .into(imageView);



    }}
