package com.lottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.lottery.constant.Constant;
import com.lottery.ui.activity.web.ZouShiTuActivity;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/11/30 17:14
 * @description: 彩票走势图
 */
public class ZstActivity extends BaseWebViewActivity {


    private String url = "http://m.500.com/datachart/";
    private String title = "走势图";
    private boolean back = false;
    private String javascript = "javascript:function hideOther() {"
            + "if(null!= document.getElementById('uiHead')) {document.getElementById('uiHead').style.display = 'none';}\n" +
            "if(null!= document.getElementsByClassName('adds-zone')[0]) {document.getElementsByClassName('adds-zone')[0].style.display = 'none';}\n"+
            "}";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressShow();
        if (null != getIntent().getStringExtra("url")) {
            url = getIntent().getStringExtra("url");
            title = getIntent().getStringExtra("title");
            back = true;
        }
        initToolbar(title, this, back);
        initWebView(url, client);
    }


    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent mIntent = new Intent(getContext(), ZstActivity.class);
            mIntent.putExtra("url", url);
            mIntent.putExtra("title", "走势图详情");
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
