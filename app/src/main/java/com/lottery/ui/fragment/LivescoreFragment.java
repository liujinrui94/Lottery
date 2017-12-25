package com.lottery.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lottery.R;
import com.lottery.base.BaseFragment;
import com.lottery.ui.activity.SportteryActivity;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/21 14:29
 * @description:
 */
public class LivescoreFragment extends BaseFragment {

    private View rootView = null;


    @BindView(R.id.fragment_nationwide_web_view)
    WebView webView;

    private String javascript = "javascript:function hideOther() {"
            + "if(document.getElementsByClassName('headbar')[0] != null) {document.getElementsByClassName('headbar')[0].style.display = 'none';}"
            + "if(document.getElementById('yc') != null) {document.getElementById('yc').style.display = 'none';}"
            + "if(document.getElementsByClassName('nav_table')[0] != null) {document.getElementsByClassName('nav_table')[0].style.display = 'none';}"
            + "if(document.getElementsByClassName('footer')[0] != null) {document.getElementsByClassName('footer')[0].style.display = 'none';}"
            + "}";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_nationwide, container, false);
        ButterKnife.bind(this, rootView);
        progressShow();
        initView();
        return rootView;
    }

    private void initView() {
        webView.loadUrl("http://m.cai8.cn/");
        webView.setVisibility(View.GONE);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setWebViewClient(client);
        webView.setWebChromeClient(chromeClient);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//            boolean fist = sharedPreferences.getBoolean(Common.NationWide_FITS, false);
//            if (fist) {
                Intent mIntent = new Intent(getContext(), SportteryActivity.class);
                mIntent.putExtra("url", url);
                mIntent.putExtra("title", "往期开奖");
                startActivity(mIntent);
//            } else {
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean(Common.NationWide_FITS, true);
//                editor.apply();
//                view.loadUrl(url);
//            }
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
