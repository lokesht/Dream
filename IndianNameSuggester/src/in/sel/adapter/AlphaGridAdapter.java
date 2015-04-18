package in.sel.adapter;

import in.sel.customview.BadgeView;
import in.sel.indianbabyname.ActivityDisplayName_Developer;
import in.sel.indianbabyname.ActivityMain;
import in.sel.indianbabyname.R;
import in.sel.utility.Utility;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlphaGridAdapter extends BaseAdapter {

	public static BadgeView selectedText;
	public static class ViewHolder {
		TextView tvAlpha;
		ImageView circular_image_view;
		BadgeView b;
	}

	String[] letter = new String[] { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z" };

	LayoutInflater mInflator;
	public static HashMap<String, Integer> hm;

	public AlphaGridAdapter(Context con, HashMap<String, Integer> hm) {
		mInflator = LayoutInflater.from(con);
		AlphaGridAdapter.hm = hm;
	}

	@Override
	public int getCount() {
		return hm.size();
	}

	@Override
	public Object getItem(int position) {
		return hm.get(0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder v = null;
		if (convertView == null) {
			v = new ViewHolder();
			convertView = mInflator.inflate(R.layout.item_alphabet, null);
			v.tvAlpha = (TextView) convertView.findViewById(R.id.tv_alphabet);
			v.circular_image_view = (ImageView) convertView
					.findViewById(R.id.circular_image_view);

			v.b = new BadgeView(mInflator.getContext(), v.circular_image_view);
			convertView.setTag(v);

		} else {
			v = (ViewHolder) convertView.getTag();
		}

		/* Text */
		v.tvAlpha.setText(letter[position]);

		v.circular_image_view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				ViewHolder vh = (ViewHolder) v.getTag();
				selectedText = vh.b;
				String alp = vh.tvAlpha.getText().toString();
				
				Intent in = new Intent(mInflator.getContext(),ActivityDisplayName_Developer.class);
				in.putExtra(ActivityMain.ALPHA, alp);
				mInflator.getContext().startActivity(in);

			}
		});

		///** Tagging the Image with respective text value*/
		v.circular_image_view.setTag(v);
		
		/** */
		Integer in = hm.get(letter[position]);
		v.b.setText(in.intValue() + "");
		v.b.show();

		return convertView;
	}

	/** Return rounded images of source Image */
	private Bitmap getBitMapImage(String path, int dim) {
		Bitmap myBitmap;
		if (path != null) {
			myBitmap = BitmapFactory.decodeFile(path);
		} else {
			myBitmap = BitmapFactory.decodeResource(mInflator.getContext()
					.getResources(), R.drawable.test);
		}
		/** Radius margin */
		int margin = 5;
		return Utility.getRoundedRectBitmap(myBitmap, dim, margin);
	}

	/** Return rounded images fixed Images */
	private Bitmap getCoverBitMapImage(int dim) {
		Bitmap myBitmap = BitmapFactory.decodeResource(mInflator.getContext()
				.getResources(), R.drawable.indicator_border);

		/** Radius margin */
		int margin = 2;

		return Utility.getRoundedRectBitmap(myBitmap, dim, margin);

	}

}
