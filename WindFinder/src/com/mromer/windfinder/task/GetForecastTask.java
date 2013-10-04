package com.mromer.windfinder.task;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mromer.windfinder.R;
import com.mromer.windfinder.bean.Forecast;
import com.mromer.windfinder.utils.GetForecastHttpUtil;
import com.mromer.windfinder.utils.XmlToForecastParserUtil;


public class GetForecastTask extends AsyncTask<Context, String, ForecastTaskResult> {

	private Context context;	
	
	private ForecastLoadTaskResultI forecastLoadTaskResult;
	
	private List<String> stations;
	
	private boolean progressDialogFlag;
	
	private ProgressDialog progressDialog;

	public GetForecastTask(Context context, ForecastLoadTaskResultI forecastLoadTaskResult, List<String> stations, 
			boolean progressDialogFlag) {

		this.context = context;		
		this.forecastLoadTaskResult = forecastLoadTaskResult;
		this.stations = stations;
		this.progressDialogFlag = progressDialogFlag;
	}	

	@Override
	protected void onPreExecute() {

		super.onPreExecute();

		if (progressDialogFlag) {
			progressDialog = ProgressDialog.show(context, null,
					context.getResources().getString(R.string.loading_data), true);
		}
	}

	@Override
	protected ForecastTaskResult doInBackground(Context... args) {	
		
		ForecastTaskResult result = new ForecastTaskResult();
		result.setError(false);

		try {

			List<Forecast> forecastList = new ArrayList<Forecast>();

			for (String station : stations) {				
				// Get forecast from a station
				String xml = new GetForecastHttpUtil().getForecasts(station);

				if (xml != null) {
					// xml string to input stream
					ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
					InputStream iStream = byteArrayInputStream;

					// xml parser
					XmlToForecastParserUtil xmlToForecastParserUtil = new XmlToForecastParserUtil();	
					Forecast forecast = xmlToForecastParserUtil.getForecast(iStream);

					closeResources(byteArrayInputStream, iStream);											

					if (forecast != null && forecast.getStationForecast() != null) {
						forecastList.add(forecast);
					}

				} else {
					result.setError(true);
					result.setDesc(context.getResources().getString(R.string.error_loading_data));
				}
			}

			result.setForecastList(forecastList);

		} catch (Exception e) {
			e.printStackTrace();

			result.setError(true);
			result.setDesc(context.getResources().getString(R.string.error_loading_data));
		}

		return result;
		
	}

	@Override
	protected void onPostExecute(ForecastTaskResult result) {
		if (progressDialogFlag) {
			progressDialog.dismiss();
		}

		if (!result.isError()) {
			forecastLoadTaskResult.taskSuccess(result);
		} else {
			forecastLoadTaskResult.taskFailure(result);
		}
	}
	

	private void closeResources(ByteArrayInputStream byteArrayInputStream,
			InputStream iStream) throws IOException {

		byteArrayInputStream.close();
		iStream.close();
	}
}