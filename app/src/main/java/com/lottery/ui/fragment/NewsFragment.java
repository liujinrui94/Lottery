package com.lottery.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lottery.R;
import com.lottery.adapter.FragmentAdapter;
import com.lottery.base.BaseFragment;
import com.lottery.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/22 16:38
 * @description:
 */
public class NewsFragment extends BaseFragment {

    private View rootView = null;

    @BindView(R.id.activity_home_list_tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.activity_home_view_pager)
    ViewPager viewPager;
    @BindView(R.id.top_toolbar)
    Toolbar toolbar;


    private String json = "[{\"channelid\":\"1\",\"channel\":\"热门\"}," +
            "{\"channelid\":\"2\",\"channel\":\"推荐\"}," +
            "{\"channelid\":\"3\",\"channel\":\"段子\"}," +
            "{\"channelid\":\"8\",\"channel\":\"财经\"}," +
            "{\"channelid\":\"9\",\"channel\":\"汽车\"}," +
            "{\"channelid\":\"10\",\"channel\":\"科技\"}," +
            "{\"channelid\":\"14\",\"channel\":\"旅行\"}," +
            "{\"channelid\":\"15\",\"channel\":\"职场\"}," +
            "{\"channelid\":\"16\",\"channel\":\"美食\"}," +
            "{\"channelid\":\"19\",\"channel\":\"星座\"}," +
            "{\"channelid\":\"20\",\"channel\":\"体育\"}]";


    private String[] channel = {"头条", "财经", "新闻", "体育", "股票", "娱乐", "科技", "NBA", "星座", "健康"};

    private FragmentAdapter mFragmentAdapter;

    private List<Fragment> fragments = new ArrayList<>();

    private List<NewsBean> newsBeanArrayList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_home_tab, container, false);
        ButterKnife.bind(this, rootView);
        progressShow();
        initView();
        return rootView;
    }

    private void initView() {
        toolbar.setVisibility(View.GONE);
//        newsBeanArrayList = GsonUtil.getInstance().fromJson(json, new TypeToken<ArrayList<NewsBean>>() {
//        }.getType());
//        for (int i = 0; i < newsBeanArrayList.size(); i++) {
//            titles.add(newsBeanArrayList.get(i).getChannel());
//            fragments.add(NewsAmountFragment.newInstance(newsBeanArrayList.get(i)));
//        }


        for (int i = 0; i < channel.length; i++){
            titles.add(channel[i]);
            fragments.add(NewsAmountFragment.newInstance(channel[i]));
        }
        mFragmentAdapter = new FragmentAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(mFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabsFromPagerAdapter(mFragmentAdapter);
        progressCancel();
    }


}
