package com.lottery.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.lottery.R;
import static android.R.attr.animation;
import java.io.File;

public class GlideUtils {
    private static GlideUtils instance = new GlideUtils();

    public static GlideUtils getInstance() {
        return instance;
    }

    public void loadNetImage(String url, ImageView imageView) {
        loadNetImage(url, imageView, R.mipmap.ic_load_default);
    }

    private void loadNetImage(String url, ImageView imageView, int defaultImg) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(defaultImg)
                .error(defaultImg)
                .animate(animation)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }
    public void loadNetImageNoDefaultImg(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .animate(animation)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }






    //加载本地图片
    public void loadLocalImage(int resId, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(resId)
                .placeholder(R.mipmap.ic_load_default)
                .error(R.mipmap.ic_load_default)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }

    //加载本地图片
    public void loadLocalImage(File file, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(file)
                .diskCacheStrategy( DiskCacheStrategy.NONE )//禁用磁盘缓存
                .skipMemoryCache(true)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }



}