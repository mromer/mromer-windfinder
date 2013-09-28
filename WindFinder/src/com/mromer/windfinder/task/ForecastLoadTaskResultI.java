package com.mromer.windfinder.task;

public interface ForecastLoadTaskResultI {
	
	public void taskSuccess(ForecastTaskResult result);
	
	public void taskFailure(ForecastTaskResult result);

}
