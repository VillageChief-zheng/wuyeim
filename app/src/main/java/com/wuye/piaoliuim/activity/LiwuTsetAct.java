package com.wuye.piaoliuim.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.view.DialogView;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.ChannelAdapter;
import com.wuye.piaoliuim.adapter.DialogLiwuAdapter;
import com.wuye.piaoliuim.adapter.YiJIanTypeAdapter;
import com.wuye.piaoliuim.bean.ChannelModel;
import com.wuye.piaoliuim.utils.KeyMapDailog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName LiwuTsetAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/27 13:48
 */
public class LiwuTsetAct extends BaseActivity implements DialogLiwuAdapter.OnCheckedChangedListener , DialogView.DialogViewListener{

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.buttons)
    Button buttons;

    ArrayList<ChannelModel> list = new ArrayList<>();
    DialogLiwuAdapter dialogLiwuAdapter;

    RecyclerView recommendGv;
    TextView tvNumber,tvJinbi,tvTop,tvSend;
    KeyMapDailog dialog;

     String liwuNumber="1";
     int postione=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liwutest);
        ButterKnife.bind(this);
    }
   private void initRec(){
       ChannelModel channelModel = new ChannelModel(ChannelModel.ONE, "饮料", "10金币", "", "10",R.mipmap.liwu_yin);
       ChannelModel channelModels = new ChannelModel(ChannelModel.ONE, "包包", "20金币", "", "20",R.mipmap.liwu_bao);
       ChannelModel channelModelss = new ChannelModel(ChannelModel.ONE, "蛋糕", "30金币", "+60金币", "30",R.mipmap.liwu_dan);
       ChannelModel channelModelsss = new ChannelModel(ChannelModel.ONE, "水晶鞋", "50金币", "+120金币", "50",R.mipmap.liwu_xie);
       ChannelModel channelModel1 = new ChannelModel(ChannelModel.ONE, "红唇", "50金币", "+120金币", "50",R.mipmap.liwu_chun);
       ChannelModel channelModel2 = new ChannelModel(ChannelModel.ONE, "钻戒", "100金币", "+120金币", "100",R.mipmap.liwu_zuan);
       ChannelModel channelModel3 = new ChannelModel(ChannelModel.ONE, "一箭穿心", "200金币", "+120金币", "200",R.mipmap.liwu_xin);
       ChannelModel channelModel4 = new ChannelModel(ChannelModel.ONE, "城堡", "500金币", "+120金币", "500",R.mipmap.liwu_cheng);
       list.add(channelModel);
       list.add(channelModels);
       list.add(channelModelss);
       list.add(channelModelsss);
       list.add(channelModel1);
       list.add(channelModel2);
       list.add(channelModel3);
       list.add(channelModel4);
       dialogLiwuAdapter = new DialogLiwuAdapter(this);
       dialogLiwuAdapter.replaceAll(list);
       recommendGv.setHasFixedSize(true);
       recommendGv.setLayoutManager(new GridLayoutManager(this, 4));
       recommendGv.setAdapter(dialogLiwuAdapter);
       dialogLiwuAdapter.changetShowDelImage(true, 8);
       dialogLiwuAdapter.setOnCheckChangedListener(this);

   }
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.button, R.id.buttons})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                loading(R.layout.dialog_liwu, this).setOutsideClose(true).setGravity(Gravity.BOTTOM);
                 break;
            case R.id.buttons:

                break;
        }
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_num:
                dialog =new KeyMapDailog("", new KeyMapDailog.SendBackListener() {
                    @Override
                    public void sendBack(String inputText) {
                        //TODO  点击发表后业务逻辑
                        liwuNumber=inputText;
                        tvNumber.setText("X"+liwuNumber);
                        Log.i("ssssss",inputText);
                        dialog.hideSoftkeyboard();
                        dialog.dismiss();

                    }
                });
                dialog.show(getSupportFragmentManager(),"kk");

                break;
            case R.id.tv_top:

                break;
            case R.id.tv_cancle:

                break;
            case R.id.bt_send:
            Toast toast=  showToastFree("1",list.get(postione).imgSrc);
            toast.setDuration(Toast.LENGTH_LONG);
            cancelLoading();
                break;
        }
    }
    @Override
    public void onView(View view) {
        recommendGv= view.findViewById( R.id.rv_comment);
        tvNumber=view.findViewById( R.id.tv_num);
        tvJinbi=view.findViewById( R.id.tv_jb);
        tvTop=view.findViewById( R.id.tv_top);
        tvSend=view.findViewById( R.id.bt_send);
        tvSend.setOnClickListener(this);
        tvTop.setOnClickListener(this);
        tvNumber.setOnClickListener(this);
        initRec();
     }

    @Override
    public void onItemChecked(int position) {
        postione=position;
    }
    public Toast showToastFree(String str,int resID) {
        Toast   toast = Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT);
        RelativeLayout toastView = (RelativeLayout) RelativeLayout.inflate(getBaseContext(),R.layout.toast_hor_view, null);
        ImageView iv = toastView.findViewById(R.id.im_liwu);
        iv.setImageResource(resID);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(toastView);
        toast.show();
        return toast;

    }
}
