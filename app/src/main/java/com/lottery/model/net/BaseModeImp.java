package com.lottery.model.net;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public abstract class BaseModeImp implements BaseNetRequestModel {


    public static class PostBaseModeImp implements BaseNetRequestModel {

        @Override
        public void postBaseNetRequestModel(String requestString, final BaseNetRequestCallBack callBack) {
            OkHttpUtils.post().url(requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                        callBack.SucceedCallBack(response);

                }
            });

        }
    }

}