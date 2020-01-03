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
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.FinsData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

import java.util.List;

/**
 * @ClassName FinsAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/17 10:49
 */
public class FinsAdapter extends BaseQuickAdapter<FinsData.Res.FinsList, BaseViewHolder> {

    private Context mContext;
    FinsData.Res.FinsList rseckillRow;
    ImageView imageView,imLiwu;
    TextView love,arllove;
    String iszan;
    int viedoZanNumber = 0;

    public FinsAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<FinsData.Res.FinsList> data) {
        super(layoutResId, data);
        mContext = context;

    }
    // 日 分
    @Override
    protected void convert(BaseViewHolder helper, FinsData.Res.FinsList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper .setText(R.id.tv_name,rseckillRow.getName())
         ;
        imageView = helper.getView(R.id.clock);
        love = helper.getView(R.id.tv_love);
        arllove = helper.getView(R.id.tv_arllove);
        if (rseckillRow.is_follow.equals("1")){
            love.setVisibility(View.GONE);
            arllove.setVisibility(View.VISIBLE);
        }else {
            love.setVisibility(View.VISIBLE);
            arllove.setVisibility(View.GONE);

        }
         RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(mContext)
                .load(ImagUrlUtils.getImag(rseckillRow.getLitpic())).apply(options)
                .into(imageView);
         helper.addOnClickListener(R.id.tv_love);

    }
}
