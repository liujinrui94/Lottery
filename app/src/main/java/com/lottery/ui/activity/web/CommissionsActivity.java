package com.lottery.ui.activity.web;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lottery.R;
import com.lottery.base.BaseActivity;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/1 11:22
 * @description:
 */
public class CommissionsActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.activity_run_web_view)
    WebView webview;
    private String URL;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_lottery);
        progressShow();
        URL = getIntent().getStringExtra("url");
        if (null != getIntent().getStringExtra("title")) {
            title = getIntent().getStringExtra("title");
        }
        initToolbar(title, this, true);
        initView();
    }


    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview.canGoBack()) {
                    webview.goBack();
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

            //编写 javaScript方法
            String javascript = "javascript:function hideOther() {"
                    + "if(document.getElementsByClassName('vMod_topBar')[0] != null) {document.getElementsByClassName('vMod_topBar')[0].style.display = 'none';}" +
                    "if(document.getElementsByClassName('vFooter2')[0] != null) {document.getElementsByClassName('vFooter2')[0].style.display = 'none';}" +
                    "if(document.getElementsByClassName('vMod_functionBar')[0] != null) {document.getElementsByClassName('vMod_functionBar')[0].style.display = 'none';}" +
                    "if(document.getElementsByClassName('vMod_tapBar')[0] != null) {document.getElementsByClassName('vMod_tapBar')[0].style.display = 'none';}" +
                    "if(document.getElementsByClassName('vMod_tabBar')[0] != null) {document.getElementsByClassName('vMod_tabBar')[0].style.display = 'none';}" +
                    "if(document.getElementsByClassName('vMod_header_more')[0] != null) {document.getElementsByClassName('vMod_header_more')[0].style.display = 'none';}" +
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
            } else {
                webview.setVisibility(View.VISIBLE);
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        webview.stopLoading();
        webview.destroy();
    }
}
