package com.lottery.ui.activity.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.lottery.constant.Constant;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/16 11:21
 * @description:
 */
public class InformationActivity extends BaseWebViewActivity {




    private String lourl = Constant.ZIXUN;
    private String title = "资讯";
    private Boolean back=false;

    private String javascript = "javascript:function hideOther() {"
            + "if(document.getElementsByClassName('zx-top-head')[0] != null) {document.getElementsByClassName('zx-top-head')[0].style.display = 'none';}"
            + "if(document.getElementsByClassName('footer')[0] != null) {document.getElementsByClassName('footer')[0].style.display = 'none';}"
            + "}";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getStringExtra("url") != null) {
            lourl = getIntent().getStringExtra("url");
            title = getIntent().getStringExtra("title");
            back=true;
        }
        getToolbar().setVisibility(View.GONE);
        initToolbar(title, this, back);
        initWebView(lourl, client);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Intent mIntent = new Intent(InformationActivity.this, InformationNetxActivity.class);
//            mIntent.putExtra("url", url);
//            mIntent.putExtra("title", "资讯详情");
//            startActivity(mIntent);
            getWebView().setVisibility(View.GONE);
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

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && getWebView().canGoBack()) {
            getWebView().goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }




}
