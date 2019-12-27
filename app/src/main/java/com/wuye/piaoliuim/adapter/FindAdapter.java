package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.FindData;
import com.wuye.piaoliuim.bean.GlodData;

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
    ImageView header;
    int viedoZanNumber = 0;
    TextView inLine,tvName;

    public FindAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<FindData.Res.FIndList> data) {
        super(layoutResId, data);
        mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper,FindData.Res.FIndList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper.setText(R.id.tv_content, rseckillRow.getSignature()).setText(R.id.tv_rq, "  人气："+rseckillRow.getSend_gold())
                .setText(R.id.tv_ml, "魅力：888"+rseckillRow.getTotal_rece_gold())
        ;
        header= helper.getView(R.id.im_header);
        inLine= helper.getView(R.id.tv_inline);
        tvName= helper.getView(R.id.tv_name);
        if (rseckillRow.getOnline().equals("1")){
            inLine.setVisibility(View.VISIBLE);
        }else {
            inLine.setVisibility(View.GONE);

        }
        if (rseckillRow.getGender().equals("1")){
            Drawable drawable= mContext.getResources().getDrawable(R.mipmap.ic_nan);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(drawable,null,null,null);
            tvName.setText(rseckillRow.getName());
         }else  if (rseckillRow.getGender().equals("2")){
            Drawable drawable= mContext.getResources().getDrawable(R.mipmap.ic_nv);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(drawable,null,null,null);
            tvName.setText(rseckillRow.getName());
        }



    }
}
