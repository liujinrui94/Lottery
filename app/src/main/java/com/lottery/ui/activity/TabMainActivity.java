package com.lottery.ui.activity;

import android.app.Application;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.lottery.R;
import com.lottery.base.AppApplication;
import com.lottery.base.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.ui.activity.web.MessageActivity;
import com.lottery.utils.ToastUtils;
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

    private Intent mChatIntent = null; //
    private Intent mContactsIntent = null; //
    private Intent mWorkIntent = null; //
    private Intent mCircleIntent = null; //

    private long exitTime = 0;//标记退出时间
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main_activity);

        mContext = this;
        setupIntent();
    }

    private void setupIntent() {
        mWorkIntent = new Intent(this, MessageActivity.class);
        mWorkIntent.putExtra("url", Constant.DALETOU);
        mChatIntent = new Intent(this, FootballActivity.class);
        mChatIntent.putExtra("url", Constant.ZHIBO);
        mContactsIntent = new Intent(this, WebViewActivity.class);
        mContactsIntent.putExtra("url", Constant.OKKAIJIANG);
        mCircleIntent = new Intent(this, MineActivity.class);
        initTabhost();
    }


    public void initTabhost() {

        mTabHost = (MenuTabHost) getTabHost();
        weakTabItem = new MenuTabItem(mContext, null, mWorkIntent, getResources().getDrawable(R.drawable.tab_appcenter),
                "资讯");
        mTabHost.addMenuItem(weakTabItem);

        contactsTabItem = new MenuTabItem(mContext, null, mChatIntent,
                getResources().getDrawable(R.drawable.selector_main_rb1), "开奖");
        mTabHost.addMenuItem(contactsTabItem);

        chatTabItem = new MenuTabItem(mContext, null, mContactsIntent, getResources().getDrawable(R.drawable.selector_main_rb2),
                "赛事");

        mTabHost.addMenuItem(chatTabItem);

        circleTabItem = new MenuTabItem(mContext, null, mCircleIntent,
                getResources().getDrawable(R.drawable.selector_main_rb3), "我的");
        mTabHost.addMenuItem(circleTabItem);
        mTabHost.setCurrentTab(0);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.getInstance().showBottomToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            AppApplication.getInstance().AppExit();
        }
        return super.onKeyDown(keyCode, event);
    }

}
