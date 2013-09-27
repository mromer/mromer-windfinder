package com.mromer.windfinder.task;

public interface LoadTaskResultI {
	
	public void taskSuccess(TaskResult result);
	
	public void taskFailure(TaskResult result);

}
