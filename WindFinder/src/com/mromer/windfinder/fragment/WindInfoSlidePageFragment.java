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
import com.mromer.windfinder.utils.ResourcesUtil;
import com.mromer.windfinder.utils.StringUtils;

public class WindInfoSlidePageFragment extends Fragment {

	public static String FORECAST_INFO = "FORECAST_INFO";
	public static String LIST_FORECAST_SIZE = "LIST_FORECAST_SIZE";
	public static String VIEW_POSITION = "VIEW_POSITION";

	private Forecast forecast;
	private int listForecastSize;
	private int viewPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	

		forecast = (Forecast) getArguments()
				.getSerializable(FORECAST_INFO);

		listForecastSize = getArguments()
				.getInt(LIST_FORECAST_SIZE);

		viewPosition = getArguments()
				.getInt(VIEW_POSITION);

	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.wind_info_fragment, container, false);	
		
		// Title + arrow buttons
		setHeaderView(rootView);

		String actualDate = "";

		// Layout for each date
		LinearLayout chart = null;

		for (ForecastItem forecastItem : forecast.getStationForecast().getForecastItems()) {	

			if (!actualDate.equals(forecastItem.getDate())) {			

				if (chart != null) {
					((LinearLayout) rootView.findViewById(R.id.dataRow)).addView(chart);
				}

				chart = (LinearLayout) inflater.inflate(
						R.layout.wind_info_chart2, (LinearLayout) rootView.findViewById(R.id.dataRow), false);

				actualDate = forecastItem.getDate();
				
				// Header date row
				addDataDateRow(chart, forecastItem, inflater);
				// Header with icons
				addDataInfoRow(chart, inflater, forecast.getStationForecast().getTimezone());
			}

			addDataForecast(chart, forecastItem, inflater);					

		}

		if (chart != null) {
			((LinearLayout) rootView.findViewById(R.id.dataRow)).addView(chart);
		}

		return rootView;
	}		
	

	/**
	 * Draw station name + arrow buttons
	 * */
	private void setHeaderView(View view) {

		TextView tv = (TextView) view.findViewById(R.id.stationName);

		tv.setText(forecast.getStationForecast().getName());

		arrowButtons(view);
	}


	/**
	 * Add a row in parentView with the date and a icon to more info.
	 * */
	private void addDataDateRow(LinearLayout parentView, ForecastItem forecastItem, LayoutInflater inflater) {

		LinearLayout column = (LinearLayout) inflater.inflate(
				R.layout.wind_info_chart_row_date, parentView, false);

		TextView tvDate = (TextView) column.findViewById(R.id.date);
		tvDate.setText(StringUtils.toDate(forecastItem.getDate()));		

		// This tag will be used to get more info.
		ImageButton moreInfoButton = (ImageButton) column.findViewById(R.id.moreInfo);
		moreInfoButton.setTag(forecastItem.getDate());

		parentView.addView(column);
	}

	
	/**
	 * Add a row in parentView with header icons.
	 * */
	private void addDataInfoRow(LinearLayout parentView, LayoutInflater inflater, String timezone) {

		LinearLayout column = (LinearLayout) inflater.inflate(
				R.layout.wind_info_chart_row_names, parentView, false);


		TextView tvTimezone = (TextView) column.findViewById(R.id.timezone);
		tvTimezone.setText(StringUtils.toTimezone(timezone));	

		parentView.addView(column);
	}


	/**
	 * Add a row in parentView with the forecast info.
	 * */
	private void addDataForecast(LinearLayout parentView, ForecastItem forecastItem, LayoutInflater inflater) {

		LinearLayout column = (LinearLayout) inflater.inflate(
				R.layout.wind_info_chart_row_data, parentView, false);

		// Time
		TextView tvTime = (TextView) column.findViewById(R.id.time);
		tvTime.setText(StringUtils.toTime(forecastItem.getTime()));

		// Wind direction
		ImageView ivWindDirection = (ImageView) column.findViewById(R.id.wind_direction);
		ivWindDirection.setImageResource(ResourcesUtil.
				getDirectionIconId(forecastItem.getForecastDataMap().get(ForecastItem.TAG_WIND_DIRECTION).getValue()));

		// Wind speed
		TextView tvWindSpeed = (TextView) column.findViewById(R.id.wind_speed);
		tvWindSpeed.setText(forecastItem.getForecastDataMap().get(ForecastItem.TAG_WIND_SPEED).getValue() + " " 
				+ forecastItem.getForecastDataMap().get(ForecastItem.TAG_WIND_SPEED).getUnit());

		// Air temperature
		TextView tvAirTemperature = (TextView) column.findViewById(R.id.air_temperature);
		tvAirTemperature.setText(StringUtils.removeDecimals(forecastItem.getForecastDataMap()
				.get(ForecastItem.TAG_AIR_TEMPERATURE).getValue()) + "º");		

		// Clouds
		TextView tvClouds = (TextView) column.findViewById(R.id.clouds);
		tvClouds.setText(forecastItem.getForecastDataMap().get(ForecastItem.TAG_CLOUDS).getValue());

		parentView.addView(column);
	}

	/**
	 * Enable or disable de left and right images from the current view.
	 * */
	private void arrowButtons(View view) {

		if (listForecastSize == 1 ) {
			// Remove the arrows
			ImageView leftArrowImage = (ImageView) view.findViewById(R.id.leftArrow);
			leftArrowImage.setVisibility(View.INVISIBLE);
			ImageView rightArrowImage = (ImageView) view.findViewById(R.id.rightArrow);
			rightArrowImage.setVisibility(View.INVISIBLE);
		} else if (viewPosition == 0) {			
			// Remove left arrow
			ImageView leftArrowImage = (ImageView) view.findViewById(R.id.leftArrow);
			leftArrowImage.setVisibility(View.INVISIBLE);			
		} else if (viewPosition == listForecastSize-1) {
			// Remove right arrow
			ImageView rightArrowImage = (ImageView) view.findViewById(R.id.rightArrow);
			rightArrowImage.setVisibility(View.INVISIBLE);
		}
	}


}