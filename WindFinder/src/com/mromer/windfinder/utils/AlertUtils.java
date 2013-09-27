package com.mromer.windfinder.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertUtils {


	public static void showAlert(Context context, String message, String positiveButtonString) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
		.setPositiveButton(positiveButtonString, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				
			}
		});
		// Create the AlertDialog object and show it
		builder.create().show();
	}

}
