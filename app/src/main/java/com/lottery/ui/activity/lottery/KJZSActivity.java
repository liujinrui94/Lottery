package com.lottery.ui.activity.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.lottery.constant.Constant;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/13 16:13
 * @description:
 */
public class KJZSActivity extends BaseWebViewActivity {


    private String url = Constant.DALETOU;
    private String title = "资讯";
    private Boolean back=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getStringExtra("url") != null) {
            url = getIntent().getStringExtra("url");
            title = getIntent().getStringExtra("title");
            back=true;
        }
        initToolbar(title, this, back);
        initWebView(url, client);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent mIntent = new Intent(KJZSActivity.this, KJZSActivity.class);
            mIntent.putExtra("url", url);
            mIntent.putExtra("title", "资讯详情");
            startActivity(mIntent);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            getWebView().setVisibility(View.VISIBLE);
            progressCancel();

        }
    };
}
