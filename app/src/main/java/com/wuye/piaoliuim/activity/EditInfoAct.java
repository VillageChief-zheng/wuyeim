package com.wuye.piaoliuim.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuange.basemodule.BaseActivity;
import com.lcw.library.imagepicker.ImagePicker;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GlideLoader;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;

/**
 * @ClassName EditInfoAct
 * @Description 更改个人信息
 * @Author VillageChief
 * @Date 2019/12/18 9:33
 */
public class EditInfoAct extends BaseActivity {


    @BindView(R.id.clock)
    ImageView clock;
    @BindView(R.id.tv_basic)
    TextView tvBasic;
    @BindView(R.id.et_nicheng)
    EditText etNicheng;
    @BindView(R.id.tv_jian)
    TextView tvJian;
    @BindView(R.id.et_jianjie)
    EditText etJianjie;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_ids)
    TextView tvIds;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_sexs)
    TextView tvSexs;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    UserInfoData userInfoData;
    private static final int REQUEST_SELECT_IMAGES_CODE = 0x022;

     private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private ArrayList<File> upPicList = new ArrayList<>(); //上传的图片源文件

    String sexStr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        ButterKnife.bind(this);
    }
   private void initView(){
        userInfoData= (UserInfoData) getIntent().getSerializableExtra("user");
        if (!userInfoData.res.listList.getLitpic().equals("")){
            RequestOptions options = new RequestOptions()//圆形图片
                    .circleCrop();
            Glide.with(this)
                    .load(userInfoData.res.listList.getLitpic())
                    .apply(options)
                    .into((clock));
        }
      etNicheng.setText(userInfoData.res.listList.getName());
      etJianjie.setText(userInfoData.res.listList.getSignature());
      tvIds.setText(userInfoData.res.listList.getId());
       if (userInfoData.res.listList.getGender().equals("1")){
           Drawable drawable= getResources().getDrawable(R.mipmap.ic_nan);
           drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
           tvSexs.setCompoundDrawables(drawable,null,null,null);
           tvSexs.setText(userInfoData.res.listList.name);
       }else  if (userInfoData.res.listList.getGender().equals("2")){
           Drawable drawable= getResources().getDrawable(R.mipmap.ic_nv);
           drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
           tvSexs.setCompoundDrawables(drawable,null,null,null);
           tvSexs.setText(userInfoData.res.listList.name);
       }

   }
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.clock, R.id.tv_sexs, R.id.bt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clock:
                ImagePicker.getInstance()
                        .setTitle("标题")//设置标题
                        .showCamera(true)//设置是否显示拍照按钮
                        .showImage(true)//设置是否展示图片
                        .showVideo(true)//设置是否展示视频
                        .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                        .setSingleType(true)//设置图片视频不能同时选择
                        .setImageLoader(new GlideLoader())
                         .start(EditInfoAct.this, REQUEST_SELECT_IMAGES_CODE);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCod
                break;
            case R.id.tv_sexs:
                break;
            case R.id.bt_submit:
                break;
       }
    }
   public void subMit(){
        String nichengStr=etNicheng.getText().toString().trim();
        String jianJieStr=etJianjie.getText().toString().trim();
        if (nichengStr.equals("")){
            loading("请输入昵称").setOnlySure();
            return;
        }if (jianJieStr.equals("")){
            loading("请输入简介").setOnlySure();
            return;
        }
       MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
       for (int i = 0; i < mPicList.size(); i++) {
           upPicList.add(new File(mPicList.get(i)));
       }
       HashMap<String, String> params = new HashMap<>();
       params.put(UrlConstant.NAME,nichengStr );
       params.put(UrlConstant.SINNTURE,jianJieStr );
       params.put(UrlConstant.AGE,"" );
       params.put(UrlConstant.GENDER,sexStr );
       if (mPicList.size()>0){
           params.put(UrlConstant.OLDLITPIC,userInfoData.res.listList.getLitpic() );
        }

       params.put(UrlConstant.GENDER,sexStr );

       RequestManager.getInstance().upDateUserinfo(this, params, upPicList,UrlConstant.LITPIC,MEDIA_TYPE_PNG, new RequestListener<String>() {
           @Override
           public void onComplete(String requestEntity) {
            //更新成功
            finish();
            }

           @Override
           public void onError(String message) {

           }
       });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
  if (requestCode == REQUEST_SELECT_IMAGES_CODE && resultCode == RESULT_OK) {
      mPicList.clear();
      mPicList = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
      RequestOptions options = new RequestOptions()//圆形图片
              .circleCrop();
      Glide.with(this)
              .load(mPicList.get(0))
              .apply(options)
              .into((clock));
   }

    }
}
