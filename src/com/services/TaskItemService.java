package com.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.transapp.extentions.DateExtentions;
import com.transapp.extentions.TaskMetadata;
import com.transapp.extentions.data.HttpStreamHelper;
import com.transapp.extentions.data.JsonParser;
import com.transapp.extentions.models.TaskItem;
import com.transapp.extentions.models.TaskStatus;

import android.os.AsyncTask;

public class TaskItemService {

	private String m_apiAddress;
	
	public TaskItemService(String apiAddress) {
		m_apiAddress = apiAddress;
	}
	
	public ArrayList<TaskItem> GetTasks(int userId, Date date)
	{
	
		DownLoadTaskItemsEvent tasks = new DownLoadTaskItemsEvent();
		
		JSONArray tasksJSONArray = null;
		
		ArrayList<TaskItem> tasksList = null;
	
		try {
			tasksJSONArray = tasks.execute(userId).get();
			
			tasksList = ExtractTasks(tasksJSONArray);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tasksList;
	}
	
	private ArrayList<TaskItem> ExtractTasks(JSONArray tasks) {
		ArrayList<TaskItem> taskList = new ArrayList<TaskItem>();
		try {
            // looping through All Events
            for(int i = 0; i < tasks.length(); i++)
            {
                JSONObject jsonTask = tasks.getJSONObject(i);
                 
                // Storing each json item in variable
                String id = jsonTask.getString(TaskMetadata.TAG_ID);
                String deliveryNumber = jsonTask.getString(TaskMetadata.TAG_DELIVERY_NUMBER);
                String taskStatus = jsonTask.getString(TaskMetadata.TAG_TASK_STATUS);
                String pickedUpAt = jsonTask.getString(TaskMetadata.TAG_PICKED_UP_AT);
                String deliveryAt = jsonTask.getString(TaskMetadata.TAG_DELIVERY_AT);
                String pickUpTime = jsonTask.getString(TaskMetadata.TAG_PICK_UP_TIME);
                String deliveryTime = jsonTask.getString(TaskMetadata.TAG_DELIVERY_TIME);
                String lastModified = jsonTask.getString(TaskMetadata.TAG_LAST_MODIFIED);
                String comment = jsonTask.getString(TaskMetadata.TAG_COMMENT);
                String senderAddress = GetAddressString(jsonTask, TaskMetadata.TAG_SENDER_ADDRESS);
                String reciverAddress = GetAddressString(jsonTask, TaskMetadata.TAG_RECIVER_ADDRESS);
                int userId = GetUserId(jsonTask, TaskMetadata.TAG_USER);
                String userComment = jsonTask.getString(TaskMetadata.TAG_USER_COMMENT);
                Boolean rejected =  GetRejected(jsonTask.getString(TaskMetadata.TAG_REKECTED));
                
                
                TaskItem taskItem = new TaskItem();
        		taskItem.SetId(Integer.parseInt(id));
        		taskItem.SetDeliveryNumber(deliveryNumber);
        		taskItem.SetUserId(userId);
        		taskItem.SetCompanyId(1);
        		taskItem.SetSenderAddress(senderAddress);
        		taskItem.SetReciverAddress(reciverAddress);
        		taskItem.SetTaskStatus(TaskStatus.fromValue(Integer.parseInt(taskStatus)));
        		
//        		//fix date bug
//        		Date tempDate = new Date();
//        		tempDate.setDate(5);
//        		tempDate.setMonth(7);
//        		tempDate.setYear(2013);
//        		tempDate.setHours(12);
//        		tempDate.setMinutes(35);
//        		taskItem.SetPickedUpAt(tempDate);
//        		taskItem.SetDeliveredAt(tempDate);
//        		taskItem.SetPickUpTime(tempDate);
//        		taskItem.SetDeliveryTime(tempDate);
//        		taskItem.SetLastModified(tempDate);
        		taskItem.SetPickedUpAt(DateExtentions.GetDateFromWebApi(pickedUpAt));
        		taskItem.SetDeliveredAt(DateExtentions.GetDateFromWebApi(deliveryAt));
        		taskItem.SetPickUpTime(DateExtentions.GetDateFromWebApi(pickUpTime));
        		taskItem.SetDeliveryTime(DateExtentions.GetDateFromWebApi(deliveryTime));
        		taskItem.SetLastModified(DateExtentions.GetDateFromWebApi(lastModified));
        		taskItem.SetComment(comment);
        		taskItem.SetContactId(1);
        		taskItem.SetTaskType(1);
        		taskItem.SetDataExtention("data");
        		taskItem.SetUserComment(userComment);
        		taskItem.SetRejected(rejected);
 
        		taskList.add(taskItem);

            }
        }
		catch (JSONException e)
		{
            e.printStackTrace();
		}
		return taskList;
	}
	
	public Boolean GetRejected(String rejected)
	{
		Boolean reuslt = null;
		if(rejected != null  && rejected != "")
		{
			reuslt = Boolean.valueOf(rejected);
		}
		
		return reuslt;
	}

	private String GetAddressString(JSONObject jsonTask, String AddressTag) throws JSONException {
		JsonParser jsonParser = new JsonParser();
		JSONObject addressJSON = jsonParser.GetJSONObject(jsonTask.getString(AddressTag));
		
		String streetName = addressJSON.getString(TaskMetadata.TAG_ADDRESS_STREET_NAME);
		int streetNumber = addressJSON.getInt(TaskMetadata.TAG_ADDRESS_STREET_NUMBER);
		String city = addressJSON.getString(TaskMetadata.TAG_ADDRESS_CITY);
		String address = streetName + " " + streetNumber + ", " + city;
		return address;
	}
	
	private int GetUserId(JSONObject jsonTask, String UserIdTag) throws JSONException {
		JsonParser jsonParser = new JsonParser();
		JSONObject addressJSON = jsonParser.GetJSONObject(jsonTask.getString(UserIdTag));
		
		int userId = addressJSON.getInt(TaskMetadata.TAG_USER_ID);
		return userId;
	}
		
	private JSONArray GetTasksArray(int userId, Date date) {
		HttpStreamHelper httpStreamHelper = new HttpStreamHelper();
		//api/Task?userId={userId}&lastModified={lastModified}
		String fullTaskAddress = m_apiAddress + "/" + userId;
		InputStream response = httpStreamHelper.getStringFromUrl(fullTaskAddress);
		
		JsonParser parser = new JsonParser();
		JSONArray tasks = (JSONArray) parser.getJSONFromString(response);
		return tasks;
	}
	
	private class DownLoadTaskItemsEvent extends AsyncTask<Integer, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(Integer... params) {
			JSONArray tasks = GetTasksArray(params[0], new Date());
			return tasks;
		}
	}

}
