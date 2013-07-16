package com.managment;

import java.util.List;

import com.services.UserAuthenticationService;
import com.transapp.extentions.data.UserDataBaseHelper;
import com.transapp.extentions.models.User;

import android.content.Context;

public class UserEngine {
	
	private String m_usersServiceAddress = "http://transappapi.apphb.com/api/user";
	private UserDataBaseHelper userDbHelper;
	private List<User> users;
	
	public UserEngine(Context context) {
		userDbHelper = new UserDataBaseHelper(context);
	}
	
	public int ValidateUser(User user)
	{
		//check if there is a connection if not take from data base
		UserAuthenticationService userAuthenticationService = new UserAuthenticationService(m_usersServiceAddress);
		return userAuthenticationService.GetUserId(user);
	}

	public User[] GetUsers()
	{
		return (User[]) users.toArray();
	}
	
	public boolean DoesUserExist(User user) {
		
		boolean result = userDbHelper.userExists(user.GetUserName());
		
		return result;
	}
	
	public int GetUserId(String userName) {
		User user = userDbHelper.GetUser(userName);
		 
		 return user.GetUserId();
	}
	
	public void SaveUser(User user)
	{
		boolean userExists = userDbHelper.userExists(user.GetUserName());
		if(userExists)
		{
			userDbHelper.updateUser(user);
		}
		else
		{
			userDbHelper.addUser(user);
		}
	}
}
