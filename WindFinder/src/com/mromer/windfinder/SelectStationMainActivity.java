package com.mromer.windfinder;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mromer.windfinder.utils.ActivityUtil;

public abstract class SelectStationMainActivity extends ActionBarActivity {	

	private ActionBar actionBar;

	protected ListView listView;	

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_station_menu, menu);
		return true;
	}

}
