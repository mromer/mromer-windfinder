package com.mromer.windfinder.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mromer.windfinder.R;
import com.mromer.windfinder.manager.ContinentManager;


public class GetContinentTask {
	
	private Context context;	
	private ContinentLoadTaskResultI loadTaskResult;
	
	
	public GetContinentTask(Context context, ContinentLoadTaskResultI loadTaskResult) {
		this.context = context;		
		this.loadTaskResult = loadTaskResult;
	}
	
	public void execute () {
		new LoadingXmlTask().execute();
	}

	public class LoadingXmlTask extends AsyncTask<Context, String, ContinentTaskResult> {		

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			progressDialog = ProgressDialog.show(context, null,
					context.getResources().getString(R.string.loading_data), true);
			
		}

		@Override
		protected ContinentTaskResult doInBackground(Context... args) {	
			
			ContinentTaskResult result = new ContinentTaskResult();
			
			try {
				
				ContinentManager continentManager = ContinentManager.getInstance(context);
				continentManager.loadContinents();
				
				if (continentManager.getAllContinents() != null) {
					result.setError(false);					
				} else {
					result.setError(true);
					result.setDesc(context.getResources().getString(R.string.error_loading_data));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				
				result.setError(true);
				result.setDesc(context.getResources().getString(R.string.error_loading_data));
			}
			
			
			return result;
		}

		@Override
		protected void onPostExecute(ContinentTaskResult result) {
			
			progressDialog.dismiss();
			
			if (!result.isError()) {
				loadTaskResult.taskSuccess(result);
			} else {
				loadTaskResult.taskFailure(result);
			}
			
			

			

		}		
		
	}
}