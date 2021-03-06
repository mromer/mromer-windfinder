package com.mromer.windfinder;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.mromer.windfinder.bean.Continent;
import com.mromer.windfinder.bean.Country;
import com.mromer.windfinder.manager.ContinentManager;
import com.mromer.windfinder.task.ContinentLoadTaskResultI;
import com.mromer.windfinder.task.ContinentTaskResult;
import com.mromer.windfinder.task.GetContinentTask;
import com.mromer.windfinder.utils.AlertUtils;

public class CountryActivity extends SelectStationMainActivity {	

	private final String TAG = this.getClass().getName();

	private ContinentManager continentManager;

	private String continentId;
	
	private String continentName;

	private ArrayList<Continent> continentList;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreate " + TAG);

		super.onCreate(savedInstanceState);			

		setActionBar(R.string.select_country);

		setIntentData();
		
		setHeader(continentName);
		
		setUIComponents();

		drawListProcess();
		
	}
	

	private void drawListProcess() {
		
		continentManager = ContinentManager.getInstance(this);

		continentList = ContinentManager.getInstance(this).getAllContinents();

		if (continentList == null) {

			continentList = continentManager.getAllContinents();	

			new GetContinentTask(this, new ContinentLoadTaskResultI() {

				@Override
				public void taskSuccess(ContinentTaskResult result) {
					continentList = continentManager.getAllContinents();
					Continent continent = continentManager.getContinentById(continentId);

					drawList(continent.getCountryList());					

				}

				@Override
				public void taskFailure(ContinentTaskResult result) {

					AlertUtils.showAlert(CountryActivity.this, result.getDesc(), 
							getResources().getString(R.string.accept));

				}
			}).execute();

		} else {

			Continent continent = continentManager.getContinentById(continentId);

			drawList(continent.getCountryList());
		}

	}

	private void setIntentData() {
		continentId = getIntent().getExtras().getString(BUNDLE_CONTINENT_ID);
		continentName = getIntent().getExtras().getString(BUNDLE_CONTINENT_NAME);
	}


	@Override
	protected void onListItemClick(AdapterView<?> adapterView, View v, int position, long id) {

		Country country = (Country) adapterView.getAdapter().getItem(position);

		Log.d(TAG, "country " + country.getName());

		Intent intent = new Intent(this, RegionActivity.class);

		intent.putExtra(BUNDLE_COUNTRY_ID, country.getId());
		intent.putExtra(BUNDLE_COUNTRY_NAME, country.getName());

		intent.putExtra(BUNDLE_CONTINENT_ID, continentId);
		intent.putExtra(BUNDLE_CONTINENT_NAME, continentName);

		startActivity(intent);

	}
	

}
