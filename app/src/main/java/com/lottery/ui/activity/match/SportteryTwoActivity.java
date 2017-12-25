package com.lottery.ui.activity.match;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class SportteryTwoActivity extends BaseWebViewActivity {


//    private String url = "http://www.sporttery.cn/wap/sz/";
    private String javascript = "javascript:function hideOther() {"
            + "if(document.getElementsByClassName('header')[0] != null) {document.getElementsByClassName('header')[0].style.display = 'none';}"
            + "if(document.getElementsByClassName('footer')[0] != null) {document.getElementsByClassName('footer')[0].style.display = 'none';}"
        + "if(document.getElementsByClassName('headbar')[0] != null) {document.getElementsByClassName('headbar')[0].style.display = 'none';}"
        + "}";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(getIntent().getStringExtra("title"), this, true);
        initWebView(getIntent().getStringExtra("url"), client);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("basketball/info")) {
                return true;
            }
            progressShow();
            getWebView().setVisibility(View.GONE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.loadUrl(javascript);
            view.loadUrl("javascript:hideOther();");
            getWebView().setVisibility(View.VISIBLE);
            progressCancel();

        }
    };

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && getWebView().canGoBack()) {
            getWebView().goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}