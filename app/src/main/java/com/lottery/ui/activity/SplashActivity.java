package com.lottery.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lottery.MainActivity;
import com.lottery.R;
import com.lottery.base.BaseActivity;
import com.lottery.constant.Common;
import com.lottery.constant.Constant;
import com.lottery.utils.AppLogger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/11/28 16:08
 * @description:
 */
public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppLogger.e(JPushInterface.getRegistrationID(this));
        initView();
    }

    protected void initView() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstTime = sharedPreferences.getBoolean(Constant.LOGIN, true);
        if (firstTime) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constant.LOGIN, false);
            editor.apply();
            Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
            return;
        } else {
            boolean open = sharedPreferences.getBoolean(Common.FINISH_LOGIN, true);
            if (open) {
                intoSplashActivity();
                return;
            } else {
                Intent intent = new Intent(this, TabMainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }

    }

    protected void initData() {

    }


    private void intoSplashActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 1000);
    }

    private void getData() {
        OkHttpUtils.get().url(Constant.Url)
                .build().execute(new StringCallback() {

            public void onError(Call call, Exception e, int id) {
                Intent intent = new Intent(SplashActivity.this, TabMainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data3 = jsonObject.optJSONObject("data");
                    if (data3 != null) {
                        if (data3.getString("show_url") != null) {
                            if (data3.getString("show_url").equals("1")) {
                                Intent intent = new Intent();
                                intent.putExtra("url", data3.getString("url"));
                                intent.setClass(SplashActivity.this, OfficalNetActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            } else {
                                Intent intent = new Intent();
                                intent.setClass(SplashActivity.this, TabMainActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(SplashActivity.this, TabMainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //新版本调用方法获取网络状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            //否则调用旧版本方法
            if (connectivityManager != null) {
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network", "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}