package com.wuye.piaoliuim.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.DecimalFormat;

public class PeterTimeCountRefreshone extends CountDownTimer {


    private TextView button;
    private long millisUntilFinished;

    public PeterTimeCountRefreshone(long millisInFuture, long countDownInterval, final TextView button) {
        super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔,要显示的按钮
        this.button = button;
    }

    @Override
    public void onTick(long millisUntilFinished) {//计时过程显示
        this.millisUntilFinished = millisUntilFinished;
         //button.setBackgroundResource(R.drawable.send_code_wait);
        button.setClickable(false);
        button.setTextSize((float) 11.5);
        DecimalFormat dec = new DecimalFormat("##.##");
        button.setText("0" + (int) Math.floor(millisUntilFinished / 60000) + ":" + dec.format((millisUntilFinished % 60000) / 1000) + "s");
    }

    @Override
    public void onFinish() {//计时完毕时触发
          // button.setBackgroundResource(R.drawable.send_code);

     }
}
