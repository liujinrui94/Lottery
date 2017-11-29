package com.lottery.ui.activity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lottery.R;
import com.lottery.base.BaseActivity;
import com.lottery.widget.MenuTabHost;
import com.lottery.widget.MenuTabItem;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/11/28 16:08
 * @description:
 */
public class TabMainActivity extends TabActivity {

    private MenuTabHost mTabHost = null;

    private MenuTabItem chatTabItem = null;
    private MenuTabItem contactsTabItem = null;
    private MenuTabItem weakTabItem = null;
    private MenuTabItem circleTabItem = null;

    private Intent mChatIntent = null; // 敏信
    private Intent mContactsIntent = null; // 通讯录
    private Intent mWorkIntent = null; // 我的应用
    private Intent mCircleIntent = null; // 工作圈


    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main_activity);
        mContext=this;
        setupIntent();
    }

    private void setupIntent() {
        mWorkIntent = new Intent(this, OfficalNetActivity.class);
        mChatIntent = new Intent(this, OfficalNetActivity.class);
        mContactsIntent = new Intent(this, OfficalNetActivity.class);
        mCircleIntent = new Intent(this, OfficalNetActivity.class);
        initTabhost();
    }



    public void initTabhost() {

        mTabHost = (MenuTabHost) getTabHost();
        weakTabItem = new MenuTabItem(mContext, null, mWorkIntent, getResources().getDrawable(R.drawable.tab_appcenter),
                getString(R.string.app_name));
        mTabHost.addMenuItem(weakTabItem);

        contactsTabItem = new MenuTabItem(mContext, null, mContactsIntent,
                getResources().getDrawable(R.drawable.selector_main_rb1), getString(R.string.app_name));
        mTabHost.addMenuItem(contactsTabItem);

        chatTabItem = new MenuTabItem(mContext, null, mChatIntent, getResources().getDrawable(R.drawable.selector_main_rb2),
                getString(R.string.app_name));

        mTabHost.addMenuItem(chatTabItem);

        circleTabItem = new MenuTabItem(mContext, null, mCircleIntent,
                getResources().getDrawable(R.drawable.selector_main_rb3), getString(R.string.app_name));
        mTabHost.addMenuItem(circleTabItem);
        mTabHost.setCurrentTab(0);

    }

}
