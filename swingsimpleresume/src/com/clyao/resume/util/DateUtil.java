package com.clyao.resume.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String getTodateString() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String str = simpleDateFormat.format(new Date());
		return str;
	}

	public static String getWeekOfDateString() {
		String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	}
	
	public static String getCurrentTimeString(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		String str = simpleDateFormat.format(new Date());
		return str;
	}

}
