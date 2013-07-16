package com.transapp.extentions.models;

public class Contact {
	
	public Contact(String userName, String password, int userId) {
		m_userName = userName;
		m_password = password;
		m_userId = userId;
	}
	
	public Contact(String userName, String password){
		m_userName = userName;
		m_password = password;
		m_userId = null;
	}

	private String m_userName;
	
	private String m_password;
	
	private java.lang.Integer m_userId;
	
	public String GetUserName()
	{
		return m_userName;
	}
	
	public void SettUserName(String value)
	{
		m_userName = value;
	}
	
	public String GetUserPassword()
	{
		return m_password;
	}
	
	public void SetUserPassword(String value)
	{
		m_password = value;
	}
	
	public int GetUserId()
	{
		return m_userId;
	}
	
	public void SetUserPassword(int value)
	{
		m_userId = value;
	}
}
