package in.sel.indianbabyname;

import in.sel.utility.AppLogger;
import in.sel.utility.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class ActivitySplash extends Activity {
	String TAG = "ActivitySplash";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		new AsyncTask<Void, Integer, String>() {

			protected void onPreExecute() {

			};

			@Override
			protected String doInBackground(Void... params) {

				/** Copy full database from asset Folder to database Folder */
				copyDatabaseFromAsset();
				return "";
			}

			protected void onPostExecute(String result) {
				// writeDataBase();
				/**/
				Intent in = new Intent(ActivitySplash.this, ActivityMain.class);
				startActivity(in);
				finish();
			};
		}.execute();
	}

	
	/**
	 * Copy Database from asset Folder to data directory
	 */
	public void copyDatabaseFromAsset() {

		/** Just to calculate time How much it will take to copy database */
		Utility t = new Utility();

		/* Insert Database */
		DBHelper db = new DBHelper(this);
		try {
			boolean dbExist = db.isDataBaseAvailable();
			
			if (!dbExist)
				db.copyDataBaseFromAsset();

		} catch (IOException e) {
			AppLogger.WriteIntoFile("state " + TAG + " -- " + e.toString());
			Log.e("", e.toString());
		}
		System.out.println(t.getTime(t));
		// }
	}

	/* */
	public void writeDataBase() {
		String dataBase = getApplicationInfo().dataDir + DBHelper.DB_SUFFIX + DBHelper.DB_NAME;
		File f = new File(dataBase);
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			/*
			 * code is coming till here so data is loaded in successfully
			 */
			fis = new FileInputStream(f);
			fos = new FileOutputStream("/mnt/sdcard/Download/"+ DBHelper.DB_NAME + ".db");
			while (true) {
				int i = fis.read();
				if (i != -1) {
					fos.write(i);
				} else {
					break;
				}
			}
			fos.flush();
		} catch (Exception e) {
			AppLogger.WriteIntoFile(TAG + " -- " + e.toString());
			Log.e("", e.toString());
		} finally {
			try {
				fos.close();
				fis.close();
			} catch (IOException ioe) {
				Log.e("", ioe.toString());
			}
		}
	}
}
