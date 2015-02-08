package in.sel.indianbabyname;

import in.sel.adapter.AlphaGridAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ActivityMain extends Activity {

	public static final String ALPHA = "alpha";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final String[] letter = new String[] { "A", "B", "C", "D", "E", "F",
				"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };

		AlphaGridAdapter adapter = new AlphaGridAdapter(this);
		GridView gvAl = (GridView) findViewById(R.id.gv_alphabet);
		gvAl.setAdapter(adapter);

		gvAl.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent in = new Intent(ActivityMain.this,
						ActivityDisplayName.class);
				in.putExtra(ALPHA, letter[position] + "");
				startActivity(in);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		/* Inflate the menu; this adds items to the action bar if it is present */
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.action_about) {
			Intent in = new Intent(this, ActivityAbout.class);
			startActivity(in);
		}
		return super.onOptionsItemSelected(item);
	}
}
