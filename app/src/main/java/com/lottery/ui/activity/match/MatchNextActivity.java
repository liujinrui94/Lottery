package com.lottery.ui.activity.match;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.lottery.constant.Constant;
import com.lottery.ui.activity.web.ZuCaiActivity;
import com.lottery.ui.activity.web.ZucaiNextActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/9 19:35
 * @description:
 */
public class MatchNextActivity extends BaseWebViewActivity {



    private String url = Constant.LIVE360;
    private String title = "赛事";
    private String intent_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getIntent().getStringExtra(MatchActivity.MATCH_URL)) {
            intent_url = getIntent().getStringExtra(MatchActivity.MATCH_URL);
        }
        initToolbar(title, this, false);
        initWebView(url, client);
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
            getWebView().setVisibility(View.VISIBLE);
            progressCancel();

        }
    };

}
