package com.lottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.lottery.ui.activity.web.KnowledgeNextActivity;
import com.lottery.ui.activity.web.NoJsWebViewActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/4 10:53
 * @description:
 */
public class KnowledgeActivity extends BaseWebViewActivity {

    private String url = "http://m.500.com/info/zhishi/";
    private String title = "彩票知识库";
    public static final String KNOWLEDGE_URL ="Knowledge_url";
    public static final String KNOWLEDGE_TITLE ="Knowledge_title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(title, this, false);
        initWebView(url, client);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent mIntent = new Intent(KnowledgeActivity.this, KnowledgeNextActivity.class);
            mIntent.putExtra("Knowledge", url);
            mIntent.putExtra("NoJsTitle", "赛事");
            startActivity(mIntent);
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
