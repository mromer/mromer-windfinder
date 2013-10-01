package com.mromer.windfinder.adapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.mromer.windfinder.bean.Forecast;
import com.mromer.windfinder.fragment.WindInfoSlidePageFragment;

/**
 * A simple pager adapter that represents ScreenSlidePageFragment objects, in
 * sequence.
 */
public class WindInfoSlidePagerAdapter extends FragmentStatePagerAdapter {

	ArrayList<Forecast> windCards;

	public WindInfoSlidePagerAdapter(FragmentManager fm, 
			List<Forecast> list) {

		super(fm);
		
		this.windCards = (ArrayList<Forecast>) list;

	}

	
	@Override
	public Fragment getItem(int position) {
		
		Forecast forecast = windCards.get(position);
		
		Fragment f = new WindInfoSlidePageFragment();
		Bundle b = new Bundle();
		b.putSerializable(WindInfoSlidePageFragment.FORECAST_INFO, forecast);		
		b.putInt(WindInfoSlidePageFragment.LIST_FORECAST_SIZE, windCards.size());
		b.putInt(WindInfoSlidePageFragment.VIEW_POSITION, position);
		
		f.setArguments(b);					

		return f;
	}

	@Override
	public int getCount() {
		return windCards.size();
	}
	
	@Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }

}