package com.wuye.piaoliuim.adapter;

import android.content.Context;
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
import com.wuye.piaoliuim.bean.LoveData;
import com.wuye.piaoliuim.bean.SysData;
import com.wuye.piaoliuim.config.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SysAdaptet extends BaseQuickAdapter<SysData.Res.SysList, BaseViewHolder> {

    private Context mContext;
    SysData.Res.SysList rseckillRow;
    ImageView imageView,imLiwu;
    TextView tpNotlove;
    String iszan;
    int viedoZanNumber = 0;


    public SysAdaptet(Context context, @LayoutRes int layoutResId, @Nullable List<SysData.Res.SysList> data) {
        super(layoutResId, data);
        mContext = context;

    }
    // 日 分
    @Override
    protected void convert(BaseViewHolder helper, SysData.Res.SysList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper .setText(R.id.tv_title,rseckillRow.getTitle()).setText(R.id.tv_content,rseckillRow.getContent()).setText(R.id.tv_date, getDateAndMin(Long.parseLong(rseckillRow.getCreate_time())))
        ;


    }
    public static String getDateAndMin(  Long time1 ) {
        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time1 * 1000));
        Log.i("oooooooo",result1);
        String s_Data = result1.substring(0, 10); // 年份
        String s_Minn = result1.substring(10, result1.length()); // 年份
         return s_Data;
    }
}
