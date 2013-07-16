package com.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.transapp.extentions.models.TaskEvent;

import android.os.AsyncTask;

public class TaskEventService {

	private String m_apiAddress;
	
	public TaskEventService(String apiAddress) {
		m_apiAddress = apiAddress;
	}
	
	public void UploadEvents(TaskEvent event)
	{
		UpLoadTaskEventsEvent uploadEventEvent = new UpLoadTaskEventsEvent();
		
		uploadEventEvent.execute(event);
	}
	

	private void PostEvents(TaskEvent event) {	
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(m_apiAddress);

	    try {
	    	
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("Id", "0"));
	        nameValuePairs.add(new BasicNameValuePair("Text", event.m_text));
	        Date now = new Date();
	        nameValuePairs.add(new BasicNameValuePair("Time", now.toGMTString()));
	        if(event.m_userId != null)
	        {
	        	nameValuePairs.add(new BasicNameValuePair("UserId", event.m_userId.toString()));
	        }
	        if(event.m_inputType != null)
	        {
	        	nameValuePairs.add(new BasicNameValuePair("InputType", event.m_inputType.toString()));
	        }
	        nameValuePairs.add(new BasicNameValuePair("RowStatus", "0"));
	        if(event.m_eventType != null)
	        {
	        	nameValuePairs.add(new BasicNameValuePair("EventType", event.m_eventType.toString()));
	        }
	        if(event.m_taskId != null)
	        {
	        	nameValuePairs.add(new BasicNameValuePair("TaskId", event.m_taskId.toString()));
	        }
	        
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);

	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	}
	
	private class UpLoadTaskEventsEvent extends AsyncTask<TaskEvent, Void, Void> {

		@Override
		protected Void doInBackground(TaskEvent... params) {
			PostEvents(params[0]);
			return null;
		}
	}
}
