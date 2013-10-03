package com.mromer.windfinder;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.mromer.windfinder.bean.Continent;
import com.mromer.windfinder.bean.Country;
import com.mromer.windfinder.bean.Region;
import com.mromer.windfinder.manager.ContinentManager;
import com.mromer.windfinder.task.LoadTaskResultI;
import com.mromer.windfinder.task.LoadXmlTask;
import com.mromer.windfinder.task.TaskResult;
import com.mromer.windfinder.utils.AlertUtils;

public class RegionActivity extends SelectStationMainActivity {

	private final String TAG = this.getClass().getName();

	private ContinentManager continentManager;

	private String continentId;
	private String continentName;
	
	private String countryId;
	private String countryName;

	private ArrayList<Continent> continentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreate " + TAG);
		
		super.onCreate(savedInstanceState);		

		setActionBar(R.string.select_region);

		setIntentData();
		
		setHeader(continentName + " | " + countryName);

		setUIComponents();

		drawListProcess();
	}
	
	private void drawListProcess() {
		continentManager = ContinentManager.getInstance(this);

		continentList = ContinentManager.getInstance(this).getAllContinents();;

		if (continentList == null) {

			new LoadXmlTask(this, new LoadTaskResultI() {

				@Override
				public void taskSuccess(TaskResult result) {
					continentList = continentManager.getAllContinents();
					Country country = continentManager.getCountryById(continentId, countryId);

					drawList(country.getRegionList());					

				}

				@Override
				public void taskFailure(TaskResult result) {

					AlertUtils.showAlert(RegionActivity.this, result.getDesc(), 
							getResources().getString(R.string.accept));

				}
			}).execute();

		} else {
			Country country = continentManager.getCountryById(continentId, countryId);

			drawList(country.getRegionList());
		}		
	}

	private void setIntentData() {
		continentId = getIntent().getExtras().getString(BUNDLE_CONTINENT_ID);
		continentName = getIntent().getExtras().getString(BUNDLE_CONTINENT_NAME);
		countryId = getIntent().getExtras().getString(BUNDLE_COUNTRY_ID);	
		countryName = getIntent().getExtras().getString(BUNDLE_COUNTRY_NAME);	
	}

	
	protected void onListItemClick(AdapterView<?> adapterView, View v, int position, long id) {

		Region region = (Region) adapterView.getAdapter().getItem(position);

		Log.d(TAG, "region " + region.getName());

		Intent intent = new Intent(this, StationActivity.class);		
		
		intent.putExtra(BUNDLE_CONTINENT_ID, continentId);
		intent.putExtra(BUNDLE_CONTINENT_NAME, continentName);
		
		intent.putExtra(BUNDLE_COUNTRY_ID, countryId);
		intent.putExtra(BUNDLE_COUNTRY_NAME, countryName);		

		intent.putExtra(BUNDLE_REGION_ID, region.getId());
		intent.putExtra(BUNDLE_REGION_NAME, region.getName());

		startActivity(intent);
	}

}
