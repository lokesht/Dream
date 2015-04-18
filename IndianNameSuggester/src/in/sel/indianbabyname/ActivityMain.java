package in.sel.indianbabyname;

import in.sel.adapter.AlphaGridAdapter;
import in.sel.logging.AppLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

public class ActivityMain extends Activity {

	public static final String ALPHA = "alpha";
	public static final String TAG = "ActivityMain";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
	}

	@Override
	protected void onStart() {
		super.onStart();

		/** Update count of selected element */
		if (ActivityDisplayName_Developer.selectedAlphabet.length() != 0) {

			String alphabet = ActivityDisplayName_Developer.selectedAlphabet;
			String where = TableContract.Name.NAME_EN + " like '" + alphabet + "%' AND " + TableContract.Name.GENDER_CAST + "=''";
			DBHelper db = new DBHelper(this);
			long count = db.getTableRowCount(TableContract.Name.TABLE_NAME, where);
			/** Close Database*/
			db.close();
			
			AlphaGridAdapter.selectedText.setText(count + "");
		}

	}

	/** Initialize Variable and list */
	private void init() {
		/** */
		HashMap<String, Integer> hm = new HashMap<String, Integer>(26);
		hm = getCount(hm);

		/** */
		AlphaGridAdapter adapter = new AlphaGridAdapter(this, hm);
		GridView gvAl = (GridView) findViewById(R.id.gv_alphabet);
		gvAl.setAdapter(adapter);
	}

	/** Give count of Remaining Value in each section */
	private HashMap<String, Integer> getCount(HashMap<String, Integer> hm) {
		DBHelper db = new DBHelper(this);

		String[] letter = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
				"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

		for (int i = 0; i < letter.length; i++) {
			String where = TableContract.Name.NAME_EN + " like '" + letter[i] + "%' AND " + TableContract.Name.GENDER_CAST
					+ "=''";
			long count = db.getTableRowCount(TableContract.Name.TABLE_NAME, where);

			hm.put(letter[i], (int) count);
		}
		db.close();
		return hm;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		/* Inflate the menu; this adds items to the action bar if it is present */
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_settings:
			break;
		case R.id.action_about:
			Intent in = new Intent(this, ActivityAbout.class);
			startActivity(in);
			break;
		case R.id.action_database_copy:
			writeDataBase();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	/* */
	public void writeDataBase() {
		String dataBase = getApplicationInfo().dataDir + DBHelper.DB_SUFFIX + DBHelper.DB_NAME;
		File f = new File(dataBase);
		InputStream fis = null;
		OutputStream fos = null;

		try {

			fis = new FileInputStream(f);
			fos = new FileOutputStream("/mnt/sdcard/Download/" + DBHelper.DB_NAME);

			byte buffer[] = new byte[1024];
			int length;
			while ((length = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
			fos.flush();

			Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			AppLogger.writeLog(TAG + " -- " + e.toString());
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
