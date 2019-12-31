package com.wuye.piaoliuim.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.view.DialogView;
import com.lcw.library.imagepicker.ImagePicker;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.UpFileData;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GlideLoader;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @ClassName EditInfoAct
 * @Description 更改个人信息
 * @Author VillageChief
 * @Date 2019/12/18 9:33
 */
public class EditInfoAct extends BaseActivity implements DialogView.DialogViewListener {


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
   String tuPianList="";
    String sexStr;
    UpFileData upFileData;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        ButterKnife.bind(this);
         verifyStoragePermissions(this);
        initView();
    }
   private void initView(){
        userInfoData= (UserInfoData) getIntent().getSerializableExtra("user");
        if (!userInfoData.res.listList.getLitpic().equals("")){
            RequestOptions options = new RequestOptions()//圆形图片
                    .circleCrop();
            Glide.with(this)
                    .load(Constants.BASEURL+userInfoData.res.listList.getLitpic())
                    .apply(options)
                    .into((clock));
        }
      etNicheng.setText(userInfoData.res.listList.getName());
      etJianjie.setText(userInfoData.res.listList.getSignature());
      tvIds.setText(userInfoData.res.listList.getId());
       if (userInfoData.res.listList.getGender().equals("1")){
           Drawable drawable= getResources().getDrawable(R.mipmap.ic_nan);
           drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
           tvSexs.setCompoundDrawables(null,null,null,null);
           tvSexs.setText("男 ");
           sexStr="1";

       }else  if (userInfoData.res.listList.getGender().equals("2")){
           Drawable drawable= getResources().getDrawable(R.mipmap.ic_nv);
           drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
           tvSexs.setCompoundDrawables(null,null,drawable,null);
           tvSexs.setText("女 ");
           sexStr="2";
       }
       tuPianList=userInfoData.res.listList.getLitpic();
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
                loading(R.layout.dialog_nanandnv, this).setOutsideClose(true).setGravity(Gravity.BOTTOM);

                break;
            case R.id.bt_submit:
                subMit();
                 break;
       }
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

    }
    public void upFile(){

        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png/jpg");

        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.TYPE,"1" );
        upPicList.clear();
        for (int i = 0; i < mPicList.size(); i++) {
            upPicList.add(new File(mPicList.get(i)));
        }
          RequestManager.getInstance().upUpFile(this, params,upPicList,UrlConstant.FILEDATA, MEDIA_TYPE_PNG,new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                //更新成功
                upFileData= GsonUtil.getDefaultGson().fromJson(requestEntity, UpFileData.class);
                tuPianList=upFileData.getFilename();
             }

            @Override
            public void onError(String message) {

            }
        });
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

       HashMap<String, String> params = new HashMap<>();
       params.put(UrlConstant.NAME,nichengStr );
       params.put(UrlConstant.SINNTURE,jianJieStr );
       params.put(UrlConstant.AGE,userInfoData.res.listList.getAge() );
       params.put(UrlConstant.GENDER,sexStr );
       params.put(UrlConstant.LITPIC,tuPianList );



       RequestManager.getInstance().publicPostMap(this, params,UrlConstant.UPDATEUSERINFO, new RequestListener<String>() {
           @Override
           public void onComplete(String requestEntity) {
            //更新成功
               EventBus.getDefault().post(new MessageEvent("shuaxin"));

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
      upFile();
    }

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
         switch (v.getId()) {
            case R.id.tv_nan:
                 sexStr = "1";
                Drawable drawables= getResources().getDrawable(R.mipmap.ic_nan);
                drawables.setBounds(0, 0, drawables.getMinimumWidth(), drawables.getMinimumHeight());
                tvSexs.setCompoundDrawables(null,null,drawables,null);
                tvSexs.setText("男 ");
                  cancelLoading();
                break;
            case R.id.tv_nv:


             Drawable drawable= getResources().getDrawable(R.mipmap.ic_nv);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvSexs.setCompoundDrawables(null,null,drawable,null);
                tvSexs.setText("女 ");

                 sexStr = "2";

                cancelLoading();
                break;
        }
     }
    @Override
    public void onView(View view) {
        view.findViewById(R.id.tv_nv).setOnClickListener(this);
        view.findViewById(R.id.tv_nan).setOnClickListener(this);
        view.findViewById(R.id.tv_all).setOnClickListener(this);
    }
//    private void getFile(){
//        OkHttpClient client = new OkHttpClient.Builder()
//                .build();
//   File file=new File(mPicList.get(0));
//        // 设置文件以及文件上传类型封装
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"),file );
//
//        // 文件上传的请求体封装
//        MultipartBody multipartBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                 .addFormDataPart("Filedata", file.getName(), requestBody)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("http://piaoliu.kuaiyueread.com/Index/uploadfile")
//                .post(multipartBody)
//                .build();
//
//
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("[[[[[[[[[异常",e.getMessage());
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.i("[[[[[[[[[","lkkskss");
//
//            }
//        });
//    }
}
