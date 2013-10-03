package com.mromer.windfinder;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.mromer.windfinder.adapter.StationListAdapter;
import com.mromer.windfinder.bean.Continent;
import com.mromer.windfinder.bean.Region;
import com.mromer.windfinder.bean.Station;
import com.mromer.windfinder.manager.ContinentManager;
import com.mromer.windfinder.task.LoadTaskResultI;
import com.mromer.windfinder.task.LoadXmlTask;
import com.mromer.windfinder.task.TaskResult;
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

	private StationListAdapter stationListAdapter;

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

			new LoadXmlTask(this, new LoadTaskResultI() {

				@Override
				public void taskSuccess(TaskResult result) {
					continentList = continentManager.getAllContinents();

					Region region = continentManager.getRegionById(continentId, countryId, regionId);

					drawList(region.getStationList());				

				}

				@Override
				public void taskFailure(TaskResult result) {

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

	private void drawList(List<Station> stations) {
		if (stations != null) {

			stationListAdapter = new StationListAdapter(this, stations);
			listView.setAdapter(stationListAdapter);

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


	@Override
	public boolean onQueryTextChange(String text) {
		stationListAdapter.getFilter().filter(text);
		return false;
	}


	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_station_menu, menu);

		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		if (searchView != null) {
			searchView.setOnQueryTextListener(this);
		}
		return true;
	}


}
