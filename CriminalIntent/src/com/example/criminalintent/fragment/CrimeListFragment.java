package com.example.criminalintent.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.criminalintent.R;
import com.example.criminalintent.activity.CrimeActivity;
import com.example.criminalintent.activity.CrimePagerActivity;
import com.example.criminalintent.model.Crime;
import com.example.criminalintent.model.CrimeLab;

public class CrimeListFragment extends ListFragment {

	private static final String TAG = CrimeListFragment.class.getName();
	
	private List<Crime> crimes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		
		initCrimes();
		initListViewAdapter();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
		Toast.makeText(getActivity(), c.toString() + " was clicked", Toast.LENGTH_SHORT).show();
		Log.d(TAG, c.toString() + " was clicked");
		
		Intent i = new Intent(getActivity(), CrimePagerActivity.class);
		i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		startActivity(i);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	private void initCrimes() {
		this.crimes = CrimeLab.getInstance(getActivity()).getCrimes();
	}
	
	private void initListViewAdapter() {
		ArrayAdapter<Crime> adapter = new CrimeAdapter(this.crimes);
		setListAdapter(adapter);
	}
	
	private class CrimeAdapter extends ArrayAdapter<Crime> {
		
		public CrimeAdapter(List<Crime> crimes) {
			super(getActivity(), 0, crimes);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// If we weren't given a view, inflate one
			if (convertView == null)
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
			
			// Configure the view for this crime
			Crime c = getItem(position);
			
			TextView titleTextView = (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
			titleTextView.setText(c.getTitle());
			
			TextView dateTextView = (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
			dateTextView.setText(c.getDate().toString());
			
			CheckBox solvedCheckBox = (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.isSolved());
			
			return convertView;
		}
	}
}
