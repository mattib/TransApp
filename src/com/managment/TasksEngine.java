package com.managment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.services.TaskItemService;
import com.transapp.extentions.data.TasksDataBaseHelper;
import com.transapp.extentions.models.TaskItem;
import com.transapp.extentions.models.TaskStatus;

/**
 * TasksEngine - - In charge of downloading and saving in database TaskItems
 * @author Matti Ben-Avraham
 *
 */
public class TasksEngine {

	private String m_tasksServiceAddress = "http://transappapi.apphb.com/api/task";
	private TasksDataBaseHelper tasksDbHelper;
	
	public TasksEngine(Context context) {
		tasksDbHelper = new TasksDataBaseHelper(context);
	}
	
	/**
	 * UpdateTaskItems - updates the TaskItem's with the userId with TaskItem's in the server
	 * @param userId
	 */
	public void UpdateTaskItems(int userId)
	{
		TaskItemService taskItemService = new TaskItemService(m_tasksServiceAddress);
		
		ArrayList<TaskItem> taskList = taskItemService.GetTasks(userId);
		
		SaveTaskItems(taskList);
	}
	
	/**
	 * Get TaskItem by TaskId
	 * @param taskId
	 * @return TaskItem
	 */
	public TaskItem GetTaskItem(int taskId)
	{
		return tasksDbHelper.getTaskItem(taskId);
	}
	
	/**
	 * Get TaskItems by user Id
	 * @param userId
	 * @return Array list with TaskItems
	 */
	public ArrayList<TaskItem> GetTaskItems(int userId)
	{
		List<TaskItem> tasksList = tasksDbHelper.getAllTaskItems();
		
		//change later that only users tasks are taken
		ArrayList<TaskItem> result = new ArrayList<TaskItem>();
		for(int index = 0; index < tasksList.size(); index++)
		{
			TaskItem taskItem = tasksList.get(index);
			if(taskItem.GetUserId() == userId) {
				result.add(taskItem);
			}
		}
		return result;
	}
	
	/**
	 * Save TaskItems in database
	 * @param tasks
	 */
	public void SaveTaskItems(ArrayList<TaskItem> tasks)
	{
		for (int i = 0; i < tasks.size(); i++){
			TaskItem taskItem = tasks.get(i);
			SaveTaskItem(taskItem);
			}
	}
	
	/**
	 * Saves an TaskItem
	 * @param task
	 */
	public void SaveTaskItem(TaskItem task)
	{
		boolean taskExists = tasksDbHelper.taskItemExists(task.GetId());
		if(taskExists)
		{
			tasksDbHelper.updateTaskItem(task);
		}
		else
		{
			tasksDbHelper.addTaskItem(task);
		}
	}
	
	/**
	 * Change Task Status
	 * @param task
	 * @param taskstatus
	 */
	public void ChangeTaskStatus(TaskItem task, TaskStatus taskstatus)
	{
		if(taskstatus == TaskStatus.Accepted)
		{
			task.SetRejected(false);
		}
		else if(taskstatus == TaskStatus.Rejected)
		{
			task.SetRejected(true);
		}
		task.SetTaskStatus(taskstatus);
		Date now = new Date();
		task.SetLastModified(now);
		task.SetPickedUpAt(now);
		
		tasksDbHelper.updateTaskItem(task);
	}
	
	/**
	 * Complete Task
	 * @param task
	 * @param comment
	 */
	public void CompleteTask(TaskItem task, String comment)
	{
		task.SetTaskStatus(TaskStatus.Finished);
		task.SetUserComment(comment);
		Date now = new Date();
		task.SetLastModified(now);
		
		tasksDbHelper.updateTaskItem(task);
	}

}
