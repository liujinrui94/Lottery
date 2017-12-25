package com.lottery.ui.NewsInfoActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lottery.R;
import com.lottery.base.BaseActivity;
import com.lottery.utils.NetUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/23 21:08
 * @description:
 */
public class NewsInfoActivity extends BaseActivity {

    private String title;
    @BindView(R.id.activity_base_web_view)
    WebView webView;

    private String content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = getIntent().getStringExtra("content");
        setContentView(R.layout.activity_base_web_view);
        initWebView();
    }
    protected void initWebView() {
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setWebViewClient(client);
        webView.setWebChromeClient(chromeClient);
        if (NetUtil.isNetworkAvailable(getApplicationContext())) {
            //有网络连接，设置默认缓存模式
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //无网络连接，设置本地缓存模式
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }
    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
        }
    };

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressCancel();

        }
    };
}
