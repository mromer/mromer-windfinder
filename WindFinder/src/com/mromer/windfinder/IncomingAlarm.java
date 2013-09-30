package com.mromer.windfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.mromer.windfinder.bean.Forecast;
import com.mromer.windfinder.bean.ForecastItem;
import com.mromer.windfinder.task.ForecastLoadTaskResultI;
import com.mromer.windfinder.task.ForecastTaskResult;
import com.mromer.windfinder.task.GetForecastTask;
import com.mromer.windfinder.utils.SharedPreferencesUtil;

public class IncomingAlarm extends BroadcastReceiver {

	private Context context;
	private final static int NOTIFICATION_ID = 1;	
	
	public void onReceive(Context context, Intent intent) {

		this.context = context;

		// Phone call state change then this method will automaticaly called
		//		Toast.makeText(context, "prueba", Toast.LENGTH_SHORT).show();
		processNotification();
	}


	private void sendNotificacion(String tittle) {
		
		Log.d("", "Sending notification " + tittle);
		
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(context)
		.setSmallIcon(R.drawable.wind_icon_orange)
		.setContentTitle(context.getResources().getString(R.string.app_name))
		.setContentText(tittle);

		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(context, WindInfoActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(WindInfoActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
				stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.

		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());		

	}


	private void processNotification() {


		Map<String, String> stations = SharedPreferencesUtil.getStationsSelected(context);

		Log.d("", "stations " + stations);
		
		
		// Is notification set in preferences?
		List<String> stationsWithNotification = stationsWithNotification(stations);
		
		Log.d("", "stationsWithNotification " + stationsWithNotification);

		
		sendNotificationMenssagesTask(stationsWithNotification);
		

	}


	private void sendNotificationMenssagesTask(final List<String> stationsWithNotification) {

		 
		// Compare and add notification to the list
		if (stationsWithNotification.size() > 0) {
			// Get data
			new GetForecastTask(context, new ForecastLoadTaskResultI() {
				
				@Override
				public void taskSuccess(ForecastTaskResult result) {
					
					Log.d("", "sendNotificationMenssagesTaskSuccess ");
					
					sendNotificationMenssagesTaskSuccess(result, stationsWithNotification);
					
				}
				
				@Override
				public void taskFailure(ForecastTaskResult result) {
					// TODO Auto-generated method stub
					
				}
			}, stationsWithNotification, false).execute();			

						
		}
		
	}
	
	
	private void sendNotificationMenssagesTaskSuccess(ForecastTaskResult result,
			List<String> stationsWithNotification) {
		
		Log.d("", "sendNotificationMenssagesTaskSuccess ");
		Log.d("", "stationsWithNotification " + stationsWithNotification);
		
		List<String> notificationMenssages = new ArrayList<String>();
		
		// Compare data
		for (String stationId : stationsWithNotification) {
			String message = compareData(stationId, result);
			if (message != null){
				notificationMenssages.add(message);
			}
		}
		
		if (notificationMenssages.size() > 0) {
			sendNotificacion(notificationMenssages.toString());
		}
		
	}


	private String compareData(String stationId, ForecastTaskResult forecastTaskResult) {
		
		String messageRetult = null;
		
		Map<String, ?> stationPreferences = SharedPreferencesUtil.getStationPreferences(context, stationId);
		String windDirection = (String) stationPreferences.get(SharedPreferencesUtil.SHARED_WIND_DIRECTION);
		
		
		Integer windLevel = 0;		
		String windLevelString = (String) stationPreferences.get(SharedPreferencesUtil.SHARED_WIND_LEVEL);
		if (windLevelString != null ) {
			windLevel = Integer.parseInt((String) stationPreferences.get(SharedPreferencesUtil.SHARED_WIND_LEVEL));
		}
		
		
		for (Forecast forecast : forecastTaskResult.getForecastList()) {
			if (stationId.equals(forecast.getStationForecast().getId())) {
				
				for (ForecastItem forecastItem : forecast.getStationForecast().getForecastItems()) {
					
					Integer windLevelCandidate =
							Integer.parseInt(forecastItem.getForecastDataMap()
									.get(ForecastItem.WIND_SPEED).getValue());
					
					String windDirectionCandidate = forecastItem.getForecastDataMap()
							.get(ForecastItem.WIND_DIRECTION).getValue();					
					
					if (windLevel <= windLevelCandidate ||
							windDirection.equals(windDirectionCandidate)) {
						
						messageRetult = context.getResources().getString(R.string.wind_info_push) 
								+ " " + forecast.getStationForecast().getName();
						break;
					}
				}
				
			}			
		}
		
		return messageRetult;
	}


	private List<String> stationsWithNotification(Map<String, String> stations) {

		List<String> stationsWithNotification = new ArrayList<String>();

		for (Entry<String, String> entry : stations.entrySet()) {

			String stationId = entry.getKey();

			// Is set notifications?
			if (isSetNotificacions(stationId)){
				stationsWithNotification.add(stationId);
			}		

		}

		return stationsWithNotification;
	}

	private boolean isSetNotificacions(String idStation) {
		
		boolean result = false;

		Map<String, ?> stationPreferences = SharedPreferencesUtil.getStationPreferences(context, idStation);

		if (stationPreferences != null &&
				stationPreferences.get(SharedPreferencesUtil.PROPERTY_NOTIFICATION_ACTIVED) != null) {
			result = (Boolean) stationPreferences.get(SharedPreferencesUtil.PROPERTY_NOTIFICATION_ACTIVED);
		}
				

		return result;
	}




	public static void cancelNotification(Context ctx) {
	    String ns = Context.NOTIFICATION_SERVICE;
	    NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
	    nMgr.cancel(NOTIFICATION_ID);
	}


}