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
import android.widget.ListView;

public class ActivityDisplayName extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_list);
		String alphabet = getIntent().getStringExtra("A");

		String where = TableContract.Name.NAME_EN + " like '" + alphabet + "%'";

		Cursor c = new DatabaseHelper(this).getTableValue(
				TableContract.Name.TABLE_NAME, new String[] {
						TableContract.Name.NAME_EN, TableContract.Name.NAME_MA,
						TableContract.Name.NAME_FRE }, where);

		/** */
		List<M_Name> name = getName(c);
		Collections.sort(name, new Comparator<M_Name>() {

			@Override
			public int compare(M_Name lhs, M_Name rhs) {
				return rhs.getFrequency() - lhs.getFrequency();
			}
		});
		ListView lsName = (ListView) findViewById(R.id.lv_alphabet);
		NameAdapter na = new NameAdapter(this, name);
		lsName.setAdapter(na);
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
		return lsName;
	}
}
