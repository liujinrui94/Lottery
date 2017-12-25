package com.lottery.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.lottery.R;
import com.lottery.base.BaseFragment;
import com.lottery.bean.HomeBean;
import com.lottery.constant.Common;
import com.lottery.constant.Constant;
import com.lottery.ui.activity.lottery.KJZSActivity;
import com.lottery.utils.GlideUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/22 8:25
 * @description:
 */
public class HomeFragment extends BaseFragment {

    private View rootView = null;
    private String url = "https://vipc.cn/columns/digit?in=result_content";

    @BindView(R.id.home_main_webView)
    WebView webView;

    @BindView(R.id.fragment_home_roll_pager_view)
    RollPagerView rollPagerView;
    private int[] imageUrls = {R.mipmap.lunbo1, R.mipmap.remen_head
    };
    String javascript = "javascript:function hideOther() {"
            + "if(null!=document.getElementsByClassName('vMod_topBar')[0]){document.getElementsByClassName('vMod_topBar')[0].style.display = 'none';}" +
            "if(null!=document.getElementsByClassName('vFooter2')[0]){document.getElementsByClassName('vFooter2')[0].style.display = 'none';}" +
            "if(null!=document.getElementsByClassName('vLottery_info_buttons')[0]){document.getElementsByClassName('vLottery_info_buttons')[0].style.display = 'none';}" +
            "}";
    private List<HomeBean> homeBeanList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        progressShow();
        webView.setVisibility(View.GONE);
        initData();
        initView();
        return rootView;
    }

    private void initData() {


    }

    private void initView() {

        rollPagerView.setAdapter(new LoopPagerAdapter(rollPagerView) {
            @Override
            public View getView(ViewGroup container, int position) {
                ImageView view = new ImageView(container.getContext());
                GlideUtils.getInstance().loadLocalImage(imageUrls[position]
                        , view);
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return view;
            }

            @Override
            public int getRealCount() {
                return imageUrls.length;
            }
        });

//        webView.loadDataWithBaseURL(null, Html.homehtml, "text/html", "utf-8", null);
//        webView.loadUrl("http://zxwap.caipiao.163.com/");
        webView.loadUrl(Constant.DASHI);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setWebViewClient(client);
        webView.setWebChromeClient(chromeClient);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Intent mIntent = new Intent(getContext(), RunlotteryActivity.class);
//            mIntent.putExtra("url", url);
//            mIntent.putExtra("title", "详情");
//            startActivity(mIntent);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            boolean fist = sharedPreferences.getBoolean(Common.FINISH_LOGIN, false);
            if (fist) {
                Intent mIntent = new Intent(getContext(), KJZSActivity.class);
                mIntent.putExtra("url", url);
                mIntent.putExtra("title", "资讯详情");
                startActivity(mIntent);
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Common.FINISH_LOGIN, true);
                editor.apply();
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressCancel();
            view.loadUrl(javascript);
            view.loadUrl("javascript:hideOther();");
            webView.setVisibility(View.VISIBLE);

        }
    };
    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
        }
    };
}
