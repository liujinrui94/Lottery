package com.lottery.ui.activity.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.lottery.ui.activity.KnowledgeActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/7 14:34
 * @description:
 */
public class ZucaiNextActivity extends BaseWebViewActivity {

    private String title = "文字直播";
    private String javascript = "javascript:function hideOther() {"
            + "if(document.getElementsByClassName('clearfix')[0]!= null) {document.getElementsByClassName('clearfix')[0].style.display = 'none';}"
            + "if(document.getElementById('BAIDU_SSP__wrapper_u2891404_0')!= null) {document.getElementById('BAIDU_SSP__wrapper_u2891404_0').style.display = 'none';}"
            + "if(document.getElementsByClassName('ok_adv')[0] != null) {document.getElementsByClassName('ok_adv')[0].style.display = 'none';}"
            +"}";

    private String intent_url;
    private  String intent_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getIntent().getStringExtra(ZuCaiActivity.ZuCai_URL)) {
            intent_url = getIntent().getStringExtra(ZuCaiActivity.ZuCai_URL).replace("history","form");
        }
        initToolbar(title, this, false);
        initWebView(intent_url, client);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
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

}
