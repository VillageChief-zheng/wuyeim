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
import com.wuye.piaoliuim.bean.PiaoliuData;

import java.util.List;

/**
 * @ClassName PiaoliuAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/23 13:42
 */
public class PiaoliuAdapter  extends BaseQuickAdapter<PiaoliuData.Res.PiaoliuList, BaseViewHolder> {

    private Context mContext;
    PiaoliuData.Res.PiaoliuList rseckillRow;
    TextView type;
    String iszan;
    ImageView header;
    int viedoZanNumber = 0;
    TextView inLine,tvName,tvContent,tvContents;

    public PiaoliuAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<PiaoliuData.Res.PiaoliuList> data) {
        super(layoutResId, data);
        mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper,PiaoliuData.Res.PiaoliuList rseckillRow) {
        this.rseckillRow=rseckillRow;

        header= helper.getView(R.id.im_header);
        inLine= helper.getView(R.id.tv_inline);
        tvName= helper.getView(R.id.tv_name);
        tvContent= helper.getView(R.id.tv_drif_content);
        tvContents= helper.getView(R.id.tv_drif_contents);
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
        if (rseckillRow.getType().equals("1")){
            tvContents.setVisibility(View.VISIBLE);
            tvContents.setText(rseckillRow.getContent());
            tvContent.setVisibility(View.GONE);
        }else {
            tvContents.setVisibility(View.GONE);
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText("(点击进入瓶子，即可听语音)");
        }


    }
}
