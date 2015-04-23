package in.sel.indianbabyname;

import in.sel.fragment.FragAlphabet;
import in.sel.logging.AppLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityMain extends Activity {

	public static final String ALPHA = "alpha";
	public static final String TAG = "ActivityMain";

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	ListView lsView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init(savedInstanceState);
	}

	/** Initialize Variable and list */
	private void init(Bundle savedInstanceState) {

		lsView = (ListView) findViewById(R.id.lv_left_drawer);

		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		mTitle = mDrawerTitle = getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_drawer_layout);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.open, R.string.close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);

				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			};
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		/** */
		ListView lsView = (ListView) findViewById(R.id.lv_left_drawer);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.drawer_menu,
				android.R.layout.simple_list_item_1);
		lsView.setAdapter(adapter);

		lsView.setOnItemClickListener(new DrawerListListener());
		
		if (savedInstanceState == null) {
            // on first time display view for first nav item
			display(0);
        }

	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(lsView);
		menu.findItem(R.id.action_database_copy).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.i(TAG, "onRestoreInstanceState");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(TAG, "onRestart");
		
	}
	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");

	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(TAG, "onStop");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause");
	}
	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSaveInstanceState");
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

		/** If drawer is selected directly send true */
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (id) {

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

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/** */
	class DrawerListListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			display(position);
		}
	}

	/** display */
	public void display(int position)
	{
		/** */
		if (position == 0) {
			FragAlphabet fragAlpha = new FragAlphabet();
			FragmentManager fragManage = getFragmentManager();
			FragmentTransaction fragTrans = fragManage.beginTransaction();

			fragTrans.replace(R.id.fl_container, fragAlpha, "FragAlpha");
			fragTrans.commit();
		}

		mDrawerLayout.closeDrawer(lsView);
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
