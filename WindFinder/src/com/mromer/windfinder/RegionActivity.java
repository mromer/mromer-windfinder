package com.mromer.windfinder;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.mromer.windfinder.adapter.RegionListAdapter;
import com.mromer.windfinder.bean.Continent;
import com.mromer.windfinder.bean.Country;
import com.mromer.windfinder.bean.Region;
import com.mromer.windfinder.manager.ContinentManager;
import com.mromer.windfinder.task.LoadTaskResultI;
import com.mromer.windfinder.task.LoadXmlTask;
import com.mromer.windfinder.task.TaskResult;
import com.mromer.windfinder.utils.AlertUtils;

public class RegionActivity extends ListActivity {
	
	private final String TAG = this.getClass().getName();
	
	private ContinentManager continentManager;
	
	private String continentId;
	private String countryId;
	
	private ArrayList<Continent> continentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.continents);
		
		continentId = getIntent().getExtras().getString("CONTINENT_ID");
		countryId = getIntent().getExtras().getString("COUNTRY_ID");
		
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

					AlertUtils.showAlert(RegionActivity.this, result.getDesc(), "aceptar");
					
				}
			}).execute();
			
		} else {
			Country country = continentManager.getCountryById(continentId, countryId);

			drawList(country.getRegionList());
		}
			
		
		
	}

	private void drawList(List<Region> regions) {
		if (regions != null) {
						
			RegionListAdapter adapter = new RegionListAdapter(this, regions);
	        getListView().setAdapter(adapter);
	        
		}
		
	}
	
	
	@Override
	protected void onListItemClick(ListView listView, View v, int position, long id) {
		
		Region region = (Region) listView.getAdapter().getItem(position);
		
		Log.d(TAG, "region " + region.getName());
		
		Intent intent = new Intent(this, StationActivity.class);
	    
		intent.putExtra("COUNTRY_ID", countryId);
		
		intent.putExtra("CONTINENT_ID", continentId);
		
		intent.putExtra("REGION_ID", region.getId());		
	    
	    startActivity(intent);
	  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.country, menu);
		return true;
	}

}
