package com.lottery.ui.activity.explain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/13 21:03
 * @description:
 */
public class NoJsIntentActivity extends BaseWebViewActivity {


    //    private String url = "https://statics.xiaobaicp.com/page/found/jcxy/index.html";
    private String url = "http://m.cp.360.cn/live/jclq";
    private String title = "";
    private Boolean back = false;

    public static final String NoJsIntent_URL = "NoJsIntent_url";
    public static final String NoJsIntent_TITLE = "NoJsIntent_title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getIntent().getStringExtra(NoJsIntentActivity.NoJsIntent_URL)) {
            url = getIntent().getStringExtra(NoJsIntentActivity.NoJsIntent_URL);
            title = getIntent().getStringExtra(NoJsIntentActivity.NoJsIntent_TITLE);
            back = true;
        }
        getToolbar().setVisibility(View.GONE);
        initToolbar(title, this, back);
        initWebView(url, client);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent mIntent = new Intent(NoJsIntentActivity.this, NoJsIntentActivity.class);
            mIntent.putExtra(NoJsIntent_URL, url);
            mIntent.putExtra(NoJsIntent_TITLE, "详情");
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
