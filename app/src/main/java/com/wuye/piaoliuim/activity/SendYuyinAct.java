package com.wuye.piaoliuim.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.chuange.basemodule.BaseActivity;
import com.chuange.basemodule.utils.ActivityTaskManager;
import com.chuange.basemodule.utils.ToastUtil;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.WuyeApplicatione;
import com.wuye.piaoliuim.bean.UpFileData;
import com.wuye.piaoliuim.config.UrlConstant;
import com.wuye.piaoliuim.http.RequestListener;
import com.wuye.piaoliuim.http.RequestManager;
import com.wuye.piaoliuim.utils.GsonUtil;
import com.wuye.piaoliuim.utils.MediaPlayerHolder;
import com.wuye.piaoliuim.utils.PlayUtils;
import com.wuye.piaoliuim.utils.PlaybackInfoListener;
import com.wuye.piaoliuim.utils.RoundImageView;
import com.wuye.piaoliuim.utils.WhewView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.RecordConfig;
import com.zlw.main.recorderlib.recorder.RecordHelper;
import com.zlw.main.recorderlib.recorder.listener.RecordFftDataListener;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;
import com.zlw.main.recorderlib.recorder.listener.RecordSoundSizeListener;
import com.zlw.main.recorderlib.recorder.listener.RecordStateListener;
import com.zlw.main.recorderlib.utils.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;

import static com.wuye.piaoliuim.utils.MediaPlayerHolder.PLAYSTATUS3;

/**
 * @ClassName SendYuyinAct
 * @Description
 * @Author VillageChief
 * @Date 2019/12/26 11:01
 */
public class SendYuyinAct extends BaseActivity implements PlaybackInfoListener {



    MediaPlayerHolder mediaPlayerIngHolder;
    @BindView(R.id.im_ztandks)
    ImageView imZtandks;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.start)
    TextView start;
    @BindView(R.id.end)
    TextView end;
    @BindView(R.id.rl_musci)
    RelativeLayout rlMusci;


    @BindView(R.id.restyuyim)
    TextView restyuyim;
    boolean isPlayAndZant = false;

    int maxVideo;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.wv)
    WhewView wv;
    @BindView(R.id.my_photo)
    RoundImageView myPhoto;
    private ArrayList<File> upViedoList = new ArrayList<>(); //上传的图片源文件
    private static final int GET_RECODE_AUDIO = 1;
    private static String[] PERMISSION_AUDIO = {
            Manifest.permission.RECORD_AUDIO
    };
    final RecordManager recordManager = RecordManager.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendyuyin);
        ButterKnife.bind(this);
        verifyAudioPermissions(this);
        AndPermission.with(this)
                .runtime()
                .permission(new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.RECORD_AUDIO})
                .start();
        initRecord();
        mediaPlayerIngHolder = new MediaPlayerHolder();
        mediaPlayerIngHolder.setmPlaybackInfoListener(this);//设置监听
        myPhoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
 //                        rlMusci.setVisibility(View.GONE);
