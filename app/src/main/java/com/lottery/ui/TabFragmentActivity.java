package com.lottery.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lottery.R;
import com.lottery.adapter.TabFragmentAdapter;
import com.lottery.base.BaseAppActivity;
import com.lottery.constant.Common;
import com.lottery.constant.Constant;
import com.lottery.ui.activity.UserFeedbackActivity;
import com.lottery.ui.activity.explain.ExplainActivity;
import com.lottery.ui.activity.explain.LotteryQueryActivity;
import com.lottery.ui.activity.explain.NoJsIntentActivity;
import com.lottery.ui.activity.web.CommissionsActivity;
import com.lottery.ui.fragment.HomeFragment;
import com.lottery.ui.fragment.LivescoreFragment;
import com.lottery.ui.fragment.NewsFragment;
import com.lottery.ui.fragment.ZixunFragment;
import com.lottery.utils.ToastUtils;
import com.lottery.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/16 20:00
 * @description:
 */
public class TabFragmentActivity extends BaseAppActivity implements View.OnClickListener {
    private TabFragmentAdapter tabFragmentAdapter;
    @BindView(R.id.home_main_radio)
    RadioGroup radioGroup;
    @BindView(R.id.tab_home_toolbar)
    Toolbar toolbar;
    private List<Fragment> list = new ArrayList<>();

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    SwitchButton switchButton;

    NavigationView navigationView;

    private SharedPreferences sharedPreferences;

    private TextView toolbar_title;

    private ArrayList<Integer> imageTextGridViewBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_home_fragment);
        initView();
        list.add(new HomeFragment());
        list.add(new LivescoreFragment());
        list.add(new ZixunFragment());
        list.add(new NewsFragment());
        tabFragmentAdapter = new TabFragmentAdapter(this, list, R.id.home_tab_content, radioGroup);
        tabFragmentAdapter.setOnRgsExtraCheckedChangedListener(new TabFragmentAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                toolbar_title.setText(radioButton.getText().toString());
            }
        });

    }

    private void initView() {
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        toolbar_title = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbar.findViewById(R.id.iv_toolbar_right).setOnClickListener(this);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        View view = navigationView.getHeaderView(0);
        switchButton = view.findViewById(R.id.home_setting_switchButton);
        view.findViewById(R.id.home_setting_computation).setOnClickListener(this);
        view.findViewById(R.id.home_setting_kaijiangchax).setOnClickListener(this);
        view.findViewById(R.id.home_setting_help).setOnClickListener(this);
        view.findViewById(R.id.home_setting_fuli).setOnClickListener(this);
        view.findViewById(R.id.home_setting_feedback_ll).setOnClickListener(this);
        view.findViewById(R.id.home_setting_version_update_ll).setOnClickListener(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        switchButton.updateSwitchState(sharedPreferences.getBoolean(Common.VOICE, true));
        switchButton.setOnSwitchListener(new SwitchButton.OnSwitchListener() {
            @Override
            public void onSwitched(boolean isSwitchOn) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isSwitchOn) {
                    editor.putBoolean(Common.VOICE, true);
                    ToastUtils.getInstance().showShortToast("消息推送已打开");
                } else {
                    editor.putBoolean(Common.VOICE, false);
                    ToastUtils.getInstance().showShortToast("消息推送已关闭");
                }
                editor.apply();
            }
        });


    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.home_setting_computation:
                intent = new Intent(getContext(), CommissionsActivity.class);
                intent.putExtra("url", Constant.JIANGJINJISUAN);
                intent.putExtra("title", "奖金计算");
                startActivity(intent);
                break;
            case R.id.home_setting_feedback_ll:
                intent = new Intent(getContext(), UserFeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.home_setting_version_update_ll:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressCancel();
                        ToastUtils.getInstance().showShortToast("已是最新版本");
                    }
                }, 500);
                break;

            case R.id.home_setting_help:
                intent = new Intent(getContext(), ExplainActivity.class);
                intent.putExtra("url", "http://www.sporttery.cn/wap/help/");
                intent.putExtra("title", "帮助");
                startActivity(intent);
                break;
            case R.id.home_setting_fuli:
                intent = new Intent(getContext(), NoJsIntentActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_toolbar_right:
                intent = new Intent(getContext(), LotteryQueryActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }

    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
