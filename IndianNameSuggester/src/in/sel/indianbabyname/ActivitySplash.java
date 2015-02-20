package in.sel.indianbabyname;

import in.sel.utility.AppLogger;
import in.sel.utility.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

		} catch (Exception e) {
			AppLogger.WriteIntoFile("state " + TAG + " -- " + e.toString());
			Log.e("", e.toString());
		}
		System.out.println(t.getTime(t));
		// }
	}
}
