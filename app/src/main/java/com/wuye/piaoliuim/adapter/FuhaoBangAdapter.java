package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
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

import java.util.List;

/**
 * @ClassName FuhaoBangAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/24 13:41
 */
public class FuhaoBangAdapter extends BaseQuickAdapter<FindData.Res.FIndList, BaseViewHolder> {

    private Context mContext;
    FindData.Res.FIndList rseckillRow;
    ImageView imageView;
    TextView tvName;
    String iszan;
    int viedoZanNumber = 0;

    public FuhaoBangAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<FindData.Res.FIndList> data) {
        super(layoutResId, data);
        mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, FindData.Res.FIndList rseckillRow) {
        this.rseckillRow = rseckillRow;
              helper.setText(R.id.tv_number, helper.getAdapterPosition()+3 + "")
                    .setText(R.id.tv_jinbi, " 金币：" + rseckillRow.getTotal_rece_gold())
            ;
            imageView = helper.getView(R.id.clock);
            tvName = helper.getView(R.id.tv_name);
            if (rseckillRow.getGender().equals("1")) {
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_nan);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvName.setCompoundDrawables(drawable, null, null, null);
                tvName.setText(rseckillRow.getName());
            } else if (rseckillRow.getGender().equals("2")) {
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_nv);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvName.setCompoundDrawables(drawable, null, null, null);
                tvName.setText(rseckillRow.getName());
            }
            RequestOptions options = new RequestOptions()//圆形图片
                    .circleCrop();
            Glide.with(mContext)
                    .load(Constants.BASEURL + rseckillRow.getLitpic()).apply(options)
                    .into(imageView);
        }

}
