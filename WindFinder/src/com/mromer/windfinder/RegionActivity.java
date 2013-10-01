package com.mromer.windfinder;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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
	private String countryId;

	private ArrayList<Continent> continentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreate " + TAG);
		
		super.onCreate(savedInstanceState);		

		setActionBar(R.string.select_region);

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

	private void setIntentData() {
		continentId = getIntent().getExtras().getString("CONTINENT_ID");
		countryId = getIntent().getExtras().getString("COUNTRY_ID");		
	}


	private void drawList(List<Region> regions) {
		if (regions != null) {

			ArrayAdapter<Region> adapter = new ArrayAdapter<Region>(this, 
				    R.layout.list_item, R.id.name, regions);
			
			listView.setAdapter(adapter);

		}

	}
	
	protected void onListItemClick(AdapterView<?> adapterView, View v, int position, long id) {

		Region region = (Region) adapterView.getAdapter().getItem(position);

		Log.d(TAG, "region " + region.getName());

		Intent intent = new Intent(this, StationActivity.class);

		intent.putExtra("COUNTRY_ID", countryId);

		intent.putExtra("CONTINENT_ID", continentId);

		intent.putExtra("REGION_ID", region.getId());		

		startActivity(intent);

	}

}
