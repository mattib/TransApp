package com.transapp.extentions.activities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.managment.AsyncHttpPostImage;
import com.managment.TasksManager;
import com.transapp.R;
import com.transapp.extentions.models.TaskItem;
import com.transapp.extentions.models.TaskStatus;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TaskDetailsActivity extends Activity {

	Context mContext;
	private TasksManager m_taskManager;
	public static final int SIGN_DELIVERY = 1;
	public static final int ADDED_PHOTO = 2;
	private static final String LOG_TAG = "CameraActivity.java";
	
	public TaskDetailsActivity() {
		super();
		m_taskManager = new TasksManager(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_details);
				 
        Intent i = getIntent();
        int taskId = i.getIntExtra("taskId", 0);
        SaveTaskId(taskId);
        TaskItem item = GetCurrentTask();
        
		//fix after
		setTitle(getTitle() + "  -  " + item.GetComment());
		Refresh();

		mContext = TaskDetailsActivity.this;
		
		View.OnClickListener photoHandler = new View.OnClickListener() {
			public void onClick(View v) {

				switch (v.getId()) {

				case R.id.takePictureButton:
					Intent nextActivity = new Intent(mContext, CameraActivity.class);
					startActivityForResult(nextActivity,ADDED_PHOTO );
	
					break;
				}
			}
		};
		
		View.OnClickListener signatureHandler = new View.OnClickListener() {
			public void onClick(View v) {

				switch (v.getId()) {

				case R.id.takeSignatureButton:
					Intent nextActivity = new Intent(mContext, CaptureSignatureActivity.class);
					startActivityForResult(nextActivity, SIGN_DELIVERY);

					break;
				}
			}
		};

		findViewById(R.id.takePictureButton).setOnClickListener(photoHandler);
		findViewById(R.id.takeSignatureButton).setOnClickListener(signatureHandler);
	}
	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data)
	    {
	        switch(requestCode) {
	        case SIGN_DELIVERY: 
	            if (resultCode == RESULT_OK) {
	            	try {
	            		String directoryPath = GetDirectoryPath("Signatures");

        				// where do you want to save the images
        				String signatureKey = GetSignatureKey();
        				String signaturePath = directoryPath + File.separator + signatureKey + ".jpg";
        				
        				AsyncImageWithRepositoryService(signatureKey, signaturePath);
        				Log.v(LOG_TAG, "Picture taken.");

        				// delete last image from dcim
        				if(!deleteLastSavedDcimImage()){
        					Log.v(LOG_TAG,"Unable to delete last saved image in /Camera/DCIM/");
        				}

	        		} catch (NullPointerException e) {
	        			e.printStackTrace();
	        		} catch (Exception e) {
	        			e.printStackTrace();
	        		}
	 
//	                Bundle bundle = data.getExtras();
//	                String status  = bundle.getString("status");
//	                if(status.equalsIgnoreCase("done")){
//	                    Toast toast = Toast.makeText(this, "Signature capture successful!", Toast.LENGTH_SHORT);
//	                    toast.setGravity(Gravity.TOP, 105, 50);
//	                    toast.show();
//	                }
	            }
	            break;
//	        case ADDED_PHOTO: 
//	            if (resultCode == RESULT_OK) {
//	            	try {
//	            		String directoryPath = GetDirectoryPath("Images");
//
//        				// where do you want to save the images
//        				String imageKey = GetImageKey();
//        				String imagePath = directoryPath + File.separator + imageKey + ".jpg";
//        				
//        				AsyncImageWithRepositoryService(imageKey, imagePath);
//        				Log.v(LOG_TAG, "Picture taken.");
//
//        				// delete last image from dcim
//        				if(!deleteLastSavedDcimImage()){
//        					Log.v(LOG_TAG,"Unable to delete last saved image in /Camera/DCIM/");
//        				}
//
//	        		} catch (NullPointerException e) {
//	        			e.printStackTrace();
//	        		} catch (Exception e) {
//	        			e.printStackTrace();
//	        		}
//	            }
//	            break;
	        }
	    }

	private void AsyncImageWithRepositoryService(String imageKey, String imagePath) {
		File file = new File(imagePath);
		
		AsyncHttpPostImage asyncHttpPostImageEvent =  (AsyncHttpPostImage) new AsyncHttpPostImage("transApp");
		asyncHttpPostImageEvent.fileId = imageKey + ".jpg";
		asyncHttpPostImageEvent.execute(file);
	}

	private String GetDirectoryPath(String folderName) {
		String state = Environment.getExternalStorageState();

		String directoryPath = "";
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
			
			directoryPath = Environment.getExternalStorageDirectory() + File.separator;
		}
		directoryPath = directoryPath + "TransApp" + File.separator + folderName;
		return directoryPath;
	}
	 
	 private boolean deleteLastSavedDcimImage() {

			Log.v(LOG_TAG, "Deleting late image from DCIM.");

			boolean success = false;

			try {

				// list the images in the device /DCIM/Camera directory
				File[] images = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera").listFiles();
				File lastSavedImage = images[0];
				int imagesLen =  images.length;
				
				//loop and check for the last modified image to get the last save image
				for (int i = 1; i < imagesLen; ++i) {
					if (images[i].lastModified() > lastSavedImage.lastModified()) {
						lastSavedImage = images[i];
					}
				}

				//then delete the last saved image
				success = new File(lastSavedImage.toString()).delete();

			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return success;
		}
	
	@Override
	  protected void onResume() {
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	  }
	
	private void SaveTaskId(java.lang.Integer taskId) {
		if(taskId != null) {
			SharedPreferences preferences = this.getPreferences(Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putInt("CurrentTaskId", taskId);
			editor.commit();
		}
	}

	 private int GetTaskId(){
		    SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
		    int result = sharedPreferences.getInt("CurrentTaskId", 0);
		    return result;
		   }

	private TaskItem GetCurrentTask() {
		int taskId = GetTaskId();
		TasksManager taskDataSource= new TasksManager(this);
		TaskItem item = taskDataSource.GetTask(taskId);
		return item;
	}
	
	@Override
    protected void onStop() {
        super.onStop();
    }

	private String GetComment(TaskItem task) {
		EditText commentTextBox = (EditText) findViewById(R.id.text_comment_textbox);
		String comment = commentTextBox.getText().toString();
		return comment;
	}

	private void ShowStartTaskButton(boolean enable) {
		Button startButton = (Button) findViewById(R.id.startTaskButton);
		startButton.setEnabled(enable);
	}
	
	private void ShowAcceptandRejectButtons(boolean enable) {
		Button rejectButton = (Button) findViewById(R.id.rejectButton);
		rejectButton.setEnabled(enable);
		Button acceptButton = (Button) findViewById(R.id.acceptButton);
		acceptButton.setEnabled(enable);
	}
	
	private void ShowUserReasons(boolean enable) {
		Button taskPicturButton = (Button) findViewById(R.id.takePictureButton);
		taskPicturButton.setEnabled(enable);
		Button signatureButton = (Button) findViewById(R.id.takeSignatureButton);
		signatureButton.setEnabled(enable);
		EditText commentTextBox = (EditText) findViewById(R.id.text_comment_textbox);
		commentTextBox.setEnabled(enable);
		Button finishTaskButton = (Button) findViewById(R.id.finishTaskButton);
		finishTaskButton.setEnabled(enable);
	}
	
	public void start(View view) {
		TaskItem task = GetCurrentTask();
		
		m_taskManager.StartTask(task.GetId());
		
		Refresh();
	}
	
	public void accept(View view) {
		TaskItem task = GetCurrentTask();
		
		m_taskManager.AcceptTask(task.GetId());
		
		Refresh();
	}
	
	public void reject(View view) {
		TaskItem task = GetCurrentTask();
		
		m_taskManager.RejectTask(task.GetId());
		
		Refresh();
	}
	
	
	public void finish(View view) {	
		TaskItem task = GetCurrentTask();
		String comment = GetComment(task);
		
		String imageKey = GetImageKey();
		String signatureKey = GetSignatureKey();
		if(!imageKey.equals(""))
		{
			m_taskManager.ImageKeyEvent(task.GetId(), imageKey);
		}
		if(!signatureKey.equals(""))
		{
			m_taskManager.SignatureKeyEvent(task.GetId(), signatureKey);
		}
		m_taskManager.CompleteTask(task.GetId(), comment);
		finish();
	}
	
	public void Refresh()
	{
		TaskItem item = GetCurrentTask();
		
		TextView userNameTextBox = (TextView) findViewById(R.id.taskName);
		userNameTextBox.setText(item.GetComment());
		
		TextView deliveryNumberTextBox = (TextView) findViewById(R.id.deliveryNumber);
		deliveryNumberTextBox.setText("מספר תעודת משלוח: " +  item.GetDeliveryNumber());
		
		TextView reciverAddressTextBox = (TextView) findViewById(R.id.senderAddress);
		reciverAddressTextBox.setText("כתובת איסוף: " +  item.GetReciverAddress());
		
		TextView senderAddressTextBox = (TextView) findViewById(R.id.reciverAddress);
		senderAddressTextBox.setText("כתובת מסירה: " +  item.GetReciverAddress());
		
		String pickupDate = GetStringDate(item.GetPickUpTime());
		TextView pickupTimeTextBox = (TextView) findViewById(R.id.pickupTime);
		pickupTimeTextBox.setText("זמן יעד איסוף: " + pickupDate);
		
		String deliveryDate = GetStringDate(item.GetDeliveryTime());
		TextView deliveryTimeTextBox = (TextView) findViewById(R.id.deliveryTime);
		deliveryTimeTextBox.setText("זמן יעד מסירה: " + deliveryDate);
		
		
        	String statusString = "";
        	switch (item.GetTaskStatus())
        	{
        		case NotStarted: statusString = "לא התחיל";
        				break;
        		case Started: statusString = "התחיל";
						break;
        		case Accepted: statusString = "התקבל";
						break;
        		case Rejected: statusString = "נדחה";
						break;
        		case Finished: statusString = "הסתיים";
						break;
        		
        	}
		TextView taskStatusTextBox = (TextView) findViewById(R.id.taskStatus);
		taskStatusTextBox.setText("מצב משימה: " + statusString);
		
		String pickupAtDate = GetStringDate(item.GetPickedUpAt());
		TextView pickupAtTextBox = (TextView) findViewById(R.id.pickupAt);
		pickupAtTextBox.setText("זמן איסוף: " + pickupAtDate);
		
		String deliveryAtDate = GetStringDate(item.GetDeliveredAt());
		TextView deliveryAtTextBox = (TextView) findViewById(R.id.deliveryAt);
		deliveryAtTextBox.setText("זמן מסירה: " + deliveryAtDate);
		
		String userComment = item.GetUserComment();
		if(userComment != null || userComment != "null")
		{
			EditText commentTextBox = (EditText) findViewById(R.id.text_comment_textbox);
			commentTextBox.setText(item.GetUserComment());
		}
		
		ShowStartTaskButton(false);
		ShowAcceptandRejectButtons(false);
		ShowUserReasons(false);
		
		Boolean rejected = item.GetRejected();
		TextView rejectedTextBox = (TextView) findViewById(R.id.reject);
		if(rejected != null)
		{
			rejectedTextBox.setVisibility(0);
			if(rejected)
			{
				rejectedTextBox.setText("משימה נדחתה");
			}else
			{
				rejectedTextBox.setText("משימה אושרה");
			}
		}
		else
		{
			rejectedTextBox.setVisibility(1);
		}
			
		if(item.GetTaskStatus() == TaskStatus.NotStarted)
		{
			SaveIamgeKey("");
			SaveSignatureKey("");
			ShowStartTaskButton(true);
		}
		
		if(item.GetTaskStatus() == TaskStatus.Started)
		{
			ShowAcceptandRejectButtons(true);
		}
		
		if(item.GetTaskStatus() == TaskStatus.Accepted || item.GetTaskStatus() == TaskStatus.Rejected)
		{
			ShowUserReasons(true);
		}
	}
	
	private void SaveIamgeKey(String imageKey) {
		if(imageKey != null) {
			SharedPreferences sharedPreferences = this.getSharedPreferences("com.transapp.extentions", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("ImageKey", imageKey);
			editor.commit();
		}
	}
	
	private void SaveSignatureKey(String signatureKey) {
		if(signatureKey != null) {
			SharedPreferences sharedPreferences = this.getSharedPreferences("com.transapp.extentions", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("SignatureKey", signatureKey);
			editor.commit();
		}
	}
	
	 private String GetImageKey(){
		    SharedPreferences sharedPreferences = this.getSharedPreferences("com.transapp.extentions", Context.MODE_PRIVATE);
		    String result = sharedPreferences.getString("ImageKey", "");
		    return result;
		   }
	 
	 private String GetSignatureKey(){
		    SharedPreferences sharedPreferences = this.getSharedPreferences("com.transapp.extentions", Context.MODE_PRIVATE);
		    String result = sharedPreferences.getString("SignatureKey", "");
		    return result;
		   }
	
	//move to date extensions
	private String GetStringDate(Date date)
	{
		String result = "";
		if(date != null)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
			StringBuilder dateString = new StringBuilder( dateFormat.format( date ) );
			result = dateString.toString();
		}
		return result;
	}
}
