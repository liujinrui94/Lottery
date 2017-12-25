package com.lottery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.google.gson.Gson;
import com.lottery.base.BaseActivity;
import com.lottery.bean.MyBean;
import com.lottery.constant.Common;
import com.lottery.constant.Constant;
import com.lottery.model.net.BaseNetRetRequestPresenter;
import com.lottery.model.net.NetRequestView;
import com.lottery.ui.OfficalMainActivity;
import com.lottery.ui.TabFragmentActivity;
import com.lottery.ui.activity.GuideActivity;
import com.lottery.ui.activity.OfficalNetActivity;
import com.lottery.utils.AppLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.jpush.android.api.JPushInterface;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/9 18:26
 * @description:
 */
public class SplashActivity extends BaseActivity implements NetRequestView {
    // 1 heijin
    // 2 stting
    // 3 hunan
    // 4 lihk
    private String URL = "5";
    private String heijinID = "no25001";
    private String sttingID = "17112117";
    //    private String sttingID = "2017112837";
    private String hunanID = "pk10001";
        private String lihkID = "yj20171208005";
    private String lihkTSID = "no25001";


    private String showUrl;

    private Boolean show = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppLogger.e(JPushInterface.getRegistrationID(this));
        initView();
//        intoSplashActivity();
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
            boolean open = sharedPreferences.getBoolean(Common.FINISH_LOGIN, false);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            Long l1 = new Long(201712232000l);
            if (!open || Long.parseLong(sdf.format(Calendar.getInstance().getTime())) > l1) {
                intoSplashActivity();
                return;
            } else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, TabFragmentActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1000);

                return;
            }
        }
    }

    private void intoSplashActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new BaseNetRetRequestPresenter(SplashActivity.this).PostNetRetRequest();
            }
        }, 1000);
    }

    @Override
    public void showCordError(String msg) {
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, TabFragmentActivity.class);
//        intent.setClass(SplashActivity.this, SportteryActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public String getPostJsonString() {
        String str = null;
        switch (URL) {
            case "1":
                str = "http://www.08cpapp.com/Lottery_server/check_and_get_url.php?type=android&appid=" + heijinID;
                break;
            case "2":
                str = "http://www.27305.com/frontApi/getAboutUs?appid=" + sttingID;
                break;
            case "3":
                str = "http://vipapp.01appkkk.com/Lottery_server/get_init_data.php?type=android&appid=" + hunanID;
                break;
            case "4":
                str = "http://1114600.com:8080/appgl/appShow/getByAppId?appId=" + lihkID;
                break;
            case "5":
                str="http://app.you228.com/Lottery_server/check_and_get_url.php?type=android&appid="+lihkTSID;
        }
        return str;
    }


    @Override
    public void NetInfoResponse(String data, int netCode) {
        MyBean myBean = null;
        Intent intent = new Intent();
        switch (URL) {
            case "1":
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject data3 = jsonObject.optJSONObject("data");
                    if (data3 != null) {
                        if (data3.getString("show_url") != null) {
                            if (data3.getString("show_url").equals("1")) {
                                show = true;
                                showUrl = data3.getString("url");
                                intent.setClass(SplashActivity.this, OfficalNetActivity.class);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "2":
                myBean = new Gson().fromJson(data, MyBean.class);
                if ("1".equals(myBean.getIsshowwap())) {
                    show = true;
                    showUrl = myBean.getWapurl();
                    intent.setClass(SplashActivity.this, OfficalNetActivity.class);
                }
                break;
            case "3":
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
                if ("200".equals(code)) {
                    String B = new String(Base64.decode(netdata.getBytes(), Base64.DEFAULT));
                    myBean = new Gson().fromJson(B, MyBean.class);
                }
                if (null != myBean && "1".equals(myBean.getShow_url())) {
                    show = true;
                    showUrl = myBean.getUrl();
                    intent.setClass(SplashActivity.this, OfficalNetActivity.class);
                }
                break;
            case "4":
                myBean = new Gson().fromJson(data, MyBean.class);
                if ("1".equals(myBean.getStatus())) {
                    show = true;
                    showUrl = myBean.getUrl();
                    intent.setClass(SplashActivity.this, OfficalMainActivity.class);
                }
                break;
            case "5":
                try {
                    JSONObject jsonObject1 = new JSONObject(data);
                    JSONObject data3 = jsonObject1.optJSONObject("data");
                    if (data3 != null) {
                        if (data3.getString("show_url") != null) {
                            if (data3.getString("show_url").equals("1")) {
                                show = true;
                                showUrl = data3.getString("url");
                                intent.setClass(SplashActivity.this, OfficalNetActivity.class);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        if (show) {
            intent.putExtra("url", showUrl);
            startActivity(intent);
            finish();
        } else {
            intent.setClass(SplashActivity.this, TabFragmentActivity.class);
//            intent.setClass(SplashActivity.this, SportteryActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public int code() {
        return 0;
    }
}
