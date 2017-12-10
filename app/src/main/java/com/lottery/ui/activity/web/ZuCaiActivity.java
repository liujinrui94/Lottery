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
 * @time: 2017/12/7 14:33
 * @description:
 */
public class ZuCaiActivity extends BaseWebViewActivity {

    private String url = "http://m.okooo.com/zucai/";
    private String title = "足球赛事";
    public static final String ZuCai_URL = "ZuCai_url";
    public static final String ZuCai_TITLE = "ZuCai_title";
    private String javascript = "javascript:function hideOther() {"
            + "if(document.getElementsByClassName('betting')[0] != null) {document.getElementsByClassName('betting')[0].style.display = 'none';}"
            + "if(document.getElementsByClassName('header')[0] != null) {document.getElementsByClassName('header')[0].style.display = 'none';}"
            + "if(document.getElementsByClassName('ok_adv')[0] != null) {document.getElementsByClassName('ok_adv')[0].style.display = 'none';}"
            + "}";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(title, this, false);
        initWebView(url, client);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("LotteryNo")) {
                view.loadUrl(url);
                getWebView().setVisibility(View.GONE);
            } else {
                Intent mIntent = new Intent(ZuCaiActivity.this, ZucaiNextActivity.class);
                mIntent.putExtra(ZuCai_URL, url);
                startActivity(mIntent);
            }
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
