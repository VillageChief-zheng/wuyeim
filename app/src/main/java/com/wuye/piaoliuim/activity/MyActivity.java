package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.LoveData;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName MyActivity
 * @Description 我的用户主页
 * @Author VillageChief
 * @Date 2019/12/18 9:44
 */
public class MyActivity extends BaseActivity {

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
    @BindView(R.id.tv_toedit)
    TextView tvToedit;
    UserInfoData userInfoData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persone);
        ButterKnife.bind(this);
    }

    public void getNetData(int page){
        HashMap<String, String> params = new HashMap<>();
         RequestManager.getInstance().publicPostMap(this, params, UrlConstant.GETUSERINFO, new RequestListener<String>() {
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
    public void setUser( UserInfoData userInfoData){

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
tvToedit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getBaseContext(),EditInfoAct.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("user",userInfoData);
        intent.putExtras(bundle);
         startActivity(intent);
    }
});
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
