package com.mromer.windfinder.utils;

import android.content.Context;

import com.mromer.windfinder.R;
import com.mromer.windfinder.bean.ForecastItem;
import com.mromer.windfinder.commons.ConstantsWindDirection;

public class ResourcesUtil {

	public static int getDirectionIconId(String direction) {


		if (ConstantsWindDirection.WIND_E.equals(direction)) {			
			return R.drawable.direction_e;			
		} else if (ConstantsWindDirection.WIND_ENE.equals(direction)) {
			return R.drawable.direction_ene;
		} else if (ConstantsWindDirection.WIND_ESE.equals(direction)) {
			return R.drawable.direction_ese;
		} else if (ConstantsWindDirection.WIND_N.equals(direction)) {
			return R.drawable.direction_n;
		} else if (ConstantsWindDirection.WIND_NE.equals(direction)) {
			return R.drawable.direction_ne;
		} else if (ConstantsWindDirection.WIND_NNE.equals(direction)) {
			return R.drawable.direction_nne;
		} else if (ConstantsWindDirection.WIND_NNW.equals(direction)) {
			return R.drawable.direction_nnw;
		} else if (ConstantsWindDirection.WIND_NW.equals(direction)) {
			return R.drawable.direction_nw;
		} else if (ConstantsWindDirection.WIND_S.equals(direction)) {
			return R.drawable.direction_s;
		} else if (ConstantsWindDirection.WIND_SE.equals(direction)) {
			return R.drawable.direction_se;
		} else if (ConstantsWindDirection.WIND_SSE.equals(direction)) {
			return R.drawable.direction_sse;
		} else if (ConstantsWindDirection.WIND_SSW.equals(direction)) {
			return R.drawable.direction_ssw;
		} else if (ConstantsWindDirection.WIND_SW.equals(direction)) {
			return R.drawable.direction_sw;
		} else if (ConstantsWindDirection.WIND_W.equals(direction)) {
			return R.drawable.direction_w;
		} else if (ConstantsWindDirection.WIND_WNW.equals(direction)) {
			return R.drawable.direction_wnw;
		} else {
			return R.drawable.direction_wsw;
		}		
	}


	public static String getForecastTitle(Context context, String key) {

		int idString = -1;

		if (key.equals(ForecastItem.TAG_AIR_TEMPERATURE)) {			
			idString = R.string.air_temperature ;			
		} else if (key.equals(ForecastItem.TAG_WATER_TEMPERATURE)) {
			idString = R.string.water_temperature ;
		} else if (key.equals(ForecastItem.TAG_WIND_DIRECTION)) {
			idString = R.string.wind_direction ;
		} else if (key.equals(ForecastItem.TAG_WIND_SPEED)) {
			idString = R.string.wind_speed ;
		} else if (key.equals(ForecastItem.TAG_WIND_GUSTS)) {
			idString = R.string.wind_gusts ;
		} else if (key.equals(ForecastItem.TAG_WEATHER)) {
			idString = R.string.weather ;
		} else if (key.equals(ForecastItem.TAG_CLOUDS)) {
			idString = R.string.clouds ;
		} else if (key.equals(ForecastItem.TAG_PRECIPITATION)) {
			idString = R.string.precipitation ;
		} else if (key.equals(ForecastItem.TAG_PRECIPITATION_TYPE)) {
			idString = R.string.precipitation_type ;
		} else if (key.equals(ForecastItem.TAG_WAVE_HEIGHT)) {
			idString = R.string.wave_height ;
		} else if (key.equals(ForecastItem.TAG_WAVE_DIRECTION)) {
			idString = R.string.wave_direction ;
		} else if (key.equals(ForecastItem.TAG_WAVE_PERIOD)) {
			idString = R.string.wave_period ;
		} else if (key.equals(ForecastItem.TAG_AIR_PRESSURE)) {
			idString = R.string.air_pressure ;
		}

		if (idString >= 0) {
			return context.getResources().getString(idString);
		} else {
			return null;
		}		
	}

}
