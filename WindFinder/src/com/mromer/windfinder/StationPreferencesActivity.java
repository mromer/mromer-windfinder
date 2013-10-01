package com.mromer.windfinder;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.mromer.windfinder.utils.SharedPreferencesUtil;

public class StationPreferencesActivity extends PreferenceActivity {

	public final static String BUNDLE_PREFERENCE_NAME = "BUNDLE_PREFERENCE_NAME";
	public final static String BUNDLE_STATION_NAME = "BUNDLE_STATION_NAME";

	private final String KEY_MINIMUN_WIND_LEVEL = "minimun_wind_level";
	private final String KEY_WIND_DIRECTION = "wind_direction";	
	
	private ListPreference windDirectionPreference;
	private EditTextPreference minimunWindPreference;
	
	private String preferencesName;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		preferencesName = getIntent().getExtras().getString(BUNDLE_PREFERENCE_NAME);
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
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		initializeFields(preferencesName);
		
	}
	

	@SuppressWarnings("deprecation")
	private void initializeFields(String preferencesName) {
		
		minimunWindPreference = (EditTextPreference) getPreferenceScreen()
				.findPreference(KEY_MINIMUN_WIND_LEVEL);
		
		minimunWindPreference.setOnPreferenceChangeListener(checkPrefevenceListener);
		
		windDirectionPreference = (ListPreference) getPreferenceScreen()
				.findPreference(KEY_WIND_DIRECTION);
		windDirectionPreference.setOnPreferenceChangeListener(checkPrefevenceListener);
		
		
		minimunWindPreference.setSummary(SharedPreferencesUtil.getWindLevelStation(this, preferencesName).toString());
		windDirectionPreference.setSummary(SharedPreferencesUtil.getWindDirectionStation(this, preferencesName));
		
	}


	/**
	 * Checks that a preference is a valid numerical value
	 */
	Preference.OnPreferenceChangeListener checkPrefevenceListener = new OnPreferenceChangeListener() {

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {

			if (KEY_MINIMUN_WIND_LEVEL.equals(preference.getKey())) {
				return windLevelPreference(newValue);
			} else if (KEY_WIND_DIRECTION.equals(preference.getKey())) {
				return windLevelDirection(newValue);
			} else {
				return false;
			}
		}
	};


	private boolean windLevelDirection(Object newValue) {		

		windDirectionPreference.setSummary((String) newValue);

		return true;		
	}

	/**
	 * Check if the value is a numbre
	 * */
	private boolean windLevelPreference(Object newValue) {
		if( !newValue.toString().equals("")  &&  newValue.toString().matches("\\d*") ) {
			minimunWindPreference.setSummary((String) newValue);
			return true;
		}
		else {
			Toast.makeText(StationPreferencesActivity.this, newValue + " " + 
					getResources().getString(R.string.error_not_a_number), Toast.LENGTH_SHORT).show();

			return false;
		}
	}


}