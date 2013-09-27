package com.mromer.windfinder;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.mromer.windfinder.adapter.CotinentListAdapter;
import com.mromer.windfinder.bean.Continent;
import com.mromer.windfinder.manager.ContinentManager;
import com.mromer.windfinder.task.LoadTaskResultI;
import com.mromer.windfinder.task.LoadXmlTask;
import com.mromer.windfinder.task.TaskResult;
import com.mromer.windfinder.utils.AlertUtils;

public class ContinentActivity extends SelectStationMainActivity  {

	private final String TAG = this.getClass().getName();	

	private ArrayList<Continent> continents;

	private ContinentManager continentManager;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreate " + TAG);
		
		super.onCreate(savedInstanceState);		

		setActionBar(R.string.select_continent);

		setUIComponents();

		drawListProcess();		

	}


	private void drawListProcess() {
		
		continentManager = ContinentManager.getInstance(this);

		continents = continentManager.getAllContinents();

		if (continents == null) {

			new LoadXmlTask(this, new LoadTaskResultI() {

				@Override
				public void taskSuccess(TaskResult result) {
					continents = continentManager.getAllContinents();
					drawList(continents);

				}

				@Override
				public void taskFailure(TaskResult result) {

					AlertUtils.showAlert(ContinentActivity.this, result.getDesc(), "aceptar");

				}
			}).execute();			

		} else {

			drawList(continents);

		}

	}

	private void drawList(ArrayList<Continent> continents) {
		if (continents != null) {

			CotinentListAdapter adapter = new CotinentListAdapter(this, continents);
			listView.setAdapter(adapter);

		}

	}

	@Override
	protected void onListItemClick(AdapterView<?> adapterView, View v, int position, long id) {

		Continent continent = (Continent) adapterView.getAdapter().getItem(position);

		Log.d(TAG, "continent " + continent.getName());

		Intent intent = new Intent(this, CountryActivity.class);

		intent.putExtra("CONTINENT_ID", continent.getId());

		startActivity(intent);

	}
	

}
