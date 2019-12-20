package com.wuye.piaoliuim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wuye.piaoliuim.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName ImageAdapter
 * @Description
 * @Author VillageChief
 * @Date 2019/12/20 13:31
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    protected ArrayList<String> mData;
    protected Context context;
    private OnItemClickListener listener ;

    public void setmData(ArrayList<String> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }


    public String getItem(int position){
        return mData.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.home_seckill_item, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent,false);
        ViewHolder myViewHolder = new ViewHolder(v);
        return myViewHolder;
//    return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageIv.setTag(position);
        holder.imageIv.setImageResource(R.mipmap.ic_selimg);
        if (position < mData.size()) {
            String name = mData.get(position);
            Glide.with(context)
                    .load(name)
                    .into(holder.imageIv);

        }else {
            holder.imageIv.setImageResource(R.mipmap.ic_selimg);

        }

    }

    @Override
    public int getItemCount() {
//        return mData != null ? mData.size() : 0;
        int count = mData == null ? 1 : mData.size() + 1;
        if (count >3) {
            return mData.size();
        } else {
            return count;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.pic_iv)
        ImageView imageIv;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
    public  interface OnItemClickListener{
        void onItemClick(View view ,int postion);
    }

}
