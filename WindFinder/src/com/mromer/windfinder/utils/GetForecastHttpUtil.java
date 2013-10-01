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

import com.mromer.windfinder.commons.ConstantsMain;

import android.util.Log;

public class GetForecastHttpUtil {
	
	private final String ENCODE = "UTF-8";


	/**
	 * Get string xml from winf finder server
	 * */
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

		String endpoint = ConstantsMain.WINF_FINDER_END_POINT + stationId;		

		String xml = null;
		HttpClient httpClient = null;

		// Send a GET request to the servlet
		try	{

			httpClient = new DefaultHttpClient(httpParameters);

			Log.d("tag", "request url: " + endpoint);
			HttpGet request = new HttpGet(endpoint);				
			HttpResponse httpresponse = httpClient.execute(request);

			in = httpresponse.getEntity().getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(in, ENCODE));
			
			xml = responseToString(reader);

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
	
	
	
	private String responseToString(BufferedReader reader) throws IOException {
		String sResponse;
		StringBuilder stringBuilder = new StringBuilder();

		while ((sResponse = reader.readLine()) != null) {
			stringBuilder = stringBuilder.append(sResponse);
		}
		
		return stringBuilder.toString();
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
