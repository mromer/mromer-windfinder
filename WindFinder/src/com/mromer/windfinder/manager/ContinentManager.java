package com.mromer.windfinder.manager;

import java.util.ArrayList;

import android.content.Context;

import com.mromer.windfinder.bean.Continent;
import com.mromer.windfinder.bean.Country;
import com.mromer.windfinder.bean.Region;
import com.mromer.windfinder.dao.ContinentDao;

public class ContinentManager {
	
	private static ContinentManager INSTANCE;
		
	private Context context;
	
	private ContinentDao continentDao;
	
	private ContinentManager(Context context) {
		this.context = context;	
		
		continentDao = new ContinentDao(this.context);
		
	}	    
 
    private synchronized static void createInstance(Context context) {
        if (INSTANCE == null) { 
            INSTANCE = new ContinentManager(context);
        }
    }
 
    public static ContinentManager getInstance(Context context) {
        createInstance(context);
        return INSTANCE;
    }
    
    public static void resetInstance() { 
    	if (INSTANCE != null) {
    		INSTANCE.context = null;
    	}
    	
        INSTANCE = null;        
    }
	
	
	public ArrayList<Continent> getAllContinents() {		
		return continentDao.getContinents();
	}
	
	
	public ArrayList<Continent> loadContinents() {
		
		continentDao.loadContinents();
		
		return continentDao.getContinents();
	}
	
	
	public Continent getContinentById(String id) {		

		return continentDao.getContinentById(id);
	}
	
	public Country getCountryById(String idContinent, String idCountry) {		

		return continentDao.getCountryById(idContinent, idCountry);
	}

	public Region getRegionById(String continentId, String countryId,
			String regionId) {
		
		return continentDao.getRegionById(continentId, countryId, regionId);
	}

}
