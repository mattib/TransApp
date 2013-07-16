package com.transapp.extentions.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonParser {

	public JsonParser() {
		// TODO Auto-generated constructor stub
	}
	
	static InputStream is = null;		
	static JSONObject jObj = null;
	static String json = "";
	static JSONArray jsonArray = null;
	 
	public JSONArray getJSONFromString(InputStream response) {
		try {
			json = convertStreamToString(response);
			}
		catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
			}
		try {
			jsonArray = new JSONArray(json);
			}
		catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			}
		return jsonArray;
	}
	
	public JSONObject GetJSONObject(String jsonString)
	{
		JSONObject result = null;
		try {
			result = new JSONObject(jsonString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	    
	public String convertStreamToString(InputStream input) {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
	    StringBuilder sb = new StringBuilder();
	
	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null)
	        {
	        	sb.append(line + "\n");
	        }
	    }
	    catch (IOException e) {
	    	e.printStackTrace();
	    }
	    finally {
	    	try {
	    		input.close();
	    		}
	    	catch (IOException e) {
	    		e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}

}
