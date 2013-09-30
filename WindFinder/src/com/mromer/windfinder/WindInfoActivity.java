package com.mromer.windfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mromer.windfinder.adapter.WindInfoSlidePagerAdapter;
import com.mromer.windfinder.bean.Forecast;
import com.mromer.windfinder.manager.ContinentManager;
import com.mromer.windfinder.task.ForecastLoadTaskResultI;
import com.mromer.windfinder.task.ForecastTaskResult;
import com.mromer.windfinder.task.GetForecastTask;
import com.mromer.windfinder.utils.ActivityUtil;
import com.mromer.windfinder.utils.AlertUtils;
import com.mromer.windfinder.utils.SharedPreferencesUtil;

public class WindInfoActivity extends ActionBarActivity  {

	private final String TAG = this.getClass().getName();

	private final int RESULT_SETTINGS = 1;

	private ActionBar actionBar;


	/**
	 * The pager widget, which handles animation and allows swiping horizontally to access previous
	 * and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;


	private ForecastTaskResult forecastTaskResult;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.d(TAG, "onCreate " + TAG);

		super.onCreate(savedInstanceState);	
		
		ContinentManager.resetInstance();

		setContentView(R.layout.wind_info);		
		
		IncomingAlarm.cancelNotification(this);
		
		addToPushNotifications();

		Map<String, String> stationsSelected = SharedPreferencesUtil.getStationsSelected(this);

		List<String> stations = new ArrayList<String>();
		for (String key : stationsSelected.keySet()) {

			stations.add(key);

		}

		processGetForecastTask(stations);

	}

	private void processGetForecastTask(List<String> stations) {

		new GetForecastTask(this, new ForecastLoadTaskResultI() {

			@Override
			public void taskSuccess(ForecastTaskResult result) {
				forecastTaskResult = result;

				createViewPager(forecastTaskResult);
				
				setActionBar(R.string.app_name);

			}

			@Override
			public void taskFailure(ForecastTaskResult result) {
				AlertUtils.showAlert(WindInfoActivity.this, result.getDesc(), 
						getResources().getString(R.string.accept));				
			}
		}, stations, true).execute();

	}

	protected void setActionBar(int idTittle) {

		actionBar = getSupportActionBar();	

		actionBar.setTitle(getResources().getString(idTittle));	
		

	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case android.R.id.home:

			super.onBackPressed();

			return true;

		case R.id.action_add_Station:

			ActivityUtil.toNextActivity(this, ContinentActivity.class);

			return true;

		case R.id.action_settings:

			preferencesAction();			

			return true;

		case R.id.action_remove:

			removeAction();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}


	private void removeAction() {
		
		if (forecastTaskResult == null || forecastTaskResult.getForecastList() == null
				|| forecastTaskResult.getForecastList().size() == 0) {
			
			AlertUtils.showAlert(this, "Add a station", "Accept");
			
			return;
		}
		
		
		final Forecast forecast = forecastTaskResult.getForecastList().get(mPager.getCurrentItem());

		DialogInterface.OnClickListener positiveAction = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				forecastTaskResult = removeStation(forecast.getStationForecast().getId(),
						forecastTaskResult);

				SharedPreferencesUtil.removeStationToSharedPreferences(WindInfoActivity.this, forecast.getStationForecast().getId());

				mPagerAdapter = new WindInfoSlidePagerAdapter(getSupportFragmentManager(), 
						forecastTaskResult.getForecastList());	

				mPager.setAdapter(mPagerAdapter);

				mPagerAdapter.notifyDataSetChanged();

			}
		};

		DialogInterface.OnClickListener negativeAction = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {


			}
		};


		AlertUtils.showAlertWithAction(this, "Do you want remove " + 
				forecast.getStationForecast().getName() + "?", "Aceptar" , "Cancelar", 
				positiveAction, negativeAction);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.wind_menu, menu);		
		
		return true;
	}


	private void createViewPager(ForecastTaskResult forecastTaskResult) {


		mPager = (ViewPager) findViewById(R.id.pager);	
		mPager.setOffscreenPageLimit(0);

		mPagerAdapter = new WindInfoSlidePagerAdapter(getSupportFragmentManager(), 
				forecastTaskResult.getForecastList());	

		mPager.setAdapter(mPagerAdapter);

		mPagerAdapter.notifyDataSetChanged();
	}


	private ForecastTaskResult removeStation(String stationId, ForecastTaskResult forecastTaskResult) {

		ForecastTaskResult result = new ForecastTaskResult();
		List<Forecast> forecastList = new ArrayList<Forecast>();
		result.setForecastList(forecastList);

		for (Forecast forecast : forecastTaskResult.getForecastList()) {

			if (!stationId.equals(forecast.getStationForecast().getId())){
				forecastList.add(forecast);
			}
		}

		return result;

	}

	public void doMoreInfo(View v) {

		String dateTag = (String) v.getTag();

		Forecast forecast = forecastTaskResult.getForecastList().get(mPager.getCurrentItem());

		Intent intent = new Intent(this, MoreInfoActivity.class);

		intent.putExtra(MoreInfoActivity.BUNDLE_FORECAST_INFO, forecast);
		intent.putExtra(MoreInfoActivity.BUNDLE_FORECAST_INFO_DATE, dateTag);

		startActivity(intent);		

	}

	public void preferencesAction() {	

		if (forecastTaskResult == null || forecastTaskResult.getForecastList() == null
				|| forecastTaskResult.getForecastList().size() == 0) {
			
			AlertUtils.showAlert(this, "Add a station", "Accept");
			
			return;
		}
		
		Forecast forecast = forecastTaskResult.getForecastList().get(mPager.getCurrentItem());

		Intent intent = new Intent(this, PreferenceWithHeaders.class);

		String stationId = forecast.getStationForecast().getId();
		String stationName = forecast.getStationForecast().getName();

		intent.putExtra(PreferenceWithHeaders.BUNDLE_PREFERENCE_NAME, stationId);
		intent.putExtra(PreferenceWithHeaders.BUNDLE_STATION_NAME, stationName);

		startActivityForResult(intent, RESULT_SETTINGS);	

	}


	private void addToPushNotifications() {

		PendingIntent  pi = PendingIntent.getBroadcast( this, 0, new Intent("com.mromer.windfinder"), 0);
		AlarmManager am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 
				10000, 10000, pi);

	}
}
