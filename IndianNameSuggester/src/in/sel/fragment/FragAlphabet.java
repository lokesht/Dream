package in.sel.fragment;

import in.sel.adapter.AlphaGridAdapter;
import in.sel.indianbabyname.ActivityDisplayName_Developer;
import in.sel.indianbabyname.DBHelper;
import in.sel.indianbabyname.R;
import in.sel.indianbabyname.TableContract;

import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class FragAlphabet extends Fragment {

	private String TAG = "FragAlphabet";
	private HashMap<String, Integer> hm;
	private GridView gv;

	/** */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		hm = new HashMap<String, Integer>(26);
		Log.e(TAG, "F-A-onAttach");
		// AppLogger.ToastShort(getActivity(), "onAttach");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_alphabet, container, false);

		/** Enable for Click */
		gv = (GridView) v.findViewById(R.id.gv_alphabet);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		hm = getCount(hm);

		AlphaGridAdapter adapter = new AlphaGridAdapter(getActivity(), hm);
		gv.setAdapter(adapter);
		Log.e(TAG, "onActivityCreated");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.e(TAG, "F-A-onCreate");
		// AppLogger.ToastShort(getActivity(), "onCreate");
	}

	@Override
	public void onStart() {
		super.onStart();

		Log.e(TAG, "F-A-onStart");

		//
		/** Update count of selected element */
		if (ActivityDisplayName_Developer.selectedAlphabet.length() != 0) {

			String alphabet = ActivityDisplayName_Developer.selectedAlphabet;
			String where = TableContract.Name.NAME_EN + " like '" + alphabet + "%' AND " + TableContract.Name.GENDER_CAST + "=''";
			DBHelper db = new DBHelper(getActivity());
			long count = db.getTableRowCount(TableContract.Name.TABLE_NAME, where);

			/** Close Database */
			db.close();

			// AppLogger.ToastLong(this, count + "");
			AlphaGridAdapter.selectedText.setText(count + "");

			/** Update value of Adapter */
			if (AlphaGridAdapter.hm != null)
				AlphaGridAdapter.hm.put(alphabet, Integer.valueOf((int) count));
		}

	}

	@Override
	public void onStop() {

		super.onStop();
		// AppLogger.ToastShort(getActivity(), "onStop");
		Log.e(TAG, "F-A-onStop");
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// AppLogger.ToastShort(getActivity(), "onSaveInstanceState");
		Log.e(TAG, "F-A-onSaveInstanceState");
	}

	@Override
	public void onPause() {
		super.onPause();
		// AppLogger.ToastShort(getActivity(), "onPause");
		Log.e(TAG, "F-A-onPause");
	}

	@Override
	public void onResume() {
		super.onResume();
		// AppLogger.ToastShort(getActivity(), "onPause");
		Log.e(TAG, "F-A-onResume");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.e(TAG, "F-A-onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "F-A-onDestroy");
	}

	/** Give count of Remaining Value in each section */
	private HashMap<String, Integer> getCount(HashMap<String, Integer> hm) {
		DBHelper db = new DBHelper(getActivity());

		String[] letter = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
				"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

		for (int i = 0; i < letter.length; i++) {
			String where = TableContract.Name.NAME_EN + " like '" + letter[i] + "%' AND " + TableContract.Name.GENDER_CAST
					+ "=''";
			long count = db.getTableRowCount(TableContract.Name.TABLE_NAME, where);

			hm.put(letter[i], (int) count);
		}
		db.close();
		return hm;
	}
}
// public class Alphabet extends Fragment implements OnItemClickListener {
//
// FragmentGridViewListner fragGridListener;
//
// public interface FragmentGridViewListner {
// void onGridViewListener(String alphabet);
// }
//
// @Override
// public void onAttach(Activity activity) {
// super.onAttach(activity);
// fragGridListener = (FragmentGridViewListner) getActivity();
// }
//
// @Override
// public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
// View v = inflater.inflate(R.layout.fragment_alphabet, container, false);
//
// /** Enable for Click*/
// GridView gv = (GridView) v.findViewById(R.id.gv_alphabet);
// gv.setOnItemClickListener(this);
//
// return v;
// }
//
// @Override
// public void onActivityCreated(Bundle savedInstanceState) {
// super.onActivityCreated(savedInstanceState);
//
//
// }
//
// @Override
// public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
// fragGridListener.onGridViewListener(",");
// }
// }
