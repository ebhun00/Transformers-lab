package com.titan.Transformerslab.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String getCurrentDateIn_YYYYMMDD() {

		String pattern = "yyyyMMdd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(new Date());
		System.out.println(date);
		return date;
	}

	public static String getCurrentDateIn_MMDDYYYY() {
		String pattern = "MM/dd/yyyy HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(new Date());
		return date;
	}
}
