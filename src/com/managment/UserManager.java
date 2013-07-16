package com.managment;

import com.transapp.extentions.models.User;

import android.content.Context;

public class UserManager {

	private UserEngine m_userEngine;
	
	public UserManager(Context context) {
		m_userEngine = new UserEngine(context);
	}
	
	public int GetUserId(String userName) {
		
		return m_userEngine.GetUserId(userName);
	}
	
	public boolean ValidateUser(User user) {
		boolean result = false;
		int validUser = m_userEngine.ValidateUser(user);
		if(validUser > 0)
		{
			user.SetUserId(validUser);
			m_userEngine.SaveUser(user);
			result = true;
		}
		return result;
	}

}
