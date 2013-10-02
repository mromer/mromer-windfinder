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
import com.mromer.windfinder.utils.ResourcesUtil;
import com.mromer.windfinder.utils.StringUtils;

/**
 * Show forecast info about a station and a date.
 * 
 * */
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
		
		// Get extras
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
				// If we have the same date
				addInfoForecastItemRow(forecastItem, dataInfo, inflater);
			}			
		}		
	}

	
	/**
	 * Draw a row time and data info rows.
	 * */
	private void addInfoForecastItemRow(ForecastItem forecastItem, LinearLayout parentView, LayoutInflater inflater) {
		
		addItemTimeRow(parentView, inflater, StringUtils.toTime(forecastItem.getTime()));
		
		for (Entry<String, ForecastData> entry : forecastItem.getForecastDataMap().entrySet()){
			String title = ResourcesUtil.getForecastTitle(this, entry.getKey());
			String value = getValue(entry.getValue());
			
			if (title != null && value != null) {
				addItemRow(parentView, inflater, title, value);
			}
		}
	}
	
	
	/**
	 * Add a row with the time info in parentView.
	 * */
	private void addItemTimeRow(LinearLayout parentView,
			LayoutInflater inflater, String time) {

		LinearLayout row = (LinearLayout) inflater.inflate(
				R.layout.more_info_time_row, parentView, false);
		
		TextView timeTv = (TextView) row.findViewById(R.id.time);
		timeTv.setText(time + " ");		
		
		parentView.addView(row);		
	}
	

	/**
	 * Return the formated string value + unit in forecastData.
	 * */
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
	

	/**
	 * Add a row with info title : value
	 * */
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
	

	private void setActionBar(String tittle) {

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
