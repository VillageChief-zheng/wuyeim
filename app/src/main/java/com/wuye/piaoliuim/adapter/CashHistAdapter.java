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
import com.wuye.piaoliuim.bean.CashListData;
import com.wuye.piaoliuim.bean.FinsData;
import com.wuye.piaoliuim.utils.DateSm;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

import java.util.List;

public class CashHistAdapter extends BaseQuickAdapter<CashListData.Res.CashList, BaseViewHolder> {

    private Context mContext;
    CashListData.Res.CashList rseckillRow;
     TextView name,tvstate;
    String iszan;
    int viedoZanNumber = 0;

    public CashHistAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<CashListData.Res.CashList> data) {
        super(layoutResId, data);
        mContext = context;

    }
    // 日 分
    @Override
    protected void convert(BaseViewHolder helper, CashListData.Res.CashList rseckillRow) {
        this.rseckillRow=rseckillRow;
        helper.setText(R.id.tv_riqi,  DateSm.getDateAndMin( Long.parseLong(rseckillRow.getCreate_time()),"1" ))
                .setText(R.id.tv_riqiyear,  DateSm.getDateAndMin( Long.parseLong(rseckillRow.getCreate_time()),"2" ));

        tvstate = helper.getView(R.id.tv_name);
        name = helper.getView(R.id.tv_number);
        name.setText("+"+rseckillRow.getMoney());
        //1已打款 0拒绝打款 2审核中
        if (rseckillRow.getStatus().equals("0")){
            tvstate.setText("已打款");
        }else if(rseckillRow.getStatus().equals("0")){
            tvstate.setText("拒绝打款");
         }else {
            tvstate.setText("审核中");

        }


    }

}
