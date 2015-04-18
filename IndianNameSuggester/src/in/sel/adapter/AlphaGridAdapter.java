package in.sel.adapter;

import in.sel.customview.BadgeView;
import in.sel.customview.CircularImageView;
import in.sel.indianbabyname.DBHelper;
import in.sel.indianbabyname.R;
import in.sel.indianbabyname.TableContract;
import in.sel.utility.Utility;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AlphaGridAdapter extends BaseAdapter {

	public static class ViewHolder {
		TextView tvAlpha;
		// ImageView ivUpper;
		// ImageView ivLower;

		CircularImageView circular_image_view;

		BadgeView b;
	}

	LayoutInflater mInflator;

	public AlphaGridAdapter(Context con) {
		mInflator = LayoutInflater.from(con);
	}

	final String[] letter = new String[] { "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	@Override
	public int getCount() {
		return letter.length;
	}

	@Override
	public Object getItem(int position) {
		return letter[position];
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
			v.circular_image_view = (CircularImageView) convertView.findViewById(R.id.circular_image_view);
			
			// v.ivLower = (ImageView)convertView.findViewById(R.id.iv_product);
			// v.ivUpper =
			// (ImageView)convertView.findViewById(R.id.iv_product_cover);

			// v.ivCount = (ImageView) convertView.findViewById(R.id.iv_count);
			// v.ivCountCover =
			// (ImageView)convertView.findViewById(R.id.iv_count_cover);

			 v.b = new BadgeView(mInflator.getContext(), v.circular_image_view);
			convertView.setTag(v);

		} else {
			v = (ViewHolder) convertView.getTag();
		}

		/* set Image of Type */

		// String path = "/data/data/" + mInflator.getContext().getPackageName()
		// + "/databases/"+ DatabaseHelper.DB_NAME;
		// String path = Environment.getExternalStorageDirectory().toString() +
		// "/Download/PeriodicTableValence.png";

		// Bitmap bm=
		// BitmapFactory.decodeResource(mInflator.getContext().getResources(),R.drawable.test);

		/** This would be diameter of Circle */
		// int dim = 150;

		/** Base circle */
		// Bitmap bm = getBitMapImage(null, dim, false);
		// v.ivLower.setImageBitmap(bm);
		//
		// /* Upper Circle */
		// bm = getCoverBitMapImage(dim, false);
		// v.ivUpper.setImageBitmap(bm);

		// bm = getBitMapImage(null, 40, true);
		// v.ivCount.setImageBitmap(bm);
		//
		// /* Upper Circle */
		// bm = getCoverBitMapImage(40, true);
		// v.ivCountCover.setImageBitmap(bm);

		/* Text */
		v.tvAlpha.setText(letter[position]);

		DBHelper db = new DBHelper(mInflator.getContext());
		String where = TableContract.Name.NAME_EN + " like '"
				+ letter[position] + "%' AND " + TableContract.Name.GENDER_CAST
				+ "=''";
		long count = db.getTableRowCount(TableContract.Name.TABLE_NAME, where);

		/** */
		v.b.setText(count + "");
		v.b.show();

		return convertView;
	}

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

	private Bitmap getCoverBitMapImage(int dim) {
		Bitmap myBitmap = BitmapFactory.decodeResource(mInflator.getContext()
				.getResources(), R.drawable.indicator_border);

		/** Radius margin */
		int margin = 2;

		return Utility.getRoundedRectBitmap(myBitmap, dim, margin);

	}

}
