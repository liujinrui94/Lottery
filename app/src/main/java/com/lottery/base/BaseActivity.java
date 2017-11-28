package com.lottery.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lottery.R;
import com.lottery.model.NetEventInterface;
import com.lottery.receiver.NetBroadcastReceiver;
import com.lottery.utils.AppLogger;
import com.lottery.widget.BaseProgressDialog;
import com.lottery.widget.SnackBarUtils;

import butterknife.BindView;


public abstract class BaseActivity extends AppCompatActivity implements NetEventInterface, View.OnClickListener {

    private int netMobile;//网络状态
    private NetBroadcastReceiver netBroadcastReceiver;//监控网络的广播
    private BaseProgressDialog progressDialog;
    private Snackbar snackbar;


    protected AppApplication app;
    @BindView(R.id.top_toolbar)
     Toolbar toolbar;

    @BindView(R.id.top_toolbar_tv)
     TextView textTitle;

    private boolean view = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        initData();
        app.getInstance().addActivity(this);
        AppLogger.i(getRunningActivityName(this) + " is running");
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected void initToolbar(String title, final BaseActivity context, boolean back) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        if (back) {
            toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        }
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.finishActivity(context);
            }
        });
    }


    private String getRunningActivityName(Context mContext) {
        String contextString = mContext.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }

    protected abstract void initView();


    protected abstract void initData();

    @Override
    public abstract void onClick(View view);


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
    protected void onDestroy() {
        super.onDestroy();
        if (netBroadcastReceiver != null) {
            //注销广播
            unregisterReceiver(netBroadcastReceiver);
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        if (view) {
            isNetConnect();
        }
    }

    public void setView(boolean view) {
        this.view = view;
    }

    private void isNetConnect() {
        if (snackbar == null) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
