package com.lottery.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetConnect
{

	private NetConnect()
	{

	}

	private static NetConnect netConnect;

	
	public static NetConnect getInstance()
	{
		if (netConnect == null)
		{
			netConnect = new NetConnect();
		}
		return netConnect;
	}

	
	public boolean JudgeNet(Context ctx)
	{
		ConnectivityManager conManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		NetworkInfo mobileInfo = conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiInfo = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if ((networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) || (null != wifiInfo && wifiInfo.isAvailable() && wifiInfo.isConnected()) || (null != mobileInfo && mobileInfo.isAvailable() && mobileInfo.isConnected()))
		{
			return true;
		}
		return false;
	}

	
	public boolean isNetConnect(final Context ctx)
	{
		if (!JudgeNet(ctx))
		{
			ToastUtils.getInstance().showNormalToast("网络连接异常,请检查您的网络连接");
			return false;
		}
		else
		{
			return true;
		}
	}

	
	public static boolean isWIFIConnected(Context context)
	{
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (info != null)
		{
			return info.isConnected();
		}
		return false;
	}
}
