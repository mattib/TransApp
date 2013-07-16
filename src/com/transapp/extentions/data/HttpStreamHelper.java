package com.transapp.extentions.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;

public class HttpStreamHelper {

	public HttpStreamHelper() {
	}
	
	public InputStream getStringFromUrl(String urlString)
	{
		InputStream input = null;
		try
		{
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			InputStream in = connection.getInputStream();
			if(in != null)
			{
				input = new BufferedInputStream(connection.getInputStream());
			}
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return input;
	}

}
