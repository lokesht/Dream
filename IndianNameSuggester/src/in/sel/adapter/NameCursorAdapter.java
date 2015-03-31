package in.sel.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.sel.adapter.NameAdapter.ViewHolder;
import in.sel.indianbabyname.DBHelper;
import in.sel.indianbabyname.R;
import in.sel.indianbabyname.TableContract;
import in.sel.model.M_Name;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NameCursorAdapter extends CursorAdapter {

	/** Holds All categorized Men and Women which are not marked yet */
	public static HashMap<Integer, String> lsNameMarked = new HashMap<Integer, String>();

	public NameCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public int getViewTypeCount() {
		return getCount();
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	};

	@Override
	public void bindView(final View view, final Context context, final Cursor c) {
		final ViewHolder holder = (ViewHolder) view.getTag();

		// use the holder to assign values to the elements
		holder.c1.setText((c.getPosition() + 1) + " " + c.getString(c.getColumnIndex(TableContract.Name.NAME_EN)));
		holder.c2.setText(c.getString(c.getColumnIndex(TableContract.Name.NAME_MA)));
		holder.c3.setText(c.getString(c.getColumnIndex(TableContract.Name.NAME_FRE)));

		/** Holds Key listener */
		/** */
		holder.ll.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				TextView t = (TextView) v.findViewById(R.id.c1);
				String nameEn = t.getText().toString();
				t = (TextView) v.findViewById(R.id.c2);
				String nameMa = t.getText().toString();

				final Dialog d = new Dialog(context);
				d.setContentView(R.layout.dialog_name_editor);

				final EditText etEn = (EditText) d.findViewById(R.id.et_name_en);
				final EditText etMa = (EditText) d.findViewById(R.id.et_name_ma);

				etEn.setText((nameEn.split(" "))[1]);
				etMa.setText(nameMa);

				Button btnSub = (Button) d.findViewById(R.id.btn_submit);
				Button btnCan = (Button) d.findViewById(R.id.btn_cancel);

				btnSub.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String nameEn = etEn.getText().toString();
						String nameMa = etMa.getText().toString();

						DBHelper db = new DBHelper(context);

						String where = TableContract.Name.NAME_EN + " = '" + nameEn + "'";
						Cursor c = db.getTableValue(TableContract.Name.TABLE_NAME,
								new String[] { TableContract.Name.NAME_FRE }, where);
						long newfre = Long.parseLong(c.getString(c.getColumnIndex(TableContract.Name.NAME_FRE)));

						ContentValues cv = new ContentValues();
						if (c.getCount() > 0) {
							c.moveToFirst();
							newfre = newfre + c.getLong(0);
							cv.put(TableContract.Name.NAME_FRE, newfre);

							db.deleteRow(TableContract.Name.TABLE_NAME, where);
						} else {
							cv.put(TableContract.Name.NAME_EN, nameEn);
							cv.put(TableContract.Name.NAME_MA, nameMa);
						}

						where = TableContract.Name.AUTO_ID + " = "
								+ c.getString(c.getColumnIndex(TableContract.Name.AUTO_ID));
						long l = db.updateTable(TableContract.Name.TABLE_NAME, cv, where);
						if (l > 0) {
							Toast.makeText(context, "Text Updated", Toast.LENGTH_SHORT).show();
						}
						d.cancel();
					}
				});

				btnCan.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						d.cancel();
					}
				});
				d.show();
				return false;
			}
		});

		/** */
		holder.rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int gender_cast = -1;
				/* 5 Means It is Valid Name */
				switch (checkedId) {
				case R.id.rbMale:
					gender_cast = 0;
					break;
				case R.id.rbFeMale:
					gender_cast = 1;
					break;
				case R.id.rbBoth:
					gender_cast = 2;
					break;
				case R.id.rbCast:
					gender_cast = 3;
					break;
				case R.id.rbDelete:
					gender_cast = 100;
					break;
				}

				if (gender_cast > -1) {

					/** Method 1: to Find view Postion*/
					// View parentRow = (View) view.getParent();
					// ListView listView = (ListView) parentRow;
					// final int position = listView.getPositionForView(view);

					/** Method 2: to Find Postion by Tagging with Object */
					Object obj = holder.rg.getTag();
					int id = Integer.parseInt(obj.toString());

					/** Method 3: to get Value and then query from database*/
					// TextView t = (TextView) view.findViewById(R.id.c1);
					// String nameEn = t.getText().toString();
					// t = (TextView) view.findViewById(R.id.c2);
					// String nameMa = t.getText().toString();

					/**This will give last position visible on currunt time*/
					// int j = c.getPosition();
					// int id = c.getInt(c.getColumnIndex(TableContract.Name.AUTO_ID));

					lsNameMarked.put(id, gender_cast + "");

					// DBHelper dbtemp = new DBHelper(context);
					// ContentValues cv = new ContentValues();
					// cv.put(TableContract.Name.GENDER_CAST, gender_cast);
					//
					// /** Where clause */
					// String where = TableContract.Name.AUTO_ID + " = " +
					// cur.getString(cur.getColumnIndex(TableContract.Name.AUTO_ID));
					// int i = dbtemp.updateTable(TableContract.Name.TABLE_NAME, cv, where);
					//
					// if(i>0)
					// {
					// Log.i("Updated", i + "");
					// Toast.makeText(context, "Text Updated", Toast.LENGTH_SHORT).show();
					// }
				}
			}
		});

		/** By Default make Name as male*/
	    holder.rg.check(R.id.rbMale);
		
		/** Saving id and gender = Male by Default */
		//int id = c.getInt(c.getColumnIndex(TableContract.Name.AUTO_ID));
		//lsNameMarked.put(id, 0 + "");
		
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View convertView = inflater.inflate(R.layout.item_list_name, null, false);

		// create an instance of the holder
		ViewHolder holder = new ViewHolder();
		// add it to the Tag, so if it not null get the elements from tag.
		holder.ll = (LinearLayout) convertView.findViewById(R.id.ll_item);
		holder.c1 = (TextView) convertView.findViewById(R.id.c1);
		holder.c1.setTextColor(Color.BLACK);

		holder.c2 = (TextView) convertView.findViewById(R.id.c2);
		holder.c3 = (TextView) convertView.findViewById(R.id.c3);

		holder.rg = (RadioGroup) convertView.findViewById(R.id.rgMFBC);
		holder.ivSmile = (ImageView) convertView.findViewById(R.id.ivSmily);

		/** Tag Id of Row */
		int id = cursor.getInt(cursor.getColumnIndex(TableContract.Name.AUTO_ID));
		holder.rg.setTag(id);
		convertView.setTag(holder);

		return convertView;
	}

}
