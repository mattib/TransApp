package com.services;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;

import com.transapp.extentions.data.HttpStreamHelper;
import com.transapp.extentions.data.JsonParser;
import com.transapp.extentions.models.User;

public class UserAuthenticationService {

	private String m_apiAddress;
	
	public UserAuthenticationService(String apiAddress) {
		m_apiAddress = apiAddress;
	}
	
	public int GetUserId(User user)
	{
		ValidateUserEvent userDownloadEvent = new ValidateUserEvent();
		
		int userIsValid = -1;
	
		try {
			userIsValid = userDownloadEvent.execute(user).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userIsValid;
	}
	

	private int IsUserValidUser(User user) {
		HttpStreamHelper httpStreamHelper = new HttpStreamHelper();
		
		String userAddress = m_apiAddress + "?userName=" + user.GetUserName();
		if(isInteger(user.GetUserPassword()))
		{
			userAddress = userAddress + "&password='" + user.GetUserPassword() + "'";
		}
		else
		{
			userAddress = userAddress + "&password=" + user.GetUserPassword();
		}
		InputStream response = httpStreamHelper.getStringFromUrl(userAddress);
		JsonParser parser = new JsonParser();
		String answer = parser.convertStreamToString(response);
		answer = answer.replace("\n", "").replace("\r", "");;
		int result = Integer.parseInt(answer);
		
		return result;
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	private class ValidateUserEvent extends AsyncTask<User, Void, Integer>
	{
		@Override
		protected Integer doInBackground(User... params) {
			int userIsValid = IsUserValidUser(params[0]);

			return userIsValid;
		}
	}

}
