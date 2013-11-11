package com.managment;

import java.util.Date;
import java.util.List;

import com.transapp.extentions.models.InputType;
import com.transapp.extentions.models.TaskEvent;
import com.transapp.extentions.models.TaskItem;
import com.transapp.extentions.models.TaskStatus;

import android.content.Context;

/**
 * TasksManager - manages TaskItems in the system
 * @author Matti Ben-Avraham
 *
 */
public class TasksManager {

	private TasksEngine m_taskEngine;
	private TaskEventEngine m_taskEventEngine;
	
	public TasksManager(Context context) {
		m_taskEngine = new TasksEngine(context);
		m_taskEventEngine = new TaskEventEngine(context);
	}
	
	/**
	 * loads all Users tasks to database. (Checks if there are updates)
	 * @param userId
	 */
	public void LoadUsersTasks(int userId)
	{
		m_taskEngine.UpdateTaskItems(userId);
	}
	
	/**
	 * loads all Users tasks to database. (Checks if there are updates)
	 * @param userId
	 */
	public void LoadTodaysUsersTasks(int userId)
	{
		m_taskEngine.UpdateTaskItems(userId);
	}
	
	/**
	 * Gets user tasks
	 * @param userId
	 * @return TaskItem array
	 */
	public TaskItem[] GetTasks(int userId)
	{
		List<TaskItem> result = m_taskEngine.GetTaskItems(userId);
		
		return result.toArray(new TaskItem[result.size()]);
	}

	/**
	 * Gets specific task
	 * @param taskId
	 * @return TaskItem
	 */
	public TaskItem GetTask(int taskId) {
		return m_taskEngine.GetTaskItem(taskId);
	}
	
	/**
	 * Saves task in database
	 * @param task
	 */
	public void SaveTask(TaskItem task) {
		m_taskEngine.SaveTaskItem(task);
	}
	
	/**
	 * Task Start method is called when task is started
	 * @param taskId
	 */
	public void StartTask(int taskId){
		TaskItem task = m_taskEngine.GetTaskItem(taskId);
		m_taskEngine.ChangeTaskStatus(task, TaskStatus.Started);
		TaskEvent taskEvent = new TaskEvent();
		taskEvent.m_taskId = taskId;
		taskEvent.m_inputType = InputType.TaskStatusChange.value();
		taskEvent.m_eventType = TaskStatus.Started.value();
		taskEvent.m_userId = task.GetUserId();
		taskEvent.m_text = "";
		m_taskEventEngine.UploadTaskEvents(taskEvent);	
	}
	
	/**
	 * Task Accept is called when task was Accepted
	 * @param taskId
	 */
	public void AcceptTask(int taskId){
		TaskItem task = m_taskEngine.GetTaskItem(taskId);
		m_taskEngine.ChangeTaskStatus(task, TaskStatus.Accepted);
		TaskEvent taskEvent = new TaskEvent();
		taskEvent.m_taskId = taskId;
		taskEvent.m_inputType = InputType.TaskStatusChange.value();
		taskEvent.m_eventType = TaskStatus.Accepted.value();
		taskEvent.m_userId = task.GetUserId();
		taskEvent.m_text = "";
		m_taskEventEngine.UploadTaskEvents(taskEvent);
	}
	
	/**
	 * Task Rejected is called when Task is rejected 
	 * @param taskId
	 */
	public void RejectTask(int taskId){
		TaskItem task = m_taskEngine.GetTaskItem(taskId);
		m_taskEngine.ChangeTaskStatus(task, TaskStatus.Rejected);
		TaskEvent taskEvent = new TaskEvent();
		taskEvent.m_taskId = taskId;
		taskEvent.m_inputType = InputType.TaskStatusChange.value();
		taskEvent.m_eventType = TaskStatus.Rejected.value();
		taskEvent.m_userId = task.GetUserId();
		taskEvent.m_text = "";
		m_taskEventEngine.UploadTaskEvents(taskEvent);
	}
	
	/**
	 * Task Completed is called when task is completed
	 * @param taskId
	 * @param comment - adds user comment
	 */
	public void CompleteTask(int taskId, String comment){
		TaskItem task = m_taskEngine.GetTaskItem(taskId);
		m_taskEngine.CompleteTask(task, comment);
		TaskEvent completeTaskEvent = new TaskEvent();
		completeTaskEvent.m_taskId = taskId;
		completeTaskEvent.m_inputType = InputType.TaskStatusChange.value();
		completeTaskEvent.m_eventType = TaskStatus.Finished.value();
		completeTaskEvent.m_userId = task.GetUserId();
		
		m_taskEventEngine.UploadTaskEvents(completeTaskEvent);
		
		TaskEvent userCommentEvent = new TaskEvent();
		userCommentEvent.m_taskId = taskId;
		userCommentEvent.m_inputType = InputType.UserComment.value();
		userCommentEvent.m_userId = task.GetUserId();
		userCommentEvent.m_text = comment;
		m_taskEventEngine.UploadTaskEvents(userCommentEvent);
	}
	
	/**
	 * Updates the Api Server the Image Key of task
	 * @param taskId
	 * @param imageKey
	 */
	public void ImageKeyEvent(int taskId, String imageKey){
		TaskItem task = m_taskEngine.GetTaskItem(taskId);
		String imageId = task.GetImageId();
		if(imageId == null || imageId == ""){
			task.SetImageId(imageKey);
			m_taskEngine.SaveImageId(task);
			TaskEvent taskEvent = new TaskEvent();
			taskEvent.m_taskId = taskId;
			taskEvent.m_inputType = InputType.ImageId.value();
			taskEvent.m_userId = task.GetUserId();
			taskEvent.m_text = imageKey;
			m_taskEventEngine.UploadTaskEvents(taskEvent);
		}
	}
	
	/**
	 * Updates the Api Server with the Signature Key of task
	 * @param taskId
	 * @param signatureKey
	 */
	public void SignatureKeyEvent(int taskId, String signatureKey){
		TaskItem task = m_taskEngine.GetTaskItem(taskId);
		TaskEvent taskEvent = new TaskEvent();
		taskEvent.m_taskId = taskId;
		taskEvent.m_inputType = InputType.SignatureId.value();
		taskEvent.m_userId = task.GetUserId();
		taskEvent.m_text = signatureKey;
		m_taskEventEngine.UploadTaskEvents(taskEvent);
	}
}
