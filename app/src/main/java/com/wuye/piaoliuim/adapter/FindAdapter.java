package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.FindData;
import com.wuye.piaoliuim.bean.GlodData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

import java.util.List;

/**
 * @ClassName FindAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/23 11:22
 */
public class FindAdapter   extends BaseQuickAdapter<FindData.Res.FIndList, BaseViewHolder> {

    private Context mContext;
    FindData.Res.FIndList rseckillRow;
    TextView type;
    String iszan;
    ImageView header,imgAdd;
    int viedoZanNumber = 0;
    TextView inLine,tvName,tvAddr;

    public FindAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<FindData.Res.FIndList> data) {
        super(layoutResId, data);
        mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper,FindData.Res.FIndList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper.setText(R.id.tv_content, rseckillRow.getSignature()).setText(R.id.tv_rq, " 人气："+rseckillRow.getFans())
                .setText(R.id.tv_ml, " 魅力："+rseckillRow.getTotal_rece_gold())
        ;
        header= helper.getView(R.id.im_header);
        inLine= helper.getView(R.id.tv_inline);
        tvName= helper.getView(R.id.tv_name);
        imgAdd= helper.getView(R.id.im_add);
        tvAddr= helper.getView(R.id.tv_addr);
        if (!rseckillRow.getSignin_region().equals("")){
            imgAdd.setVisibility(View.VISIBLE);
            tvAddr.setVisibility(View.VISIBLE);
            tvAddr.setText(" "+rseckillRow.getSignin_region());
        }else {
            imgAdd.setVisibility(View.GONE);
            tvAddr.setVisibility(View.GONE);
        }
        if (rseckillRow.getOnline().equals("1")){
            inLine.setVisibility(View.VISIBLE);
        }else {
            inLine.setVisibility(View.GONE);

        }
        if (rseckillRow.getGender().equals("1")){
            Drawable drawable= mContext.getResources().getDrawable(R.mipmap.ic_nan);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(drawable,null,null,null);
         }else  if (rseckillRow.getGender().equals("2")){
            Drawable drawable= mContext.getResources().getDrawable(R.mipmap.ic_nv);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(drawable,null,null,null);
         }
        tvName.setText(" "+rseckillRow.getName());
        RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(mContext)
                .load(ImagUrlUtils.getImag(ImagUrlUtils.getImag(rseckillRow.getLitpic())) ).apply(options)
                .into(header);


    }
}
