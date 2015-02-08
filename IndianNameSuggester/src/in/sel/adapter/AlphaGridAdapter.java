package in.sel.adapter;

import in.sel.indianbabyname.R;
import in.sel.utility.UtilityControl;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlphaGridAdapter extends BaseAdapter {

	public static class ViewHolder {
		TextView tvAlpha;
		ImageView ivUpper;
		ImageView ivLower;
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
			v.ivLower = (ImageView)convertView.findViewById(R.id.iv_product);
			v.ivUpper = (ImageView)convertView.findViewById(R.id.iv_product_cover);
			convertView.setTag(v);
		} else {
			v = (ViewHolder) convertView.getTag();
		}
		
		/* set Image of Type */
		
		
		//String path = "/data/data/" + mInflator.getContext().getPackageName() + "/databases/"
		//		+ DatabaseHelper.DB_NAME;
		
		//String path = Environment.getExternalStorageDirectory().toString() + "/Download/PeriodicTableValence.png";
		
		int dim = 150;
		Bitmap bm = getBitMapImage(null, dim);
		
		//Bitmap bm= BitmapFactory.decodeResource(mInflator.getContext().getResources(), 
		//	    R.drawable.test);
		
		v.ivLower.setImageBitmap(bm);

		bm = getCoverBitMapImage(dim);
		v.ivUpper.setImageBitmap(bm);
		
		v.tvAlpha.setText(letter[position]);
		return convertView;
	}
	
	private Bitmap getBitMapImage(String path, int dim) {
		Bitmap myBitmap;
		if (path != null) {
			myBitmap = BitmapFactory.decodeFile(path);
		} else {
			myBitmap = BitmapFactory.decodeResource(mInflator.getContext().getResources(), R.drawable.test);
		}
		return UtilityControl.getRoundedRectBitmap(myBitmap, dim);
	}

	private Bitmap getCoverBitMapImage(int dim) {
		Bitmap myBitmap = BitmapFactory.decodeResource(mInflator.getContext().getResources(), R.drawable.ic_lutect_indi_border_circle);
		return UtilityControl.getAboveBitmap(myBitmap, dim);
	}

}
