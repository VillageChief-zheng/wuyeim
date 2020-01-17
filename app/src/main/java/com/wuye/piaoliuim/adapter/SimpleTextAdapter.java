package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.GongPingData;
import com.wuye.piaoliuim.utils.ImagUrlUtils;
import com.xj.marqueeview.base.CommonAdapter;
import com.xj.marqueeview.base.ItemViewDelegate;
import com.xj.marqueeview.base.ViewHolder;

import java.util.List;

/**
 * @ClassName SimpleTextAdapter
 * @Description
 * @Author VillageChief
 * @Date 2020/1/16 10:45
 */
public class SimpleTextAdapter extends CommonAdapter<GongPingData.Res.Gpist> {
 Context context;

    public SimpleTextAdapter(Context context, int layoutId, List<GongPingData.Res.Gpist> datas) {
        super(context, layoutId, datas);
        this.context=context;

    }

    @Override
    protected void convert(ViewHolder holder, GongPingData.Res.Gpist multiTypeBean, int position) {
        TextView sendLiwu = holder.getView(R.id.sendliwu);
        TextView getLiwu = holder.getView(R.id.getliwu);
        TextView getnumber = holder.getView(R.id.liwu_number);
        sendLiwu.setText(multiTypeBean.getCname());
        getLiwu.setText(multiTypeBean.getName());
        getnumber.setText("X"+multiTypeBean.getNum());

        ImageView iv = holder.getView(R.id.im_liwuimg);
        Glide.with(context)
                .load(ImagUrlUtils.getImag(ImagUrlUtils.getImag(multiTypeBean.getLitpic())))
                .into(iv);    }


}
