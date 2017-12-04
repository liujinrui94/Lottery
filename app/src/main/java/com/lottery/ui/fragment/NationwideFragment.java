package com.lottery.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lottery.R;
import com.lottery.base.BaseFragment;
import com.lottery.ui.activity.web.RunlotteryActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/1 13:43
 * @description:
 */
public class NationwideFragment extends BaseFragment {

    private boolean fist = false;
    private View rootView = null;
    private WebView webView;
    private String url = "https://vipc.cn/results?in=home_tools_0#hot";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_nationwide, container, false);
        webView=rootView.findViewById(R.id.fragment_nationwide_web_view);
        initView();
        return rootView;
    }

    private void initView() {
//        webView.setVisibility(View.GONE);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(client);
        webView.setWebChromeClient(chromeClient);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadUrl(url);
    }
    private WebViewClient client = new WebViewClient() {

        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Intent mIntent = new Intent(getActivity(), RunlotteryActivity.class);
//            mIntent.putExtra("url", url);
//            mIntent.putExtra("title", "资讯详情");
//            startActivity(mIntent);
            view.loadUrl(url);
            return true;
        }


//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            String javascript = "javascript:function hideOther() {"
//                    + "if(null!= document.getElementsByClassName('vFooter2')) {document.getElementsByClassName('vFooter2')[0].style.display = 'none';}\n" +
//                    "if(null!= document.getElementsByClassName('vMod_topBar2')) {document.getElementsByClassName('vMod_topBar2')[0].style.display = 'none';}\n" +
//                    "if(null!= document.getElementsByClassName('vMod_navScrollBar')) {document.getElementsByClassName('vMod_navScrollBar')[0].style.display = 'none';}\n" +
//                    "}";
//            view.loadUrl(javascript);
//            view.loadUrl("javascript:hideOther();");
//            webView.setVisibility(View.VISIBLE);
//        }
    };
    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
        }
    };
}
