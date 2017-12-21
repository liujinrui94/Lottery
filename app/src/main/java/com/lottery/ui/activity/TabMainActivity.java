package com.lottery.ui.activity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.lottery.R;
import com.lottery.base.AppApplication;
import com.lottery.constant.Constant;
import com.lottery.ui.activity.explain.FLActivity;
import com.lottery.ui.activity.explain.LotteryQueryActivity;
import com.lottery.ui.activity.explain.NoJsIntentActivity;
import com.lottery.ui.activity.lottery.InformationActivity;
import com.lottery.ui.activity.lottery.KJZSActivity;
import com.lottery.ui.activity.lottery.NationWideActivity;
import com.lottery.ui.activity.lottery.SSCActivity;
import com.lottery.ui.activity.match.MatchActivity;
import com.lottery.ui.activity.match.ScoreLiveActivity;
import com.lottery.ui.activity.match.SportteryOneActivity;
import com.lottery.ui.activity.match.SportteryTwoActivity;
import com.lottery.ui.activity.web.KnowledgeNextActivity;
import com.lottery.ui.activity.web.WCLiveActivity;
import com.lottery.ui.activity.web.ZuCaiActivity;
import com.lottery.ui.activity.web.ZucaiNextActivity;
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

    /*    private void setupIntent() {
    //            mWorkIntent = new Intent(this, NationWideActivity.class);
            mWorkIntent = new Intent(this, FootballActivity.class);
            mWorkIntent.putExtra("url", Constant.ZHIBO);
            //        mWorkIntent = new Intent(this, LotteryLobbyActivity.class);
            mChatIntent = new Intent(this, KJZSActivity.class);
            mContactsIntent = new Intent(this, FLActivity.class);
            mCircleIntent = new Intent(this, MineActivity.class);
    //        mCircleIntent = new Intent(this, MineActivity.class);
            initTabhost();
        }*/
//    private void setupIntent() {
//        mWorkIntent = new Intent(this, NationWideActivity.class);
//        mChatIntent = new Intent(this, KJZSActivity.class);
//        mContactsIntent = new Intent(this, ZuCaiActivity.class);
//        mCircleIntent = new Intent(this, SportteryActivity.class);
//        initTabhost();
//    }
 /*   private void setupIntent() {
        mWorkIntent = new Intent(this, HomeTabActivity.class);
        mChatIntent = new Intent(this, MatchActivity.class);
        mContactsIntent = new Intent(this, InformationActivity.class);
        mCircleIntent = new Intent(this, LotteryQueryActivity.class);
        initTabhost();
    }*/


    private void setupIntent() {
        mWorkIntent = new Intent(this, SportteryActivity.class);
        mWorkIntent.putExtra("url", Constant.columns);
        mChatIntent = new Intent(this, ZstActivity.class);
//        mContactsIntent = new Intent(this, NoJsIntentActivity.class);
        mCircleIntent = new Intent(this, MineActivity.class);
        initTabhost();
    }

//    private void setupIntent() {
//        mWorkIntent = new Intent(this, HomeTabActivity.class);
//        mChatIntent = new Intent(this, NationWideActivity.class);
//        mContactsIntent = new Intent(this, KnowledgeActivity.class);
////        mCircleIntent = new Intent(this, MineActivity.class);
//        initTabhost();
//    }

    public void initTabhost() {
        mTabHost = (MenuTabHost) getTabHost();
        weakTabItem = new MenuTabItem(mContext, null, mWorkIntent,
                getResources().getDrawable(R.drawable.tab_appcenter), "首页");
        mTabHost.addMenuItem(weakTabItem);
        contactsTabItem = new MenuTabItem(mContext, null, mChatIntent,
                getResources().getDrawable(R.drawable.selector_main_rb1), "开奖");
        mTabHost.addMenuItem(contactsTabItem);
        chatTabItem = new MenuTabItem(mContext, null, mContactsIntent,
                getResources().getDrawable(R.drawable.selector_main_rb2), "赛事");
//        mTabHost.addMenuItem(chatTabItem);
        circleTabItem = new MenuTabItem(mContext, null, mCircleIntent,
                getResources().getDrawable(R.drawable.selector_main_rb3), "我的");
        mTabHost.addMenuItem(circleTabItem);
        mTabHost.setCurrentTab(0);

    }

    //        public void initTabhost() {
//        mTabHost = (MenuTabHost) getTabHost();
//        weakTabItem = new MenuTabItem(mContext, null, mWorkIntent,
//                getResources().getDrawable(R.drawable.tab_appcenter),"资讯");
//        mTabHost.addMenuItem(weakTabItem);
//        contactsTabItem = new MenuTabItem(mContext, null, mChatIntent,
//                getResources().getDrawable(R.drawable.selector_zoushi_bg), "走势");
//        mTabHost.addMenuItem(contactsTabItem);
//        chatTabItem = new MenuTabItem(mContext, null, mContactsIntent,
//                getResources().getDrawable(R.drawable.selector_main_rb2),"赛事");
//        mTabHost.addMenuItem(chatTabItem);
//        circleTabItem = new MenuTabItem(mContext, null, mCircleIntent,
//                getResources().getDrawable(R.drawable.select_knowledge_rb), "知识");
//        mTabHost.addMenuItem(circleTabItem);
//        mTabHost.setCurrentTab(0);
//
//    }
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
