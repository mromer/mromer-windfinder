package com.mromer.windfinder.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.mromer.windfinder.bean.Forecast;
import com.mromer.windfinder.bean.ForecastData;
import com.mromer.windfinder.bean.ForecastItem;
import com.mromer.windfinder.bean.ForecastStation;

public class XmlToForecastParserUtil {

	// We don't use namespaces
	private static final String ns = null;
	
	private static final String TAG_FORECASTS = "forecasts";


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

		parser.require(XmlPullParser.START_TAG, ns, TAG_FORECASTS);

		forecast.setTimestamp(parser.getAttributeValue(null, Forecast.TAG_TIMESTAMP));

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals(Forecast.TAG_STATION)) {

				forecast.setStationForecast(readForecastStation(parser));

			} else {
				skip(parser);
			}
		}  
		return forecast;
	}

	private ForecastStation readForecastStation(XmlPullParser parser) throws XmlPullParserException, IOException {
		ForecastStation forecastStation = new ForecastStation();

		parser.require(XmlPullParser.START_TAG, ns, Forecast.TAG_STATION);

		List<ForecastItem> forecastItems = new ArrayList<ForecastItem>();

		forecastStation.setId(parser.getAttributeValue(null, ForecastStation.TAG_ID));
		forecastStation.setName(parser.getAttributeValue(null, ForecastStation.TAG_NAME));
		forecastStation.setTimezone(parser.getAttributeValue(null, ForecastStation.TAG_TIMEZONE));

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals(ForecastStation.TAG_FORECAST)) {

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

		parser.require(XmlPullParser.START_TAG, ns, ForecastStation.TAG_FORECAST);
		

		forecastItem.setDate(parser.getAttributeValue(null, ForecastItem.TAG_DATE));
		forecastItem.setTime(parser.getAttributeValue(null, ForecastItem.TAG_TIME));
		
		HashMap<String, ForecastData> forecastDataMap = new HashMap<String, ForecastData>();


		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag			

			if (name.equals(ForecastItem.TAG_AIR_TEMPERATURE)) {
				forecastDataMap.put(ForecastItem.TAG_AIR_TEMPERATURE, 
						(readForecastData(parser, ForecastItem.TAG_AIR_TEMPERATURE)));

			} else if (name.equals(ForecastItem.TAG_WATER_TEMPERATURE)) {
				forecastDataMap.put(ForecastItem.TAG_WATER_TEMPERATURE, 
						(readForecastData(parser, ForecastItem.TAG_WATER_TEMPERATURE)));			
				
			} else if (name.equals(ForecastItem.TAG_WIND_DIRECTION)) {
				forecastDataMap.put(ForecastItem.TAG_WIND_DIRECTION, 
						(readForecastData(parser, ForecastItem.TAG_WIND_DIRECTION)));	
				
			} else if (name.equals(ForecastItem.TAG_WIND_SPEED)) {				
				forecastDataMap.put(ForecastItem.TAG_WIND_SPEED, 
						(readForecastData(parser, ForecastItem.TAG_WIND_SPEED)));	
				
			} else if (name.equals(ForecastItem.TAG_WIND_GUSTS)) {
				forecastDataMap.put(ForecastItem.TAG_WIND_GUSTS, 
						(readForecastData(parser, ForecastItem.TAG_WIND_GUSTS)));	
				
			} else if (name.equals(ForecastItem.TAG_WEATHER)) {
				forecastDataMap.put(ForecastItem.TAG_WEATHER, 
						(readForecastData(parser, ForecastItem.TAG_WEATHER)));	
				
			} else if (name.equals(ForecastItem.TAG_CLOUDS)) {
				forecastDataMap.put(ForecastItem.TAG_CLOUDS, 
						(readForecastData(parser, ForecastItem.TAG_CLOUDS)));	
				
			} else if (name.equals(ForecastItem.TAG_PRECIPITATION)) {
				forecastDataMap.put(ForecastItem.TAG_PRECIPITATION, 
						(readForecastData(parser, ForecastItem.TAG_PRECIPITATION)));	
				
			} else if (name.equals(ForecastItem.TAG_PRECIPITATION_TYPE)) {
				forecastDataMap.put(ForecastItem.TAG_PRECIPITATION_TYPE, 
						(readForecastData(parser, ForecastItem.TAG_PRECIPITATION_TYPE)));	
				
			} else if (name.equals(ForecastItem.TAG_WAVE_HEIGHT)) {
				forecastDataMap.put(ForecastItem.TAG_WAVE_HEIGHT, 
						(readForecastData(parser, ForecastItem.TAG_WAVE_HEIGHT)));	
				
			} else if (name.equals(ForecastItem.TAG_WAVE_DIRECTION)) {
				forecastDataMap.put(ForecastItem.TAG_WAVE_DIRECTION, (readForecastData(parser, ForecastItem.TAG_WAVE_DIRECTION)));	
				
			} else if (name.equals(ForecastItem.TAG_WAVE_PERIOD)) {
				forecastDataMap.put(ForecastItem.TAG_WAVE_PERIOD, (readForecastData(parser, ForecastItem.TAG_WAVE_PERIOD)));	
				
			} else if (name.equals(ForecastItem.TAG_AIR_PRESSURE)) {
				forecastDataMap.put(ForecastItem.TAG_AIR_PRESSURE, (readForecastData(parser, ForecastItem.TAG_AIR_PRESSURE)));	
				
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
		
		forecastData.setUnit(parser.getAttributeValue(null, ForecastData.TAG_UNIT));		
		
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
