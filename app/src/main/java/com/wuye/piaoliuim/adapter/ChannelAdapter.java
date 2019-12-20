package com.wuye.piaoliuim.adapter;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.ChannelModel;

import java.util.ArrayList;

/**
 * @ClassName ChannelAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/16 10:38
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.BaseViewHolder> {
    private ArrayList<ChannelModel> dataList = new ArrayList<>();
    public static int lastPressIndex = 0;
    private OnCheckedChangedListener listener;
    int mPostion;
    public void replaceAll(ArrayList<ChannelModel> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }
    private boolean isShow;
    //改变显示删除的imageview，通过定义变量isShow去接收变量isManager
    public void changetShowDelImage(boolean isShow,int mPostion) {
        this.isShow = isShow;
        this.mPostion=mPostion;
        notifyDataSetChanged();
    }
    @Override
    public ChannelAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ChannelModel.ONE:
                return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_typeone, parent, false));
            case ChannelModel.TWO:
                return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_type_tow, parent, false));
            case ChannelModel.THREE:
                return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_typethree, parent, false));
            case ChannelModel.FOUR:
                return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_typeone, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ChannelAdapter.BaseViewHolder holder, int position) {

        holder.setData(dataList.get(position));
        if(isShow&&position<mPostion){
            holder.setVisibility(true);
        }else{
            holder.setVisibility(false);
        }


    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        void setData(ChannelModel data) {
        }
        public void setVisibility(boolean isVisible){
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)itemView.getLayoutParams();
            if (isVisible){
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                itemView.setVisibility(View.VISIBLE);
            }else{
                itemView.setVisibility(View.GONE);
                param.height = 0;
                param.width = 0;
            }
            itemView.setLayoutParams(param);
        }
    }

    private class OneViewHolder extends BaseViewHolder {
        private TextView tv,jinbi,addjinbi;
        private TextView yuan;
        private FrameLayout ztai;

        public OneViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
            yuan = (TextView) view.findViewById(R.id.selected_text_view);
            jinbi = (TextView) view.findViewById(R.id.jinbi);
            addjinbi = (TextView) view.findViewById(R.id.addjinbi);
            ztai = (FrameLayout) view.findViewById(R.id.ztai);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (lastPressIndex == position) {
                        lastPressIndex = -1;
                    } else {
                        lastPressIndex = position;
                    }
                    if(lastPressIndex==-1){
                        lastPressIndex = position;
                    }
                    listener.onItemChecked(lastPressIndex);
                    notifyDataSetChanged();
                }

            });
        }

        @Override
        void setData(ChannelModel data) {
            if (data != null) {
                String text = "";//(String) data
                yuan.setText(data.data+"  ");
                jinbi.setText(data.jinBi);
                addjinbi.setText(data.addJinbi);
//                tv.setText("可获得"+data.jinBi+data.addJinbi);
                if (getAdapterPosition() == lastPressIndex) {
                    ztai.setSelected(true);

 //                    SpannableString s = new SpannableString(data.jinBi);
//                    SpannableString s1 = new SpannableString(data.addJinbi);
//                    s.setSpan(new ForegroundColorSpan(Color.parseColor("#656565")), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    s1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF775EFF")), 0, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    tv.setText(s+""+s1);

                    //                    tv.setText(Html.fromHtml("<font color=\'#FF999999\'>可获得</font><font color=\'#FFFF5B63\'>" +
//                            data.jinBi+" </font> "));
                    jinbi.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.redthemcolor));// 有活动启用
                 } else {
                    ztai.setSelected(false);
                    jinbi.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.text_ff99));// 有活动启用

                }

            }


        }
        private ForegroundColorSpan getThemSpan(){
            return new ForegroundColorSpan(ContextCompat.getColor(itemView.getContext(),R.color.themcolor));
        }
        private ForegroundColorSpan getRedSpan(){
            return new ForegroundColorSpan(ContextCompat.getColor(itemView.getContext(),R.color.redthemcolor));
        }
    }
    public void setOnCheckChangedListener(OnCheckedChangedListener l) {
        this.listener = l;
    }
    public interface OnCheckedChangedListener {
        public void onItemChecked( int position);
    }
    public String  getChannelId(){
        return (String) dataList.get(lastPressIndex).data;
    }

}
