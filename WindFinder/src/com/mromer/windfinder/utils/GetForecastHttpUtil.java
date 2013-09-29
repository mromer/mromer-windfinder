package com.mromer.windfinder.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class GetForecastHttpUtil {



	public String getForecasts(String stationId) {		

		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = 10000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 10000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		InputStream in = null;

		String endpoint = "http://www.windfinder.com/api/forecast?CUSTOMER=vista&FORMAT=XML&STATIONS=" + stationId;
		//		String requestParameters = "latlng=" + latitud + "," + longitud + "&sensor=false";

		String xml = null;
		HttpClient httpClient = null;

		// Send a GET request to the servlet
		try	{

			// Send data
			String urlStr = endpoint;
			//				if (requestParameters != null && requestParameters.length () > 0) {
			//					urlStr += "?" + requestParameters;
			//				}


			httpClient = new DefaultHttpClient(httpParameters);

			Log.d("tag", "request url: " + urlStr);
			HttpGet request = new HttpGet(urlStr);				
			HttpResponse httpresponse = httpClient.execute(request);

			in = httpresponse.getEntity().getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			String sResponse;
			StringBuilder s = new StringBuilder();

			while ((sResponse = reader.readLine()) != null) {
				s = s.append(sResponse);
			}
			xml = s.toString();

			in.close();
			httpClient.getConnectionManager().shutdown();

		} catch (Exception e) {

			Log.d("Error", "Error getting data");
			e.printStackTrace();

		} finally {
			closeResources(in, httpClient);
		}

		return xml;
	}
	
	private void closeResources(InputStream in, HttpClient httpClient) {
		try {
			if (in != null) {
				in.close();
			} 
			if (httpClient != null){
				httpClient.getConnectionManager().shutdown();
			}
		} catch (IOException e) {
			Log.d("Error", "Error closing resources");
			e.printStackTrace();
		}
	}


}
