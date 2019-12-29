package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuange.basemodule.BaseActivity;
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

/**
 * @ClassName OpinionAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/16 14:40
 */
public class OpinionAct extends BaseActivity implements YiJIanTypeAdapter.OnCheckedChangedListener {


    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    String type = "";
    YiJIanTypeAdapter yiJIanTypeAdapter;//通道adapter
    ArrayList<ChannelModel> list = new ArrayList<>();


    ImageAdapter imageAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private ArrayList<File> upPicList = new ArrayList<>(); //上传的图片源文件
    private static final int REQUEST_SELECT_IMAGES_CODE = 0x022;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fankui);
        ButterKnife.bind(this);
        initChannel();
    }

    private void subMit() {
        String content = edContent.getText().toString().trim();
        if (content.equals("")) {
            loading("请输入意见").setOnlySure();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.TYPE, type);
        params.put(UrlConstant.CONTENT, content);
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.USEROPINION, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {

            }

            @Override
            public void onError(String message) {

            }
        });

    }

    //初始化充值通道
    public void initChannel() {
        ChannelModel channelModel = new ChannelModel(ChannelModel.ONE, "产品体验", "500金币", "1", "1",1);
        ChannelModel channelModels = new ChannelModel(ChannelModel.ONE, "产品功能", "600金币", "2", "6",1);
        ChannelModel channelModelss = new ChannelModel(ChannelModel.ONE, "账号相关", "1000金币", "3", "10",1);
        ChannelModel channelModelsss = new ChannelModel(ChannelModel.ONE, "其它", "1500金币", "4", "15",1);
        list.add(channelModel);
        list.add(channelModels);
        list.add(channelModelss);
        list.add(channelModelsss);
        yiJIanTypeAdapter = new YiJIanTypeAdapter();
        yiJIanTypeAdapter.replaceAll(list);
        recommendGv.setHasFixedSize(true);
        recommendGv.setLayoutManager(new GridLayoutManager(this, 4));
        recommendGv.setAdapter(yiJIanTypeAdapter);
        yiJIanTypeAdapter.changetShowDelImage(true, 4);
        yiJIanTypeAdapter.setOnCheckChangedListener(this);
        type = list.get(0).addJinbi + "";
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
                        .start(OpinionAct.this, REQUEST_SELECT_IMAGES_CODE);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCod
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onItemChecked(int position) {
        type = list.get(position).addJinbi + "";
    }

    @OnClick({R.id.bt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.bt_submit:
                subMit();
                break;
        }
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
}
