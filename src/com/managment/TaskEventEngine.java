package com.managment;

import com.services.TaskEventService;
import com.transapp.extentions.models.TaskEvent;

import android.content.Context;
/**
 * UploadTaskEvents - In charge of uploading TaskEvents to server
 * In the future will save to database and queue them before sending
 * @author Matti Ben-Avraham
 *
 */
public class TaskEventEngine {

	private String m_tasksServiceAddress = "http://transappapi.apphb.com/api/event";
	
	/**
	 * TaskEventEngine - constructor
	 * @param context - will be used in the future
	 */
	public TaskEventEngine(Context context) {
	}
	
	/**
	 * UploadTaskEvents - Method to upload TaskEvents to server.
	 * @param event - the TaskEvent
	 */
	public void UploadTaskEvents(TaskEvent event)
	{
		TaskEventService taskItemService = new TaskEventService(m_tasksServiceAddress);
		
		taskItemService.UploadEvents(event);

	}

}
