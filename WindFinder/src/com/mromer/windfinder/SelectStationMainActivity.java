package com.mromer.windfinder;

import java.util.List;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mromer.windfinder.adapter.GeneralListAdapter;
import com.mromer.windfinder.adapter.StationListAdapter;
import com.mromer.windfinder.bean.DataType;
import com.mromer.windfinder.utils.ActivityUtil;

public abstract class SelectStationMainActivity extends ActionBarActivity implements OnQueryTextListener {	
	
	public final static String BUNDLE_CONTINENT_ID = "BUNDLE_CONTINENT_ID";
	public final static String BUNDLE_CONTINENT_NAME = "BUNDLE_CONTINENT_NAME";
	
	public final static String BUNDLE_COUNTRY_ID = "BUNDLE_COUNTRY_ID";
	public final static String BUNDLE_COUNTRY_NAME = "BUNDLE_COUNTRY_NAME";
	
	public final static String BUNDLE_REGION_ID = "BUNDLE_REGION_ID";
	public final static String BUNDLE_REGION_NAME = "BUNDLE_REGION_NAME";
	

	private ActionBar actionBar;

	private ListView listView;	
	
	private GeneralListAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.general_list);		

	}

	protected void setUIComponents() {

		listView = (ListView) findViewById(R.id.listId);	

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View arg1, int position,
					long id) {

				onListItemClick(listView, arg1, position, id);

			}
		});
	}
	
	
	protected void setHeader(String header) {
		TextView title = (TextView) findViewById(R.id.title);
		if (header != null) {
			title.setText(header);
		} else {
			title.setVisibility(TextView.GONE);
		}		
	}


	protected void setActionBar(int idTittle) {

		actionBar = getSupportActionBar();	

		actionBar.setTitle(getResources().getString(idTittle));

		actionBar.setDisplayHomeAsUpEnabled(true);

	}

	abstract protected void onListItemClick(AdapterView<?> adapterView, View v, int position, long id);

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case android.R.id.home:

			super.onBackPressed();

			return true;

		case R.id.action_to_wind_info:

			ActivityUtil.toNextActivity(this, WindInfoActivity.class);

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void drawList(List<? extends DataType> listData) {
		if (listData != null) {

			listAdapter = new StationListAdapter(this, listData);
			listView.setAdapter(listAdapter);
		}
	}	
	
	@Override
	public boolean onQueryTextChange(String text) {
		listAdapter.getFilter().filter(text);
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

	public ListView getListView() {
		return listView;
	}

	public void setListView(ListView listView) {
		this.listView = listView;
	}

	public GeneralListAdapter getListAdapter() {
		return listAdapter;
	}

	public void setListAdapter(GeneralListAdapter listAdapter) {
		this.listAdapter = listAdapter;
	}
	
	

}
