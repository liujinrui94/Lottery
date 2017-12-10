package com.lottery.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lottery.R;
import com.lottery.base.BaseFragment;
import com.lottery.constant.Common;
import com.lottery.ui.activity.FootballActivity;
import com.lottery.ui.activity.WebViewActivity;
import com.lottery.ui.activity.web.RunlotteryActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/1 13:39
 * @description:
 */
public class LocalLotteriesFragment extends BaseFragment {

    private View rootView = null;


    @BindView(R.id.fragment_lotteries_web_view)
    WebView webView;

    private String url = "https://vipc.cn/results?in=home_tools_0#other";
    String javascript = "javascript:function hideOther() {"
            + "if(null!= document.getElementsByClassName('vFooter2')) {document.getElementsByClassName('vFooter2')[0].style.display = 'none';}\n" +
            "if(null!= document.getElementsByClassName('vMod_topBar2')) {document.getElementsByClassName('vMod_topBar2')[0].style.display = 'none';}\n" +
            "if(null!= document.getElementsByClassName('vMod_navScrollBar')) {document.getElementsByClassName('vMod_navScrollBar')[0].style.display = 'none';}\n" +
            "}";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_lotteries, container, false);
        ButterKnife.bind(this, rootView);
        progressShow();
        initView();
        return rootView;
    }

    private void initView() {
        webView.loadUrl(url);
        webView.setVisibility(View.GONE);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        //滚动条
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setWebViewClient(client);
        webView.setWebChromeClient(chromeClient);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            boolean fist = sharedPreferences.getBoolean("LocalLotteries", false);
            if (fist) {
                Intent mIntent = new Intent(getContext(), RunlotteryActivity.class);
                mIntent.putExtra("url", url);
                mIntent.putExtra("title", "资讯详情");
                startActivity(mIntent);
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("LocalLotteries", true);
                editor.apply();
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.loadUrl(javascript);
            view.loadUrl("javascript:hideOther();");
            webView.setVisibility(View.VISIBLE);
            progressCancel();

        }
    };
    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
        }
    };
}
