package in.sel.indianbabyname;

import in.sel.adapter.NameAdapter;
import in.sel.model.M_Name;
import in.sel.utility.AppLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.EventLogTags.Description;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityDisplayName extends Activity {
	String TAG = "ActivityDisplayName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_list);
		String alphabet = getIntent().getStringExtra(ActivityMain.ALPHA);

		/** DBHelper Obeject */
		DBHelper dbHelper = new DBHelper(this);

		/** just to check total entry inside table */
		int count = dbHelper.getTableRowCount(TableContract.Name.TABLE_NAME,
				null);

		/** This is will select only those which are not marked */
		String where = TableContract.Name.NAME_EN + " like '" + alphabet
				+ "%' AND " + TableContract.Name.GENDER_CAST + " IS NULL";

		Cursor c = dbHelper.getTableValue(TableContract.Name.TABLE_NAME,
				new String[] { TableContract.AppColumn.CAUTO_ID,
						TableContract.Name.NAME_EN, TableContract.Name.NAME_MA,
						TableContract.Name.NAME_FRE,
						TableContract.Name.GENDER_CAST}, where);

		/** Parse */
		final List<M_Name> name = parseListName(c);
		dbHelper.close();
		/* */
		TextView tvTotal = (TextView) findViewById(R.id.tvTotal);
		tvTotal.setText("Total unique word in this group is " + name.size());

		/* sort on Frequency By Default */
		Collections.sort(name, new Comparator<M_Name>() {

			@Override
			public int compare(M_Name lhs, M_Name rhs) {
				return rhs.getFrequency() - lhs.getFrequency();
			}
		});

		final ListView lsName = (ListView) findViewById(R.id.lv_alphabet);
		final NameAdapter na = new NameAdapter(this, name);
		lsName.setAdapter(na);

		/* Sorting on Name based on English Name */
		TextView tvEnName = (TextView) findViewById(R.id.tvEnglish);
		tvEnName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/* sort on EnName By Default */
				Collections.sort(name, new Comparator<M_Name>() {

					@Override
					public int compare(M_Name lhs, M_Name rhs) {
						return lhs.getName_en().compareTo(rhs.getName_en());
					}
				});

				lsName.setAdapter(new NameAdapter(ActivityDisplayName.this,
						name));
				lsName.invalidate();
			}
		});

		/* Sorting on Name based on Marathi Name */
		TextView tvHinName = (TextView) findViewById(R.id.tvHindi);
		tvHinName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/* sort on EnName By Default */
				Collections.sort(name, new Comparator<M_Name>() {

					@Override
					public int compare(M_Name lhs, M_Name rhs) {
						return lhs.getName_ma().compareTo(rhs.getName_ma());
					}
				});

				lsName.setAdapter(new NameAdapter(ActivityDisplayName.this,
						name));
				lsName.invalidate();
			}
		});

		/* Sorting on Name based on Frequency */
		TextView tvFrequ = (TextView) findViewById(R.id.tvFrequency);
		tvFrequ.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/* sort on Frequency By Default */
				Collections.sort(name, new Comparator<M_Name>() {

					@Override
					public int compare(M_Name lhs, M_Name rhs) {
						return rhs.getFrequency() - lhs.getFrequency();
					}
				});

				lsName.setAdapter(new NameAdapter(ActivityDisplayName.this,
						name));
				lsName.invalidate();
			}
		});
	}

	/** */
	List<M_Name> parseListName(Cursor c) {
		List<M_Name> lsName = new ArrayList<M_Name>();
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			do {
				int id = c.getInt(c
						.getColumnIndex(TableContract.AppColumn.CAUTO_ID));

				String en = c.getString(c
						.getColumnIndex(TableContract.Name.NAME_EN));
				String ma = c.getString(c
						.getColumnIndex(TableContract.Name.NAME_MA));
				int fre = c.getInt(c
						.getColumnIndex(TableContract.Name.NAME_FRE));

				String s = c.getString(c
						.getColumnIndex(TableContract.Name.GENDER_CAST));

				/* Considering default value as -1 */
				int desc = -1;
				if (s != null && s.length() > 0)
					desc = Integer.parseInt(s);

				M_Name temp = new M_Name(ma, en, fre, id, desc);
				lsName.add(temp);
			} while (c.moveToNext());

			/** Close database */
			c.close();
		}
		return lsName;
	}
	
}
