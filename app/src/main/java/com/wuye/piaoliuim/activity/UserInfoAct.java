package com.wuye.piaoliuim.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.ToastUtil;
import com.chuange.basemodule.view.DialogView;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.WuyeApplicatione;
import com.wuye.piaoliuim.bean.UserInfoData;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
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
 * @ClassName UserInfoAct
 * @Description 查看其他用户个人主页
 * @Author VillageChief
 * @Date 2019/12/17 16:07
 */
public class UserInfoAct extends BaseActivity implements DialogView.DialogViewListener {
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
    @BindView(R.id.tv_togz)
    TextView tvTogz;
    @BindView(R.id.tv_yiguanzhu)
    TextView tvYiguanzhu;

    @BindView(R.id.im1)
    ImageView im1;
    @BindView(R.id.im2)
    ImageView im2;
    @BindView(R.id.im3)
    ImageView im3;
    @BindView(R.id.im4)
    ImageView im4;
    @BindView(R.id.im5)
    ImageView im5;
    @BindView(R.id.im6)
    ImageView im6;

    UserInfoData userInfoData;
    List<String> mlist = new ArrayList<>();
    List<ImageView> imageList = new ArrayList<>();
    String id;
    List<Object> imcList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peopleinfo);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        getNetData(id);

    }

    public void getNetData(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.USER_ID, id);
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.GETOTHERUSERINFO, new RequestListener<String>() {
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

    public void toBlack() {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.BLICKID, userInfoData.res.listList.getId());
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.ADDBLACk, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                ToastUtil.show(getBaseContext(), "已加入黑名单");
                finish();
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public void toFlow() {
        HashMap<String, String> params = new HashMap<>();
        params.put(UrlConstant.PASSID, userInfoData.res.listList.getId());
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.ADDFOLLOW, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                ToastUtil.show(getBaseContext(), "关注成功");
                tvYiguanzhu.setVisibility(View.VISIBLE);
                tvTogz.setVisibility(View.GONE);
                int i = Integer.parseInt(userInfoData.res.listList.getFans());
                tvFins.setText(i + 1 + "  粉丝");


            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.im_more, R.id.tv_tosx, R.id.tv_togz, R.id.im_back,R.id.im1, R.id.im2, R.id.im3, R.id.im4, R.id.im5, R.id.im6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_more:
                loading(R.layout.dialog_black, this).setOutsideClose(true).setGravity(Gravity.BOTTOM);
                break;
            case R.id.tv_tosx:
                ConversationInfo conversationInfo = new ConversationInfo();
                conversationInfo.setId(userInfoData.res.getListList().getId());
                imcList.add(ImagUrlUtils.getImag(userInfoData.res.getListList().getLitpic()));
                conversationInfo.setIconUrlList(imcList);
                conversationInfo.setGroup(false);
                conversationInfo.setTitle(userInfoData.res.getListList().getName());
                startChatActivity(conversationInfo);
                break;
            case R.id.tv_togz:
                toFlow();
                break;
            case R.id.im_back:
                finish();
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
            case R.id.im4:
                starPicsee(3);

                break;
            case R.id.im5:
                starPicsee(4);

                break;
            case R.id.im6:
                starPicsee(5);

                break;

        }
    }
private void starPicsee(int postione){
        Intent intent=new Intent(this,UserPicSeeAct.class);
        intent.putExtra("picurl",mlist.get(postione));
        startActivity(intent);

}
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_toblack:
                cancelLoading();
                toBlack();
                break;
            case R.id.tv_jubao:
                Intent intent = new Intent(this, JubaoAct.class);
                intent.putExtra("uid", userInfoData.res.listList.getId());
                startActivity(intent);
                cancelLoading();
                break;
            case R.id.tv_cancle:
                cancelLoading();
                break;
        }
    }

    public void setUser(UserInfoData userInfoData) {
        imageList.add(im1);
        imageList.add(im2);
        imageList.add(im3);
        imageList.add(im4);
        imageList.add(im5);
        imageList.add(im6);
        if (userInfoData.res.listList.getGender().equals("1")) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_nan);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(drawable, null, null, null);
        } else if (userInfoData.res.listList.getGender().equals("2")) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_nv);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvName.setCompoundDrawables(drawable, null, null, null);
        }
        tvName.setText(userInfoData.res.listList.name);

        if (userInfoData.res.listList.getIs_follow().equals("1")) {
            tvYiguanzhu.setVisibility(View.VISIBLE);
            tvTogz.setVisibility(View.GONE);
        } else {
            tvYiguanzhu.setVisibility(View.GONE);
            tvTogz.setVisibility(View.VISIBLE);
        }
        tvQianming.setText(userInfoData.res.listList.getSignature());
        tvFins.setText(userInfoData.res.listList.getFans() + "  粉丝");
        tvGz.setText(userInfoData.res.listList.getFollows() + "  关注");
        RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(this)
                .load(ImagUrlUtils.getImag(userInfoData.res.listList.getLitpic())).apply(options)
                .into(imHeader);
        if (!userInfoData.res.listList.getUser_imgs().equals("")) {
            mlist = getimagList(userInfoData.res.listList.getUser_imgs());
            for (int i = 0; i < mlist.size(); i++) {
                Log.i("tupianaaa", mlist.get(i));
                imageList.get(i).setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(Constants.BASEURL + mlist.get(i))
                        .into(imageList.get(i));
            }
        }

    }

    @Override
    public void onView(View view) {
        view.findViewById(R.id.tv_toblack).setOnClickListener(this);
        view.findViewById(R.id.tv_jubao).setOnClickListener(this);
        view.findViewById(R.id.tv_cancle).setOnClickListener(this);
    }

    public List<String> getimagList(String picImag) {

        List<String> idList = Arrays.asList(picImag.split(","));//根据逗号分隔转化为list

        return idList;
    }


    private void startChatActivity(ConversationInfo conversationInfo) {
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(conversationInfo.isGroup() ? TIMConversationType.Group : TIMConversationType.C2C);
        chatInfo.setId(conversationInfo.getId());
        chatInfo.setChatName(conversationInfo.getTitle());
        Intent intent = new Intent(WuyeApplicatione.getContext(), ChatActivity.class);
        intent.putExtra(Constants.CHAT_INFO, chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        WuyeApplicatione.instance().startActivity(intent);
    }

}
