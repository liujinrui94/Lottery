package com.lottery.utils;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {

    // 超时时间
    public static final int TIMEOUT = 1000 * 60;
    //json请求
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String TAG = "debug-okhttp";
    public static boolean isDebug = true;
    private static HttpUtils httpUtils = null;
    private OkHttpClient client;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Call call;

    private HttpUtils() {
        // TODO Auto-generated constructor stub
        this.init();
    }

    public static HttpUtils getInstance() {

        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    private void init() {
        // 设置超时时间
        client = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS).build();
    }

    /**
     * post请求，json数据为body
     *
     * @param url
     * @param param
     * @param callback
     */
    public void postJson(String url, String param, final HttpCallback callback) {


        RequestBody body = RequestBody.create(JSON, param);
        Request request = new Request.Builder().url(url).post(body).build();

        onStart(callback);
        call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException arg1) {
                // TODO Auto-generated method stub
                onError(callback, arg1.getMessage());
                arg1.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // TODO Auto-generated method stub

                if (response.isSuccessful()) {
                    onSuccess(callback, response.body().string());
                } else {
                    onError(callback, response.message());
                }
            }
        });
    }

    public void cancel() {
        if (call != null)
            call.cancel();
    }

    /**
     * post请求 map为body
     *
     * @param url
     * @param map
     * @param callback
     */
    public void post(String url, Map<String, Object> map, final HttpCallback callback) {

        // FormBody.Builder builder = unread FormBody.Builder();
        // FormBody body=unread FormBody.Builder().add("key", "value").build();

        /**
         * 创建请求的参数body
         */
        FormBody.Builder builder = new FormBody.Builder();

        /**
         * 遍历key
         */
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {

                System.out.println("Key = " + entry.getKey() + ", Value = "
                        + entry.getValue());
                builder.add(entry.getKey(), entry.getValue().toString());

            }
        }

        RequestBody body = builder.build();

        Request request = new Request.Builder().url(url).post(body).build();

        onStart(callback);

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException arg1) {
                // TODO Auto-generated method stub
                arg1.printStackTrace();
                onError(callback, arg1.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // TODO Auto-generated method stub

                if (response.isSuccessful()) {
                    onSuccess(callback, response.body().string());
                } else {
                    onError(callback, response.message());
                }
            }

        });

    }

    /**
     * get请求
     *
     * @param url
     * @param callback
     */
    public void get(String url, final HttpCallback callback) {

        Request request = new Request.Builder().url(url).build();

        onStart(callback);

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                // TODO Auto-generated method stub

                onError(callback, e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // TODO Auto-generated method stub
                if (response.isSuccessful()) {
                    onSuccess(callback, response.body().string());
                } else {
                    onError(callback, response.message());
                }
            }

        });

    }

    /**
     * log信息打印
     *
     * @param params
     */
    public void debug(String params) {
        if (false == isDebug) {
            return;
        }

        if (null == params) {
            Log.d(TAG, "params is null");
        } else {
            Log.d(TAG, params);
        }
    }

    private void onStart(HttpCallback callback) {
        if (null != callback) {
            callback.onStart();
        }
    }

    private void onSuccess(final HttpCallback callback, final String data) {

        debug(data);

        if (null != callback) {
            handler.post(new Runnable() {
                public void run() {
                    // 需要在主线程的操作。
                    callback.onSuccess(data);
                }
            });
        }
    }

    private void onError(final HttpCallback callback, final String msg) {
        if (null != callback) {
            handler.post(new Runnable() {
                public void run() {
                    // 需要在主线程的操作。
                    callback.onError(msg);
                }
            });
        }
    }


    /**
     * http请求回调
     *
     * @author Abel.Han
     */
    public static abstract class HttpCallback {
        // 开始
        public void onStart() {
        }

        // 成功回调
        public abstract void onSuccess(String data);

        // 失败回调
        public void onError(String msg) {
        }
    }
}