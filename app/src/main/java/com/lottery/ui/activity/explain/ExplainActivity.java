package com.lottery.ui.activity.explain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.lottery.ui.activity.web.ZuCaiActivity;
import com.lottery.ui.activity.web.ZucaiNextActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/9 20:07
 * @description:
 */
public class ExplainActivity extends BaseWebViewActivity {


    private String url = "http://www.sporttery.cn/wap/help/";
    private String title = "帮助中心";
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
        getToolbar().setVisibility(View.GONE);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            getWebView().setVisibility(View.GONE);
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
