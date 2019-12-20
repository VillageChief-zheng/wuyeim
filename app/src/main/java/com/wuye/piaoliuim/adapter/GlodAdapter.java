package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.BlackData;
import com.wuye.piaoliuim.bean.GlodData;

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
        helper.setText(R.id.tv_riqi, rseckillRow.getCreate_time()).setText(R.id.tv_riqiyear, rseckillRow.getCreate_time())
        .setText(R.id.tv_number, rseckillRow.getGold()).setText(R.id.tv_name,returntype(rseckillRow.getType()))
        ;




    }
    public String returntype(String showType){
        String types = "";
        if (showType.equals("1")){
            types="注册";
        }else if (showType.equals("2")){
            types="充值";
        }else if (showType.equals("3")){
            types="收到礼物";
        }else if (showType.equals("4")){
            types="看广告";
        }else if (showType.equals("-1")){
            types="扔瓶子";
        }else if (showType.equals("-2")){
            types="送礼物";
        }
        return types;
    }
}
