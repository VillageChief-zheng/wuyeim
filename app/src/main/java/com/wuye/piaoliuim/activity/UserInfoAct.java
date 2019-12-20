package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.ToastUtil;
import com.chuange.basemodule.view.DialogView;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName UserInfoAct
 * @Description 查看其他用户个人主页
 * @Author VillageChief
 * @Date 2019/12/17 16:07
 */
public class UserInfoAct extends BaseActivity implements DialogView.DialogViewListener {
    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.im_more)
    ImageView imMore;
    @BindView(R.id.im_header)
    ImageView imHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_qianming)
    TextView tvQianming;
    @BindView(R.id.tv_gz)
    TextView tvGz;
    @BindView(R.id.tv_fins)
    TextView tvFins;
    @BindView(R.id.tv_tosx)
    TextView tvTosx;
    @BindView(R.id.tv_togz)
    TextView tvTogz;

    UserInfoData userInfoData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peopleinfo);
        ButterKnife.bind(this);
    }
    public void getNetData(int page){
        HashMap<String, String> params = new HashMap<>();
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.GETOTHERUSERINFO, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                userInfoData= GsonUtil.getDefaultGson().fromJson(requestEntity, UserInfoData.class);
                setUser(userInfoData);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public void toBlack(){
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.BLICKID,userInfoData.res.listList.getId());
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.ADDBLACk, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                ToastUtil.show(getBaseContext(),"加入黑名单");
                finish();
            }

            @Override
            public void onError(String message) {

            }
        });
    }
    public void toFlow(){
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.BLICKID,userInfoData.res.listList.getId());
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.ADDFOLLOW, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                ToastUtil.show(getBaseContext(),"关注成功");
                tvGz.setText("已关注");
             }

            @Override
            public void onError(String message) {

            }
        });
    }
     @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.im_more, R.id.tv_tosx, R.id.tv_togz,R.id.im_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_more:
                loading(R.layout.dialog_black, this).setOutsideClose(true).setGravity(Gravity.BOTTOM);
                 break;
            case R.id.tv_tosx:
                break;
            case R.id.tv_togz:
                toFlow();
                break;
            case R.id.im_back:
                finish();
                break;


        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_toblack:
                cancelLoading();
                 toBlack();
                break;
            case R.id.tv_jubao:
                Intent intent=new Intent(this,JubaoAct.class);
//                intent.putExtra("uid",userInfoData.res.listList.getId());
                startActivity(intent);
                cancelLoading();
                 break;
            case R.id.tv_cancle:
                cancelLoading();
                break;
        }
    }

    public void setUser(UserInfoData userInfoData){

        if (userInfoData.res.listList.getGender().equals("1")){
            Drawable drawable= getResources().getDrawable(R.mipmap.ic_nan);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(drawable,null,null,null);
            tvName.setText(userInfoData.res.listList.name);
        }else  if (userInfoData.res.listList.getGender().equals("2")){
            Drawable drawable= getResources().getDrawable(R.mipmap.ic_nv);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(drawable,null,null,null);
            tvName.setText(userInfoData.res.listList.name);
        }
        tvQianming.setText(userInfoData.res.listList.getSignature());
        tvFins.setText(userInfoData.res.listList.getFans()+"  粉丝");
        tvGz.setText(userInfoData.res.listList.getFollows()+"  关注");
        RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(this)
                .load(userInfoData.res.listList.getLitpic()).apply(options)
                .into(imHeader);

    }
    @Override
    public void onView(View view) {
     view.findViewById(R.id.tv_toblack).setOnClickListener(this);
     view.findViewById(R.id.tv_jubao).setOnClickListener(this);
     view.findViewById(R.id.tv_cancle).setOnClickListener(this);
    }
}
