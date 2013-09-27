package com.mromer.windfinder;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.mromer.windfinder.adapter.WindInfoSlidePagerAdapter;

public class WindInfoActivity extends FragmentActivity  {

	private final String TAG = this.getClass().getName();
	
	/**
	 * The pager widget, which handles animation and allows swiping horizontally to access previous
	 * and next wizard steps.
	 */
	private ViewPager mPager;
	
	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreate " + TAG);
		
		super.onCreate(savedInstanceState);	
		
		setContentView(R.layout.wind_info);
		
		ArrayList<String> windCards = new ArrayList<String>();
		windCards.add("1");
		windCards.add("2");
		windCards.add("3");
		
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new WindInfoSlidePagerAdapter(getSupportFragmentManager(), windCards);	
		
		mPager.setAdapter(mPagerAdapter);

	}


}
