package com.wuye.piaoliuim.login;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName PswLogin
 * @Description
 * @Author VillageChief
 * @Date 2019/12/30 12:16
 */
public class PswLogin extends BaseActivity {

    @BindView(R.id.qq)
    TextView qq;
    @BindView(R.id.edphone)
    EditText edphone;
    @BindView(R.id.edpsw)
    EditText edpsw;
    @BindView(R.id.tv_findpsw)
    TextView tvFindpsw;
    @BindView(R.id.tv_smcodelogin)
    TextView tvSmcodelogin;
    @BindView(R.id.bg_login)
    Button bgLogin;
    @BindView(R.id.qq_login)
    ImageView qqLogin;
    @BindView(R.id.wx_login)
    ImageView wxLogin;
    @BindView(R.id.pswshowAndhid)
    ImageView pswshowAndhid;
    boolean mPasswordVisible=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pswlogin);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_findpsw, R.id.tv_smcodelogin, R.id.bg_login, R.id.qq_login, R.id.wx_login,R.id.pswshowAndhid})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_findpsw:
                break;
            case R.id.tv_smcodelogin:
                break;
            case R.id.bg_login:
                break;
            case R.id.qq_login:
                break;
            case R.id.wx_login:
                break;
            case R.id.pswshowAndhid:
                     if (mPasswordVisible) {
                        //设置EditText的密码为可见的
                        edpsw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        pswshowAndhid.setImageResource(R.mipmap.ic_showpsw);
                          mPasswordVisible=false;
                     } else {
                        //设置密码为隐藏的
                        edpsw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                         mPasswordVisible=true;
                         pswshowAndhid.setImageResource(R.mipmap.ic_hinpsw);


                     }


                break;
        }
    }
}
