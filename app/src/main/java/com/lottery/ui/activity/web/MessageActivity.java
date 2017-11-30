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
import com.lottery.ui.activity.WebViewActivity;
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
 * @time: 2017/11/30 17:24
 * @description:
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.activity_all_web_view)
    WebView webview;
    private String URL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_all);
        progressShow();
        URL = getIntent().getStringExtra("url");
        initToolbar("预测资讯", this, false);
        initView();
    }


    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
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
            Intent intent = new Intent(getContext(), MessageInfoActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("title", "资讯");
            startActivity(intent);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressCancel();
        }
    };

    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
        }
    };

    @Override
    @OnClick({R.id.action_bar_left_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_bar_left_iv:
                if (webview.canGoBack()) {
                    webview.goBack();
                }
                break;
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


    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webview.stopLoading();
        webview.clearHistory();
        webview.destroy();
    }
}
