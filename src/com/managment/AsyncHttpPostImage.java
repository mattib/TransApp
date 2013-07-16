package com.managment;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

/**
 * AsyncHttpPostImage - An Async Class to upload images to Image Repository
 * @author Matti Ben-Avraham
 *
 */
public class AsyncHttpPostImage extends AsyncTask<File, Void, String> {
    private  final String TAG = AsyncHttpPostImage.class.getSimpleName();
    public String fileId;
    
    private static String imageRepositryUri = "http://50.112.174.134/Image?fileId=";

/**
 * AsyncHttpPostImage - in charge of synchronizing image with image repository
 * @param server - server uri address
 */
    public AsyncHttpPostImage(final String server) {
        this.fileId = server;
    }

    @Override
    protected String doInBackground(File... params) {
        Log.d(TAG, "doInBackground");
        HttpClient http = AndroidHttpClient.newInstance("ImageRepository");
        HttpPost method = new HttpPost(imageRepositryUri + fileId);

        method.setEntity(new FileEntity(params[0], "text/plain"));

        try {
            HttpResponse response = http.execute(method);


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
