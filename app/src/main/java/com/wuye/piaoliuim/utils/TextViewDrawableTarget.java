package com.wuye.piaoliuim.utils;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * @ClassName TextViewDrawableTarget
 * @Description
 * @Author VillageChief
 * @Date 2019/12/11 16:12
 */
public class TextViewDrawableTarget extends CustomViewTarget<TextView, Drawable> {
    /**
     * Constructor that defaults {@code waitForLayout} to {@code false}.
     *
     * @param view
     */
    public TextViewDrawableTarget(@NonNull TextView view) {
        super(view);
    }

    @Override
    protected void onResourceCleared(@Nullable Drawable placeholder) {

    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        //加载失败例如url=null，此时使用 fallback不生效
//        view.setCompoundDrawablesWithIntrinsicBounds(null, mContext.getDrawable(R.mipmap.ic_about), null, null);
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        view.setCompoundDrawablesWithIntrinsicBounds(null, resource, null, null);
    }
}




