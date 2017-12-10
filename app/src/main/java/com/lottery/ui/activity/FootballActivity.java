package com.lottery.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.lottery.R;
import com.lottery.base.BaseActivity;
import com.lottery.constant.Common;
import com.lottery.constant.Constant;
import com.lottery.ui.activity.web.RunlotteryActivity;
import com.lottery.utils.AppLogger;
import com.lottery.utils.ToastUtils;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/11/29 8:43
 * @description:
 */
public class FootballActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.activity_all_web_view)
    WebView webview;
    private String URL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_all);
        URL = getIntent().getStringExtra("url");
        initToolbar("开奖资讯", this, false);
        initView();
    }


    protected void initView() {
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview.canGoBack()) {
                    webview.goBack();
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webview.loadUrl(URL);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        //滚动条
        webview.setWebViewClient(client);
        webview.setWebChromeClient(chromeClient);
        webview.setDownloadListener(new MyWebViewDownLoadListener());
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            boolean fist = sharedPreferences.getBoolean(Common.FINISH_LOGIN, false);
            if (fist) {
                Intent mIntent = new Intent(FootballActivity.this, RunlotteryActivity.class);
                mIntent.putExtra("url", url);
                mIntent.putExtra("title", "资讯详情");
                startActivity(mIntent);
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Common.FINISH_LOGIN, true);
                editor.apply();
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //编写 javaScript方法
            String javascript = "javascript:function hideOther() {"
                    + "if(null!=document.getElementsByClassName('vMod_topBar')[0]){document.getElementsByClassName('vMod_topBar')[0].style.display = 'none';}" +
                    "if(null!=document.getElementsByClassName('vFooter2')[0]){document.getElementsByClassName('vFooter2')[0].style.display = 'none';}" +
                    "if(null!=document.getElementsByClassName('vLottery_info_buttons')[0]){document.getElementsByClassName('vLottery_info_buttons')[0].style.display = 'none';}" +
                    "}";
            //创建方法
            view.loadUrl(javascript);
            view.loadUrl("javascript:hideOther();");
            webview.setVisibility(View.VISIBLE);
            progressCancel();

        }
    };

    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            webview.setVisibility(View.GONE);
            progressShow();
            super.onProgressChanged(webView, i);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_bar_left_iv:

                break;
            default:
                break;
        }

    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,

                                    long contentLength) {
            Uri uri = Uri.parse(url);

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            startActivity(intent);

        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webview.stopLoading();
        webview.destroy();
    }
}
