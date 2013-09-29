package com.mromer.windfinder.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.mromer.windfinder.bean.Forecast;
import com.mromer.windfinder.bean.ForecastData;
import com.mromer.windfinder.bean.ForecastItem;
import com.mromer.windfinder.bean.ForecastStation;

public class XmlToForecastParserUtil {

	// We don't use namespaces
	private static final String ns = null;


	public Forecast getForecast(InputStream inputStream) {
		
		Forecast forecast = null;		

		try {			

			forecast = parse(inputStream);		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {

				if (inputStream != null) {
					inputStream.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return forecast;
	}

	public Forecast parse(InputStream in) throws XmlPullParserException, IOException {

		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(in, null);
		parser.nextTag();
		return readForecast(parser);

	}

	private Forecast readForecast(XmlPullParser parser) throws XmlPullParserException, IOException {
		Forecast forecast = new Forecast();

		parser.require(XmlPullParser.START_TAG, ns, "forecasts");

		forecast.setTimestamp(parser.getAttributeValue(null, "timestamp"));

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("station")) {

				forecast.setStationForecast(readForecastStation(parser));

			} else {
				skip(parser);
			}
		}  
		return forecast;
	}

	private ForecastStation readForecastStation(XmlPullParser parser) throws XmlPullParserException, IOException {
		ForecastStation forecastStation = new ForecastStation();

		parser.require(XmlPullParser.START_TAG, ns, "station");

		List<ForecastItem> forecastItems = new ArrayList<ForecastItem>();

		forecastStation.setId(parser.getAttributeValue(null, "id"));
		forecastStation.setName(parser.getAttributeValue(null, "name"));
		forecastStation.setTimezone(parser.getAttributeValue(null, "timezone"));

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("forecast")) {

				forecastItems.add(readForecastItem(parser));

			} else {
				skip(parser);
			}
		}  

		forecastStation.setForecastItems(forecastItems);

		return forecastStation;
	}


	private ForecastItem readForecastItem(XmlPullParser parser) throws XmlPullParserException, IOException {

		ForecastItem forecastItem = new ForecastItem();

		parser.require(XmlPullParser.START_TAG, ns, "forecast");

		

		forecastItem.setDate(parser.getAttributeValue(null, "date"));
		forecastItem.setTime(parser.getAttributeValue(null, "time"));
		
		HashMap<String, ForecastData> forecastDataMap = new HashMap<String, ForecastData>();


		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag			

			if (name.equals("air_temperature")) {
				forecastDataMap.put("air_temperature", (readForecastData(parser, "air_temperature")));

			} else if (name.equals("water_temperature")) {
				forecastDataMap.put("water_temperature", (readForecastData(parser, "water_temperature")));			
				
			} else if (name.equals("wind_direction")) {
				forecastDataMap.put("wind_direction", (readForecastData(parser, "wind_direction")));	
				
			} else if (name.equals("wind_speed")) {				
				forecastDataMap.put("wind_speed", (readForecastData(parser, "wind_speed")));	
				
			} else if (name.equals("wind_gusts")) {
				forecastDataMap.put("wind_gusts", (readForecastData(parser, "wind_gusts")));	
				
			} else if (name.equals("weather")) {
				forecastDataMap.put("weather", (readForecastData(parser, "weather")));	
				
			} else if (name.equals("clouds")) {
				forecastDataMap.put("clouds", (readForecastData(parser, "clouds")));	
				
			} else if (name.equals("precipitation")) {
				forecastDataMap.put("precipitation", (readForecastData(parser, "precipitation")));	
				
			} else if (name.equals("precipitation_type")) {
				forecastDataMap.put("precipitation_type", (readForecastData(parser, "precipitation_type")));	
				
			} else if (name.equals("wave_height")) {
				forecastDataMap.put("wave_height", (readForecastData(parser, "wave_height")));	
				
			} else if (name.equals("wave_direction")) {
				forecastDataMap.put("wave_direction", (readForecastData(parser, "wave_direction")));	
				
			} else if (name.equals("wave_period")) {
				forecastDataMap.put("wave_period", (readForecastData(parser, "wave_period")));	
				
			} else if (name.equals("air_pressure")) {
				forecastDataMap.put("air_pressure", (readForecastData(parser, "air_pressure")));	
				
			} else {
				skip(parser);
			}
		} 
		
		forecastItem.setForecastDataMap(forecastDataMap);

		return forecastItem;
	}


	private ForecastData readForecastData(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
		ForecastData forecastData = new ForecastData();
		
		parser.require(XmlPullParser.START_TAG, ns, tag);		
		
		forecastData.setUnit(parser.getAttributeValue(null, "unit"));		
		
		forecastData.setValue(readText(parser));		
		
		parser.require(XmlPullParser.END_TAG, ns, tag);
		
		return forecastData;
	}

	
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
