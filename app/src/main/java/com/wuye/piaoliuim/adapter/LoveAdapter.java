package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.view.View;
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
import com.wuye.piaoliuim.bean.FinsData;
import com.wuye.piaoliuim.bean.LiwuData;
import com.wuye.piaoliuim.bean.LoveData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

import java.util.List;

/**
 * @ClassName LoveAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/17 13:33
 */
public class LoveAdapter extends BaseQuickAdapter<LoveData.Res.LoveList, BaseViewHolder> {

    private Context mContext;
    LoveData.Res.LoveList rseckillRow;
    ImageView imageView,imLiwu;
    TextView tpNotlove;
    String iszan;
    int viedoZanNumber = 0;

    public LoveAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<LoveData.Res.LoveList> data) {
        super(layoutResId, data);
        mContext = context;
     }
    // 日 分
    @Override
    protected void convert(BaseViewHolder helper, LoveData.Res.LoveList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper .setText(R.id.tv_name,rseckillRow.getName())
        ;
        imageView = helper.getView(R.id.clock);
        tpNotlove = helper.getView(R.id.tv_deleteblove);

        RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(mContext)
                .load(ImagUrlUtils.getImag(rseckillRow.getLitpic())).apply(options)
                .into(imageView);
        helper.addOnClickListener(R.id.tv_deleteblove);

    }
}
