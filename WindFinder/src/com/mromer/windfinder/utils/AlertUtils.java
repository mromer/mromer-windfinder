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


	public static void showAlertWithAction(Context context, String message, String positiveButtonString, 
			String negativeButtonString, DialogInterface.OnClickListener positiveAction, 
			DialogInterface.OnClickListener negativeAction) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
		.setPositiveButton(positiveButtonString, positiveAction)
		.setNegativeButton(negativeButtonString, negativeAction);
		
		// Create the AlertDialog object and show it
		builder.create().show();
	}

}
