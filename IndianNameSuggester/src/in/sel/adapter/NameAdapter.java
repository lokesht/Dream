package in.sel.adapter;

import in.sel.indianbabyname.ActivityDisplayName_Developer;
import in.sel.indianbabyname.DBHelper;
import in.sel.indianbabyname.R;
import in.sel.indianbabyname.TableContract;
import in.sel.model.M_Name;

import java.util.List;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class NameAdapter extends BaseAdapter {

	public static class ViewHolder {
		LinearLayout ll;
		TextView c1;
		TextView c2;
		TextView c3;
		RadioGroup rg;
		ImageView ivSmile;
	}

	private List<M_Name> allElementDetails;
	private LayoutInflater mInflater;

	private int[] colors = new int[] { Color.parseColor("#DCDBDB"), Color.parseColor("#E8E8E8") };

	public NameAdapter(Context context, List<M_Name> results) {
		allElementDetails = results;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return allElementDetails.size();
	}

	@Override
	public Object getItem(int position) {
		return allElementDetails.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int getPosition(String resource_type) {
		return allElementDetails.indexOf(resource_type);
	}

	@Override
	public int getViewTypeCount() {
		return allElementDetails.size();
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_list_name, null);

			holder = new ViewHolder();
			holder.ll = (LinearLayout) convertView.findViewById(R.id.ll_item);
			holder.c1 = (TextView) convertView.findViewById(R.id.c1);
			holder.c2 = (TextView) convertView.findViewById(R.id.c2);
			holder.c3 = (TextView) convertView.findViewById(R.id.c3);

			holder.rg = (RadioGroup) convertView.findViewById(R.id.rgMFBC);
			holder.ivSmile = (ImageView) convertView.findViewById(R.id.ivSmily);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.c1.setTextColor(Color.BLACK);

		/* */
		M_Name mName = allElementDetails.get(position);

		String enName = mName.getName_en();
		if (enName == null)
			enName = "";
		holder.c1.setText((position + 1) + " " + enName);

		String maName = mName.getName_ma();
		if (maName == null)
			maName = "";
		holder.c2.setText(maName);

		String fre = mName.getFrequency() + "";
		holder.c3.setText(fre);

		int posCheck = Integer.parseInt(allElementDetails.get(position).getDescription());
		if (posCheck > -1) {
			int checkedId = -1;
			switch (posCheck) {
			case 5:
				checkedId = R.id.rbMale;
				break;
			case 1:
				checkedId = R.id.rbFeMale;
				break;
			case 2:
				checkedId = R.id.rbBoth;
				break;
			case 3:
				checkedId = R.id.rbCast;
				break;
			case 100:
				checkedId = R.id.rbDelete;
				break;
			}
			holder.rg.check(checkedId);

		} else {
			holder.rg.clearCheck();
		}
		
		/** */
		holder.ll.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				
				
				final Dialog d = new Dialog(mInflater.getContext());
				d.setContentView(R.layout.dialog_name_editor);
				
				final EditText etEn = (EditText)d.findViewById(R.id.et_name_en);
				final EditText etMa = (EditText)d.findViewById(R.id.et_name_ma);
				
				etEn.setText(allElementDetails.get(position).getName_en());
				etMa.setText(allElementDetails.get(position).getName_ma());
				
				Button btnSub = (Button)d.findViewById(R.id.btn_submit);
				Button btnCan = (Button)d.findViewById(R.id.btn_cancel);
				
				btnSub.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
                     String nameEn = etEn.getText().toString();   		
                     String nameMa = etMa.getText().toString();
                     
                     DBHelper db = new DBHelper(mInflater.getContext());
                     
                     String where = TableContract.Name.NAME_EN+" = '"+nameEn+"'";
                     Cursor c = db.getTableValue(TableContract.Name.TABLE_NAME,new String[]{TableContract.Name.NAME_FRE}, where);
                     long newfre = allElementDetails.get(position).getFrequency();
                     
                     ContentValues cv = new ContentValues();
                     if(c.getCount()>0)
                     {
                    	 c.moveToFirst();
                    	 newfre = newfre + c.getLong(0);
                    	 cv.put(TableContract.Name.NAME_FRE,newfre);
                    	 
                    	 db.deleteRow(TableContract.Name.TABLE_NAME, where);
                     }else
                     {
                    	 cv.put(TableContract.Name.NAME_EN,nameEn );
                    	 cv.put(TableContract.Name.NAME_MA,nameMa ); 
                     }
                     
                     where  = TableContract.Name.AUTO_ID+" = "+allElementDetails.get(position).getId();
                     long l = db.updateTable(TableContract.Name.TABLE_NAME, cv, where);
                     if(l>0)
                     { Toast.makeText(mInflater.getContext(), "Text Updated", Toast.LENGTH_SHORT).show();
                     
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
				
				Toast.makeText(mInflater.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
				d.show();
				return false;
			}
		});
		
		/** */
		holder.rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int checked = -1;
				/* 5 Means It is Valid Name */
				switch (checkedId) {
				case R.id.rbMale:
					checked = 0;
					break;
				case R.id.rbFeMale:
					checked = 1;
					break;
				case R.id.rbBoth:
					checked = 2;
					break;
				case R.id.rbCast:
					checked = 3;
					break;
				case R.id.rbDelete:
					checked = 100;
					break;
				}

				if (checked > -1) {

					allElementDetails.get(position).setDescription(checked + "");

					DBHelper dbtemp = new DBHelper(mInflater.getContext());
					ContentValues cv = new ContentValues();
					cv.put(TableContract.Name.GENDER_CAST, checked);

					/** Where clause */
					String where = TableContract.Name.AUTO_ID + " = " + allElementDetails.get(position).getId();
					int i = dbtemp.updateTable(TableContract.Name.TABLE_NAME, cv, where);
					Log.i("Updated", i + "");
				}
			}
		});

		return convertView;
	}
}
