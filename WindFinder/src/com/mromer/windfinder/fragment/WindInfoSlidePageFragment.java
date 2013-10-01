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

		setHeaderView(rootView);

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
	

	private void setHeaderView(View view) {

		TextView tv = (TextView) view.findViewById(R.id.stationName);

		tv.setText(forecast.getStationForecast().getName());

		arrowButtons(view);

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