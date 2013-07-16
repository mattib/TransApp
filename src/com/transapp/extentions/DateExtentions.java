package com.transapp.extentions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateExtentions {
// take care of this
	private static final String java_date_format = "EEE MMM dd HH:mm:ss zzz yyyy";
	private static final String webapi_date_format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private static final String webapi_date_format1 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	private static final String display_format = "dd-MM-yyyy HH:mm";
	
	public static Date GetDate(String dateText) {
		Date result = null;
		if (dateText != null && dateText != "null" && dateText != "")
		{	
			DateFormat dateFormat = GetDateFormat();
			try {
				result = dateFormat.parse(dateText);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return result;
	}
	
	public static Date GetDateFromWebApi(String dateText) {
		Date result = null;
		if (dateText != null && dateText != "null" && dateText != "")
		{	
			try {
				DateFormat dateFormat = GetDateFormatFromWebApi();
				result = dateFormat.parse(dateText);
			} catch (ParseException e) {
				try{
					DateFormat dateFormat = GetDateFormatFromWebApi1();
					result = dateFormat.parse(dateText);
				}catch (ParseException e1)
				{
					e1.printStackTrace();
				}
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return result;
	}
	
	public static DateFormat GetDateFormat() {
		DateFormat dateFormat = new SimpleDateFormat(java_date_format);
		return dateFormat;
	}
	
	public static DateFormat GetDateFormatFromWebApi() {
		DateFormat dateFormat = new SimpleDateFormat(webapi_date_format);
		return dateFormat;
	}
	
	public static DateFormat GetDateFormatFromWebApi1() {
		DateFormat dateFormat = new SimpleDateFormat(webapi_date_format1);
		return dateFormat;
	}
	
	
	
	public static String GetDateInSQLFormat(Date date) {
		String result = "";
		if(date != null)
		{
		DateFormat dateFormat = GetDateFormat();
		result = dateFormat.format(date);
		}
		return result;
	}
	
	private static String GetDisplayDateString(String unparsedDay){
		String result = "";

		try {
			SimpleDateFormat unParsedFormatter = new SimpleDateFormat(webapi_date_format);
			Date date;
			date = unParsedFormatter.parse(unparsedDay.substring(0, 20));
			SimpleDateFormat parsedFormatter = new SimpleDateFormat(display_format);
			
			result = parsedFormatter.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
