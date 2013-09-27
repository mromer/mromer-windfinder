package com.mromer.windfinder.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mromer.windfinder.manager.ContinentManager;


public class LoadXmlTask {
	
	private Context context;	
	private LoadTaskResultI loadTaskResult;
	
	
	public LoadXmlTask(Context context, LoadTaskResultI loadTaskResult) {
		this.context = context;		
		this.loadTaskResult = loadTaskResult;
	}
	
	public void execute () {
		new LoadingXmlTask().execute();
	}

	public class LoadingXmlTask extends AsyncTask<Context, String, TaskResult> {		

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			progressDialog = ProgressDialog.show(context, null,
					"cargando", true);
			
		}

		@Override
		protected TaskResult doInBackground(Context... args) {	
			
			TaskResult result = new TaskResult();
			
			try {
				
				ContinentManager continentManager = ContinentManager.getInstance(context);
				continentManager.loadContinents();
				
				if (continentManager.getAllContinents() != null) {
					result.setError(false);					
				} else {
					result.setError(true);
					result.setDesc("Error cargando datos");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				
				result.setError(true);
				result.setDesc("Error cargando datos");
			}
			
			
			return result;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			
			progressDialog.dismiss();
			
			if (!result.isError()) {
				loadTaskResult.taskSuccess(result);
			} else {
				loadTaskResult.taskFailure(result);
			}
			
			

			

		}		
		
	}
}