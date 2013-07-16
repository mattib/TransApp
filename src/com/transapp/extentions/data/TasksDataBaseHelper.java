package com.transapp.extentions.data;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import com.transapp.extentions.DateExtentions;
import com.transapp.extentions.models.TaskItem;
import com.transapp.extentions.models.TaskStatus;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

public class TasksDataBaseHelper extends SQLiteOpenHelper {

	//private static final String date_format = "EEE MMM dd HH:mm:ss zzz yyyy";
	
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "TransWorldData.db";

	// Contacts table name
	private static final String TABLE_TASK_ITEMS = "taskItems";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_DELIVERY_NUMBER = "DeliveryNumber";
	private static final String KEY_USER_ID = "UserId";
	private static final String KEY_COMPANY_ID = "CompanyId";
	private static final String KEY_SENDER_ADDRESS = "SenderAddress";
	private static final String KEY_RECIVER_ADDRESS = "ReciverAddress";
	private static final String KEY_TASK_STATUS = "TaskStatus";
	private static final String KEY_PICKED_UP_AT = "PickedUpAt";
	private static final String KEY_DELIVERED_AT = "DeliveredAt";
	private static final String KEY_PICK_UP_TIME = "PickUpTime";
	private static final String KEY_DELIVERY_TIME = "DeliveryTime";
	private static final String KEY_LAST_MODIFIED = "LastModified";
	private static final String KEY_COMMENT = "Comment";
	private static final String KEY_CONTACT_ID = "ContactId";
	private static final String KEY_TASK_TYPE = "TaskType";
	private static final String KEY_DATA_EXTENTION = "DataExtention";
	private static final String KEY_SIGNATURE_ID = "SignatureId";
	private static final String KEY_IMAGE_ID = "ImageId";
	private static final String KEY_USER_COMMENT = "UserComment";
	private static final String KEY_REJECTED = "Rejected";

    public TasksDataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
    
    // Creating Tables
 	@Override
 	public void onCreate(SQLiteDatabase db) {
 		Log.w("Debug: ", "Installing new DataBase version: " + DATABASE_VERSION);
 		String CREATE_CONTACTS_TABLE = GetCreateTableString();
 		db.execSQL(CREATE_CONTACTS_TABLE);
 	}

	private String GetCreateTableString() {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TASK_ITEMS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_DELIVERY_NUMBER + " TEXT,"
				+ KEY_USER_ID + " TEXT,"
				+ KEY_COMPANY_ID + " TEXT,"
				+ KEY_SENDER_ADDRESS + " TEXT,"
				+ KEY_RECIVER_ADDRESS + " TEXT,"
				+ KEY_TASK_STATUS + " TEXT,"
				+ KEY_PICKED_UP_AT + " TEXT,"
				+ KEY_DELIVERED_AT + " TEXT,"
				+ KEY_PICK_UP_TIME + " TEXT,"
				+ KEY_DELIVERY_TIME + " TEXT,"
				+ KEY_LAST_MODIFIED + " TEXT,"
				+ KEY_COMMENT + " TEXT,"
				+ KEY_CONTACT_ID + " TEXT,"
				+ KEY_TASK_TYPE + " TEXT,"
				+ KEY_DATA_EXTENTION + " TEXT,"
				+ KEY_SIGNATURE_ID + " TEXT,"
				+ KEY_IMAGE_ID + " TEXT,"
				+ KEY_USER_COMMENT + " TEXT,"
				+ KEY_REJECTED + " TEXT" + ")";
		return CREATE_CONTACTS_TABLE;
	}
	
	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		if(oldVersion != newVersion)
		{
			Log.w("Debug: ", "Installing new version of the dataBase. OldVersion: " + oldVersion + ", NewVersion: " + newVersion);
			// Drop older table if existed
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK_ITEMS);

