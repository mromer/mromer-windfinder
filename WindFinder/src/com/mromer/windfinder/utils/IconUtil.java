package com.mromer.windfinder.utils;

import com.mromer.windfinder.R;
import com.mromer.windfinder.commons.ConstantsWindDirection;

public class IconUtil {
	
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

}
