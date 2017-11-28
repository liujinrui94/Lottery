package com.lottery.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.lottery.base.AppApplication;


public class ToastUtils
{
	private static Context context = AppApplication.instance;
	// 单例模式, 构造函数私有化
	private ToastUtils()
	{

	}

	private static ToastUtils toastUtil;
	private Toast toast = null;

	
	public static ToastUtils getInstance()
	{
		if (toastUtil == null)
		{
			toastUtil = new ToastUtils();
		}
		return toastUtil;
	}
	public  void showShortToast(String msg) {
		if (toast != null) {
			toast.cancel();
		}
		toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public void showMiddleToast(String content)
	{
		if (null == toast)
		{
			toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
		}
		else
		{
			toast.setText(content);
		}
		toast.show();
	}

	
	public void showMiddleLengthToast( String content)
	{
		if (null == toast)
		{
			toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
		}
		else
		{
			toast.setText(content);
		}
		toast.show();
	}

	
	public void showNormalToast( String content)
	{
		if (null == toast)
		{
			toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
		}
		else
		{
			toast.setText(content);
		}
		toast.show();
	}

	
	public void showLongToast( String content)
	{
		if (null == toast)
		{
			toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
		}
		else
		{
			toast.setText(content);
		}
		toast.show();
	}

	
	public void showBottomToast(String content)
	{
		if (null == toast)
		{
			toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		}
		else
		{
			toast.setText(content);
		}
		toast.show();
	}
}