			// Create tables again
			onCreate(db);
		}
		else{
			Log.w("Debug: ", "OldVersion and NewVersion are the same. OldVersion: " + oldVersion + ", NewVersion: " + newVersion);
		}
	}
	
	/**text.replace("\n", "");text.replace("\n", "");text.replace("\n", "");text.replace("\n", "");text text .replace("\r", "");.replace("\r", "");
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
	
	// Adding new TaskItem
	public void addTaskItem(TaskItem taskItem) {
		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);
		ContentValues taskValue = GetTaskContentValue(taskItem);
		
		// Inserting Row
		db.insert(TABLE_TASK_ITEMS, null, taskValue);
		db.close(); // Closing database connection
	}
	
//	public static String getDatabaseName() {
//		return DATABASE_NAME;
//	}

	private ContentValues GetTaskContentValue(TaskItem taskItem) {
		ContentValues taskValue = new ContentValues();
		taskValue.put(KEY_ID, taskItem.GetId());
		taskValue.put(KEY_DELIVERY_NUMBER, taskItem.GetDeliveryNumber());
		taskValue.put(KEY_USER_ID, taskItem.GetUserId());
		taskValue.put(KEY_COMPANY_ID, taskItem.GetCompanyId());
		taskValue.put(KEY_SENDER_ADDRESS, taskItem.GetSenderAddress());
		taskValue.put(KEY_RECIVER_ADDRESS, taskItem.GetReciverAddress());
		taskValue.put(KEY_TASK_STATUS, taskItem.GetTaskStatus().value());
		Date pickUpAt = taskItem.GetPickedUpAt();
		taskValue.put(KEY_PICKED_UP_AT, DateExtentions.GetDateInSQLFormat(pickUpAt));
		Date deliveredAt = taskItem.GetDeliveredAt();
		taskValue.put(KEY_DELIVERED_AT, DateExtentions.GetDateInSQLFormat(deliveredAt));
		Date pickUpTime = taskItem.GetPickUpTime();
		taskValue.put(KEY_PICK_UP_TIME, DateExtentions.GetDateInSQLFormat(pickUpTime));
		Date deliveryTime = taskItem.GetDeliveryTime();
		taskValue.put(KEY_DELIVERY_TIME, DateExtentions.GetDateInSQLFormat(deliveryTime));
		Date lastModified = taskItem.GetLastModified();
		taskValue.put(KEY_LAST_MODIFIED, DateExtentions.GetDateInSQLFormat(lastModified));
		taskValue.put(KEY_DELIVERY_NUMBER, taskItem.GetDeliveryNumber());
		taskValue.put(KEY_COMMENT, taskItem.GetComment());
		taskValue.put(KEY_CONTACT_ID, taskItem.GetContactId());
		taskValue.put(KEY_TASK_TYPE, taskItem.GetTaskType());
		taskValue.put(KEY_DATA_EXTENTION, taskItem.GetDataExtention());
		taskValue.put(KEY_SIGNATURE_ID, taskItem.GetSignatureId());
		taskValue.put(KEY_IMAGE_ID, taskItem.GetImageId());
		taskValue.put(KEY_USER_COMMENT, taskItem.GetUserComment());
		taskValue.put(KEY_REJECTED, taskItem.GetRejected());
		
		return taskValue;
	}
	
	// Getting single taskItem
	public TaskItem getTaskItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		onCreate(db);
		Cursor cursor = db.query(TABLE_TASK_ITEMS, new String[] { KEY_ID,
				KEY_DELIVERY_NUMBER, KEY_USER_ID, KEY_COMPANY_ID, KEY_SENDER_ADDRESS, KEY_RECIVER_ADDRESS,
				KEY_TASK_STATUS,KEY_PICKED_UP_AT, KEY_DELIVERED_AT, KEY_PICK_UP_TIME, KEY_DELIVERY_TIME,
				KEY_LAST_MODIFIED, KEY_COMMENT, KEY_CONTACT_ID, KEY_TASK_TYPE, KEY_DATA_EXTENTION,
				KEY_SIGNATURE_ID, KEY_IMAGE_ID, KEY_USER_COMMENT, KEY_REJECTED }, KEY_ID + "=?",

				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		TaskItem taskItem = MapTaskItem(cursor);
		
		return taskItem;
	}
	
	private TaskItem MapTaskItem(Cursor cursor) {
		TaskItem taskItem = new TaskItem();
		taskItem.SetId(Integer.parseInt(cursor.getString(0)));
		taskItem.SetDeliveryNumber(cursor.getString(1));
		taskItem.SetUserId(cursor.getInt(2));
		taskItem.SetCompanyId(cursor.getInt(3));
		taskItem.SetSenderAddress(cursor.getString(4));
		taskItem.SetReciverAddress(cursor.getString(5));
		taskItem.SetTaskStatus(TaskStatus.fromValue(cursor.getInt(6)));
		taskItem.SetPickedUpAt(DateExtentions.GetDate(cursor.getString(7)));
		taskItem.SetDeliveredAt(DateExtentions.GetDate(cursor.getString(8)));
		taskItem.SetPickUpTime(DateExtentions.GetDate(cursor.getString(9)));
		taskItem.SetDeliveryTime(DateExtentions.GetDate(cursor.getString(10)));
		taskItem.SetLastModified(DateExtentions.GetDate(cursor.getString(11)));
		taskItem.SetComment(cursor.getString(12));
		taskItem.SetContactId(cursor.getInt(13));
		taskItem.SetTaskType(cursor.getInt(14));
		taskItem.SetDataExtention(cursor.getString(15));
		taskItem.SetSignatureId(cursor.getString(16));
		taskItem.SetImageId(cursor.getString(17));
		taskItem.SetUserComment(cursor.getString(18));
		taskItem.SetRejected(Boolean.parseBoolean(cursor.getString(19)));

		return taskItem;
	}
	
	// Getting All TaskItems
	public List<TaskItem> getAllTaskItems() {
		List<TaskItem> taskItemsList = new ArrayList<TaskItem>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_TASK_ITEMS;

		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				TaskItem taskItem = MapTaskItem(cursor);
				// Adding taskItem to list
				taskItemsList.add(taskItem);
			} while (cursor.moveToNext());
		}

		// return taskItems list
		return taskItemsList;
	}
	
	// Check if taskItem exists
	public boolean taskItemExists(int taskId) {
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_TASK_ITEMS + " WHERE " + KEY_ID + " = " + taskId;

		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		boolean result = cursor.getCount()> 0 ? true: false;
		
		return result;
	}
	
	
//	public List<TaskItem> getAllTodaysTaskItems() {
//		List<TaskItem> taskItemsList = new ArrayList<TaskItem>();
//		// Select All Query
//		String selectQuery = "SELECT  * FROM " + TABLE_TASK_ITEMS + " WHERE ";
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				TaskItem taskItem = MapTaskItem(cursor);
//				// Adding taskItem to list
//				taskItemsList.add(taskItem);
//			} while (cursor.moveToNext());
//		}
//
//		// return taskItems list
//		return taskItemsList;
//	}
	
	// Deleting single contact
	public void deleteTaskItem(TaskItem taskItem) {
		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);
		db.delete(TABLE_TASK_ITEMS, KEY_ID + " = ?",
				new String[] { String.valueOf(taskItem.GetId()) });
		db.close();
	}
	
	// Updating single contact
	public int updateTaskItem(TaskItem taskItem) {
		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);
		ContentValues taskValue = GetTaskContentValue(taskItem);

		// updating row
		return db.update(TABLE_TASK_ITEMS, taskValue, KEY_ID + " = ?",
				new String[] { String.valueOf(taskItem.GetId()) });
	}
	
	// Getting contacts Count
	public int getTaskItemsCount() {
		Log.w("Debug: ", "Getting  taskItems count");
		String countQuery = "SELECT  * FROM " + TABLE_TASK_ITEMS;
		SQLiteDatabase db = this.getReadableDatabase();
		onCreate(db);
		Cursor cursor = db.rawQuery(countQuery, null);
		
		int result = cursor.getCount();
		cursor.close();
		// return count
		return result;
	}

//	private String GetDateText(Date date) {
//		DateFormat df = GetDateFormat();
//		String dateText = df.format(date);
//		return dateText;
//	}

//	private Date GetDate(String dateText) {
//		DateFormat df = GetDateFormat();
//		Date date = null;
//		try {
//			date = df.parse(dateText);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return date;
//	}
	
//	private DateFormat GetDateFormat() {
//		DateFormat df = new SimpleDateFormat(date_format);
//		return df;
//	}
}
