package com.mromer.windfinder;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class StationActivity extends SelectStationMainActivity {

	private final String TAG = this.getClass().getName();

	private ContinentManager continentManager;

	private String continentId;
	private String countryId;
	private String regionId;

	private ArrayList<Continent> continentList;		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreate " + TAG);
		
		super.onCreate(savedInstanceState);

		setActionBar(R.string.select_station);

		setIntentData();

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

					AlertUtils.showAlert(StationActivity.this, result.getDesc(), "aceptar");

				}
			}).execute();



		} else {

			Region region = continentManager.getRegionById(continentId, countryId, regionId);

			drawList(region.getStationList());

		}	

	}

	private void setIntentData() {
		continentId = getIntent().getExtras().getString("CONTINENT_ID");
		countryId = getIntent().getExtras().getString("COUNTRY_ID");
		regionId = getIntent().getExtras().getString("REGION_ID");		
	}

	private void drawList(List<Station> stations) {
		if (stations != null) {

			StationListAdapter adapter = new StationListAdapter(this, stations);
			listView.setAdapter(adapter);

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
