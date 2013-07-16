package com.transapp.extentions.models;

public class User {
	
	public User() {
	}
	
	public User(String userName, String password, int userId, int companyId) {
		m_userName = userName;
		m_password = password;
		m_userId = userId;
		m_companyId = companyId;
	}
	
	public User(String userName, String password){
		m_userName = userName;
		m_password = password;
		m_userId = null;
		m_companyId = null;
	}

	private String m_userName;
	
	private String m_password;
	
	private java.lang.Integer m_userId;
	
	private java.lang.Integer m_companyId;
	
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
	
	public void SetUserId(int value)
	{
		m_userId = value;
	}
	
	public int GetCompanyId()
	{
		return m_companyId;
	}
	
	public void SetCompanyId(int value)
	{
		m_companyId = value;
	}
}
