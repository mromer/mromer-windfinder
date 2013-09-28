package com.mromer.windfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.mromer.windfinder.adapter.WindInfoSlidePagerAdapter;
import com.mromer.windfinder.task.ForecastLoadTaskResultI;
import com.mromer.windfinder.task.ForecastTaskResult;
import com.mromer.windfinder.task.GetForecastTask;
import com.mromer.windfinder.utils.SharedPreferencesUtil;

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
		
		
		Map<String, String> stationsSelected = SharedPreferencesUtil.getStationsSelected(this);
		
		
		List<String> stations = new ArrayList<String>();
		for (String key : stationsSelected.keySet()) {
			
			stations.add(key);
			
		}
		
		
		new GetForecastTask(this, new ForecastLoadTaskResultI() {
			
			@Override
			public void taskSuccess(ForecastTaskResult result) {
				createViewPager(result);
				
			}
			
			@Override
			public void taskFailure(ForecastTaskResult result) {
				// TODO Auto-generated method stub
				
			}
		}, stations).execute();
		
		

	}
	
	
	private void createViewPager(ForecastTaskResult forecastTaskResult) {
				
		
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new WindInfoSlidePagerAdapter(getSupportFragmentManager(), 
				forecastTaskResult.getForecastList());	
		
		mPager.setAdapter(mPagerAdapter);
	}


}
