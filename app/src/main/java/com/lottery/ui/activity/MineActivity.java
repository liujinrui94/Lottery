package com.lottery.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;

import com.lottery.R;
import com.lottery.base.BaseActivity;
import com.lottery.constant.Common;
import com.lottery.constant.Constant;
import com.lottery.ui.activity.explain.ExplainActivity;
import com.lottery.ui.activity.explain.NoJsIntentActivity;
import com.lottery.ui.activity.web.CommissionsActivity;
import com.lottery.ui.activity.web.RunlotteryActivity;
import com.lottery.ui.activity.web.ZuCaiActivity;
import com.lottery.utils.ToastUtils;
import com.lottery.widget.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/11/30 16:20
 * @description:
 */
public class MineActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.setting_switchButton)
    SwitchButton switchButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        initView();
        initToolbar("我的",this,false);
    }

    private void initView() {

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
    @OnClick({R.id.setting_computation, R.id.setting_feedback_ll, R.id.setting_version_update_ll,R.id.setting_help,R.id.setting_fuli})
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.setting_computation:
                intent = new Intent(getContext(), CommissionsActivity.class);
                intent.putExtra("url", Constant.JIANGJINJISUAN);
                intent.putExtra("title", "奖金计算");
                startActivity(intent);
                break;
            case R.id.setting_feedback_ll:
                intent = new Intent(getContext(), UserFeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_version_update_ll:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressCancel();
                        ToastUtils.getInstance().showShortToast("已是最新版本");
                    }
                }, 500);
                break;

            case R.id.setting_help:
                intent = new Intent(getContext(), ExplainActivity.class);
                intent.putExtra("url", "http://www.sporttery.cn/wap/help/");
                intent.putExtra("title", "帮助");
                startActivity(intent);
                break;
            case R.id.setting_fuli:
                intent = new Intent(getContext(), NoJsIntentActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }

    }
}
