package com.wuye.piaoliuim.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.lcw.library.imagepicker.utils.ImageLoader;
import com.wuye.piaoliuim.R;
import com.wuye.piaoliuim.WuyeApplicatione;

/**
 * @ClassName GlideLoader
 * @Description
 * @Author VillageChief
 * @Date 2019/12/18 15:26
 */
public class GlideLoader implements ImageLoader {

    private RequestOptions mOptions = new RequestOptions()
            .centerCrop()
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(R.mipmap.defal_head)
            ;

    private RequestOptions mPreOptions = new RequestOptions()
            .skipMemoryCache(true)
            ;

    @Override
    public void loadImage(ImageView imageView, String imagePath) {
        //小图加载
        Glide.with(imageView.getContext()).load(imagePath).apply(mOptions).into(imageView);
    }

    @Override
    public void loadPreImage(ImageView imageView, String imagePath) {
        //大图加载
        Glide.with(imageView.getContext()).load(imagePath).apply(mPreOptions).into(imageView);

    }

    @Override
    public void clearMemoryCache() {
        //清理缓存
        Glide.get(WuyeApplicatione.etdApplication).clearMemory();
    }
}
