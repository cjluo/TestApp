package com.example.testapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FromFile {
	private byte[] data;
	private InputStream inStream;
	private File file;
	private String filename;
	private String parameterName;
	private String contentType = "application/octet-stream";
	
	public FromFile(String filename, byte[] data, String parameterName, String contentType){
		this.data = data;
		this.filename = filename;
		this.parameterName = parameterName;
		if(contentType != null)
			this.contentType = contentType;
	}
	
	public FromFile(String filename, File file, String parameterName, String contentType){
		this.filename = filename;
		this.parameterName = parameterName;
		this.file = file;
		try{
			this.inStream = new FileInputStream(file);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		if(contentType != null){
			this.contentType = contentType;
		}
	}
	
	public File getFile(){
		return file;
	}
	
	public InputStream getInStream(){
		return inStream;
	}
	
	public byte[] getDate(){
		return data;
	}
	
	public String getFileName(){
		return filename;
	}
	
	public void setFileName(String filename){
		this.filename = filename;
	}
	
	public String getParameterName(){
		return parameterName;
	}
	
	public void setParameterName(String parameterName){
		this.parameterName = parameterName;
	}
	
	public String getContentType(){
		return contentType;
	}
	
	public void setContentType(String contentType){
		this.contentType = contentType;
	}

}
