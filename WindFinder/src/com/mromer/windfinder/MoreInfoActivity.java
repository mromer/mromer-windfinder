package com.mromer.windfinder;

import java.util.Map.Entry;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mromer.windfinder.bean.Forecast;
import com.mromer.windfinder.bean.ForecastData;
import com.mromer.windfinder.bean.ForecastItem;
import com.mromer.windfinder.utils.StringUtils;

public class MoreInfoActivity extends ActionBarActivity {

	private final String TAG = this.getClass().getName();
	
	public final static String BUNDLE_FORECAST_INFO = "FORECAST_INFO";
	public final static String BUNDLE_FORECAST_INFO_DATE = "FORECAST_INFO_DATE";

	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreate " + TAG);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_info);
		
		Bundle bundle = getIntent().getExtras();
		
		Forecast forecast = (Forecast) bundle.getSerializable(BUNDLE_FORECAST_INFO);
		String date = bundle.getString(BUNDLE_FORECAST_INFO_DATE);
		
		setActionBar(forecast.getStationForecast().getName());	
		
		showData(date, forecast);

	}
	
	private void showData(String date, Forecast forecast) {


		// Set date
		TextView tvDate = (TextView) findViewById(R.id.date);
		tvDate.setText(StringUtils.toDate(date));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		LinearLayout dataInfo = (LinearLayout) findViewById(R.id.dataInfo);
		for (ForecastItem forecastItem : forecast.getStationForecast().getForecastItems()) {
			
			if (forecastItem.getDate().equals(date)) {
				addInfoForecastItemRow(forecastItem, dataInfo, inflater);
			}
			
		}
		
		
	}

	private void addInfoForecastItemRow(ForecastItem forecastItem, LinearLayout parentView, LayoutInflater inflater) {
		
		addItemTimeRow(parentView, inflater, StringUtils.toTime(forecastItem.getTime()));
		
		for (Entry<String, ForecastData> entry : forecastItem.getForecastDataMap().entrySet()){
			String title = getTitle(entry.getKey());
			String value = getValue(entry.getValue());
			
			if (title != null && value != null) {
				addItemRow(parentView, inflater, title, value);
			}
		}		
		
	}
	
	
	private void addItemTimeRow(LinearLayout parentView,
			LayoutInflater inflater, String time) {

		LinearLayout row = (LinearLayout) inflater.inflate(
				R.layout.more_info_time_row, parentView, false);
		
		TextView timeTv = (TextView) row.findViewById(R.id.time);
		timeTv.setText(time + " ");		
		
		parentView.addView(row);
		
	}

	private String getValue(ForecastData forecastData) {
		if (forecastData != null && forecastData.getValue() != null 
				&& forecastData.getValue().length() > 0) {
			
			String value = "";
			if (forecastData.getUnit() != null) {
				value = forecastData.getValue() + " " + forecastData.getUnit();
			} else {
				value = forecastData.getValue();
			}
			return value;
			
		}
		return null;
	}

	private String getTitle(String key) {
		
		int idString = -1;
		
		if (key.equals("air_temperature")) {			
			idString = R.string.air_temperature ;			
		} else if (key.equals("water_temperature")) {
			idString = R.string.water_temperature ;
		} else if (key.equals("wind_direction")) {
			idString = R.string.wind_direction ;
		} else if (key.equals("wind_speed")) {
			idString = R.string.wind_speed ;
		} else if (key.equals("wind_gusts")) {
			idString = R.string.wind_gusts ;
		} else if (key.equals("weather")) {
			idString = R.string.weather ;
		} else if (key.equals("clouds")) {
			idString = R.string.clouds ;
		} else if (key.equals("precipitation")) {
			idString = R.string.precipitation ;
		} else if (key.equals("precipitation_type")) {
			idString = R.string.precipitation_type ;
		} else if (key.equals("wave_height")) {
			idString = R.string.wave_height ;
		} else if (key.equals("wave_direction")) {
			idString = R.string.wave_direction ;
		} else if (key.equals("wave_period")) {
			idString = R.string.wave_period ;
		} else if (key.equals("air_pressure")) {
			idString = R.string.air_pressure ;
		}
		
		if (idString >= 0) {
			return getResources().getString(idString);
		} else {
			return null;
		}
		
		
	}

	private void addItemRow(LinearLayout parentView, LayoutInflater inflater,
			String title, String value) {
		LinearLayout row = (LinearLayout) inflater.inflate(
				R.layout.more_info_row, parentView, false);
		
		TextView titleTv = (TextView) row.findViewById(R.id.title);
		titleTv.setText(title);
		
		TextView valueTv = (TextView) row.findViewById(R.id.value);
		valueTv.setText(value);
		
		parentView.addView(row);		
		
	}

	protected void setActionBar(String tittle) {

		actionBar = getSupportActionBar();	

		actionBar.setTitle(tittle);		
		
		actionBar.setDisplayHomeAsUpEnabled(true);

	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case android.R.id.home:

			super.onBackPressed();

			return true;		

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
