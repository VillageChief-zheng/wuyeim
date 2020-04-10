package com.wuye.piaoliuim.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import java.text.DecimalFormat;

public class PeterTimeTow extends CountDownTimer {

    private Button button,buttons;
    private long millisUntilFinished;

    public PeterTimeTow(long millisInFuture, long countDownInterval, final Button button,final Button buttons) {
        super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔,要显示的按钮
        this.button = button;
        this.buttons = buttons;
    }

    @Override
    public void onTick(long millisUntilFinished) {//计时过程显示
        this.millisUntilFinished = millisUntilFinished;
        //button.setBackgroundResource(R.drawable.send_code_wait);
        DecimalFormat dec = new DecimalFormat("##.##");
        button.setText( (int) Math.floor(millisUntilFinished / 60000) + ":" + dec.format((millisUntilFinished % 60000) / 1000) + "s");
    }

    @Override
    public void onFinish() {//计时完毕时触发
        button.setVisibility(View.GONE);
        buttons.setVisibility(View.VISIBLE);
    }

}
