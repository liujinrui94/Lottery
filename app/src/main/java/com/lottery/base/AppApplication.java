package com.lottery.base;

import android.app.Application;


import com.tencent.smtt.sdk.QbSdk;


import java.util.Stack;

import cn.jpush.android.api.JPushInterface;

public class AppApplication extends Application {

    public static AppApplication instance;

    public Stack<BaseActivity> allActivity = new Stack<>();


    public AppApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initTBS();
        //极光推送
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
    }

    private void initTBS() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }




    public static AppApplication getInstance() {
        return instance;
    }

    public void addActivity(BaseActivity activity) {
        if (allActivity == null) {
            allActivity = new Stack<BaseActivity>();
        }
        allActivity.add(activity);
    }

    public void finishActivity(BaseActivity activity) {
        if (activity != null) {
            allActivity.remove(activity);
        }
    }

    public void finishAllActivity() {
        for (int i = 0, size = allActivity.size(); i < size; i++) {
            if (null != allActivity.get(i)) {
                allActivity.get(i).finish();
            }
        }
        allActivity.clear();
    }

    public void finishActivitys() {
        for (int i = 0, size = allActivity.size(); i < size; i++) {
            if (allActivity.size() == i) {
                break;
            }
//            if (null != allActivitys.get(i) && !(allActivitys.get(i) instanceof LoginActivity)) {
//                allActivitys.get(i).finish();
//            }

            if (null != allActivity.get(i)) {
                allActivity.get(i).finish();
            }
        }
        allActivity.clear();
    }

    public void AppExit() {
        try {
            finishAllActivity();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.exit(0);
        }
    }

}
