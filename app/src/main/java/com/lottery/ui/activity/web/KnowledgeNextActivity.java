package com.lottery.ui.activity.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.lottery.ui.activity.KnowledgeActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/4 11:01
 * @description:
 */
public class KnowledgeNextActivity extends BaseWebViewActivity {
    private String intent_url;
    private String intent_title;

    private String javascript = "javascript:function hideOther() {"
            + "if(document.getElementsByClassName('ui-head-r')[0] != null) {document.getElementsByClassName('ui-head-r')[0].style.display = 'none';}"
            + "if(document.getElementsByClassName('index-top')[0] != null) {document.getElementsByClassName('index-top')[0].style.display = 'none';}"
            + "if(document.getElementsByClassName('load-more')[0] != null) {document.getElementsByClassName('load-more')[0].style.display = 'none';}"
            +"}";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getIntent().getStringExtra(KnowledgeActivity.KNOWLEDGE_URL)) {
            intent_url = getIntent().getStringExtra(KnowledgeActivity.KNOWLEDGE_URL);
            intent_title = getIntent().getStringExtra(KnowledgeActivity.KNOWLEDGE_TITLE);
        }
        getToolbar().setVisibility(View.GONE);
        initToolbar(intent_title, this, true);
        initWebView(intent_url, client);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
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
