package in.sel.adapter;

import in.sel.indianbabyname.R;
import in.sel.model.M_Name;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class NameAdapter extends BaseAdapter {

	public static class ViewHolder {
		TextView c1;
		TextView c2;
		TextView c3;
		RadioGroup rg;
	}

	static HashMap<Integer, Integer> hmChecked = new HashMap<Integer, Integer>();
	private List<M_Name> allElementDetails;
	private LayoutInflater mInflater;

	private int[] colors = new int[] { Color.parseColor("#DCDBDB"),
			Color.parseColor("#E8E8E8") };

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
			holder.c1 = (TextView) convertView.findViewById(R.id.c1);
			holder.c2 = (TextView) convertView.findViewById(R.id.c2);
			holder.c3 = (TextView) convertView.findViewById(R.id.c3);

			holder.rg = (RadioGroup) convertView.findViewById(R.id.rgMFBC);

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
		holder.c1.setText((position+1) + " " + enName);

		String maName = mName.getName_ma();
		if (maName == null)
			maName = "";
		holder.c2.setText(maName);

		String fre = mName.getFrequency() + "";
		holder.c3.setText(fre);

//		if (hmChecked.get(position) != null && hmChecked.containsKey(position)
//				&& hmChecked.get(position) > 0) {
//			holder.rg.check(hmChecked.get(position));
//		} else {
//			holder.rg.clearCheck();
//		}

		/** */
		holder.rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int checked = 0;

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
				}

				if (checkedId > 0) {
					Log.i("Listed", position + " " + checked + " " + checkedId);
					hmChecked.put(position, checkedId);
				}

				Log.i("Adapter", position + " " + checked + " " + checkedId);
			}
		});

		Log.i("Position", position + "");
		return convertView;
	}
}
