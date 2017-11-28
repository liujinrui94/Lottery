
package com.lottery.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

public class StringUtil
{

	public static boolean isEmpty(Object obj)
	{

		return null == obj || "".equals(obj.toString().trim());
	}

	public static boolean isNotEmpty(Object obj)
	{

		return !isEmpty(obj);
	}

	public static String getSequenceId()
	{
		String mark = String.valueOf(System.currentTimeMillis());
		return mark;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentlyDateTime()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new Date());
	}

	@SuppressLint("SimpleDateFormat")
	public static String transformDateTime(long t)
	{
		Date date = new Date(t);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentlyDate()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(new Date());
	}

	public static String getUUID()
	{
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	
	public static SpannableString handlerText(String str, int start, Context context)
	{
		SharedPreferences sh = context.getSharedPreferences("dpi", 0);
		SpannableString sp = new SpannableString(str);
		if (sh.getInt("dpi_type", 0) == 1)
		{
			sp.setSpan(new AbsoluteSizeSpan(25), start, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		else
		{
			sp.setSpan(new AbsoluteSizeSpan(18), start, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		// sp.setSpan(new
		// ForegroundColorSpan(R.color.btn_font_black_white),0,str.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sp;
	}

	public static String returnNotNull(String name)
	{

		return StringUtil.isEmpty(name) ? "" : name;
	}

	public static boolean isEmpty(String s)
	{
		if (null == s)
			return true;
		if (s.length() == 0)
			return true;
		if (s.trim().length() == 0)
			return true;
		return false;
	}

	public static String formatDate(String date)
	{
		return date.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").trim();
	}
}
