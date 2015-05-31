package in.sel.indianbabyname;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class ActivityRecycleView extends Activity{
   @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_name_list_recycle_view);
	
	init();
}
   
   public void init()
   {
	   RecyclerView rv  = (RecyclerView)findViewById(R.id.rv_frequency_list);
   }
}
