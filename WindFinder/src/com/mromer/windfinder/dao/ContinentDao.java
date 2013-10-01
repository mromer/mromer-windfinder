package com.mromer.windfinder.dao;

import java.util.ArrayList;

import android.content.Context;

import com.mromer.windfinder.bean.Continent;
import com.mromer.windfinder.bean.Country;
import com.mromer.windfinder.bean.Region;
import com.mromer.windfinder.utils.XmlToContinentParserUtil;

public class ContinentDao {

	private ArrayList<Continent> continents;

	private Context context;	

	public ContinentDao(Context context) {

		this.context = context;
	}


	public void loadContinents() {
		continents = new XmlToContinentParserUtil().getContinents(this.context);
	}


	public ArrayList<Continent> getContinents() {
		return continents;
	}


	public void setContinents(ArrayList<Continent> continents) {
		this.continents = continents;
	}


	public Continent getContinentById(String id) {

		ArrayList<Continent> continents = getContinents();

		Continent continentResult = null;

		for (Continent continent : continents) {

			if (continent.getId().equals(id)) {				
				continentResult = continent;
				break;
			}
		}

		return continentResult;
	}



	public Country getCountryById(String idContinent, String idCountry) {

		Continent continent = getContinentById(idContinent);

		Country countryResult = null;

		for (Country country : continent.getCountryList()) {

			if (country.getId().equals(idCountry)) {				
				countryResult = country;
				break;
			}
		}

		return countryResult;
	}


	public Region getRegionById(String continentId, String countryId,
			String regionId) {


		Country country = getCountryById(continentId, countryId);

		Region regionResult = null;

		for (Region region : country.getRegionList()) {

			if (region.getId().equals(regionId)) {

				regionResult = region;
				break;
			}
		}
		return regionResult;
	}

}
