package com.lottery.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lottery.R;
import com.lottery.adapter.FragmentAdapter;
import com.lottery.base.BaseActivity;
import com.lottery.ui.fragment.SsczstFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/10 15:12
 * @description:
 */
public class HomeTabActivity extends BaseActivity {

    @BindView(R.id.activity_home_list_tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.activity_home_view_pager)
    ViewPager viewPager;

    private FragmentAdapter mFragmentAdapter;

    private List<Fragment> fragments = new ArrayList<>();

    private List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tab);
        initView();
        initToolbar("开奖走势",this,false);
    }


    private void initView() {

       String javascript = "javascript:function hideOther() {"
                + "if(null!= document.getElementsByClassName('nav')) {document.getElementsByClassName('nav')[0].style.display = 'none';}" +
                "}";
        fragments.add(SsczstFragment.newInstance("http://112.74.102.204:86/m/zst.html",javascript));
        fragments.add(SsczstFragment.newInstance("http://112.74.102.204:86/m/tool.html",javascript));
        fragments.add(SsczstFragment.newInstance("http://112.74.102.204:86/m/yu.html",javascript));
        titles.add("开奖走势");
        titles.add("号码缩水");
        titles.add("人工计划");
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(mFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(mFragmentAdapter);
    }
}
