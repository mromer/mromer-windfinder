package com.mromer.windfinder;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;

import com.mromer.windfinder.adapter.CotinentListAdapter;
import com.mromer.windfinder.bean.Continent;
import com.mromer.windfinder.utils.XmlParseUtil;

public class ContinentActivity extends ListActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.continents);
		
		ArrayList<Continent> continents = new XmlParseUtil().getContinents(this);
		
		
		drawList(continents);
		
		
	}

	private void drawList(ArrayList<Continent> continents) {
		if (continents != null) {
						
			CotinentListAdapter adapter = new CotinentListAdapter(this, continents);
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
