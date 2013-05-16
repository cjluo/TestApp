package com.example.testapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

public class HttpDownloader extends AsyncTask<String, Void, Integer>{
	private URL url = null;
	
	public String download(String urlStr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			while((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public int downFile(String urlStr, String path, String fileName){
		InputStream inputStream = null;
		try {
			FileUtils fileUtils = new FileUtils();
			
			fileUtils.deleteFileifExist(path + fileName);

			inputStream = getInputStreamFromURL(urlStr);
			assert (inputStream != null);
			File resultFile = fileUtils.write2SDFromInput(path, fileName, inputStream);
			if(resultFile == null) {
				return -1;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				inputStream.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public InputStream getInputStreamFromURL(String urlStr) {
		HttpURLConnection urlConn = null;
		InputStream inputStream = null;
		try {
			url = new URL(urlStr);
			urlConn = (HttpURLConnection)url.openConnection();
			inputStream = urlConn.getInputStream();
		} catch(MalformedURLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	@Override
	protected Integer doInBackground(String... params) {
		return downFile(params[0], params[1], params[2]);
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		MainActivity.keys.SendKey(116, true);
		MainActivity.keys.SendKey(116, false);		
		MainActivity.wl.release();
		System.out.println(result);
	}
}

