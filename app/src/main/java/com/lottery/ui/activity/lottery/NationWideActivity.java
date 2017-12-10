package com.lottery.ui.activity.lottery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.lottery.constant.Common;
import com.lottery.constant.Constant;
import com.lottery.ui.activity.web.RunlotteryActivity;
import com.lottery.utils.AppLogger;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/9 21:54
 * @description:
 */
public class NationWideActivity extends BaseWebViewActivity {


    private String title = "开奖大厅";
    public static final String MATCH_URL = "MATCH_url";
    public static final String MATCH_TITLE = "MATCH_title";

    private String url = "https://vipc.cn/results?in=home_tools_0#hot";
    String javascript = "javascript:function hideOther() {"
            + "if(null!= document.getElementsByClassName('vFooter2')) {document.getElementsByClassName('vFooter2')[0].style.display = 'none';}\n" +
            "if(null!= document.getElementsByClassName('vMod_topBar2')) {document.getElementsByClassName('vMod_topBar2')[0].style.display = 'none';}\n" +
            "if(null!= document.getElementsByClassName('vMod_navScrollBar')) {document.getElementsByClassName('vMod_navScrollBar')[0].style.display = 'none';}\n" +
            "}";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(title, this, false);
        initWebView(url, client);

    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            boolean fist = sharedPreferences.getBoolean(Common.NationWide_FITS, false);
            if (fist) {
                Intent mIntent = new Intent(getContext(), RunlotteryActivity.class);
                mIntent.putExtra("url", url);
                mIntent.putExtra("title", "资讯详情");
                startActivity(mIntent);
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Common.NationWide_FITS, true);
                editor.apply();
                view.loadUrl(url);
                getWebView().setVisibility(View.GONE);
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
