package com.wuye.piaoliuim.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.chuange.basemodule.BaseActivity;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.BlackData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName ResetPswAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/16 16:03
 */
public class ResetPswAct extends BaseActivity {
    @BindView(R.id.et_oldpsw)
    EditText etOldpsw;
    @BindView(R.id.et_newpsw)
    EditText etNewpsw;
    @BindView(R.id.et_configpsw)
    EditText etConfigpsw;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepsw);
        ButterKnife.bind(this);
    }


    @OnClick({ R.id.bt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.bt_submit:
                subMit();
                break;
        }
    }
   public  void subMit(){
      String oldPsw=etOldpsw.getText().toString().trim();
      String newPsw=etNewpsw.getText().toString().trim();
      String configPsw=etConfigpsw.getText().toString().trim();
      if (oldPsw.equals("")){
          loading("请输入正确密码");
          return;
      } if (newPsw.equals("")){
           loading("请输入正确新密码");
           return;
      } if (configPsw.equals("")){
           loading("请输入确认新密码");
            return;
      }
       if (configPsw.equals("")){
           loading("新密码不一致");
           return;
       }
       HashMap<String, String> params = new HashMap<>();
//       params.put(UrlConstant.PAGE,page+"");
//       params.put(UrlConstant.TOKEN,"");
       RequestManager.getInstance().publicPostMap(this, params, UrlConstant.BLACKLIST, new RequestListener<String>() {
           @Override
           public void onComplete(String requestEntity) {

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
}
