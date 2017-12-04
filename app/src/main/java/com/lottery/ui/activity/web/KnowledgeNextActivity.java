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
 * @time: 2017/12/4 11:01
 * @description:
 */
public class KnowledgeNextActivity extends BaseWebViewActivity {
    private String intent_url;
    private String intent_title;

    private boolean fist=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getIntent().getStringExtra(KnowledgeActivity.KNOWLEDGE_URL)) {
            intent_url = getIntent().getStringExtra(KnowledgeActivity.KNOWLEDGE_URL);
            intent_title = getIntent().getStringExtra(KnowledgeActivity.KNOWLEDGE_TITLE);
        }
        initToolbar(intent_title, this, true);
        initWebView(intent_url, client);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (fist){
                Intent mIntent = new Intent(KnowledgeNextActivity.this, KnowledgeNextActivity.class);
                mIntent.putExtra(KnowledgeActivity.KNOWLEDGE_URL, url);
                mIntent.putExtra(KnowledgeActivity.KNOWLEDGE_TITLE, intent_title);
                startActivity(mIntent);
            }else {
                view.loadUrl(url);
                fist=true;
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            getWebView().setVisibility(View.VISIBLE);
            progressCancel();

        }
    };





}
