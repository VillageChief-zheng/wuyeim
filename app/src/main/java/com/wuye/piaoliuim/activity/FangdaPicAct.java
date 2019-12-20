package com.wuye.piaoliuim.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.lcw.library.imagepicker.adapter.ImagePreViewAdapter;
import com.lcw.library.imagepicker.data.MediaFile;
import com.lcw.library.imagepicker.view.HackyViewPager;
import com.wuye.piaoliuim.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName FangdaPicAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/20 17:00
 */
public class FangdaPicAct extends BaseActivity {
    @BindView(R.id.vp_main_preImage)
    HackyViewPager vpMainPreImage;

    ImagePreViewAdapter mImagePreViewAdapter;
    private List<MediaFile> mMediaFileList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_image);
        ButterKnife.bind(this);
        initView();
    }
  private void initView(){
        mMediaFileList=new ArrayList<>();
        MediaFile mediaFile=new MediaFile();
        mediaFile.setPath("/storage/emulated/0/Download/timg.jpeg");
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
