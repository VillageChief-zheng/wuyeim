package com.wuye.piaoliuim.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
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
import com.wuye.piaoliuim.utils.ImagUrlUtils;

import java.util.ArrayList;
import java.util.Arrays;
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

    String pathUrl;
    String pathList;

    List<String> mlist=new ArrayList<>();
    List<String> mlists=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_image);
        ButterKnife.bind(this);
        pathUrl=getIntent().getStringExtra("picurl");
        pathList=getIntent().getStringExtra("piclist");
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
        mediaFile.setPath(ImagUrlUtils.getImag(pathUrl));
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
     view.findViewById(R.id.tv_delete).setOnClickListener(this);
    }
    public void deleteImg(){
         HashMap<String, String> params = new HashMap<>();
        mlist=getimagList(pathList);
        mlists=remove11(mlist,pathUrl);
//        if (mlist.size()==1){
//             params.put(UrlConstant.LITPIC,"");
//             Log.i("woshi 最后一张","ooooo");
//         }else {
//            Log.i("wo不shi 最后一张","ooooo"+listToString2(mlists,','));

            params.put(UrlConstant.LITPIC,listToString2(mlists,','));

//         }
        params.put(UrlConstant.DELLITPIC,pathUrl);
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


//正确
 public  List<String> remove11(List<String> list, String target) {
     for (int i = list.size() - 1; i >= 0; i--) {
         String item = list.get(i);
         if (target.equals(item)) {
             list.remove(item);
         }
     }
     return list;
 }


     public List<String> getimagList(String picImag) {

        List<String> idList = Arrays.asList(picImag.split(","));//根据逗号分隔转化为list
         List arrList = new ArrayList(idList);

        return arrList;
    }
    public   String listToString2(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
    }

 }
