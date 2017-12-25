package com.lottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.lottery.base.BaseWebViewToobarActivity;
import com.lottery.ui.activity.match.SportteryTwoActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/8 22:07
 * @description:
 */
public class SportteryActivity extends BaseWebViewToobarActivity {


    private String url = "http://www.sporttery.cn/wap/";
    //    private String url = "http://www.sporttery.cn/wap/sz/";
    private String javascript = "javascript:function hideOther() {"
            + "if(document.getElementsByClassName('header')[0] != null) {document.getElementsByClassName('header')[0].style.display = 'none';}"
            + "if(document.getElementsByClassName('footer')[0] != null) {document.getElementsByClassName('footer')[0].style.display = 'none';}"
            + "if(document.getElementById('rw_fuzhi') != null) {document.getElementById('rw_fuzhi').style.display = 'none';}"
            + "if(document.getElementById('_2ad46h5h0ht') != null) {document.getElementById('_2ad46h5h0ht').style.display = 'none';}"
            + "if(document.getElementsByClassName('headbar')[0] != null) {document.getElementsByClassName('headbar')[0].style.display = 'none';}"
            + "}";

    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        initToolbar(title, this, true);
        initWebView(url, client);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.equals("https://tools.m.cjcp.com.cn/activity.html")){
                return true;
            }
            Intent mIntent = new Intent(SportteryActivity.this, SportteryTwoActivity.class);
            mIntent.putExtra("url", url);
            mIntent.putExtra("title", "开奖详情");
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
