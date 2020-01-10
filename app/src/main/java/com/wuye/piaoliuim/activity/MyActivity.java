package com.wuye.piaoliuim.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.ToastUtil;
import com.lcw.library.imagepicker.ImagePicker;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.UpFileData;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GlideLoader;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;

/**
 * @ClassName MyActivity
 * @Description 我的用户主页
 * @Author VillageChief
 * @Date 2019/12/18 9:44
 */
public class MyActivity extends BaseActivity {


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
    @BindView(R.id.toupPic)
    TextView toupPic;
    @BindView(R.id.im1)
    ImageView im1;
    @BindView(R.id.im2)
    ImageView im2;
    @BindView(R.id.im3)
    ImageView im3;
    @BindView(R.id.img_lin1)
    LinearLayout imgLin1;
    @BindView(R.id.im4)
    ImageView im4;
    @BindView(R.id.im5)
    ImageView im5;
    @BindView(R.id.im6)
    ImageView im6;
    @BindView(R.id.img_lin2)
    LinearLayout imgLin2;
    @BindView(R.id.nodatpic)
    ImageView nodatpic;

    private static final int REQUEST_SELECT_IMAGES_CODE = 0x022;
    @BindView(R.id.im_back)
    RelativeLayout imBack;

    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private ArrayList<File> upPicList = new ArrayList<>(); //上传的图片源文件
    String tuPianList = "";
    String sexStr;
    UpFileData upFileData;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    List<String> mlist = new ArrayList<>();
    List<ImageView> imageList = new ArrayList<>();

