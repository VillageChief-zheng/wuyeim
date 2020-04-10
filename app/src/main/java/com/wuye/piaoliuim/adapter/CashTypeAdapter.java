package com.wuye.piaoliuim.adapter;

import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.ChannelModel;

import java.util.ArrayList;

public class CashTypeAdapter extends RecyclerView.Adapter<CashTypeAdapter.BaseViewHolder> {
    private ArrayList<ChannelModel> dataList = new ArrayList<>();
    public static int lastPressIndex = 0;
    private  OnCheckedChangedListener listener;
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
    public CashTypeAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ChannelModel.ONE:
                return new CashTypeAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cach_jine_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(CashTypeAdapter.BaseViewHolder holder, int position) {

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

    private class OneViewHolder extends CashTypeAdapter.BaseViewHolder {
        private TextView tv,tv_mlbz ;
        private FrameLayout ztai;
        private ImageView imgXz;

        public OneViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
            tv_mlbz = (TextView) view.findViewById(R.id.tv_mlbz);
            ztai = (FrameLayout) view.findViewById(R.id.ztai);
            imgXz = (ImageView) view.findViewById(R.id.delete_image_view);


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

                tv.setText(data.data+"");
                tv_mlbz.setText(data.jine);
                if (getAdapterPosition() == lastPressIndex) {
                    ztai.setSelected(true);
                    imgXz.setVisibility(View.VISIBLE);
                    //                    SpannableString s = new SpannableString(data.jinBi);
//                    SpannableString s1 = new SpannableString(data.addJinbi);
//                    s.setSpan(new ForegroundColorSpan(Color.parseColor("#656565")), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    s1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF775EFF")), 0, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    tv.setText(s+""+s1);

                    //                    tv.setText(Html.fromHtml("<font color=\'#FF999999\'>可获得</font><font color=\'#FFFF5B63\'>" +
//                            data.jinBi+" </font> "));
                } else {
                    imgXz.setVisibility(View.INVISIBLE);

                    ztai.setSelected(false);

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
    public void setOnCheckChangedListener(CashTypeAdapter.OnCheckedChangedListener l) {
        this.listener = l;
    }
    public interface OnCheckedChangedListener {
        public void onItemChecked( int position);
    }
    public String  getChannelId(){
        return (String) dataList.get(lastPressIndex).data;
    }

}
