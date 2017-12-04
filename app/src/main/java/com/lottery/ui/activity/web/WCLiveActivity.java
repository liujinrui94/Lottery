package com.lottery.ui.activity.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.lottery.ui.activity.WebViewActivity;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/1 15:30
 * @description:
 */
public class WCLiveActivity extends BaseWebViewActivity {

    private String url = "https://vipc.cn/trends?fr=shareToWeixinTimeline";
    private String title = "走势图";
    private boolean back = false;
    private String javascript = "javascript:function hideOther() {"
            + "if(null!= document.getElementsByClassName('vMod_topBar')) {document.getElementsByClassName('vMod_topBar')[0].style.display = 'none';}\n" +
            "if(null!= document.getElementsByClassName('vFooter2')) {document.getElementsByClassName('vFooter2')[0].style.display = 'none';}\n" +
            "if(null!= document.getElementsByClassName('vMod_topBar')) {document.getElementsByClassName('vMod_topBar')[0].style.display = 'none';}\n" +
            "}";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getIntent().getStringExtra("url")) {
            url = getIntent().getStringExtra("url");
            title = getIntent().getStringExtra("title");
            back = true;
        }
        initToolbar(title, WCLiveActivity.this, back);
        initWebView(url, client);
    }


    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent mIntent = new Intent(getContext(), WebViewActivity.class);
            mIntent.putExtra("url", url);
            mIntent.putExtra("title", "资讯详情");
            startActivity(mIntent);
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
