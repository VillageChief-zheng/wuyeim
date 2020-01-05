package com.wuye.piaoliuim.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.lcw.library.imagepicker.ImagePicker;
import com.lcw.library.imagepicker.adapter.ImagePreViewAdapter;
import com.lcw.library.imagepicker.data.MediaFile;
import com.lcw.library.imagepicker.view.HackyViewPager;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.utils.GlideLoader;
import com.wuye.piaoliuim.utils.ImagUrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPicSeeAct extends BaseActivity {
    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.im_more)
    ImageView imMore;
    @BindView(R.id.vp_main_preImage)
    HackyViewPager vpMainPreImage;
    private List<MediaFile> mMediaFileList;
    ImagePreViewAdapter mImagePreViewAdapter;

    String picUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_image);
        ButterKnife.bind(this);
        imMore.setVisibility(View.GONE);
        picUrl=getIntent().getStringExtra("picurl");
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
        mediaFile.setPath(ImagUrlUtils.getImag(picUrl));
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
}
