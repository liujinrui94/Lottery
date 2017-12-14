package com.lottery.ui.activity.explain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.lottery.ui.activity.KnowledgeActivity;
import com.lottery.ui.activity.web.KnowledgeNextActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/13 16:58
 * @description:
 */
public class FLActivity extends BaseWebViewActivity {
    private String url = "http://5.9188.com/jcbf/?flag=wks";
    private String title = "赛事中心";
    private Boolean back=false;

    public static final String KNOWLEDGE_URL ="FL_url";
    public static final String KNOWLEDGE_TITLE ="FL_title";
    private String javascript = "javascript:function hideOther() {"
            + "if(document.getElementsByClassName('fixed2')[0] != null) {document.getElementsByClassName('fixed2')[0].style.display = 'none';}"
            + "if(document.getElementsByClassName('tzHeader')[0] != null) {document.getElementsByClassName('tzHeader')[0].style.display = 'none';}"
            +"}";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getIntent().getStringExtra(FLActivity.KNOWLEDGE_URL)) {
            url = getIntent().getStringExtra(FLActivity.KNOWLEDGE_URL);
            title = getIntent().getStringExtra(FLActivity.KNOWLEDGE_TITLE);
            back=true;
        }
        initToolbar(title, this, back);
        initWebView(url, client);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent mIntent = new Intent(FLActivity.this, FLActivity.class);
            mIntent.putExtra(KNOWLEDGE_URL, url);
            mIntent.putExtra(KNOWLEDGE_TITLE, "详情");
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
