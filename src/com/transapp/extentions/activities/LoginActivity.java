package com.transapp.extentions.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.managment.TasksManager;
import com.managment.UserEngine;
import com.managment.UserManager;
import com.transapp.R;
import com.transapp.R.id;
import com.transapp.R.layout;
import com.transapp.R.menu;
import com.transapp.R.string;
import com.transapp.R.xml;
import com.transapp.extentions.models.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	public final static String USER_ID = "com.transapp.extentions.USER_ID";
	
	private UserManager m_userManager;
	
	public LoginActivity() {
		super();
		m_userManager = new UserManager(this);
	}

		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	/** Called when the user clicks the Send button */
	public void login(View view) {
		Log.d("Debug: ", "Loging in");
		boolean valid = Validate();
		if(valid)
		{
		    // Do something in response to button
			Intent intent = new Intent(this, TasksListViewActivity.class);
			
			
			
			User user = GetUser();
			
			//goto UsersManager to validat user
			boolean userIsValid = m_userManager.ValidateUser(user);
			if(userIsValid) {
				int currentUserId = m_userManager.GetUserId(user.GetUserName());
				
				 SaveCurrentUserId(currentUserId);
				 Log.d("Debug: ", "Login succeseded");
				 
				 createExternalStoragePrivateFile();
					
				startActivity(intent);
			}else {
					TextView errorMessageTextBox = (TextView) findViewById(R.id.login_error_message);
					errorMessageTextBox.setVisibility(0);
					errorMessageTextBox.setText(R.string.login_error_User_or_password_incorrect);
					ClearTextBoxs();
			}
		}
	}

	private void SaveCurrentUserId(java.lang.Integer userId) {
		if(userId != null) {
			SharedPreferences sharedPreferences = this.getSharedPreferences("com.transapp.extentions", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putInt("CurrentUserId", userId);
			editor.commit();
		}
	}
	
	private boolean Validate() {
		boolean result = true;
		TextView errorMessageTextBox = (TextView) findViewById(R.id.login_error_message);
		EditText userNameTextBox = (EditText) findViewById(R.id.login_username_textbox);
		EditText passowrdTextBox = (EditText) findViewById(R.id.login_password_textbox);
		if(userNameTextBox.length() == 0)
		{
			errorMessageTextBox.setVisibility(0);
			errorMessageTextBox.setText(R.string.login_error_No_UserName);
			result = false;
		}else if(passowrdTextBox.length() == 0)
		{
			errorMessageTextBox.setVisibility(0);
			errorMessageTextBox.setText(R.string.login_error_No_Password);
			result = false;
		}
		return result;
	}

	private void ClearTextBoxs() {
		EditText userNameTextBox = (EditText) findViewById(R.id.login_username_textbox);
		EditText passowrdTextBox = (EditText) findViewById(R.id.login_password_textbox);
		userNameTextBox.setText("");
		passowrdTextBox.setText("");
	}

	private User GetUser() {
		
		EditText userNameTextBox = (EditText) findViewById(R.id.login_username_textbox);
		String username = userNameTextBox.getText().toString();
		
		EditText passowrdTextBox = (EditText) findViewById(R.id.login_password_textbox);
		String password = passowrdTextBox.getText().toString();
		
		User user = new User(username, password);
		
		return user;
	}
	
	void createExternalStoragePrivateFile() {
		
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		
	    // Create a path where we will place our private file on external
	    // storage.
		if(mExternalStorageAvailable && mExternalStorageWriteable)
		{
		Log.d("Debug:", "opening external file");
	    File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "trasnWorldData.xml");

	    try {
	        // Very simple code to copy a picture from the application's
	        // resource into the external file.  Note that this code does
	        // no error checking, and assumes the picture is small (does not
	        // try to copy it in chunks).  Note that if external storage is
	        // not currently mounted this will silently fail.
	        InputStream is = getResources().openRawResource(R.xml.trans_world_data);
	        OutputStream os = new FileOutputStream(file);
	        byte[] data = new byte[is.available()];
	        is.read(data);
	        os.write(data);
	        is.close();
	        os.close();
	    } catch (IOException e) {
	        // Unable to create file, likely because external storage is
	        // not currently mounted.
	        Log.w("ExternalStorage", "Error writing " + file, e);
	    }
		}else
		{
			Log.d("Debug:", "cannot write to storage");
		}
	}
}
