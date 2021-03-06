package com.mromer.windfinder;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.mromer.windfinder.adapter.StationListAdapter;
import com.mromer.windfinder.bean.Continent;
import com.mromer.windfinder.bean.DataType;
import com.mromer.windfinder.bean.Region;
import com.mromer.windfinder.bean.Station;
import com.mromer.windfinder.manager.ContinentManager;
import com.mromer.windfinder.task.ContinentLoadTaskResultI;
import com.mromer.windfinder.task.ContinentTaskResult;
import com.mromer.windfinder.task.GetContinentTask;
import com.mromer.windfinder.utils.AlertUtils;
import com.mromer.windfinder.utils.SharedPreferencesUtil;

public class StationActivity extends SelectStationMainActivity implements OnQueryTextListener{

	private final String TAG = this.getClass().getName();

	private ContinentManager continentManager;

	private String continentId;
	private String continentName;
	private String countryId;
	private String countryName;
	private String regionId;
	private String regionName;

	private ArrayList<Continent> continentList;		
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.d(TAG, "onCreate " + TAG);

		super.onCreate(savedInstanceState);

		setActionBar(R.string.select_station);

		setIntentData();

		setHeader(continentName + " | " + countryName + " | " + regionName);

		setUIComponents();

		drawListProcess();

	}


	private void drawListProcess() {

		continentManager = ContinentManager.getInstance(this);

		continentList = ContinentManager.getInstance(this).getAllContinents();;

		if (continentList == null) {			

			new GetContinentTask(this, new ContinentLoadTaskResultI() {

				@Override
				public void taskSuccess(ContinentTaskResult result) {
					continentList = continentManager.getAllContinents();

					Region region = continentManager.getRegionById(continentId, countryId, regionId);

					drawList(region.getStationList());				

				}

				@Override
				public void taskFailure(ContinentTaskResult result) {

					AlertUtils.showAlert(StationActivity.this, result.getDesc(), 
							getResources().getString(R.string.accept));

				}
			}).execute();



		} else {

			Region region = continentManager.getRegionById(continentId, countryId, regionId);

			drawList(region.getStationList());

		}	

	}

	private void setIntentData() {
		continentId = getIntent().getExtras().getString(BUNDLE_CONTINENT_ID);
		continentName = getIntent().getExtras().getString(BUNDLE_CONTINENT_NAME);
		countryId = getIntent().getExtras().getString(BUNDLE_COUNTRY_ID);
		countryName = getIntent().getExtras().getString(BUNDLE_COUNTRY_NAME);
		regionId = getIntent().getExtras().getString(BUNDLE_REGION_ID);
		regionName = getIntent().getExtras().getString(BUNDLE_REGION_NAME);
	}

	protected void drawList(List<? extends DataType> stations) {
		if (stations != null) {
			StationListAdapter stationListAdapter = new StationListAdapter(this, stations);
			setListAdapter(stationListAdapter);			
			getListView().setAdapter(stationListAdapter);
		}
	}


	@Override
	protected void onListItemClick(AdapterView<?> adapterView, View v, int position, long id) {

		Station station = (Station) adapterView.getAdapter().getItem(position);	


		SharedPreferences prefs = this.getSharedPreferences(
				"stations", Context.MODE_PRIVATE);

		String stationId = prefs.getString(station.getId(), null);

		if (stationId == null) {
			SharedPreferencesUtil.addStationToSharedPreferences(this, station);
		} else {
			SharedPreferencesUtil.removeStationToSharedPreferences(this, station.getId());
		}	

		((BaseAdapter) adapterView.getAdapter()).notifyDataSetChanged();

	}


}
