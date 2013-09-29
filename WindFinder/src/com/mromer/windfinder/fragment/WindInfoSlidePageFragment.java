package com.mromer.windfinder.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mromer.windfinder.R;
import com.mromer.windfinder.bean.Forecast;
import com.mromer.windfinder.bean.ForecastItem;
import com.mromer.windfinder.utils.IconUtil;
import com.mromer.windfinder.utils.StringUtils;

public class WindInfoSlidePageFragment extends Fragment {

	private Forecast forecast;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	

		forecast = (Forecast) getArguments()
				.getSerializable("FORECAST_INFO");

	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.activity_country, container, false);		

		TextView tv = (TextView) rootView.findViewById(R.id.stationName);
		
		tv.setText(forecast.getStationForecast().getName());


		String actualDate = "";

		LinearLayout chart = null;

		for (ForecastItem forecastItem : forecast.getStationForecast().getForecastItems()) {	

			if (!actualDate.equals(forecastItem.getDate())) {			

				if (chart != null) {
					((LinearLayout) rootView.findViewById(R.id.dataRow)).addView(chart);
				}

				chart = (LinearLayout) inflater.inflate(
						R.layout.wind_info_chart2, (LinearLayout) rootView.findViewById(R.id.dataRow), false);

				actualDate = forecastItem.getDate();
				// Add row date
				addDataDate(chart, forecastItem, inflater);

				addDataInfo(chart, inflater, forecast.getStationForecast().getTimezone());
			}

			addDataForecast(chart, forecastItem, inflater);					

		}

		if (chart != null) {
			((LinearLayout) rootView.findViewById(R.id.dataRow)).addView(chart);
		}

		return rootView;
	}	




	private void addDataTime(LinearLayout parentView, ForecastItem forecastItem, LayoutInflater inflater) {

		LinearLayout column = (LinearLayout) inflater.inflate(
				R.layout.wind_info_chart2, parentView, false);		


		TextView tvTime = (TextView) column.findViewById(R.id.time);
		tvTime.setText(forecastItem.getDate() + " " + forecastItem.getTime());

		TextView tv1 = (TextView) column.findViewById(R.id.air_temperature);
		tv1.setText(forecastItem.getForecastDataMap().get("air_temperature").getValue());

		TextView tv2 = (TextView) column.findViewById(R.id.water_temperature);
		tv2.setText(forecastItem.getForecastDataMap().get("water_temperature").getValue());

		TextView tv3 = (TextView) column.findViewById(R.id.wind_direction);
		tv3.setText(forecastItem.getForecastDataMap().get("wind_direction").getValue());

		TextView tv4 = (TextView) column.findViewById(R.id.wind_speed);
		tv4.setText(forecastItem.getForecastDataMap().get("wind_speed").getValue());

		TextView tv5 = (TextView) column.findViewById(R.id.wind_gusts);
		tv5.setText(forecastItem.getForecastDataMap().get("wind_gusts").getValue());

		TextView tv6 = (TextView) column.findViewById(R.id.weather);
		tv6.setText(forecastItem.getForecastDataMap().get("weather").getValue());

		TextView tv7 = (TextView) column.findViewById(R.id.clouds);
		tv7.setText(forecastItem.getForecastDataMap().get("clouds").getValue());

		TextView tv8 = (TextView) column.findViewById(R.id.precipitation);
		tv8.setText(forecastItem.getForecastDataMap().get("precipitation").getValue());

		TextView tv9 = (TextView) column.findViewById(R.id.precipitation_type);
		tv9.setText(forecastItem.getForecastDataMap().get("precipitation_type").getValue());

		TextView tv10 = (TextView) column.findViewById(R.id.wave_height);
		tv10.setText(forecastItem.getForecastDataMap().get("wave_height").getValue());

		TextView tv11 = (TextView) column.findViewById(R.id.wave_direction);
		tv11.setText(forecastItem.getForecastDataMap().get("wave_direction").getValue());

		TextView tv12 = (TextView) column.findViewById(R.id.wave_period);
		tv12.setText(forecastItem.getForecastDataMap().get("wave_period").getValue());

		TextView tv13 = (TextView) column.findViewById(R.id.air_pressure);
		tv13.setText(forecastItem.getForecastDataMap().get("air_pressure").getValue());

		parentView.addView(column);
	}


	private void addDataDate(LinearLayout parentView, ForecastItem forecastItem, LayoutInflater inflater) {

		LinearLayout column = (LinearLayout) inflater.inflate(
				R.layout.wind_info_chart_row_date, parentView, false);

		TextView tvDate = (TextView) column.findViewById(R.id.date);
		tvDate.setText(StringUtils.toDate(forecastItem.getDate()));		
		
		ImageButton moreInfoButton = (ImageButton) column.findViewById(R.id.moreInfo);
		moreInfoButton.setTag(forecastItem.getDate());
		
		parentView.addView(column);
	}

	private void addDataInfo(LinearLayout parentView, LayoutInflater inflater, String timezone) {

		LinearLayout column = (LinearLayout) inflater.inflate(
				R.layout.wind_info_chart_column_names2, parentView, false);

		
		TextView tvTimezone = (TextView) column.findViewById(R.id.timezone);
		tvTimezone.setText(StringUtils.toTimezone(timezone));	
		
		parentView.addView(column);
	}


	private void addDataForecast(LinearLayout parentView, ForecastItem forecastItem, LayoutInflater inflater) {

		LinearLayout column = (LinearLayout) inflater.inflate(
				R.layout.wind_info_chart_column_data2, parentView, false);

		TextView tvTime = (TextView) column.findViewById(R.id.time);
		tvTime.setText(StringUtils.toTime(forecastItem.getTime()));


		ImageView ivWindDirection = (ImageView) column.findViewById(R.id.wind_direction);
		ivWindDirection.setImageResource(IconUtil.
				getDirectionIconId(forecastItem.getForecastDataMap().get("wind_direction").getValue()));

		TextView tvWindSpeed = (TextView) column.findViewById(R.id.wind_speed);
		tvWindSpeed.setText(forecastItem.getForecastDataMap().get("wind_speed").getValue() + " " 
				+ forecastItem.getForecastDataMap().get("wind_speed").getUnit());


		TextView tvAirTemperature = (TextView) column.findViewById(R.id.air_temperature);
		tvAirTemperature.setText(StringUtils.removeDecimals(forecastItem.getForecastDataMap()
				.get("air_temperature").getValue()) + "º");		

		TextView tvClouds = (TextView) column.findViewById(R.id.clouds);
		tvClouds.setText(forecastItem.getForecastDataMap().get("clouds").getValue());
		

		parentView.addView(column);
	}



























}