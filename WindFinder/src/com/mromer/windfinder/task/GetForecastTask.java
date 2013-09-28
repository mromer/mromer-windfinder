package com.mromer.windfinder.task;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mromer.windfinder.bean.Forecast;
import com.mromer.windfinder.utils.GetForecastHttpUtil;
import com.mromer.windfinder.utils.XmlToForecastParserUtil;


public class GetForecastTask {
	
	private Context context;	
	private ForecastLoadTaskResultI forecastLoadTaskResult;
	List<String> stations;
	
	
	public GetForecastTask(Context context, ForecastLoadTaskResultI forecastLoadTaskResult, List<String> stations) {
		this.context = context;		
		this.forecastLoadTaskResult = forecastLoadTaskResult;
		this.stations = stations;
	}
	
	public void execute () {
		new LoadingXmlTask().execute();
	}

	public class LoadingXmlTask extends AsyncTask<Context, String, ForecastTaskResult> {		

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			progressDialog = ProgressDialog.show(context, null,
					"cargando", true);
			
		}

		@Override
		protected ForecastTaskResult doInBackground(Context... args) {	
			
			ForecastTaskResult result = new ForecastTaskResult();
			result.setError(false);
			
			try {
				
				List<Forecast> forecastList = new ArrayList<Forecast>();
				
				for (String station : stations) {
					String xml = new GetForecastHttpUtil().getForecasts(station);
					
					if (xml != null) {
						InputStream iStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
						
						XmlToForecastParserUtil xmlToForecastParserUtil = new XmlToForecastParserUtil();					
						forecastList.add(xmlToForecastParserUtil.getForecast(iStream));
						
						
					} else {
						result.setError(true);
						result.setDesc("Error cargando datos");
					}
				}
				
				result.setForecastList(forecastList);
				
			} catch (Exception e) {
				e.printStackTrace();
				
				result.setError(true);
				result.setDesc("Error cargando datos");
			}
			
			
			return result;
		}

		@Override
		protected void onPostExecute(ForecastTaskResult result) {
			
			progressDialog.dismiss();
			
			if (!result.isError()) {
				forecastLoadTaskResult.taskSuccess(result);
			} else {
				forecastLoadTaskResult.taskFailure(result);
			}
			
			

			

		}		
		
	}
}