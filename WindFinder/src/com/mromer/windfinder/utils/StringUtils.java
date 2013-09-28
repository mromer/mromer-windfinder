package com.mromer.windfinder.utils;

import java.text.DecimalFormat;

public class StringUtils {

	public static String toTime(String time) {

		String hour = time.substring(0, 2);
		String minute = time.substring(2, 4);

		return hour + ":" + minute;
	}

	public static String toDate(String date) {

		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);

		return year + "/" + month + "/" + day;
	}
	
	
	public static String oneDecimal(String data) {
		
		Float dataFloat =Float.parseFloat(data);
		
		DecimalFormat df = new DecimalFormat("0.0");
		df.setMaximumFractionDigits(1);
		data = df.format(dataFloat);

		return data;
	}

	public static String removeDecimals(String value) {

		float f = Float.valueOf(value);
		
		int i  = (int) f;
		
		return Integer.toString(i);
	}

}