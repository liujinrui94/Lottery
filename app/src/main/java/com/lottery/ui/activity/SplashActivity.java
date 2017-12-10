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
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lottery.R;
import com.lottery.base.BaseActivity;
import com.lottery.bean.MyBean;
import com.lottery.constant.Common;
import com.lottery.constant.Constant;
import com.lottery.utils.AppLogger;
import com.lottery.utils.HttpUtils;
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

    private String URL = "1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppLogger.e(JPushInterface.getRegistrationID(this));
//        initView();
        initM();
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
            finish();
            return;
        } else {
            boolean open = sharedPreferences.getBoolean(Common.FINISH_LOGIN, true);
            if (open) {
                initM();
                return;
            } else {
                Intent intent = new Intent(this, TabMainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }
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

    private void initM() {
        if (!isNetworkAvailable(getBaseContext())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SplashActivity.this, "网络连接断开", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, TabMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);

        } else {
            if ("1".equals(URL)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtils.getInstance().get("http://www.08cpapp.com/Lottery_server/check_and_get_url.php?type=android&appid=20178858", new HttpUtils.HttpCallback() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
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

                            @Override
                            public void onError(String msg) {
                                super.onError(msg);
                            }
                        });

                    }
                }, 1000);

            } else if ("2".equals(URL)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtils.getInstance().get("http://www.27305.com/frontApi/getAboutUs?appid=11211838", new HttpUtils.HttpCallback() {
                            @Override
                            public void onSuccess(String data) {
                                MyBean myBean = new Gson().fromJson(data, MyBean.class);
                                if ("1".equals(myBean.getIsshowwap())) {
                                    Intent intent = new Intent();
                                    intent.putExtra("url", myBean.getWapurl());
                                    intent.setClass(SplashActivity.this, OfficalNetActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent();
                                    intent.setClass(SplashActivity.this, SportteryActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onError(String msg) {
                                super.onError(msg);
                                Intent intent = new Intent();
                                intent.setClass(SplashActivity.this, SportteryActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                    }

                }, 1000);

            } else if ("3".equals(URL)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtils.getInstance().get("http://vipapp.01appkkk.com/Lottery_server/get_init_data.php?type=android&appid=pk10000", new HttpUtils.HttpCallback() {
                            @Override
                            public void onSuccess(String data) {
                                JSONObject jsonObject = null;
                                String code = null;
                                String netdata = null;
                                try {
                                    jsonObject = new JSONObject(data);
                                    code = jsonObject.getString("rt_code");
                                    netdata = jsonObject.getString("data");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                MyBean myBean = null;
                                if ("200".equals(code)) {
                                    String B = new String(Base64.decode(netdata.getBytes(), Base64.DEFAULT));
                                    myBean = new Gson().fromJson(B, MyBean.class);
                                }
                                if (null != myBean && "1".equals(myBean.getShow_url())) {
                                    Intent intent = new Intent();
                                    intent.putExtra("url", myBean.getUrl());
                                    intent.setClass(SplashActivity.this, OfficalNetActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent();
                                    intent.setClass(SplashActivity.this, TabMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onError(String msg) {
                                super.onError(msg);
                                Intent intent = new Intent();
                                intent.setClass(SplashActivity.this, TabMainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                    }

                }, 1000);


            }else if (URL.equals("4")){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtils.getInstance().get("http://1114600.com:8080/appgl/appShow/getByAppId?appId=yj20171208003", new HttpUtils.HttpCallback() {
                            @Override
                            public void onSuccess(String data) {
                                MyBean myBean = new Gson().fromJson(data, MyBean.class);
                                if ("1".equals(myBean.getStatus())) {
                                    Intent intent = new Intent();
                                    intent.putExtra("url", myBean.getUrl());
                                    intent.setClass(SplashActivity.this, OfficalNetActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent();
                                    intent.setClass(SplashActivity.this, SportteryActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onError(String msg) {
                                super.onError(msg);
                                Intent intent = new Intent();
                                intent.setClass(SplashActivity.this, TabMainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                    }

                }, 1000);


            }


        }


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