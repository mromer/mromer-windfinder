package com.mromer.windfinder;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;

import com.mromer.windfinder.adapter.CountryListAdapter;
import com.mromer.windfinder.bean.Country;

public class CountryActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.continents);
		
		List<Country> countries = (List<Country>) getIntent().getExtras().getParcelable("COUNTRY_LIST");
		
		
		drawList(countries);
		
		
	}

	private void drawList(List<Country> countries) {
		if (countries != null) {
						
			CountryListAdapter adapter = new CountryListAdapter(this, countries);
	        getListView().setAdapter(adapter);
	        
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.country, menu);
		return true;
	}

}
