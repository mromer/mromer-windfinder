package com.mromer.windfinder.task;

public interface ContinentLoadTaskResultI {
	
	public void taskSuccess(ContinentTaskResult result);
	
	public void taskFailure(ContinentTaskResult result);

}
