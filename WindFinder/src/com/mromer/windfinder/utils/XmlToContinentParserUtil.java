package com.mromer.windfinder.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Xml;

import com.mromer.windfinder.bean.Continent;
import com.mromer.windfinder.bean.Country;
import com.mromer.windfinder.bean.Region;
import com.mromer.windfinder.bean.Station;

public class XmlToContinentParserUtil {

	// We don't use namespaces
	private static final String ns = null;

	private final String FILE_NAME = "stations_by_country.xml";

	private final String START_TAG = "stations";

	public ArrayList<Continent> getContinents(Context context) {

		ArrayList<Continent> continents = null;

		AssetManager assetManager = context.getAssets();
		InputStream ims = null;

		try {

			ims = assetManager.open(FILE_NAME);

			continents = parse(ims);		

		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			
			e.printStackTrace();
		} finally {

			try {

				if (ims != null) {
					ims.close();
				}

			} catch (IOException e) {				
				e.printStackTrace();
			}
		}

		return continents;
	}

	public ArrayList<Continent> parse(InputStream in) throws XmlPullParserException, IOException {

		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(in, null);
		parser.nextTag();
		return readStations(parser);

	}

	
	/**
	 * Process list of continents.
	 * */
	private ArrayList<Continent> readStations(XmlPullParser parser) throws XmlPullParserException, IOException {
		ArrayList<Continent> continents = new ArrayList<Continent>();

		parser.require(XmlPullParser.START_TAG, ns, START_TAG);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals(Continent.TAG_CONTINENT)) {

				continents.add(readContinent(parser));

			} else {
				skip(parser);
			}
		}  
		return continents;
	}


	/**
	 * Process list of countries.
	 * */
	private Continent readContinent(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, Continent.TAG_CONTINENT);

		List<Country> countryList = new ArrayList<Country>();

		String idContinent = parser.getAttributeValue(null, Continent.TAG_CONTINENT_ID);
		String nameContinent = parser.getAttributeValue(null, Continent.TAG_CONTINENT_NAME);


		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}			

			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals(Country.TAG_COUNTRY)) {
				countryList.add(readCountry(parser));
			} else {
				skip(parser);
			}	        
		}

		return new Continent(idContinent, nameContinent, countryList);
	}



	/**
	 * Process list of regions.
	 * */
	private Country readCountry(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, Country.TAG_COUNTRY);		

		String idCountry = parser.getAttributeValue(null, Country.TAG_COUNTRY_ID);
		String nameCountry = parser.getAttributeValue(null, Country.TAG_COUNTRY_NAME);	

		List<Region> regionList = new ArrayList<Region>();

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals(Region.TAG_REGION)) {
				regionList.add(readRegion(parser));
			} else {
				skip(parser);
			}	                
		}

		return new Country(idCountry, nameCountry, regionList);
	}


	/**
	 * Process list of stations.
	 * */
	private Region readRegion(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, Region.TAG_REGION);		

		String idRegion = parser.getAttributeValue(null, Region.TAG_REGION_ID);
		String nameRegion = parser.getAttributeValue(null, Region.TAG_REGION_NAME);	

		List<Station> stationList = new ArrayList<Station>();

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals(Station.TAG_STATION)) {
				stationList.add(readStation(parser));
			} else {
				skip(parser);
			}	                
		}

		return new Region(idRegion, nameRegion, stationList);
	}


	/**
	 * Process a station.
	 * */
	private Station readStation(XmlPullParser parser) throws IOException, XmlPullParserException {

		parser.require(XmlPullParser.START_TAG, ns, Station.TAG_STATION);

		Station station = new Station();			

		station.setId(parser.getAttributeValue(null, Station.TAG_STATION_ID));			
		station.setName(parser.getAttributeValue(null, Station.TAG_STATION_NAME));		

		parser.nextTag();

		parser.require(XmlPullParser.END_TAG, ns, Station.TAG_STATION);			

		return station;
	}


	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}

}
