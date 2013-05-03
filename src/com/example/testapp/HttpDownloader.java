package com.example.testapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpDownloader {
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
			
			if(fileUtils.isFileExist(path + fileName)) {
				return 1;
			} else {
				inputStream = getInputStreamFromURL(urlStr);
				File resultFile = fileUtils.write2SDFromInput(path, fileName, inputStream);
				if(resultFile == null) {
					return -1;
				}
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
}

