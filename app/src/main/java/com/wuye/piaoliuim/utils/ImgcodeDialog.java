package com.wuye.piaoliuim.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.config.Constants;
import com.wuye.piaoliuim.config.UrlConstant;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.vise.utils.handler.HandlerUtil.runOnUiThread;

/**
 * @ClassName ImgcodeDialog
 * @Description
 * @Author VillageChief
 * @Date 2019/12/30 9:38
 */
public class ImgcodeDialog extends DialogFragment {

    //点击发表，内容不为空时的回调
    public  SendBackListener sendBackListener;
    public interface  SendBackListener{
        void sendBack(String inputText);
    }

    private ProgressDialog progressDialog;
    private String texthint;
      ImageView imagcode;
    private Dialog dialog;
    public EditText inputDlg;
    private int numconut=300;
    private String tag=null;

    public ImgcodeDialog() {
    }


    @SuppressLint("ValidFragment")
    public ImgcodeDialog(String texthint, SendBackListener sendBackListener){//提示文字
        this.texthint=texthint;
        this.sendBackListener=sendBackListener;

    }



    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialog = new Dialog(getActivity(), R.style.inputDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View contentview = View.inflate(getActivity(), R.layout.dialog_codeimg, null);
        dialog.setContentView(contentview);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.5f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        inputDlg = (EditText) contentview.findViewById(R.id.ed_imgcode);
        inputDlg.setHint(texthint);
        final Button tv_send = (Button) contentview.findViewById(R.id.bt_submit);
        final ImageView imb = (ImageView) contentview.findViewById(R.id.img_gb);
          imagcode = (ImageView) contentview.findViewById(R.id.imgcode);
        getcode();
        inputDlg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    tv_send.setBackgroundResource(R.drawable.fillet_grbg);
//                    tv_send.setTextColor(R.color.text_red22);
                } else {
                    tv_send.setBackgroundResource(R.drawable.fillet_graybg);
                }

            }
        });
  imagcode.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          getcode();
      }
  });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputDlg.getText().toString())||inputDlg.getText().toString().equals("0")) {
//                    ToastUtils.getInstance().showToast("输入内容为空奥");
//                    Toast.makeText(getActivity(),"输入不小于1", Toast.LENGTH_LONG).show();
                    return;
                } else {

//                    progressDialog = new ProgressDialog(getActivity());
//                    progressDialog.setCanceledOnTouchOutside(false);
//                    progressDialog.show();
                    sendBackListener.sendBack(inputDlg.getText().toString());
                }
            }
        });
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        inputDlg.setFocusable(true);
        inputDlg.setFocusableInTouchMode(true);
        inputDlg.requestFocus();
        final Handler hanler = new Handler();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public InputMethodManager mInputMethodManager;

            @Override
            public void onDismiss(DialogInterface dialog) {
                hanler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideSoftkeyboard();
                    }
                }, 200);

            }
        });
        return dialog;
    }
    public void hideProgressdialog(){
        progressDialog.cancel();
    }


    public void hideSoftkeyboard() {
        try {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {

        }
    }
    private void getcode(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request
                                .Builder()
                                .url(Constants.BASEURL+ UrlConstant.GETIMAGE)//要访问的链接
                                .build();

                        Call call = client.newCall(request);

                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                InputStream is = response.body().byteStream(); //获取 字节输入流
                                final Bitmap bitmap = BitmapFactory.decodeStream(is); // 把获取到的 数据 转换成 Bitmap 类型的
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        imagcode.setImageBitmap(bitmap);

                                    }
                                });

                            }
                        });
                    }
                }
        ).start();
    }
}
