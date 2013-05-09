package com.example.testapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils {
	private String SDPATH;
	
	public String getSDPATH(){
		return SDPATH;
	}
	
	public void setSDPATH(String sDPATH){
		SDPATH = sDPATH;
	}
	
	// get SD card directory
	public FileUtils(){
		SDPATH = Environment.getExternalStorageDirectory() + "/";
		System.out.println("sd card's direcotry path is :" + SDPATH);
	}
	
	// create file on SD card
	public File createSDFile(String fileName) throws IOException{
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}
	
	// create directory on SD card
	public File createSDDir(String dirName){
		File dir = new File(SDPATH + dirName);
		System.out.println("storage devices's state :" + Environment.getExternalStorageState());
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			System.out.println("this directory real path is :"+dir.getAbsolutePath());
			System.out.println("the result of making directory :"+dir.mkdir());
		}
		return dir;
	}
	
	// check if the file exists
	public void deleteFileifExist(String fileName){
		File file = new File(SDPATH + fileName);
		if(file.exists())
			file.delete();
	}
	
	public File write2SDFromInput(String path, String fileName, InputStream inputStream){
		File file = null;
		OutputStream output = null;
		try{
			File tempf = createSDDir(path);
			System.out.println("directory in the sd card :" + tempf.exists());
			file = createSDFile(path + fileName);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4*1024];
			int length = 0;
			while((length = inputStream.read(buffer)) != -1){
				output.write(buffer, 0, length);
				System.out.println(length);
			}
			output.flush();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try{
				output.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		return file;
	}
}
