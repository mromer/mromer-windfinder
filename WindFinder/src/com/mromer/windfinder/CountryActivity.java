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

import com.mromer.windfinder.adapter.CountryListAdapter;
import com.mromer.windfinder.bean.Continent;
import com.mromer.windfinder.bean.Country;
import com.mromer.windfinder.manager.ContinentManager;
import com.mromer.windfinder.task.LoadTaskResultI;
import com.mromer.windfinder.task.LoadXmlTask;
import com.mromer.windfinder.task.TaskResult;
import com.mromer.windfinder.utils.AlertUtils;

public class CountryActivity extends ListActivity {
	
	private final String TAG = this.getClass().getName();
	
	private ContinentManager continentManager;
	
	private String continentId;
	
	private ArrayList<Continent> continentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.continents);
		
		continentId = getIntent().getExtras().getString("CONTINENT_ID");			
		
		continentManager = ContinentManager.getInstance(this);
		
		continentList = ContinentManager.getInstance(this).getAllContinents();;
		
		if (continentList == null) {
			
			
			continentList = continentManager.getAllContinents();	
			
			new LoadXmlTask(this, new LoadTaskResultI() {
				
				@Override
				public void taskSuccess(TaskResult result) {
					continentList = continentManager.getAllContinents();
					Continent continent = continentManager.getContinentById(continentId);

					drawList(continent.getCountryList());					
					
				}
				
				@Override
				public void taskFailure(TaskResult result) {

					AlertUtils.showAlert(CountryActivity.this, result.getDesc(), "aceptar");
					
				}
			}).execute();
			
		} else {
			
			Continent continent = continentManager.getContinentById(continentId);

			drawList(continent.getCountryList());
		}
			
		
		
	}

	private void drawList(List<Country> countries) {
		if (countries != null) {
						
			CountryListAdapter adapter = new CountryListAdapter(this, countries);
	        getListView().setAdapter(adapter);
	        
		}
		
	}
	
	@Override
	protected void onListItemClick(ListView listView, View v, int position, long id) {
		
		Country country = (Country) listView.getAdapter().getItem(position);
		
		Log.d(TAG, "country " + country.getName());
		
		Intent intent = new Intent(this, RegionActivity.class);
	    
		intent.putExtra("COUNTRY_ID", country.getId());
		
		intent.putExtra("CONTINENT_ID", continentId);
	    
	    startActivity(intent);
	  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.country, menu);
		return true;
	}

}
