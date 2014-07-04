package in.sel.indiannamesuggester;

import in.sel.model.M_Name;
import in.sel.utility.AppLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
				insertValue();
				return null;
			}

			protected void onProgressUpdate(Integer[] values) {
			};

			protected void onPostExecute(String result) {
				// writeDataBase();
				/**/
				Intent in = new Intent(ActivitySplash.this, ActivityMain.class);
				startActivity(in);
			};
		}.execute();
	}

	public void insertValue() {
		// boolean isDrop = this.deleteDatabase(DatabaseHelper.DATABASE_NAME);

		/* Insert Database */
		DatabaseHelper db = new DatabaseHelper(this);
		// /db.executeStatement(dropDB);

		/* State Entry */
		int count = db.getTableRowCount(TableContract.Name.TABLE_NAME, null);
		if (count == 0) {
			try {
				InputStream im = getAssets().open("name.txt");
				BufferedReader br = new BufferedReader(
						new InputStreamReader(im));
				String line = br.readLine();
				List<M_Name> lst = new ArrayList<M_Name>();
				do {
					String temp[] = line.split(",");
					M_Name s1 = new M_Name(temp[0], temp[1], temp[2]);
					lst.add(s1);
				} while ((line = br.readLine()) != null);
				db.insertName(lst);
			} catch (IOException e) {
				AppLogger.WriteIntoFile("state " + TAG + " -- " + e.toString());
				Log.e("", e.toString());
			}
		}
	}

	/* */
	public void writeDataBase() {
		File f = new File("/data/data/" + this.getPackageName() + "/databases/"
				+ DatabaseHelper.DATABASE_NAME);
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			/*
			 * code is coming till here so data is loaded in successfully
			 */
			fis = new FileInputStream(f);
			fos = new FileOutputStream("/mnt/sdcard/Download/"
					+ DatabaseHelper.DATABASE_NAME + ".db");
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
