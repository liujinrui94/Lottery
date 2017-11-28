package com.lottery.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TabHost;

public class MenuTabHost extends TabHost {
	public final static String TAB_TAG_CHAT = "chat";
	public final static String TAB_TAG_CONTACTS = "contacts";
	public final static String TAB_TAG_APP_CENTER = "appcenter";
	public final static String TAB_TAG_CIRCLES = "circle";

	private List<MenuTabItem> menuTabItemList = new ArrayList<MenuTabItem>();

	public MenuTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MenuTabHost(Context context) {
		super(context);
	}

	@Override
	public void setCurrentTab(int index) {
		MenuTabItem menuTabItem = getMenuTabItemByIndex(index);
		if (index == getCurrentTab()) {
			if (null != menuTabItem.getOnReClickListener()) {
				menuTabItem.getOnReClickListener().onReClick(menuTabItem);
			}
		} else {
			if(null != menuTabItem.getBeforeTabChangeListener()){
				menuTabItem.getBeforeTabChangeListener().beforeTabChange(menuTabItem);
			}
			super.setCurrentTab(index);
		}
	}

	/**
     * 添加底部菜单
     * 
     * @param id 底部菜单唯一标示.
     * @param intent 底部菜单对应的intent.
     * @param drawable 底部菜单对应图标.
     * @param name 底部菜单名称.
     * 
     */
	public void addMenuItem(MenuTabItem tabItem){
		menuTabItemList.add(tabItem);
		TabSpec tSpec = newTabSpec(tabItem.getTag());
		tSpec.setContent(tabItem.getIntent());
		tSpec.setIndicator(tabItem.getView());
		addTab(tSpec);
	}
	
	private MenuTabItem getMenuTabItemByIndex(int index) {
		return menuTabItemList.get(index);
	}
}
