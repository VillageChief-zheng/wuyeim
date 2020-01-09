package com.wuye.piaoliuim.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wuye.piaoliuim.R;

/**
 * @ClassName PopupOrderPriceDetail
 * @Description
 * @Author VillageChief
 * @Date 2020/1/9 9:34
 */
public class PopupOrderPriceDetail extends PopupWindow {

    private int popupWidth;
    private int popupHeight;

    public PopupOrderPriceDetail(Activity context,   int size, boolean isInsurance) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = new LinearLayout(context);
        View mView = inflater.inflate(R.layout.layout_circle_comment, linearLayout);
         // 设置可以获得焦点
        setFocusable(true);
        // 设置弹窗内可点击
        setTouchable(true);
        // 设置弹窗外可点击
        setOutsideTouchable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        setAnimationStyle(R.style.popup_animation);
        setContentView(mView);
        //获取自身的长宽高
        mView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        popupHeight = mView.getMeasuredHeight();
        popupWidth = mView.getMeasuredWidth();
    }
    public void showUp(View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件上方显示
        showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
    }
}
