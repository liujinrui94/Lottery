package com.lottery.ui.activity.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.lottery.R;
import com.lottery.base.BaseActivity;
import com.lottery.ui.activity.FootballActivity;
import com.lottery.ui.activity.OfficalNetActivity;
import com.lottery.utils.AppLogger;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/11/30 13:08
 * @description:
 */
public class RunlotteryActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.activity_all_web_view)
    WebView webview;
    private String URL;
    private String title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_all);
        progressShow();
        URL = getIntent().getStringExtra("url");
        if (null != getIntent().getStringExtra("title")) {
            title = getIntent().getStringExtra("title");
        }
        initToolbar(title, this, true);
        initView();
    }


    protected void initView() {
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview.canGoBack()) {
                    webview.goBack();
                }else {
                    finish();
                }
            }
        });
        webview.loadUrl(URL);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        //滚动条
        webview.setHorizontalScrollBarEnabled(false);
        webview.setVerticalScrollBarEnabled(false);

        webview.setWebViewClient(client);
        webview.setWebChromeClient(chromeClient);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.setDownloadListener(new MyWebViewDownLoadListener());
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressShow();
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webview.setVisibility(View.GONE);
            //编写 javaScript方法
            String javascript = "javascript:function hideOther() {"
                    + "if(document.getElementsByClassName('vMod_topBar')[0] != null) {document.getElementsByClassName('vMod_topBar')[0].style.display = 'none';}" +
                    "if(document.getElementsByClassName('vFooter2')[0] != null) {document.getElementsByClassName('vFooter2')[0].style.display = 'none';}" +
                    "if(document.getElementsByClassName('vMod_functionBar')[0] != null) {document.getElementsByClassName('vMod_functionBar')[0].style.display = 'none';}" +
                    "if(document.getElementsByClassName('vMod_tapBar')[0] != null) {document.getElementsByClassName('vMod_tapBar')[0].style.display = 'none';}" +
                    "if(document.getElementsByClassName('vMod_tabBar')[0] != null) {document.getElementsByClassName('vMod_tabBar')[0].style.display = 'none';}"+
                    "if(document.getElementsByClassName('vMod_header_more')[0] != null) {document.getElementsByClassName('vMod_header_more')[0].style.display = 'none';}"+
            "}";
            //创建方法
            view.loadUrl(javascript);
            view.loadUrl("javascript:hideOther();");
            webview.setVisibility(View.VISIBLE);
            progressCancel();
        }
    };

    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            if (i != 100) {
                webview.setVisibility(View.GONE);
            }
            super.onProgressChanged(webView, i);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }

    }

    private class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        webview.stopLoading();
        webview.destroy();
    }
}
