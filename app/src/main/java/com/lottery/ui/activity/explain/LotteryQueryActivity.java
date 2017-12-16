package com.lottery.ui.activity.explain;

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
 * @time: 2017/12/16 12:05
 * @description:
 */
public class LotteryQueryActivity extends BaseWebViewActivity {


    private String lourl = "http://m.500.com/info/index.php?c=zhongjiang&a=dlt&from=kaijiang";
    private String title = "开奖查询";
    private Boolean back=false;

    private String javascript = "javascript:function hideOther() {"
//            + "if(document.getElementById('uiHead')!= null) {document.getElementById('uiHead').style.display = 'none';}"
            + "if(document.getElementsByClassName('ui-head-r')[0] != null) {document.getElementsByClassName('ui-head-r')[0].style.display = 'none';}"
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
