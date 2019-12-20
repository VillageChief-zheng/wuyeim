package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.ToastUtil;
import com.lcw.library.imagepicker.ImagePicker;
import com.lcw.library.imagepicker.manager.SelectionManager;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.adapter.ImageAdapter;
import com.wuye.piaoliuim.adapter.YiJIanTypeAdapter;
import com.wuye.piaoliuim.bean.ChannelModel;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.DisplayUtils;
import com.wuye.piaoliuim.utils.GlideLoader;
import com.wuye.piaoliuim.utils.GridDividerItemDecorations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;

/**
 * @ClassName JubaoAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/20 10:51
 */
public class JubaoAct extends BaseActivity implements YiJIanTypeAdapter.OnCheckedChangedListener {

    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    String type = "";
    YiJIanTypeAdapter yiJIanTypeAdapter;//通道adapter
    ArrayList<ChannelModel> list = new ArrayList<>();
    String uId = "";
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    ImageAdapter imageAdapter;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private ArrayList<File> upPicList = new ArrayList<>(); //上传的图片源文件
    private static final int REQUEST_SELECT_IMAGES_CODE = 0x022;

    String typeStr[] = {"1","2","3","4","5","6","7"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jubao);
        ButterKnife.bind(this);
        initChannel();
//        uId=getIntent().getStringExtra("uid");
    }

    //初始化充值通道
    public void initChannel() {
        ChannelModel channelModel = new ChannelModel(ChannelModel.ONE, "色情低俗", "500金币", "", "1");
        ChannelModel channelModels = new ChannelModel(ChannelModel.ONE, "政治敏感", "600金币", "", "6");
        ChannelModel channelModelss = new ChannelModel(ChannelModel.ONE, "违法暴力", "1000金币", "+60金币", "10");
        ChannelModel channelModelsss = new ChannelModel(ChannelModel.ONE, "欺诈骗钱", "1500金币", "+120金币", "15");
        ChannelModel channelModelsss1 = new ChannelModel(ChannelModel.ONE, "恐怖血腥", "1500金币", "+120金币", "15");
        ChannelModel channelModelsss2 = new ChannelModel(ChannelModel.ONE, "广告", "1500金币", "+120金币", "15");
        ChannelModel channelModelsss3 = new ChannelModel(ChannelModel.ONE, "其它", "1500金币", "+120金币", "15");
        list.add(channelModel);
        list.add(channelModels);
        list.add(channelModelss);
        list.add(channelModelsss);
        list.add(channelModelsss1);
        list.add(channelModelsss2);
        list.add(channelModelsss3);
        yiJIanTypeAdapter = new YiJIanTypeAdapter();
        yiJIanTypeAdapter.replaceAll(list);
        recommendGv.setHasFixedSize(true);
        recommendGv.setLayoutManager(new GridLayoutManager(this, 4));
        recommendGv.setAdapter(yiJIanTypeAdapter);
        yiJIanTypeAdapter.changetShowDelImage(true, 7);
        yiJIanTypeAdapter.setOnCheckChangedListener(this);
        type = typeStr[0];

        RecyclerView.LayoutManager manager = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manner = new GridLayoutManager(this,
                3, RecyclerView.VERTICAL, false);

        recyclerview.setLayoutManager(manner);
//      mPicList.add("1");
        int firstAndLastColumnW = DisplayUtils.dp2px(this, 10);
        int firstRowTopMargin = DisplayUtils.dp2px(this, 10);
        GridDividerItemDecorations gridDividerItemDecoration =
                new GridDividerItemDecorations(this, firstAndLastColumnW, firstRowTopMargin, firstRowTopMargin);
        gridDividerItemDecoration.setFirstRowTopMargin(firstRowTopMargin);
        gridDividerItemDecoration.setLastRowBottomMargin(firstRowTopMargin);
        recyclerview.addItemDecoration(gridDividerItemDecoration);
        imageAdapter = new ImageAdapter();
        imageAdapter.setmData(mPicList, this);
        recyclerview.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                ImagePicker.getInstance()
                        .setTitle("标题")//设置标题
                        .showCamera(true)//设置是否显示拍照按钮
                        .showImage(true)//设置是否展示图片
                        .showVideo(true)//设置是否展示视频
                        .setMaxCount(3)//设置最大选择图片数目(默认为1，单选)
                        .setSingleType(true)//设置图片视频不能同时选择
                        .setImageLoader(new GlideLoader())
                        .setImagePaths(mPicList)//设置历史选择记录
                        .start(JubaoAct.this, REQUEST_SELECT_IMAGES_CODE);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCod
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.bt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.bt_submit:
                toJubao();
                break;
        }
    }

    @Override
    public void onItemChecked(int position) {
        type = typeStr[position] ;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_IMAGES_CODE && resultCode == RESULT_CANCELED) {
            if (SelectionManager.getInstance().getSelectPaths().size() > 0 && mPicList.size() > 0) {
                imageAdapter.setmData(mPicList, this);
                recyclerview.setAdapter(imageAdapter);
            } else {

                mPicList.clear();
                imageAdapter.setmData(mPicList, this);
                recyclerview.setAdapter(imageAdapter);
            }
        }

        if (requestCode == REQUEST_SELECT_IMAGES_CODE && resultCode == RESULT_OK) {
            mPicList.clear();
            mPicList = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
            imageAdapter.setmData(mPicList, this);
            recyclerview.setAdapter(imageAdapter);

        }
    }
    public void toJubao(){
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.TYPE,type);
        params.put(UrlConstant.CONTENT,type);
        params.put(UrlConstant.JUBAOID,uId);
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        if (upPicList.size()>0){
            for (int i = 0; i < mPicList.size(); i++) {
                upPicList.add(new File(mPicList.get(i)));
            }
        }

        RequestManager.getInstance().upDateUserinfo(this, params, upPicList,UrlConstant.LITPIC,MEDIA_TYPE_PNG, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                //更新成功
                ToastUtil.show(getBaseContext(),"举报成功");
                finish();
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
