package com.mromer.windfinder.adapter;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mromer.windfinder.fragment.WindInfoSlidePageFragment;

/**
 * A simple pager adapter that represents ScreenSlidePageFragment objects, in
 * sequence.
 */
public class WindInfoSlidePagerAdapter extends FragmentStatePagerAdapter {

	ArrayList<String> windCards;

	public WindInfoSlidePagerAdapter(FragmentManager fm, 
			ArrayList<String> windCards) {

		super(fm);           
		this.windCards = windCards;

	}

	@Override
	public Fragment getItem(int position) {

		Fragment f = new WindInfoSlidePageFragment();
		Bundle b = new Bundle();
		b.putString("key", "value");		

		f.setArguments(b);					

		return f;
	}

	@Override
	public int getCount() {
		return windCards.size();
	}

}