package com.lottery.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lottery.base.BaseWebViewFragment;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/10 15:18
 * @description:
 */
public class SsczstFragment  {

    public static BaseWebViewFragment newInstance(String sscUrl,String javascript) {
        BaseWebViewFragment newFragment = new BaseWebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fragment_url", sscUrl);
        bundle.putString("javascript", javascript);
        newFragment.setArguments(bundle);
        return newFragment;
    }


}
