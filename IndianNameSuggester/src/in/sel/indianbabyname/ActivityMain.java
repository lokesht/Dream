package in.sel.indianbabyname;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import in.sel.adapter.AlphaGridAdapter;
import in.sel.utility.AppLogger;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ActivityMain extends Activity {

	public static final String ALPHA = "alpha";
	public static final String TAG = "ActivityMain";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final String[] letter = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
				"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

		AlphaGridAdapter adapter = new AlphaGridAdapter(this);
		GridView gvAl = (GridView) findViewById(R.id.gv_alphabet);
		gvAl.setAdapter(adapter);

		gvAl.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Intent in = new Intent(ActivityMain.this, DepricatedActivityDisplayName.class);
				Intent in = new Intent(ActivityMain.this, ActivityDisplayName_Developer.class);
				in.putExtra(ALPHA, letter[position] + "");
				startActivity(in);
			}
		});
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
			/*
			 * code is coming till here so data is loaded in successfully
			 */
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
