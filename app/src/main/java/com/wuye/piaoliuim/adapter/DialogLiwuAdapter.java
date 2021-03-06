package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.ChannelModel;
import com.wuye.piaoliuim.bean.LiwuListData;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

import java.util.ArrayList;

/**
 * @ClassName DialogLiwuAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/27 15:01
 */
public class DialogLiwuAdapter extends RecyclerView.Adapter<DialogLiwuAdapter.BaseViewHolder> {
    private ArrayList<LiwuListData.Res.LiwuLiestData> dataList = new ArrayList<>();
    public static int lastPressIndex = 0;
    public  OnCheckedChangedListener listener;
    int mPostion;
    Context context;
    public    DialogLiwuAdapter(Context context){
        this.context=context;
    }
     public void replaceAll(ArrayList<LiwuListData.Res.LiwuLiestData> list) {
        dataList.clear();
         lastPressIndex=0;
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
    public DialogLiwuAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ChannelModel.ONE:
                return new  OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dailog_liwu_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(DialogLiwuAdapter.BaseViewHolder holder, int position) {

        holder.setData(dataList.get(position));
        if(isShow&&position<mPostion){
            holder.setVisibility(true);
        }else{
            holder.setVisibility(false);
        }
        Log.i("zzzzzzzzzposition",position+"");


    }

    @Override
    public int getItemViewType(int position) {
        return  ChannelModel.ONE;
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        void setData(LiwuListData.Res.LiwuLiestData data) {
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

    private class OneViewHolder extends DialogLiwuAdapter.BaseViewHolder {
        private TextView tvName,tvJinbi ;
        private ImageView imLiwu ;

        public OneViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvJinbi = (TextView) view.findViewById(R.id.tv_jinbi);
            imLiwu = (ImageView) view.findViewById(R.id.img_liwu);
            itemView.setSelected(true);

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
                    Log.i("zzzzzzzzz",lastPressIndex+"");
                    notifyDataSetChanged();
                }

            });
        }

        @Override
        void setData(LiwuListData.Res.LiwuLiestData data) {
            if (data != null) {
                String text = "";//(String) data
                 tvName.setText(data.getName()+"");
                 tvJinbi.setText(data.getGold()+"钻石");
                Glide.with(context)
                        .load(ImagUrlUtils.getImag(data.getLitpic()))
                        .into(imLiwu);
                Log.i("zzzzzzzzzgepterPosition",getAdapterPosition()+"=="+lastPressIndex);

                if (getAdapterPosition() == lastPressIndex) {
                    imLiwu.setSelected(true);

                    //                    SpannableString s = new SpannableString(data.jinBi);
//                    SpannableString s1 = new SpannableString(data.addJinbi);
//                    s.setSpan(new ForegroundColorSpan(Color.parseColor("#656565")), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    s1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF775EFF")), 0, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    tv.setText(s+""+s1);

                    //                    tv.setText(Html.fromHtml("<font color=\'#FF999999\'>可获得</font><font color=\'#FFFF5B63\'>" +
//                            data.jinBi+" </font> "));
                } else {
                    imLiwu.setSelected(false);

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
    public void setOnCheckChangedListener( OnCheckedChangedListener l) {
        this.listener = l;
    }
    public interface OnCheckedChangedListener {
        public void onItemChecked( int position);
    }
    public String  getChannelId(){
        return (String) dataList.get(lastPressIndex).getId();
    }
}
