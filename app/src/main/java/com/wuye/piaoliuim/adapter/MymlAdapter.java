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
import com.wuye.piaoliuim.bean.MyMlListData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.utils.DateSm;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

import java.util.List;

public class MymlAdapter extends BaseQuickAdapter<MyMlListData.Res.MlListInfo, BaseViewHolder> {

    private Context mContext;
    MyMlListData.Res.MlListInfo rseckillRow;
    ImageView imageView,imLiwu;
    String iszan;
    int viedoZanNumber = 0;
    TextView tvName;

    public MymlAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<MyMlListData.Res.MlListInfo> data) {
        super(layoutResId, data);
        mContext = context;

    }
    // 日 分
    @Override
    protected void convert(BaseViewHolder helper, MyMlListData.Res.MlListInfo rseckillRow) {
        this.rseckillRow=rseckillRow;

        tvName=helper.getView(R.id.tv_name);
        if (rseckillRow.getGender().equals("1")) {
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_nan);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(null, null, drawable, null);
        } else if (rseckillRow.getGender().equals("2")) {
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_nv);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(null, null, drawable, null);
        }
        tvName.setText(rseckillRow.getName()+" ");
        helper.setText(R.id.tv_number,"+"+rseckillRow.getGold());
        imageView = helper.getView(R.id.clock);
         RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(mContext)
                .load(ImagUrlUtils.getImag(rseckillRow.getLitpic())).apply(options)
                .into(imageView);


    }
}
