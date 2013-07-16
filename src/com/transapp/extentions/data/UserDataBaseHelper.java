package com.transapp.extentions.data;

import com.transapp.extentions.models.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDataBaseHelper extends SQLiteOpenHelper{

	// All Static variables
		// Database Version
		private static final int DATABASE_VERSION = 1;

		// Database Name
		private static final String DATABASE_NAME = "TransWorldData.db";

		// Contacts table name
		private static final String TABLE_USER_ITEMS = "users";

		// Contacts Table Columns names
		private static final String KEY_ID = "id";
		private static final String KEY_USER_NAME = "UserName";
		private static final String KEY_PASSWORD = "Password";
		//private static final String KEY_COMPANY_ID = "CompanyId";
//		private static final String KEY_SENDER_ADDRESS = "SenderAddress";
//		private static final String KEY_RECIVER_ADDRESS = "ReciverAddress";
//		private static final String KEY_TASK_STATUS = "TaskStatus";
//		private static final String KEY_PICKED_UP_AT = "PickedUpAt";
//		private static final String KEY_DELIVERED_AT = "DeliveredAt";
//		private static final String KEY_PICK_UP_TIME = "PickUpTime";
//		private static final String KEY_DELIVERY_TIME = "DeliveryTime";
//		private static final String KEY_LAST_MODIFIED = "LastModified";
//		private static final String KEY_COMMENT = "Comment";
//		private static final String KEY_CONTACT_ID = "ContactId";
//		private static final String KEY_TASK_TYPE = "TaskType";
//		private static final String KEY_DATA_EXTENTION = "DataExtention";
	
	public UserDataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
//		SQLiteDatabase db = this.getWritableDatabase();
//		String CREATE_CONTACTS_TABLE = GetCreateTableString();
// 		db.execSQL(CREATE_CONTACTS_TABLE);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Log.w("Debug: ", "Installing new DataBase version: " + DATABASE_VERSION);
 		String CREATE_CONTACTS_TABLE = GetCreateTableString();
 		db.execSQL(CREATE_CONTACTS_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion != newVersion)
		{
			Log.w("Debug: ", "Installing new version of the dataBase. OldVersion: " + oldVersion + ", NewVersion: " + newVersion);
			// Drop older table if existed
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_ITEMS);

			// Create tables again
			onCreate(db);
		}
		else{
			Log.w("Debug: ", "OldVersion and NewVersion are the same. OldVersion: " + oldVersion + ", NewVersion: " + newVersion);
		}
	}
	
	private String GetCreateTableString() {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_ITEMS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_USER_NAME + " TEXT,"
				+ KEY_PASSWORD + " TEXT"
				//+ KEY_COMPANY_ID + " TEXT"
				+ ")";
		return CREATE_CONTACTS_TABLE;
	}
	
	// Adding new user
	public void addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);
		ContentValues userValue = GetUserContentValue(user);
		
		// Inserting Row
		db.insert(TABLE_USER_ITEMS, null, userValue);
		db.close(); // Closing database connection
	}
		
	private ContentValues GetUserContentValue(User user) {
		ContentValues taskValue = new ContentValues();
		taskValue.put(KEY_ID, user.GetUserId());
		taskValue.put(KEY_USER_NAME, user.GetUserName());
		taskValue.put(KEY_PASSWORD, user.GetUserPassword());
		//taskValue.put(KEY_COMPANY_ID, user.GetCompanyId());
//			taskValue.put(KEY_SENDER_ADDRESS, taskItem.GetSenderAddress());
//			taskValue.put(KEY_RECIVER_ADDRESS, taskItem.GetReciverAddress());
//			taskValue.put(KEY_TASK_STATUS, taskItem.GetTaskStatus());
//			Date pickUpAt = taskItem.GetPickedUpAt();
//			taskValue.put(KEY_PICKED_UP_AT, DateExtentions.GetDateInSQLFormat(pickUpAt));
//			Date deliveredAt = taskItem.GetDeliveredAt();
//			taskValue.put(KEY_DELIVERED_AT, DateExtentions.GetDateInSQLFormat(deliveredAt));
//			Date pickUpTime = taskItem.GetPickUpTime();
//			taskValue.put(KEY_PICK_UP_TIME, DateExtentions.GetDateInSQLFormat(pickUpTime));
//			Date deliveryTime = taskItem.GetDeliveryTime();
//			taskValue.put(KEY_DELIVERY_TIME, DateExtentions.GetDateInSQLFormat(deliveryTime));
//			Date lastModified = taskItem.GetDeliveryTime();
//			taskValue.put(KEY_LAST_MODIFIED, DateExtentions.GetDateInSQLFormat(lastModified));
//			taskValue.put(KEY_DELIVERY_NUMBER, taskItem.GetDeliveryNumber());
//			taskValue.put(KEY_COMMENT, taskItem.GetComment());
//			taskValue.put(KEY_CONTACT_ID, taskItem.GetContactId());
//			taskValue.put(KEY_TASK_TYPE, taskItem.GetTaskType());
//			taskValue.put(KEY_DATA_EXTENTION, taskItem.GetDataExtention());
		return taskValue;
	}
		
		// Updating single contact
		public int updateUser(User user) {
			SQLiteDatabase db = this.getWritableDatabase();
			onCreate(db);
			ContentValues userValue = GetUserContentValue(user);

			// updating row
			return db.update(TABLE_USER_ITEMS, userValue, KEY_ID + " = ?",
					new String[] { String.valueOf(user.GetUserId()) });
		}
		
		// Getting single user by userNAme
		public User GetUser(String userName) {
			if(!userExists(userName))
			{
				return null;
			}
			SQLiteDatabase db = this.getReadableDatabase();
			onCreate(db);
			//String selectQuery = "SELECT  * FROM " + TABLE_USER_ITEMS + " WHERE " + KEY_USER_NAME + " = '" + userName + "'";
			Cursor cursor = db.query(TABLE_USER_ITEMS, new String[] { KEY_ID,
					KEY_USER_NAME, KEY_PASSWORD }, KEY_USER_NAME + "=?",
					new String[] { userName }, null, null, null, null);
			if (cursor != null)
				cursor.moveToFirst();

			User user = MapUser(cursor);
			
			return user;
		}
		
		private User MapUser(Cursor cursor) {
			User user = new User();
			user.SetUserId(Integer.parseInt(cursor.getString(0)));
			user.SettUserName(cursor.getString(1));
			user.SetUserPassword(cursor.getString(2));
			//user.SetCompanyId(cursor.getInt(3));
			

			return user;
		}
		
		// Check if user exists
		public boolean userExists(String userName) {
//			User user = GetUser(userName);
//			boolean result = false;
//			if(user == null)
//			{
//				result = true;
//			}
//			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_USER_ITEMS + " WHERE " + KEY_USER_NAME + " = '" + userName + "'";

			SQLiteDatabase db = this.getWritableDatabase();
			onCreate(db);
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			boolean result = cursor.getCount()> 0 ? true: false;
//			
			return result;
		}

}
