package com.lottery.ui.activity.match;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.lottery.constant.Constant;
import com.lottery.ui.activity.web.ZucaiNextActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/9 19:31
 * @description:
 */
public class MatchActivity extends BaseWebViewActivity {


    private String url = Constant.LIVE360;
    private String title = "足球赛事";
    public static final String MATCH_URL = "MATCH_url";
    public static final String MATCH_TITLE = "MATCH_title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(title, this, false);
        initWebView(url, client);
        getToolbar().setVisibility(View.GONE);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Intent mIntent = new Intent(MatchActivity.this, MatchNextActivity.class);
//            mIntent.putExtra(MATCH_URL, url);
//            startActivity(mIntent);
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
