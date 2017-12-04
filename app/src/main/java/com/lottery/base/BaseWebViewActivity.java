package com.lottery.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lottery.R;
import com.lottery.model.NetEventInterface;
import com.lottery.receiver.NetBroadcastReceiver;
import com.lottery.utils.AppLogger;
import com.lottery.widget.BaseProgressDialog;
import com.lottery.widget.SnackBarUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/1 15:33
 * @description:
 */
public class BaseWebViewActivity extends AppCompatActivity implements NetEventInterface {
    private int netMobile;//网络状态
    private NetBroadcastReceiver netBroadcastReceiver;//监控网络的广播
    private BaseProgressDialog progressDialog;
    private Snackbar snackbar;

    private Context context;
    protected AppApplication app;
    @BindView(R.id.top_toolbar)
    Toolbar toolbar;

    @BindView(R.id.top_toolbar_tv)
    TextView textTitle;

    @BindView(R.id.activity_base_web_view)
    WebView webView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLogger.i(getRunningActivityName(this) + " is running");
        context = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        app.getInstance().addActivity(this);
        setContentView(R.layout.activity_base_web_view);
        progressShow();
    }

    protected void initWebView(String url, WebViewClient client) {
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setWebViewClient(client);
        webView.setWebChromeClient(chromeClient);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadUrl(url);
        webView.setVisibility(View.GONE);

    }



    public Toolbar getToolbar() {
        return toolbar;
    }

    public WebView getWebView() {
        return webView;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    protected void initToolbar(String title, final BaseWebViewActivity context, boolean back) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        if (back) {
            toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    private String getRunningActivityName(Context mContext) {
        String contextString = mContext.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }

    /**
     * 显示加载对话框
     */
    protected ProgressDialog progressShow(String dialogDetail) {
        if (progressDialog == null) {
            progressDialog = new BaseProgressDialog(this);
        }
        progressDialog.setDialogDetail(dialogDetail);
        progressDialog.show();
        return progressDialog;
    }

    /**
     * 显示加载对话框
     */
    protected ProgressDialog progressShow() {
        return progressShow("");
    }

    /**
     * 取消加载对话框
     */
    protected void progressCancel() {
        if (progressDialog != null)
            progressDialog.cancel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //注册广播
        if (netBroadcastReceiver == null) {
            netBroadcastReceiver = new NetBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netBroadcastReceiver, filter);
            netBroadcastReceiver.setNetEvent(this);//设置网络监听
        }
    }


    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }


    private void isNetConnect() {
        if (snackbar == null && textTitle != null) {

            snackbar = SnackBarUtils.indefiniteSnackbar(textTitle, "无网络连接，请检查网络设置");
            switch (netMobile) {
                case 1://wifi
                    if (snackbar.isShown()) {
                        snackbar.dismiss();
                    }
                    break;
                case 0://移动数据
                    if (snackbar.isShown()) {
                        snackbar.dismiss();
                    }
                    break;
                case -1://没有网络
                    snackbar.setAction("去设置", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        }
                    }).show();
                    break;
            }

        }
    }
    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int i) {

            super.onProgressChanged(webView, i);
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        if (netBroadcastReceiver != null) {
            //注销广播
            unregisterReceiver(netBroadcastReceiver);
        }
        super.onDestroy();
    }
}
