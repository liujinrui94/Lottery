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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/21 11:18
 * @description:
 */
public class BaseAppActivity extends AppCompatActivity implements NetEventInterface {


    private int netMobile;//网络状态
    private NetBroadcastReceiver netBroadcastReceiver;//监控网络的广播
    private BaseProgressDialog progressDialog;
    private Snackbar snackbar;

    private Context context;
    protected AppApplication app;

    private String request;

    private int code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLogger.i(getRunningActivityName(this) + " is running");
        context = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        app.getInstance().addActivity(this);
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
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
        isNetConnect();
    }


    private void isNetConnect() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}