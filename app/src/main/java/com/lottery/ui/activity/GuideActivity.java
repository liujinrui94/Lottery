package com.lottery.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lottery.R;
import com.lottery.base.BaseActivity;
import com.lottery.constant.Common;
import com.lottery.constant.Constant;
import com.lottery.widget.BezierRoundView;
import com.lottery.widgetadapter.GuideViewPagerAdapter;

import java.util.ArrayList;


public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ArrayList<View> views;// 定义一个ArrayList来存放View
    private ViewPager viewPager;// 定义ViewPager对象
    private GuideViewPagerAdapter vpAdapter;// 定义ViewPager适配器
    private Button intoSplash;//进入splash按钮

    // 引导图片资源
    private static final int[] pics = {R.mipmap.one,
            R.mipmap.two, R.mipmap.three, R.mipmap.four};


    protected void initView() {
        setContentView(R.layout.activity_guide);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstTime = sharedPreferences.getBoolean(Constant.LOGIN, true);
        if (firstTime) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constant.LOGIN, false);
            editor.apply();
        } else {
            boolean open = sharedPreferences.getBoolean(Common.FINISH_LOGIN, true);
            if (open) {
                intoSplashActivity();
                return;
            } else {
                Intent intent = new Intent(GuideActivity.this, TabMainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }
        setView(false);
        // 实例化ArrayList对象
        views = new ArrayList<>();
        // 实例化ViewPager
        viewPager = (ViewPager) findViewById(R.id.guide_vp);
        // 实例化ViewPager适配器
        vpAdapter = new GuideViewPagerAdapter(views);
        intoSplash = (Button) findViewById(R.id.guide_btn);
        intoSplash.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                intoSplashActivity();
            }
        });

        //定义一个布局并设置参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //初始化引导页图片
        for (int pic : pics) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);//防止图片不填充满屏幕
            iv.setImageResource(pic);//加载图片资源
            views.add(iv);
        }
        viewPager.setAdapter(vpAdapter);//设置数据
        viewPager.setOnPageChangeListener(this);//设置监听
        BezierRoundView bezRound = (BezierRoundView) findViewById(R.id.guide_bezier);
        bezRound.attachViewPage(viewPager);

    }


    /**
     * 初始化viewpager需加载的数据
     */
    protected void initData() {
    }



    @Override
    public void onClick(View view) {

    }

    private void intoSplashActivity() {
        Intent intent = new Intent(GuideActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();

    }

    /**
     * 当前页面滑动时调用
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 新的页面被选中时调用
     */
    @Override
    public void onPageSelected(int position) {
        // 设置底部小点选中状态
        if (position == pics.length - 1) {
            intoSplash.setVisibility(View.VISIBLE);
        } else {
            intoSplash.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 滑动状态改变时调用
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}