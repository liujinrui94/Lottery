package com.lottery.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lottery.model.net.BaseNetRequestModel;
import com.lottery.model.net.BaseNetRetRequestPresenter;
import com.lottery.model.net.NetRequestView;
import com.lottery.utils.AppLogger;
import com.lottery.widget.BaseProgressDialog;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/21 10:16
 * @description:
 */
public class BaseNetFragment extends Fragment implements NetRequestView {
    private BaseProgressDialog progressDialog;
    private Context mContext;
    private String request;

    private int requestSign;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AppLogger.i(getClass().getSimpleName() + " onCreate");
        mContext=getActivity();
        super.onCreate(savedInstanceState);
    }

    public Context getContext() {
        return mContext;
    }



    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
    }

    /**
     * 显示加载对话框
     */
    protected ProgressDialog progressShow(String dialogDetail) {
        if (progressDialog != null)
            progressDialog.cancel();
        progressDialog = new BaseProgressDialog(getActivity());
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
    public void showCordError(String msg) {

    }

    @Override
    public String getPostJsonString() {
        return request;
    }

    @Override
    public void NetInfoResponse(String data, int code) {

    }

    @Override
    public int code() {
        return requestSign;
    }

    public void getNetData(String data, int requestSign ){
        progressShow();
        this.requestSign = requestSign;
        this.request = data;
        new BaseNetRetRequestPresenter(BaseNetFragment.this).PostNetRetRequest();
    }

}
