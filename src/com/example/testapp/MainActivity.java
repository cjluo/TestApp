package com.example.testapp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText urlAddress;
	private EditText downloadText;
	private Button downloadButton;
	private EditText uploadfileText;
	private Button uploadButton;
	private File file;
	private Handler handler;
	private static final String TAG="MainActivity";
	
	public static PowerManager pm;
	public static PowerManager.WakeLock wl;
	public static NativeInput keys = new NativeInput();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		urlAddress = (EditText)findViewById(R.id.urlAddress);
		
		downloadText = (EditText)findViewById(R.id.downloadfile);
		downloadButton = (Button)findViewById(R.id.download);
		downloadButton.setOnClickListener(new DownloadListener());

		uploadfileText = (EditText)findViewById(R.id.uploadfile);
		uploadButton = (Button)findViewById(R.id.upload);
		uploadButton.setOnClickListener(new UploadfileListener());
		
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DimScreen");
		
	}
	
	class DownloadListener implements Button.OnClickListener {
		
		public void onClick(View v) {
			String downloadmsg = downloadText.getText().toString();
			String urlmsg = urlAddress.getText().toString();
			Log.i(TAG, "if exists: " + downloadmsg);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			wl.acquire();			
			keys.SendKey(116, true);
			keys.SendKey(116, false);		
			
			HttpDownloader downloader = new HttpDownloader();
			downloader.execute(urlmsg+downloadmsg, "test/", downloadmsg);
		}
	}
	
	class UploadfileListener implements Button.OnClickListener {
		public void onClick(View v) {
			handler = new Handler();
			String uploadmsg = uploadfileText.getText().toString();
			Log.i(TAG, "if exists: " + uploadmsg);
			file = new File(Environment.getExternalStorageDirectory(), uploadmsg);
			handler.post(new Runnable() {
				public void run() {
					uploadFile(file);
				}
			});
		}
	}
	
	public void uploadFile(File imageFile) {
		String requestUrl = urlAddress.getText().toString();
		Map<String, String> params = new HashMap<String, String>();
		FormFile formfile = new FormFile(imageFile.getName(), imageFile, "image", "application/octet-stream");
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		wl.acquire();			
		keys.SendKey(116, true);
		keys.SendKey(116, false);
		
		HttpUploader uploader = new HttpUploader();
		uploader.execute((Object)requestUrl, (Object)params, (Object)formfile);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
