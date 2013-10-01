package com.mromer.windfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.mromer.windfinder.receiver.IncomingAlarmReceiver;
import com.mromer.windfinder.task.ForecastLoadTaskResultI;
import com.mromer.windfinder.task.ForecastTaskResult;
import com.mromer.windfinder.task.GetForecastTask;
import com.mromer.windfinder.utils.ActivityUtil;
import com.mromer.windfinder.utils.AlertUtils;
import com.mromer.windfinder.utils.SharedPreferencesUtil;

/**
 * Show the forecast stations calling to a web service.
 * The stations are get from sharedpreferences.
 * 
 * */
public class WindInfoActivity extends ActionBarActivity  {

	private final String TAG = this.getClass().getName();

	/** On activity result constant. */
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
	
	/** List of stations stored in SharedPreferences.*/
	private List<String> stations;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.d(TAG, "onCreate " + TAG);

		super.onCreate(savedInstanceState);			

		setContentView(R.layout.wind_info);	

		manageNotification();		

		stations = getListStationsSelected();
	}


	@Override
	protected void onResume() {

		super.onResume();

		// Get the forecast (http) and draw the viewpager
		processGetForecastTask(stations);

	};


	/**
	 * Get the stations selected from SharedPreferences.
	 * 
	 * @return <code>List<String></code> stations
	 * */
	private List<String> getListStationsSelected() {

		Map<String, String> stationsSelected = SharedPreferencesUtil.getStationsSelected(this);

		List<String> stations = new ArrayList<String>();
		for (String key : stationsSelected.keySet()) {

			stations.add(key);

		}

		return stations;
	}


	private void manageNotification() {
		// Delete notification in the bar
		IncomingAlarmReceiver.cancelNotification(this);

		IncomingAlarmReceiver.startAlarmManager(this);
	}
	

	/**
	 * Get the forecast (http) and draw the viewpager. If faid, show an alert.
	 * */ 
	private void processGetForecastTask(List<String> stations) {

		new GetForecastTask(this, new ForecastLoadTaskResultI() {

			@Override
			public void taskSuccess(ForecastTaskResult result) {
				
				processGetForecastTaskSuccess(result);
			}

			@Override
			public void taskFailure(ForecastTaskResult result) {
				
				AlertUtils.showAlert(WindInfoActivity.this, result.getDesc(), 
						getResources().getString(R.string.accept));		
				
			}
		}, stations, true).execute();
	}
	
	
	private void processGetForecastTaskSuccess(ForecastTaskResult result) {
		
		forecastTaskResult = result;

		createViewPager(forecastTaskResult);
		
		setActionBar(R.string.app_name);
	}
	

	protected void setActionBar(int idTittle) {

		actionBar = getSupportActionBar();	

		actionBar.setTitle(getResources().getString(idTittle));
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case android.R.id.home:

			toHome();

			return true;

		case R.id.action_add_Station:

			ActivityUtil.toNextActivity(this, ContinentActivity.class);

			return true;

		case R.id.action_settings:

			preferencesActionProcess();			

			return true;

		case R.id.action_remove:

			removeStationActionProcess();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Go to home screen.
	 * */
	private void toHome() {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
	}

	
	/**
	 * Check if there are stations and remove the station.
	 * */
	private void removeStationActionProcess() {

		// If there aren´t stations, show alert and finish
		if (forecastTaskResult == null || forecastTaskResult.getForecastList() == null
				|| forecastTaskResult.getForecastList().size() == 0) {

			String alertTitle = getResources().getString(R.string.action_add_station);
			String textButton = getResources().getString(R.string.accept);
			
			AlertUtils.showAlert(this, alertTitle, textButton);
			
		} else {
			removeStationAction();
		}		
	}

	
	/**
	 * Remove the station selected in viewpager.
	 * */
	private void removeStationAction() {
		
		// Get the current forecast
		final Forecast forecast = forecastTaskResult.getForecastList().get(mPager.getCurrentItem());

		// Accept button action
		DialogInterface.OnClickListener positiveAction = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				removeStationAcceptButtonAction(forecast);
			}
		};

		// Cancel button action
		DialogInterface.OnClickListener negativeAction = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		};
		
		showConfirm(forecast, positiveAction, negativeAction);
	}
	
	
	/**
	 * Show dialog to confirm
	 * */
	private void showConfirm(Forecast forecast, DialogInterface.OnClickListener positiveAction, 
			DialogInterface.OnClickListener negativeAction) {
		
		String warningText = getResources().getString(R.string.remove_station_warning);
		String acceptText = getResources().getString(R.string.accept);
		String cancelText = getResources().getString(R.string.cancel);
		AlertUtils.showAlertWithAction(this, warningText + " " +
				forecast.getStationForecast().getName() + "?", acceptText , cancelText, 
				positiveAction, negativeAction);
		
	}


	/**
	 * Accept button is pressed.
	 * */
	private void removeStationAcceptButtonAction(Forecast forecast) {
		
		// Remove from view
		forecastTaskResult = removeStation(forecast.getStationForecast().getId(),
				forecastTaskResult);

		// Remove from SharedPreferences
		SharedPreferencesUtil.removeStationToSharedPreferences(WindInfoActivity.this, forecast.getStationForecast().getId());

		mPagerAdapter = new WindInfoSlidePagerAdapter(getSupportFragmentManager(), 
				forecastTaskResult.getForecastList());	

		mPager.setAdapter(mPagerAdapter);

		mPagerAdapter.notifyDataSetChanged();
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


	/**
	 * Remove a station in <code>forecastTaskResult</code>.
	 * 
	 * @param stationId
	 * @param forecastTaskResult set of stations
	 * @return the new set of stations
	 * */
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

	
	/**
	 * Button more info pressed. There is a button per each chart of info.
	 * Go to the activity info.
	 * */
	public void doMoreInfo(View v) {
		// Get the date 
		String dateTag = (String) v.getTag();

		Forecast forecast = forecastTaskResult.getForecastList().get(mPager.getCurrentItem());

		Intent intent = new Intent(this, MoreInfoActivity.class);

		intent.putExtra(MoreInfoActivity.BUNDLE_FORECAST_INFO, forecast);
		intent.putExtra(MoreInfoActivity.BUNDLE_FORECAST_INFO_DATE, dateTag);

		startActivity(intent);		

	}

	
	/**
	 * Button settings pressed. Show the activity to set the notification preferences to the
	 * current station in viewpager.
	 * */
	private void preferencesActionProcess() {	

		if (forecastTaskResult == null || forecastTaskResult.getForecastList() == null
				|| forecastTaskResult.getForecastList().size() == 0) {

			String alertTitle = getResources().getString(R.string.action_add_station);
			String textButton = getResources().getString(R.string.accept);
			
			AlertUtils.showAlert(this, alertTitle, textButton);
			
		} else {
			preferencesAction();
		}
	}
	
	private void preferencesAction() {
		Forecast forecast = forecastTaskResult.getForecastList().get(mPager.getCurrentItem());

		Intent intent = new Intent(this, StationPreferencesActivity.class);

		String stationId = forecast.getStationForecast().getId();
		String stationName = forecast.getStationForecast().getName();

		intent.putExtra(StationPreferencesActivity.BUNDLE_PREFERENCE_NAME, stationId);
		intent.putExtra(StationPreferencesActivity.BUNDLE_STATION_NAME, stationName);

		startActivityForResult(intent, RESULT_SETTINGS);	
	}
}
