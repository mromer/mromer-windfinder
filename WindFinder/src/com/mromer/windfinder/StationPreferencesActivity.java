package com.mromer.windfinder;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class StationPreferencesActivity extends PreferenceActivity {

	public final static String BUNDLE_PREFERENCE_NAME = "BUNDLE_PREFERENCE_NAME";
	public final static String BUNDLE_STATION_NAME = "BUNDLE_STATION_NAME";

	public final static String PREFERENCE_MINIMUN_WIND_LEVEL = "minimun_wind_level";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String preferencesName = getIntent().getExtras().getString(BUNDLE_PREFERENCE_NAME);
		String stationName = getIntent().getExtras().getString(BUNDLE_STATION_NAME);
		
		Intent intent = getIntent();
		intent.putExtra(StationPreferencesActivity.BUNDLE_PREFERENCE_NAME, preferencesName);
		setResult(RESULT_OK, intent);
		

		setTitle(stationName);        

		PreferenceManager prefMgr = getPreferenceManager();
		prefMgr.setSharedPreferencesName(preferencesName);
		prefMgr.setSharedPreferencesMode(MODE_WORLD_READABLE);

		// This method was deprecated in API level 11. 
		// This function is not relevant for a modern fragment-based PreferenceActivity
		// but we are working with min api level 9        
		addPreferencesFromResource(R.xml.station_preferences);


		//get a handle on preferences that require validation
		EditTextPreference minimunWindPreference = (EditTextPreference) getPreferenceScreen()
				.findPreference(PREFERENCE_MINIMUN_WIND_LEVEL);

		//Validate numbers only
		minimunWindPreference.setOnPreferenceChangeListener(numberCheckListener);

	}


	/**
	 * Checks that a preference is a valid numerical value
	 */
	Preference.OnPreferenceChangeListener numberCheckListener = new OnPreferenceChangeListener() {

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			//Check that the string is an integer.
			return numberCheck(newValue);
		}
	};

	private boolean numberCheck(Object newValue) {
		if( !newValue.toString().equals("")  &&  newValue.toString().matches("\\d*") ) {
			return true;
		}
		else {
			Toast.makeText(StationPreferencesActivity.this, newValue + " " + 
					getResources().getString(R.string.error_not_a_number), Toast.LENGTH_SHORT).show();
			
			return false;
		}
	}


}