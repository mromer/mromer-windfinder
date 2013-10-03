package com.mromer.windfinder.receiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.mromer.windfinder.R;
import com.mromer.windfinder.WindInfoActivity;
import com.mromer.windfinder.bean.Forecast;
import com.mromer.windfinder.bean.ForecastItem;
import com.mromer.windfinder.commons.ConstantsMain;
import com.mromer.windfinder.task.ForecastLoadTaskResultI;
import com.mromer.windfinder.task.ForecastTaskResult;
import com.mromer.windfinder.task.GetForecastTask;
import com.mromer.windfinder.utils.SharedPreferencesUtil;

/**
 * Manage the alarm manager.
 * Manage the alarm manager events and throw notifications.
 * */
public class IncomingAlarmReceiver extends BroadcastReceiver {

	private final String TAG = this.getClass().getName();

	private Context context;
	
	private static final  int NOTIFICATION_ID = 1;
	

	@Override
	public void onReceive(Context context, Intent intent) {

		this.context = context;

		processNotification();
	}

	
	/**
	 * Get the stations in shared preferences with enabled notifications and
	 * send the notification.
	 * */
	private void processNotification() {

		Map<String, String> stations = SharedPreferencesUtil.getStationsSelected(context);

		// Get the stations with notification actived.
		List<String> stationsWithNotificationEnabled = stationsWithNotificationEnabled(stations);

		// Is notification set in preferences?
		if (stationsWithNotificationEnabled.size() > 0) {
			sendNotificationMenssagesTask(stationsWithNotificationEnabled);
		}
	}


	/**
	 * Get the forecast, check if send notifications and it send it.
	 * */
	private void sendNotificationMenssagesTask(final List<String> stationsWithNotification) {

		// Run the GetForecastTask
		new GetForecastTask(context, new ForecastLoadTaskResultI() {

			@Override
			public void taskSuccess(ForecastTaskResult result) {

				sendNotificationMenssagesTaskSuccess(result, stationsWithNotification);
			}

			@Override
			public void taskFailure(ForecastTaskResult result) {
				Log.e(TAG, result.getDesc());

			}
		}, stationsWithNotification, false).execute();
	}


	/**
	 * Run when get forecast success. Check notifications and send it.
	 * */
	private void sendNotificationMenssagesTaskSuccess(ForecastTaskResult result,
			List<String> stationsWithNotification) {

		List<String> notificationMenssages = new ArrayList<String>();

		// Compare data preferences
		// Get the messages to notifications
		for (String stationId : stationsWithNotification) {
			String message = compareDataPreferences(stationId, result);
			if (message != null){
				notificationMenssages.add(message);
			}
		}

		if (notificationMenssages.size() > 0) {
			sendNotificacion(notificationMenssages.toString());
		}

	}


	/**
	 * Compare the preferences with forecast.
	 * */
	private String compareDataPreferences(String stationId, ForecastTaskResult forecastTaskResult) {

		String messageResult = null;

		String windDirection = SharedPreferencesUtil.getWindDirectionStation(context, stationId);
		Integer windLevel = SharedPreferencesUtil.getWindLevelStation(context, stationId);
		
		for (Forecast forecast : forecastTaskResult.getForecastList()) {
			if (stationId.equals(forecast.getStationForecast().getId())) {

				messageResult = getMessageWithCondition(forecast, windDirection, windLevel);				

				// Only get the first station
				if (messageResult != null) {
					break;
				}
			}			
		}

		return messageResult;
	}


	/**
	 * Return the message for the notification if check the conditions.
	 * 
	 * */
	private String getMessageWithCondition(Forecast forecast, String windDirection, Integer windLevel) {

		String messageResult = null;

		for (ForecastItem forecastItem : forecast.getStationForecast().getForecastItems()) {

			Integer windLevelCandidate = forecastItem.getWindSpeed();

			String windDirectionCandidate = forecastItem.getWindDirection();					

			if (windLevel <= windLevelCandidate ||
					windDirection.equals(windDirectionCandidate)) {
				// Only get the first station
				messageResult = context.getResources().getString(R.string.wind_info_push) 
						+ " " + forecast.getStationForecast().getName();
				break;
			}
		}

		return messageResult;
	}	

	
	/**
	 * Get the stations with enabled notification.
	 * */
	private List<String> stationsWithNotificationEnabled(Map<String, String> stations) {

		List<String> stationsWithNotification = new ArrayList<String>();

		for (Entry<String, String> entry : stations.entrySet()) {

			String stationId = entry.getKey();

			// Is set notifications?
			if (SharedPreferencesUtil.isNotificacionEnabled(context, stationId)){
				stationsWithNotification.add(stationId);
			}
		}

		return stationsWithNotification;
	}
	

	/**
	 * Remove the notification in bar
	 * */
	public static void cancelNotification(Context ctx) {
		String ns = Context.NOTIFICATION_SERVICE;
		
		NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
		
		nMgr.cancel(NOTIFICATION_ID);
	}


	/**
	 * Process that check periodically if send a notification. The period
	 * is set in <code>ConstantsMain.ALARM_MANAGER_REPETITION_TIME</code>
	 * */
	public static void startAlarmManager(Context context) {

		PendingIntent  pi = PendingIntent.getBroadcast( context, 0, new Intent(context, 
				IncomingAlarmReceiver.class), 0);
		
		AlarmManager am = (AlarmManager)(context.getSystemService( Context.ALARM_SERVICE ));
		
		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 
				ConstantsMain.ALARM_MANAGER_REPETITION_TIME, 
				ConstantsMain.ALARM_MANAGER_REPETITION_TIME, pi);
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
}