    String picname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persone);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNetData();

    }

    public void getNetData() {
        HashMap<String, String> params = new HashMap<>();
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.GETUSERINFO, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                userInfoData = GsonUtil.getDefaultGson().fromJson(requestEntity, UserInfoData.class);
                setUser(userInfoData);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public void setUser(final UserInfoData userInfoData) {
        imageList.add(im1);
        imageList.add(im2);
        imageList.add(im3);
        imageList.add(im4);
        imageList.add(im5);
        imageList.add(im6);
        if (userInfoData.res.listList.getGender().equals("1")) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_nan);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(null, null, drawable, null);
        } else if (userInfoData.res.listList.getGender().equals("2")) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_nv);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(null, null, drawable, null);
        }
        tvName.setText(userInfoData.res.listList.name);
        tvQianming.setText(userInfoData.res.listList.getSignature());
        tvFins.setText(userInfoData.res.listList.getFans() + "  粉丝");
        tvGz.setText(userInfoData.res.listList.getFollows() + "  关注");
        RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(this)
                .load(ImagUrlUtils.getImag(userInfoData.res.listList.getLitpic())).apply(options)
                .into(imHeader);
        tvToedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EditInfoAct.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", userInfoData);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mlist = getimagList(userInfoData.res.listList.getUser_imgs());
        picname = userInfoData.res.listList.getUser_imgs();
         initView(mlist);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.toupPic, R.id.im1, R.id.im2, R.id.im3, R.id.img_lin1, R.id.im4, R.id.im5, R.id.im6, R.id.nodatpic,R.id.im_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toupPic:
                if (mlist.size() == 6) {
                    ToastUtil.show(getBaseContext(), "添加图片达到上限");
                } else {
                    ImagePicker.getInstance()
                            .setTitle("标题")//设置标题
                            .showCamera(true)//设置是否显示拍照按钮
                            .showImage(true)//设置是否展示图片
                            .showVideo(false)//设置是否展示视频
                            .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                            .setSingleType(true)//设置图片视频不能同时选择
                            .setImageLoader(new GlideLoader())
                            .start(MyActivity.this, REQUEST_SELECT_IMAGES_CODE);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCod
                }

                break;
            case R.id.im1:
                starPicsee(0);
                break;
            case R.id.im2:
                starPicsee(1);

                break;
            case R.id.im3:
                starPicsee(2);

                break;
            case R.id.img_lin1:
                break;
            case R.id.im4:
                starPicsee(3);

                break;
            case R.id.im5:
                starPicsee(4);

                break;
            case R.id.im6:
                starPicsee(5);
                break;
            case R.id.nodatpic:
                Log.i("ppppp", mlist.size() + "大图片");
                if (mlist.size() == 1) {
                    starPicsee(0);
                }
                break;
            case R.id.im_back:
                finish();
                break;
        }
    }

    private void starPicsee(int postione) {
        Intent intent = new Intent(this, FangdaPicAct.class);
        intent.putExtra("picurl", mlist.get(postione));
        intent.putExtra("piclist", picname);

        startActivity(intent);

    }

    //上传图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_IMAGES_CODE && resultCode == RESULT_OK) {
            mPicList.clear();
            mPicList = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);

            upFile();
        }

    }

    public void upFile() {

        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png/jpg");

        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.TYPE, "2");
        upPicList.clear();
        for (int i = 0; i < mPicList.size(); i++) {
            upPicList.add(new File(mPicList.get(i)));
        }
        RequestManager.getInstance().upUpFile(this, params, upPicList, UrlConstant.FILEDATA, MEDIA_TYPE_PNG, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                //更新成功
                upFileData = GsonUtil.getDefaultGson().fromJson(requestEntity, UpFileData.class);
                tuPianList = upFileData.getFilename();
                nextPic(tuPianList);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void nextPic(String nextPic) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.LITPIC, returnPic(nextPic));
        params.put(UrlConstant.DELLITPIC, "");
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.DELUSERIMG, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                //删除成功操作
                ToastUtil.show(getBaseContext(), "添加成功");
                getNetData();
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public List<String> getimagList(String picImag) {
        List<String> idList;
        if (picImag.equals("")) {
            return idList = new ArrayList<>();
        } else {
            idList = Arrays.asList(picImag.split(","));//根据逗号分隔转化为list

        }

        return idList;
    }

    private void initView(List<String> listPic) {
        if (listPic.size() == 0) {
            Glide.with(this)
                    .load(R.mipmap.bg_nodate)
                    .into(nodatpic);
            nodatpic.setVisibility(View.VISIBLE);
            imgLin2.setVisibility(View.GONE);
            imgLin1.setVisibility(View.GONE);
        } else if (listPic.size() == 1) {
            imgLin2.setVisibility(View.GONE);
            imgLin1.setVisibility(View.GONE);
            nodatpic.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(ImagUrlUtils.getImag(listPic.get(0)))
                    .into(nodatpic);
            Log.i("数据为一个只显示", "大图" + ImagUrlUtils.getImag(listPic.get(0)));
        } else if (listPic.size() <= 3) {
            Log.i("数据为俩个san只显示", "大图+小图");
            imgLin1.setVisibility(View.VISIBLE);
            imgLin2.setVisibility(View.GONE);
            nodatpic.setVisibility(View.GONE);
            forList(listPic);
        } else if (listPic.size() > 3) {
            Log.i("数据为俩个只显示", "大图+小图");
            imgLin1.setVisibility(View.VISIBLE);
            imgLin2.setVisibility(View.VISIBLE);
            nodatpic.setVisibility(View.GONE);

            forList(listPic);
        }
    }

    private void forList(List<String> msList) {
        Log.i("数据的长度", msList.size() + "");
        for (int a = 0; a < imageList.size(); a++) {
            imageList.get(a).setVisibility(View.INVISIBLE);

        }
        for (int i = 0; i < msList.size(); i++) {
            imageList.get(i).setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(ImagUrlUtils.getImag(msList.get(i)))
                    .into(imageList.get(i));
        }
    }

    private String returnPic(String picStr) {
        String aa = "";
        if (picname.equals("")) {
            return picStr;
        } else {
            return picname + "," + picStr;
        }
    }


}
