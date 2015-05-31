package in.sel.indianbabyname;

import in.sel.adapter.NameAdapter;
import in.sel.adapter.NameCursorAdapter;
import in.sel.adapter.RecycleViewAdapter;
import in.sel.logging.AppLogger;
import in.sel.model.M_Name;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.TextView;

/** Class is designed for Developer For Marking of Name */
public class ActivityDisplayName_Developer_Recycler extends Activity implements OnClickListener {
	String TAG = "ActivityDisplayName";

	RecyclerView lsName;

	/** */
	public static String selectedAlphabet = "";

	/* adapter */
	NameCursorAdapter adapter;

	/** */
	DBHelper dbHelper = new DBHelper(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_list_recycle_view);

		//selectedAlphabet = getIntent().getStringExtra(ActivityMain.ALPHA);

		
		//init();
	}

	@Override
	protected void onStart() {

		super.onStart();
	}

	private void init() {

		/** This is will select only those which are not marked */
		String where = TableContract.Name.NAME_EN + " like '" + selectedAlphabet + "%' AND " + TableContract.Name.GENDER_CAST
				+ " = ''" + " ORDER BY " + TableContract.Name.NAME_FRE + " DESC";

		Cursor c = dbHelper.getTableValue(TableContract.Name.TABLE_NAME, new String[] { TableContract.Name.AUTO_ID,
				TableContract.Name.NAME_EN, TableContract.Name.NAME_MA, TableContract.Name.NAME_FRE,
				TableContract.Name.GENDER_CAST }, where);

		if (c != null && c.getCount() > 0) {

			AppLogger.ToastLong(this, c.getCount()+"");
			
			final List<M_Name> name  = parseListName(c);
			dbHelper.close();
			displayList(name);
			
			/** Parse */
			//displayListWithCustomCursor(c);

			/* */
			TextView tvTotal = (TextView) findViewById(R.id.tvTotal);
			tvTotal.setText("Total unique word in this group is " + c.getCount());

			/* Sorting on Name based on English Name */
			TextView tvEnName = (TextView) findViewById(R.id.tvEnglish);
			// tvEnName.setOnClickListener(this);

			/* Sorting on Name based on Marathi Name */
			TextView tvHinName = (TextView) findViewById(R.id.tvHindi);
			// tvHinName.setOnClickListener(this);

			/* Sorting on Name based on Frequency */
			TextView tvFrequ = (TextView) findViewById(R.id.tvFrequency);
			// tvFrequ.setOnClickListener(this);
		} else {
			if (c != null)
				c.close();
		}

	}

	/** */
	public void displayList(List<M_Name> name) {
		lsName = (RecyclerView) findViewById(R.id.lv_alphabet);
		RecycleViewAdapter na = new RecycleViewAdapter(this, name);
		
		
		lsName.setAdapter(na);
		
		lsName.setLayoutManager(new  LinearLayoutManager(this));
	}

	

	@Override
	public void onClick(View v) {
		String where = "";
		Cursor c = null;

		switch (v.getId()) {
		case R.id.tvFrequency:
			/** First Update List */
			updateGenderCast();

			/** This is will select only those which are not marked */
			where = TableContract.Name.NAME_EN + " like '" + selectedAlphabet + "%' AND " + TableContract.Name.GENDER_CAST
					+ " = ''" + " ORDER BY " + TableContract.Name.NAME_FRE + " DESC";

			c = dbHelper.getTableValue(TableContract.Name.TABLE_NAME, new String[] { TableContract.Name.AUTO_ID,
					TableContract.Name.NAME_EN, TableContract.Name.NAME_MA, TableContract.Name.NAME_FRE,
					TableContract.Name.GENDER_CAST }, where);

			

			break;

		case R.id.tvEnglish:
			/** First Update List */
			updateGenderCast();

			/** This is will select only those which are not marked */
			where = TableContract.Name.NAME_EN + " like '" + selectedAlphabet + "%' AND " + TableContract.Name.GENDER_CAST
					+ " = ''" + " ORDER BY " + TableContract.Name.NAME_EN + " ASC";

			c = dbHelper.getTableValue(TableContract.Name.TABLE_NAME, new String[] { TableContract.Name.AUTO_ID,
					TableContract.Name.NAME_EN, TableContract.Name.NAME_MA, TableContract.Name.NAME_FRE,
					TableContract.Name.GENDER_CAST }, where);

			
			break;

		case R.id.tvHindi:
			/** First Update List */
			updateGenderCast();

			/** This is will select only those which are not marked */
			where = TableContract.Name.NAME_EN + " like '" + selectedAlphabet + "%' AND " + TableContract.Name.GENDER_CAST
					+ " = ''" + " ORDER BY " + TableContract.Name.NAME_MA + " ASC";

			c = dbHelper.getTableValue(TableContract.Name.TABLE_NAME, new String[] { TableContract.Name.AUTO_ID,
					TableContract.Name.NAME_EN, TableContract.Name.NAME_MA, TableContract.Name.NAME_FRE,
					TableContract.Name.GENDER_CAST }, where);

			
			break;
		}

	}

	/** Update Table with gender_cast Value marked By User */
	public void updateGenderCast() {
		int count = NameCursorAdapter.lsNameMarked.size() > 0 ? NameCursorAdapter.lsNameMarked.size() : 0;
		AppLogger.ToastLong(this, "updateGenderCast"+count+"");
		if (count > 0) {

			for (Map.Entry<Integer, String> name : NameCursorAdapter.lsNameMarked.entrySet()) {
				ContentValues cv = new ContentValues();
				cv.put(TableContract.Name.GENDER_CAST, name.getValue());

				/** Where clause */
				String where = TableContract.Name.AUTO_ID + " = " + name.getKey();
				int i = dbHelper.updateTable(TableContract.Name.TABLE_NAME, cv, where);

				if (i > 0) {
					Log.i("Updated", i + "");
					// Toast.makeText(this, "Text Updated-->"+name.getKey(), Toast.LENGTH_SHORT).show();
				}
				NameCursorAdapter.lsNameMarked.remove(name);
			}
		}

		/** */
//		TextView tvTotal = (TextView) findViewById(R.id.tvTotal);
//		int in = Integer.parseInt(tvTotal.getText().toString());
//		tvTotal.setText("Total unique word in this group is " + (in - count));
	}

	/** */
	List<M_Name> parseListName(Cursor c) {
		List<M_Name> lsName = new ArrayList<M_Name>();
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			do {
				int id = c.getInt(c.getColumnIndex(TableContract.Name.AUTO_ID));

				String en = c.getString(c.getColumnIndex(TableContract.Name.NAME_EN));
				String ma = c.getString(c.getColumnIndex(TableContract.Name.NAME_MA));
				int fre = c.getInt(c.getColumnIndex(TableContract.Name.NAME_FRE));

				String s = c.getString(c.getColumnIndex(TableContract.Name.GENDER_CAST));

				/* Considering default value as -1 */
				String desc = "-1";
				if (s != null && s.length() > 0)
					desc = s;

				M_Name temp = new M_Name(ma, en, fre, id, desc);
				lsName.add(temp);
			} while (c.moveToNext());

			/** Close database */
			c.close();
		}
		return lsName;
	}

	
	@Override
	protected void onPause() {
		super.onPause();
		
		/** Update List */
		//updateGenderCast();
		
		/** */
		//dbHelper.close();
	}
	@Override
	protected void onStop() {
		super.onStop();

		
	}

	// @Override
	// protected void onPause() {
	// super.onPause();
	//
	// int index = lsName.getFirstVisiblePosition();
	//
	// View v = lsName.getChildAt(index);
	// int top = (v == null) ? 0 : (v.getTop() - lsName.getPaddingTop());
	//
	// // restore index and position
	// lsName.setSelectionFromTop(index, top);
	// ContentValues cv = new ContentValues();
	// cv.put(TableContract.SavedStatus.LETTER, alphabet);
	// cv.put(TableContract.SavedStatus.INDEX, index);
	// cv.put(TableContract.SavedStatus.POSITION, top);
	//
	// DBHelper db = new DBHelper(this);
	// long i = db.insertInTable(TableContract.SavedStatus.TABLE_NAME, TableContract.SavedStatus.LETTER, cv);
	// db.close();
	// if (i > 0)
	// Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
	//
	// Log.i(TAG, "onPause index-->" + index + " top-->" + top + " lsName.getPaddingTop()-->" + lsName.getPaddingTop());
	// }
	//
	// @Override
	// protected void onResume() {
	// super.onResume();
	//
	// DBHelper db = new DBHelper(this);
	//
	// String where = TableContract.SavedStatus.LETTER + " = '" + alphabet + "'";
	// Cursor c = db.getTableValue(TableContract.SavedStatus.TABLE_NAME, new String[] {
	// TableContract.SavedStatus.INDEX, TableContract.SavedStatus.POSITION }, where);
	//
	// if (c != null && c.getCount() > 0) {
	// c.moveToFirst();
	// int index = c.getInt(0);
	// int pos = c.getInt(1);
	// Log.i(TAG, "onResume index-->" + index + " pos-->" + pos);
	//
	// lsName.setSelectionFromTop(index, pos);
	// }
	//
	// /** */
	// if (c != null)
	// c.close();
	//
	// db.close();
	// }

	@Override
	public void onBackPressed() {
		updateGenderCast();
		dbHelper.close();
		super.onBackPressed();
	}
}
