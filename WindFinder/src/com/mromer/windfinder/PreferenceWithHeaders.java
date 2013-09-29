package com.mromer.windfinder;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferenceWithHeaders extends PreferenceActivity {
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        // This method was deprecated in API level 11. 
        // This function is not relevant for a modern fragment-based PreferenceActivity
        // but we are working with min api level 9        
        addPreferencesFromResource(R.xml.preference_headers);

       
        
    }

   
}