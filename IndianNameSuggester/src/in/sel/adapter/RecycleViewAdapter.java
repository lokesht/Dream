package in.sel.adapter;

import in.sel.indianbabyname.R;
import in.sel.indianbabyname.TableContract;
import in.sel.model.M_Name;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

	private List<M_Name> allElementDetails;
	private LayoutInflater mInflater;
	
	public RecycleViewAdapter(Context context, List<M_Name> results) {
		allElementDetails = results;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getItemCount() {
		return allElementDetails.size();
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		
		View view = mInflater.inflate(R.layout.item_list_name,parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	
	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		holder.c1.setText("ABC");
		holder.c2.setText("ABC");
		holder.c3.setText("ABC");
	}
	
	class MyViewHolder extends RecyclerView.ViewHolder {

		TextView c1;
		TextView c2;
		TextView c3;
		RadioGroup rg;
		ImageView ivSmile;
		
		public MyViewHolder(View convertView) {
			super(convertView);
			
			c1 = (TextView) convertView.findViewById(R.id.c1);
			c2 = (TextView) convertView.findViewById(R.id.c2);
			c3 = (TextView) convertView.findViewById(R.id.c3);

			rg = (RadioGroup) convertView.findViewById(R.id.rgMFBC);
			ivSmile = (ImageView) convertView.findViewById(R.id.ivSmily);
		}
	}


}
