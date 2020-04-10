package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chuange.basemodule.utils.DateUtils;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.BlackData;
import com.wuye.piaoliuim.bean.GlodData;
import com.wuye.piaoliuim.utils.DateSm;

import java.util.List;

/**
 * @ClassName GlodAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/17 15:20
 */
public class GlodAdapter  extends BaseQuickAdapter<GlodData.Res.GlodList, BaseViewHolder> {

    private Context mContext;
    GlodData.Res.GlodList rseckillRow;
    TextView type;
     String iszan;
    int viedoZanNumber = 0;

    public GlodAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<GlodData.Res.GlodList> data) {
        super(layoutResId, data);
        mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, GlodData.Res.GlodList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper.setText(R.id.tv_riqi,  DateSm.getDateAndMin( Long.parseLong(rseckillRow.getCreate_time()),"1" )).setText(R.id.tv_riqiyear,  DateSm.getDateAndMin( Long.parseLong(rseckillRow.getCreate_time()),"2" ))
        .setText(R.id.tv_number, "+"+rseckillRow.getGold())
        ;
        type=helper.getView(R.id.tv_name);


        if (rseckillRow.getType().equals("1")){
            type.setText("注册送");
        }else if (rseckillRow.getType().equals("2")){
            type.setText("充值");
        }else if (rseckillRow.getType().equals("3")){
            type.setText("看广告");
        }else if (rseckillRow.getType().equals("4")){
            type.setText("签到");
        }else if (rseckillRow.getType().equals("-1")){
            type.setText("扔瓶子");
        }else if (rseckillRow.getType().equals("-2")){
            type.setText("送礼物");
        }else if (rseckillRow.getType().equals("-3")){
            type.setText("捞瓶子");
        }else if (rseckillRow.getType().equals("-4")){
            type.setText("刷新瓶子");
        }else if (rseckillRow.getType().equals("-5")){
            type.setText("发现/排行榜");
        }
    }
 }
