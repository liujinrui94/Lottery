package com.lottery.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.RadioGroup;

import com.lottery.R;
import com.lottery.adapter.TabFragmentAdapter;
import com.lottery.base.BaseActivity;
import com.lottery.ui.fragment.LocalLotteriesFragment;
import com.lottery.ui.fragment.NationwideFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/1 13:13
 * @description:
 */
public class LotteryLobbyActivity extends BaseActivity {

    private TabFragmentAdapter tabFragmentAdapter;
    @BindView(R.id.activity_lottery_rg)
    RadioGroup radioGroup;
    private List<Fragment> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_lobby);
        list.add(new NationwideFragment());
        list.add(new LocalLotteriesFragment());
        tabFragmentAdapter = new TabFragmentAdapter(this, list, R.id.fragment_these_month_profit_fl, radioGroup);
        tabFragmentAdapter.setOnRgsExtraCheckedChangedListener(new TabFragmentAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
            }
        });

    }
}
