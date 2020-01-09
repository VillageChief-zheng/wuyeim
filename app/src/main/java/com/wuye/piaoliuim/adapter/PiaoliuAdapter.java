package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tencent.qcloud.tim.uikit.component.AudioPlayer;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.FindData;
import com.wuye.piaoliuim.bean.PiaoliuData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

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
    LinearLayout bofang;
    ImageView audioPlayImage;
    public PiaoliuAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<PiaoliuData.Res.PiaoliuList> data) {
        super(layoutResId, data);
        mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper,PiaoliuData.Res.PiaoliuList rseckillRow) {
        this.rseckillRow=rseckillRow;
         header= helper.getView(R.id.im_header);
        inLine= helper.getView(R.id.tv_inlin);
        tvName= helper.getView(R.id.tv_name);
        tvContent= helper.getView(R.id.tv_drif_content);
        tvContents= helper.getView(R.id.tv_drif_contents);
        bofang= helper.getView(R.id.bofang);
        audioPlayImage= helper.getView(R.id.audioPlayImage);
        if (rseckillRow.getOnline().equals("1")){
            inLine.setVisibility(View.VISIBLE);
        }else {
            inLine.setVisibility(View.GONE);
        }
        if (rseckillRow.getGender().equals("1")){
            Drawable drawable= mContext.getResources().getDrawable(R.mipmap.ic_nan);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(null,null,drawable,null);
        }else  if (rseckillRow.getGender().equals("2")){
            Drawable drawable= mContext.getResources().getDrawable(R.mipmap.ic_nv);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(null,null,drawable,null);
         }
        if (rseckillRow.getType().equals("1")){
            tvContents.setVisibility(View.VISIBLE);
            tvContents.setText(rseckillRow.getContent());
            bofang.setVisibility(View.GONE);
        }else {
            tvContents.setVisibility(View.GONE);
            bofang.setVisibility(View.VISIBLE);
//            tvContent.setText("(点击进入瓶子，即可听语音)");
        }

        tvName.setText(rseckillRow.getName()+"  ");
        RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(mContext)
                .load(ImagUrlUtils.getImag(rseckillRow.getLitpic())).apply(options)
                .into(header);
        helper.addOnClickListener(R.id.bofang);


    }
    }
