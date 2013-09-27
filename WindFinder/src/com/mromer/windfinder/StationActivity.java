package com.mromer.windfinder;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.mromer.windfinder.adapter.StationListAdapter;
import com.mromer.windfinder.bean.Continent;
import com.mromer.windfinder.bean.Region;
import com.mromer.windfinder.bean.Station;
import com.mromer.windfinder.manager.ContinentManager;
import com.mromer.windfinder.task.LoadTaskResultI;
import com.mromer.windfinder.task.LoadXmlTask;
import com.mromer.windfinder.task.TaskResult;
import com.mromer.windfinder.utils.AlertUtils;

public class StationActivity extends ListActivity {
	
	private final String TAG = this.getClass().getName();
	
	private ContinentManager continentManager;
	
	private String continentId;
	private String countryId;
	private String regionId;
	
	private ArrayList<Continent> continentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.continents);
		
		continentId = getIntent().getExtras().getString("CONTINENT_ID");
		countryId = getIntent().getExtras().getString("COUNTRY_ID");
		regionId = getIntent().getExtras().getString("REGION_ID");
		
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

	private void drawList(List<Station> stations) {
		if (stations != null) {
						
			StationListAdapter adapter = new StationListAdapter(this, stations);
	        getListView().setAdapter(adapter);
	        
		}
		
	}
	
	
	@Override
	protected void onListItemClick(ListView listView, View v, int position, long id) {
		
		Station station = (Station) getListAdapter().getItem(position);	
		
		
		SharedPreferences prefs = this.getSharedPreferences(
			      "stations", Context.MODE_PRIVATE);
		
		String stationId = prefs.getString(station.getId(), null);

		if (stationId == null) {
			addStationToSharedPreferences(station);
		} else {
			removeStationToSharedPreferences(station);
		}	
		
		((BaseAdapter) getListAdapter()).notifyDataSetChanged();
	  
	}
	
	private void addStationToSharedPreferences(Station station) {
		
		SharedPreferences prefs = this.getSharedPreferences(
			      "stations", Context.MODE_PRIVATE);		
		
		
		prefs.edit().putString(station.getId(), station.getId()).commit();
		
	}
	
	private void removeStationToSharedPreferences(Station station) {
		
		SharedPreferences prefs = this.getSharedPreferences(
			      "stations", Context.MODE_PRIVATE);
		
		prefs.edit().remove(station.getId()).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.country, menu);
		return true;
	}

}
