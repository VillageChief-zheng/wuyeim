package com.wuye.piaoliuim.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.ToastUtil;
import com.chuange.basemodule.view.DialogView;
import com.lcw.library.imagepicker.ImagePicker;
import com.lcw.library.imagepicker.adapter.ImagePreViewAdapter;
import com.lcw.library.imagepicker.data.MediaFile;
import com.lcw.library.imagepicker.view.HackyViewPager;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GlideLoader;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName FangdaPicAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/20 17:00
 */
public class FangdaPicAct extends BaseActivity implements DialogView.DialogViewListener {
    @BindView(R.id.vp_main_preImage)
    HackyViewPager vpMainPreImage;

    ImagePreViewAdapter mImagePreViewAdapter;
    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.im_more)
    ImageView imMore;
    private List<MediaFile> mMediaFileList;

    String picName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_image);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ImagePicker.getInstance()
                .setTitle("标题")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(true)//设置是否展示视频
                .setMaxCount(3)//设置最大选择图片数目(默认为1，单选)
                .setSingleType(true)//设置图片视频不能同时选择
                .setImageLoader(new GlideLoader());
        mMediaFileList = new ArrayList<>();
        MediaFile mediaFile = new MediaFile();
        mediaFile.setPath("https://img-blog.csdnimg.cn/20191216172544126.png");
        mMediaFileList.add(mediaFile);
        mImagePreViewAdapter = new ImagePreViewAdapter(this, mMediaFileList);
        vpMainPreImage.setAdapter(mImagePreViewAdapter);
        vpMainPreImage.setCurrentItem(0);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }



    @OnClick({R.id.im_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.im_more:
                loading(R.layout.dialog_delete, this).setOutsideClose(true).setGravity(Gravity.BOTTOM);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_delete:
                deleteImg();
                break;
        }
    }

    @Override
    public void onView(View view) {
     view.findViewById(R.id.tv_delete);
    }
    public void deleteImg(){
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.LITPIC,picName);
        params.put(UrlConstant.DELLITPIC,picName);
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.DELUSERIMG, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                //删除成功操作
                ToastUtil.show(getBaseContext(),"删除成功");
                finish();
             }

            @Override
            public void onError(String message) {

            }
        });
    }
}
