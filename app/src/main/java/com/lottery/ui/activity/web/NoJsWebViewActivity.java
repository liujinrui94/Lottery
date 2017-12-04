package com.lottery.ui.activity.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.lottery.R;
import com.lottery.base.BaseWebViewActivity;
import com.lottery.constant.Constant;
import com.lottery.ui.activity.WebViewActivity;
import com.lottery.ui.activity.ZstActivity;
import com.lottery.utils.AppLogger;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/2 17:01
 * @description:
 */
public class NoJsWebViewActivity extends BaseWebViewActivity {

    private String url = "http://live.m.500.com/center/football?from=app_bet";
    private String title = "赛事";
    private boolean back = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressShow();
        if (null != getIntent().getStringExtra("NoJsUrl")) {
            url = getIntent().getStringExtra("NoJsUrl");
            title = getIntent().getStringExtra("NoJsTitle");
            back = true;
        }
        initToolbar(title, this, back);
        initWebView(url, client, chromeClient);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getWebView().canGoBack()) {
                    getWebView().goBack();
                }
            }
        });
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent mIntent = new Intent(NoJsWebViewActivity.this, NoJsWebViewActivity.class);
            mIntent.putExtra("NoJsUrl", url);
            mIntent.putExtra("NoJsTitle", "赛事");
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

    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int i) {

            super.onProgressChanged(webView, i);
        }
    };
}
