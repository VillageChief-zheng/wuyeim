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
import com.chuange.basemodule.utils.DateUtils;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.FindData;
import com.wuye.piaoliuim.bean.GlodData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.utils.DateSm;

import java.text.ParseException;
import java.util.List;

public class SendGlodAdapter  extends BaseQuickAdapter<GlodData.Res.GlodList, BaseViewHolder> {

    private Context mContext;
    GlodData.Res.GlodList rseckillRow;
    TextView type;
    String iszan;
    int viedoZanNumber = 0;

    public SendGlodAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<GlodData.Res.GlodList> data) {
        super(layoutResId, data);
        mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, GlodData.Res.GlodList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper.setText(R.id.tv_riqi, DateSm.getDateAndMin(Long.parseLong(rseckillRow.getCreate_time()),"1")).setText(R.id.tv_riqiyear, DateSm.getDateAndMin(Long.parseLong(rseckillRow.getCreate_time()),"2"))
                .setText(R.id.tv_number, "-"+rseckillRow.getGold())
        ;
             Log.i("ppppppp", DateUtils.getDateAndMin( Long.parseLong(rseckillRow.getCreate_time()),"1" ));
             Log.i("ppppppp", DateUtils.getDateAndMin( Long.parseLong(rseckillRow.getCreate_time()),"2" ));


        type=helper.getView(R.id.tv_name);

        if (rseckillRow.getType().equals("1")){
            type.setText("注册送");
        }else if (rseckillRow.getType().equals("2")){
            type.setText("充值");
        }else if (rseckillRow.getType().equals("3")){
            type.setText("收到礼物");
        }else if (rseckillRow.getType().equals("4")){
            type.setText("看广告");
        }else if (rseckillRow.getType().equals("-1")){
            type.setText("扔瓶子");
        }else if (rseckillRow.getType().equals("-2")){
            type.setText("送礼物");
        }
    }

}
