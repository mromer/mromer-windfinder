package com.mromer.windfinder.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.util.Xml;

import com.mromer.windfinder.bean.Continent;
import com.mromer.windfinder.bean.Country;
import com.mromer.windfinder.bean.Region;
import com.mromer.windfinder.bean.Station;

public class XmlParseUtil {

	// We don't use namespaces
	private static final String ns = null;

	
	public ArrayList<Continent> getContinents(Context context) {
		
		ArrayList<Continent> continents = null;
		
		AssetManager assetManager = context.getAssets();
		InputStream ims = null;

		try {

			ims = assetManager.open("stations_by_country.xml");

			continents = parse(ims);		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				
				if (ims != null) {
					ims.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
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

	private ArrayList<Continent> readStations(XmlPullParser parser) throws XmlPullParserException, IOException {
		ArrayList<Continent> continents = new ArrayList<Continent>();

		parser.require(XmlPullParser.START_TAG, ns, "stations");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("continent")) {
				Log.d("XMLPARSER", "continent");
				continents.add(readContinent(parser));
		
			} else {
				skip(parser);
			}
		}  
		return continents;
	}


	// Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
	// to their respective "read" methods for processing. Otherwise, skips the tag.
	private Continent readContinent(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "continent");
		
		List<Country> countryList = new ArrayList<Country>();
		
		String idContinent = parser.getAttributeValue(null, "id");
		String nameContinent = parser.getAttributeValue(null, "name");
		
		
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}			
			
			String name = parser.getName();
	        // Starts by looking for the entry tag
	        if (name.equals("country")) {
	        	countryList.add(readCountry(parser));
	        } else {
	            skip(parser);
	        }	        
		}
		
		return new Continent(idContinent, nameContinent, countryList);
	}
	
	
	// Processes link tags in the feed.
	private Country readCountry(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "country");		
		
		String idCountry = parser.getAttributeValue(null, "id");
		String nameCountry = parser.getAttributeValue(null, "name");	
		
		List<Region> regionList = new ArrayList<Region>();
		
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			
			String name = parser.getName();
	        // Starts by looking for the entry tag
	        if (name.equals("region")) {
	        	regionList.add(readRegion(parser));
	        } else {
	            skip(parser);
	        }	                
		}
		
		return new Country(idCountry, nameCountry, regionList);
	}
	
	
	// Processes link tags in the feed.
	private Region readRegion(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "region");		
		
		String idRegion = parser.getAttributeValue(null, "id");
		String nameRegion = parser.getAttributeValue(null, "name");	
		
		List<Station> stationList = new ArrayList<Station>();
		
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			
			String name = parser.getName();
	        // Starts by looking for the entry tag
	        if (name.equals("station")) {
	        	stationList.add(readStation(parser));
	        } else {
	            skip(parser);
	        }	                
		}
		
		return new Region(idRegion, nameRegion, stationList);
	}
	
	
	// Processes title tags in the feed.
		private Station readStation(XmlPullParser parser) throws IOException, XmlPullParserException {
			
			parser.require(XmlPullParser.START_TAG, ns, "station");
			
			Station station = new Station();
			
			station.setForecast(parser.getAttributeValue(null, "forecast"));
			station.setId(parser.getAttributeValue(null, "id"));
			station.setKeyword(parser.getAttributeValue(null, "keyword"));
			station.setName(parser.getAttributeValue(null, "name"));
			station.setReport(parser.getAttributeValue(null, "report"));
			station.setStatistic(parser.getAttributeValue(null, "statistic"));
			station.setSuperforecast(parser.getAttributeValue(null, "superforecast"));
			station.setWaveforecast(parser.getAttributeValue(null, "waveforecast"));
			station.setWavereport(parser.getAttributeValue(null, "wavereport"));	
			
			parser.nextTag();
			
			parser.require(XmlPullParser.END_TAG, ns, "station");			
			
			return station;
		}

	// Processes title tags in the feed.
	private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "title");
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "title");
		return title;
	}

	// Processes link tags in the feed.
	private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
		String link = "";
		parser.require(XmlPullParser.START_TAG, ns, "link");
		String tag = parser.getName();
		String relType = parser.getAttributeValue(null, "rel");  
		if (tag.equals("link")) {
			if (relType.equals("alternate")){
				link = parser.getAttributeValue(null, "href");
				parser.nextTag();
			} 
		}
		parser.require(XmlPullParser.END_TAG, ns, "link");
		return link;
	}

	// Processes summary tags in the feed.
	private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "summary");
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "summary");
		return summary;
	}

	// For the tags title and summary, extracts their text values.
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
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