//                        restyuyim.setVisibility(View.GONE);
//                        btSubmit.setVisibility(View.GONE);
                        wv.start();
                        recordManager.start();

                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                        wv.chongzhi();
                         rlMusci.setVisibility(View.VISIBLE);
                        btSubmit.setVisibility(View.VISIBLE);
                        restyuyim.setVisibility(View.VISIBLE);
                        recordManager.stop();

                        break;
                    default:
                        break;
                }
                return true;

            }
        });

    }


    private void initRecord() {
         recordManager.changeFormat(RecordConfig.RecordFormat.MP3);
        String recordDir = String.format(Locale.getDefault(), "%s/Record/com.piaoliu.main/",
                Environment.getExternalStorageDirectory().getAbsolutePath());
        recordManager.init(WuyeApplicatione.etdApplication, false);
        recordManager.changeRecordDir(recordDir);
         initRecordEvent();
    }

    private void initRecordEvent() {
        recordManager.setRecordStateListener(new RecordStateListener() {
            @Override
            public void onStateChange(RecordHelper.RecordState state) {

                switch (state) {
                    case PAUSE:
//                        tvState.setText("暂停中");
                        break;
                    case IDLE:
//                        tvState.setText("空闲中");
                        break;
                    case RECORDING:
//                        tvState.setText("录音中");
                        break;
                    case STOP:
//                        tvState.setText("停止");
                        break;
                    case FINISH:
//                        tvState.setText("录音结束");
//                        tvSoundSize.setText("---");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(String error) {
                Logger.i("iii", "onError %s", error);
            }
        });
        recordManager.setRecordSoundSizeListener(new RecordSoundSizeListener() {
            @Override
            public void onSoundSize(int soundSize) {
                Log.i("ppppppp", String.format(Locale.getDefault(), "声音大小：%s db", soundSize));
            }
        });
        recordManager.setRecordResultListener(new RecordResultListener() {
            @Override
            public void onResult(File result) {
                upViedoList.clear();
                upViedoList.add(new File(result.getAbsolutePath()));
                mediaPlayerIngHolder.loadMedia(result.getAbsolutePath());
                start.setText(PlayUtils.getAllTime(0));
                end.setText(PlayUtils.getAllTime(mediaPlayerIngHolder.getVido()));
            }
        });
        recordManager.setRecordFftDataListener(new RecordFftDataListener() {
            @Override
            public void onFftData(byte[] data) {
//                audioView.setWaveData(data);
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
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayerIngHolder.release();
        mediaPlayerIngHolder = null;
    }


    @Override
    public void onDurationChanged(int duration) {
        maxVideo = duration;
        seekbar.setMax(duration);
    }

    @Override
    public void onPositionChanged(final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (position == 0) {

//                            start.setText(PlayUtils.getAllTime(875));

                        } else {
                            start.setText(PlayUtils.getAllTime(position));
                            end.setText(PlayUtils.getAllTime(maxVideo - position));
                            seekbar.setProgress(position);

                        }
                    }
                });
            }
        }).start();


    }

    @Override
    public void onStateChanged(int state) {
        if (state == PLAYSTATUS3) {
            imZtandks.setImageResource(R.mipmap.ic_ks);
            seekbar.setProgress(0);
            isPlayAndZant = false;
            start.setText(PlayUtils.getAllTime(0));
            end.setText(PlayUtils.getAllTime(mediaPlayerIngHolder.getVido()));

        }

    }

    @Override
    public void onPlaybackCompleted() {

    }

    @OnClick({R.id.im_ztandks, R.id.bt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_ztandks:
                if (isPlayAndZant) {
                    isPlayAndZant = false;
                    mediaPlayerIngHolder.pause();
                    imZtandks.setImageResource(R.mipmap.ic_ks);

                } else {
                    isPlayAndZant = true;
                    mediaPlayerIngHolder.play();
                    imZtandks.setImageResource(R.mipmap.ic_zt);


                }

                break;
            case R.id.bt_submit:
                subMit();

                break;
        }
    }

    public void subMit() {

        MediaType MEDIA_TYPE_PNG = MediaType.parse("audio/mp3");

        HashMap<String, String> params = new HashMap<>();


        RequestManager.getInstance().upYuyin(this, params, upViedoList, UrlConstant.FILEDATA, MEDIA_TYPE_PNG, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {
                //更新成功
                UpFileData upFileData= GsonUtil.getDefaultGson().fromJson(requestEntity,UpFileData.class);
                subMits(upFileData.getFilename());
            }

            @Override
            public void onError(String message) {

            }
        });
    } public void subMits(String filname) {
        HashMap<String, String> params = new HashMap<>();
          params.put(UrlConstant.TYPE,"2");
        params.put(UrlConstant.CONTENT,filname);
        RequestManager.getInstance().publicPostMap(this, params, UrlConstant.SENDTEXT, new RequestListener<String>() {
            @Override
            public void onComplete(String requestEntity) {

                ToastUtil.show(getBaseContext(),"发送成功");
                ActivityTaskManager.getInstance().finishActiviy(SendTxtAndYuyinAct.class);
                finish();
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    /*
     * 申请录音权限*/
    public static void verifyAudioPermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.RECORD_AUDIO);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_AUDIO,
                    GET_RECODE_AUDIO);
        }
    }
}
