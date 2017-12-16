package com.lottery.ui.activity.match;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.lottery.base.BaseWebViewActivity;
import com.lottery.ui.activity.KnowledgeActivity;
import com.lottery.ui.activity.SportteryActivity;
import com.lottery.ui.activity.web.KnowledgeNextActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/15 0:35
 * @description:
 */
public class SportteryOneActivity extends BaseWebViewActivity {


//    private String url = "http://m.sporttery.cn/wap/bk_result_list.html";

    public static String KNOWLEDGE_URL="sporttery_url";
    private String KNOWLEDGE_TITLE="sporttery_title";

    private String url ="http://m.sporttery.cn/wap/fb_lottery.html";
    private String javascript = "javascript:function hideOther() {"
            + "if(document.getElementsByClassName('header')[0] != null) {document.getElementsByClassName('header')[0].style.display = 'none';}"
            + "if(document.getElementsByClassName('footer')[0] != null) {document.getElementsByClassName('footer')[0].style.display = 'none';}"
            + "}";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar("app", this, false);
        getToolbar().setVisibility(View.GONE);
        initWebView(url, client);
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("basketball/info")) {
                return true;
            }else {
                Intent mIntent = new Intent(SportteryOneActivity.this, SportteryTwoActivity.class);
                mIntent.putExtra(KNOWLEDGE_URL, url);
                mIntent.putExtra(KNOWLEDGE_TITLE, "详情");
                startActivity(mIntent);
            }
//            progressShow();
//            getWebView().setVisibility(View.GONE);
//            view.loadUrl(url);
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