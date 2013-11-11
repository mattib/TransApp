package com.transapp.extentions.activities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.managment.AsyncHttpPostImage;
import com.transapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class CameraActivity extends Activity {
//
	private static final String LOG_TAG = "CameraActivity.java";
	public static final int SIGN_DELIVERY = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);

		try {
			
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

			UUID fileName = UUID.randomUUID();

			String state = Environment.getExternalStorageState();

			String directoryPath = "";
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				
				directoryPath = Environment.getExternalStorageDirectory() + File.separator;
			}
			directoryPath = directoryPath + "TransApp" + File.separator + "Images";

			SaveImageKey(fileName.toString());
			String fullPath = directoryPath + File.separator + fileName + ".jpg";

			File direct = new File(directoryPath);

			if(!direct.exists())
		    {
			   direct.mkdirs();
		    }
			File file = new File(fullPath);

			Uri outputFileUri = Uri.fromFile(file);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

			startActivityForResult(intent,SIGN_DELIVERY);			

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void SaveImageKey(String imageKey) {
		if(imageKey != null) {
			SharedPreferences sharedPreferences = this.getSharedPreferences("com.transapp.extentions", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("ImageKey", imageKey);
			editor.commit();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
			 case SIGN_DELIVERY: 
				  if (resultCode == RESULT_OK) {
		            	try {
		            		String directoryPath = GetDirectoryPath("Images");

	        				// where do you want to save the images
	        				String imageKey = GetImageKey();
	        				String imagePath = directoryPath + File.separator + imageKey + ".jpg";
	        				
	        				Bitmap mBitmap = BitmapFactory.decodeFile(imagePath);
//	        				
	        				FileOutputStream out = new FileOutputStream(imagePath);
	        				mBitmap.compress(Bitmap.CompressFormat.JPEG, 10, out);
//	        				FileInputStream in = new FileInputStream(imagePath)
//	        				
//	        				 FileOutputStream mFileOutStream = new FileOutputStream(imagePath);
//	        				 
//	                        mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, mFileOutStream); 
//	                        mFileOutStream.flush();
//	                        mFileOutStream.close();
	                        
//	                        FileInputStream imageStream = new FileInputStream(imagePath);
//	                        
//	                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);
//
//	                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//	                        bmp.compress(Bitmap.CompressFormat.JPEG, 20, stream);
//	                        byte[] byteArray = stream.toByteArray();
//	                        try {
//	                            stream.close();
//	                            stream = null;
//	                        } catch (IOException e) {
//
//	                            e.printStackTrace();
//	                        }
	        				
	        				
	        				AsyncImageWithRepositoryService(imageKey, imagePath);
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
		            }
		            break;
		
		//             Bundle bundle = data.getExtras();
		//             String status  = bundle.getString("status");
		//             if(status.equalsIgnoreCase("done")){
		//                 Toast toast = Toast.makeText(this, "Signature capture successful!", Toast.LENGTH_SHORT);
		//                 toast.setGravity(Gravity.TOP, 105, 50);
		//                 toast.show();
		//             }
		         
		}
		CameraActivity.this.finish();
	}
	
	private void AsyncImageWithRepositoryService(String imageKey, String imagePath) {
		File file = new File(imagePath);
		
		AsyncHttpPostImage asyncHttpPostImageEvent =  (AsyncHttpPostImage) new AsyncHttpPostImage("transApp");
		asyncHttpPostImageEvent.fileId = imageKey + ".jpg";
		asyncHttpPostImageEvent.execute(file);
	}
	
	 private String GetImageKey(){
		    SharedPreferences sharedPreferences = this.getSharedPreferences("com.transapp.extentions", Context.MODE_PRIVATE);
		    String result = sharedPreferences.getString("ImageKey", "");
		    return result;
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

	@Override
	public void onBackPressed() {
		CameraActivity.this.finish();
	}
	
//	private String GetImageKey(){
//	    SharedPreferences sharedPreferences = this.getSharedPreferences("com.transapp.extentions", Context.MODE_PRIVATE);
//	    String result = sharedPreferences.getString("ImageKey", "");
//	    return result;
//	   }

	// since our code also saves the taken images to DCIM/Camera folder of the
	// device, we have to delete it there so that the image will be saved
	// only to the directory we have specified.
}

