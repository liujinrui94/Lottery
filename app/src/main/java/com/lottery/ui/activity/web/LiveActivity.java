package com.lottery.ui.activity.web;

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
 * @time: 2017/12/7 12:34
 * @description:
 */
public class LiveActivity extends BaseWebViewActivity {

    private String url = "http://live.m.500.com/home/lq/jclq/cur?render=local";
    private String title = "赛事";
    private boolean back = true;

    private String javascript = "javascript:function hideOther() {"
            + "if(document.getElementsByClassName('item-tab')[0] != null) {document.getElementsByClassName('item-tab')[0].style.display = 'none';}" +
            "if(document.getElementsByClassName('head-zone')[0] != null) {document.getElementsByClassName('head-zone')[0].style.display = 'none';}" +
            "}";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getIntent().getStringExtra("NoJsUrl")) {
            url = getIntent().getStringExtra("NoJsUrl");
            title = getIntent().getStringExtra("NoJsTitle");
            back = true;
        }
        initToolbar(title, this, back);
        initWebView(url, client);
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
//            Intent mIntent = new Intent(LiveActivity.this, NoJsWebViewActivity.class);
//            mIntent.putExtra("NoJsUrl", url);
//            mIntent.putExtra("NoJsTitle", "赛事");
//            startActivity(mIntent);
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
}
