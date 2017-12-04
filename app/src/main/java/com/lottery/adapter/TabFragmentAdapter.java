package com.lottery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.lottery.R;

import java.util.List;

public class TabFragmentAdapter implements RadioGroup.OnCheckedChangeListener {
    private List<Fragment> fragments;
    private RadioGroup radioGroup;
    private FragmentActivity fragmentActivity;
    private int fragmentContentId;
    private int currentTab;

    private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener;

    public TabFragmentAdapter(FragmentActivity fragmentActivity, List<Fragment> fragments, int fragmentContentId,
                              RadioGroup radioGroup) {
        this.fragments = fragments;
        this.radioGroup = radioGroup;
        this.fragmentActivity = fragmentActivity;
        this.fragmentContentId = fragmentContentId;
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        ft.add(fragmentContentId, fragments.get(0));
        ft.commit();
        radioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for (int i = 0; i < this.radioGroup.getChildCount(); i++) {
            if (this.radioGroup.getChildAt(i).getId() == checkedId) {
                Fragment fragment = fragments.get(i);
                FragmentTransaction ft = obtainFragmentTransaction(i);
                getCurrentFragment().onPause();
                if (fragment.isAdded()) {
                    fragment.onResume();
                } else {
                    ft.add(fragmentContentId, fragment);
                }
                showTab(i);
                ft.commit();
                if (null != onRgsExtraCheckedChangedListener) {
                    onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(radioGroup, checkedId, i);
                }
            }
        }

    }

    private void showTab(int idx) {
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(idx);

            if (idx == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        currentTab = idx;
    }

    private FragmentTransaction obtainFragmentTransaction() {
        return fragmentActivity.getSupportFragmentManager().beginTransaction();
    }

    private FragmentTransaction obtainFragmentTransaction(int index) {
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        // 设置切换动画
        if (index > currentTab) {
            ft.setCustomAnimations(R.anim.activity_into_from_right, R.anim.activity_out_to_left);
        } else {
            ft.setCustomAnimations(R.anim.activity_into_from_left, R.anim.activity_out_to_right);
        }
        return ft;
    }


    private Fragment getCurrentFragment() {
        return fragments.get(currentTab);
    }

    public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
        return onRgsExtraCheckedChangedListener;
    }

    public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
    }

    public static class OnRgsExtraCheckedChangedListener {

        public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {


        }
    }
}
