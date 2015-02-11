package in.sel.indianbabyname;

import in.sel.adapter.NameAdapter;
import in.sel.model.M_Name;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityDisplayName extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_list);
		String alphabet = getIntent().getStringExtra(ActivityMain.ALPHA);

		/** DBHelper Obeject*/
		DatabaseHelper dbHelper = new DatabaseHelper(this);
		
		/** just to check total entry inside table*/
		int count = dbHelper.getTableRowCount(TableContract.Name.TABLE_NAME, null);
		
		/** */
		String where = TableContract.Name.NAME_EN + " like '" + alphabet + "%'";

		Cursor c = dbHelper.getTableValue(
				TableContract.Name.TABLE_NAME, new String[] {
						TableContract.Name.NAME_EN, TableContract.Name.NAME_MA,
						TableContract.Name.NAME_FRE }, where);


		final List<M_Name> name = getName(c);
		/** Close data base*/
		dbHelper.close();

		/* */
		TextView tvTotal = (TextView) findViewById(R.id.tvTotal);
		tvTotal.setText("Total unique word in this group is "+name.size());
		
		/* sort on Frequency By Default */

		/** */

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
	List<M_Name> getName(Cursor c) {
		List<M_Name> lsName = new ArrayList<M_Name>();
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			do {
				String en = c.getString(c
						.getColumnIndex(TableContract.Name.NAME_EN));
				String ma = c.getString(c
						.getColumnIndex(TableContract.Name.NAME_MA));
				String fre = c.getString(c
						.getColumnIndex(TableContract.Name.NAME_FRE));

				M_Name temp = new M_Name(en, ma, Integer.parseInt(fre));
				lsName.add(temp);
			} while (c.moveToNext());
		}
		if(c!=null)
			c.close();
		return lsName;
	}
}
