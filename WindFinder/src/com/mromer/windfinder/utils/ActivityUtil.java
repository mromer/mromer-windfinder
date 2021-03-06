package com.mromer.windfinder.utils;

import android.content.Context;
import android.content.Intent;

public class ActivityUtil {
	
	/**
	 * Go to nextActivity without extra data
	 * */
	public static void toNextActivity(Context context, 
			 Class<?>  nextActivityClass) {

		Intent intent = new Intent(context, nextActivityClass);		

		context.startActivity(intent);
	}
}
