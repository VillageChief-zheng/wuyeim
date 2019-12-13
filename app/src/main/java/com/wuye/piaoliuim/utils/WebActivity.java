package com.wuye.piaoliuim.utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.LogUtils;
import com.chuange.basemodule.utils.SystemUtils;
import com.chuange.basemodule.utils.ViewUtils;
import com.chuange.basemodule.view.NavigationTopView;
import com.google.gson.Gson;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.bean.UserTokenData;
import com.wuye.piaoliuim.config.UrlConstant;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;

/**
 * @ClassName WebActivity
 * @Description
 * @Author VillageChief
 * @Date 2019/12/6 17:35
 */
public class WebActivity extends BaseActivity {
    @ViewUtils.ViewInject(R.id.topView)
    NavigationTopView topView;
    @ViewUtils.ViewInject(R.id.webViewProgressBar)
    ProgressBar webViewProgressBar;
    @ViewUtils.ViewInject(R.id.webView)
    WebView webView;


    private int RESULT_CODE = 0;
    private ValueCallback<Uri> mUploadMessage;
    private int source;
    private boolean isBack;
    public String mCameraPhotoPath;
    public ValueCallback<Uri[]> mFilePathCallback;
    private final static int INPUT_FILE_REQUEST_CODE = 1;
    private final static int FILECHOOSER_RESULTCODE = 2;

    private final String BRIDGE_NAME = "jumpHomepage";
    private String jsReg = "window.originalPostMessage = window.postMessage," +
            "window.postMessage = function(data) { console.log('postmessage');" +
            BRIDGE_NAME + ".postMessage(String(data));console.log('postmessage11');" +
            "}";

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.activity_webview, this);
        source = getIntent().getIntExtra(UrlConstant.SOURCE, 0);
        isBack = getIntent().getBooleanExtra(UrlConstant.IS_BACK, false);
        String title = getIntent().getStringExtra(UrlConstant.TITLE);
        if (!TextUtils.isEmpty(title)&&!title.equals("")) {
            topView.setTitle(title);
        }else {
            topView.setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void processLogic(Bundle savedInstanceState) {
//        topView.setVisibility(getIntent().getBooleanExtra(UrlConstant.IS_WEB_TITLE, true) ? View.VISIBLE : View.GONE);
        init();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init() {

        final String url = getIntent().getStringExtra(UrlConstant.URL);
        LogUtils.e("url:", url);

        webView.setVerticalScrollbarOverlay(true);

        WebSettings settings = webView.getSettings();
        settings.setDefaultFontSize(12);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setUserAgentString(webView.getSettings().getUserAgentString() + " etongdaiapp/" + SystemUtils.getAppVersion(this));
        loadJs();
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.e("url:", url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadJs();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                pickFile();
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webViewProgressBar.setVisibility(View.VISIBLE);
                webViewProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    webViewProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                LogUtils.e("consoleMessage:", consoleMessage.message(), " | line:", consoleMessage.lineNumber());
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                LogUtils.e("url:", url, " | message:", message, " | result:", result);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                LogUtils.e("url:", url, " | message:", message, " | defaultValue:", defaultValue, " | result:", result);
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                LogUtils.e("title:", title);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;

                /**
                 Open Declaration   String android.provider.MediaStore.ACTION_IMAGE_CAPTURE = "android.media.action.IMAGE_CAPTURE"
                 Standard Intent action that can be sent to have the camera application capture an image and return it.
                 The caller may pass an extra EXTRA_OUTPUT to control where this image will be written. If the EXTRA_OUTPUT is not present, then a small sized image is returned as a Bitmap object in the extra field. This is useful for applications that only need a small image. If the EXTRA_OUTPUT is present, then the full-sized image will be written to the Uri value of EXTRA_OUTPUT. As of android.os.Build.VERSION_CODES.LOLLIPOP, this uri can also be supplied through android.content.Intent.setClipData(ClipData). If using this approach, you still must supply the uri through the EXTRA_OUTPUT field for compatibility with old applications. If you don't set a ClipData, it will be copied there for you when calling Context.startActivity(Intent).
                 See Also:EXTRA_OUTPUT
                 标准意图，被发送到相机应用程序捕获一个图像，并返回它。通过一个额外的extra_output控制这个图像将被写入。如果extra_output是不存在的，
                 那么一个小尺寸的图像作为位图对象返回。如果extra_output是存在的，那么全尺寸的图像将被写入extra_output URI值。
                 */
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        //设置MediaStore.EXTRA_OUTPUT路径,相机拍照写入的全路径
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (Exception ex) {
                        // Error occurred while creating the File
                        Log.e("WebViewSetting", "Unable to create Image File", ex);
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        System.out.println(mCameraPhotoPath);
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");
                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                    System.out.println(takePictureIntent);
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "选择图片");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
                return true;
            }
        });
        webView.loadUrl(url);
    }
    private void loadJs() {
        UserTokenData userTokenData = new UserTokenData();
        String msg = null;
        String useId = AppSessionEngine.getUseId();
        if (!TextUtils.isEmpty(useId)) {
            userTokenData.useId = useId;
            userTokenData.userId = userTokenData.useId;
            userTokenData.phone = AppSessionEngine.getMobile();
            String usetInfoJson = new Gson().toJson(userTokenData);
            msg = "rn_userInfo=" + usetInfoJson;
        }
        webView.addJavascriptInterface(new JsMessage(), BRIDGE_NAME);
        LogUtils.e("msg:", msg);
        if (!TextUtils.isEmpty(msg)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webView.evaluateJavascript(msg, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        LogUtils.e("value:", value);
                    }
                });
            } else {
                webView.loadUrl("javascript:" + msg);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(jsReg, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    LogUtils.e("value----:", value);
                }
            });
            /**
             * 注入声明对象
             */
            webView.evaluateJavascript("window.WebViewBridge={}", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    LogUtils.e("value:", value);
                }
            });
            webView.evaluateJavascript("window.WebViewBridge.useId=" + useId, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    LogUtils.e("value:", value);
                }
            }); webView.evaluateJavascript("window:jumpHomepage()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    LogUtils.e("value哈哈哈:", value);
                }
            });
        } else {
            webView.loadUrl("javascript:" + jsReg);
            webView.loadUrl("javascript:" + "window.WebViewBridge={}");
            webView.loadUrl("javascript:" + "window.WebViewBridge.useId=" + useId);
        }
    }
    class JsMessage {
        @JavascriptInterface
        public void postMessage(String message) {
            LogUtils.e("postMessage:" + message);
            if (!TextUtils.isEmpty(message)) {

            }
        }
    }
    @SuppressLint("SdCardPath")
    private File createImageFile() {
        //mCameraPhotoPath="/mnt/sdcard/tmp.png";
        File file = new File(Environment.getExternalStorageDirectory() + "/", "tmp.png");
        mCameraPhotoPath = file.getAbsolutePath();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

}
