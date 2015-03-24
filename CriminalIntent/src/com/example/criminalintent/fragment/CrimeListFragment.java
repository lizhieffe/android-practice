package com.example.criminalintent.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.criminalintent.R;
import com.example.criminalintent.activity.CrimePagerActivity;
import com.example.criminalintent.model.Crime;
import com.example.criminalintent.model.CrimeLab;

public class CrimeListFragment extends ListFragment {

	private static final String TAG = CrimeListFragment.class.getName();
	
	private List<Crime> crimes;
	private boolean subtitleVisible;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		setHasOptionsMenu(true);
		
		initCrimes();
		initListViewAdapter();
		
		setRetainInstance(true);
		subtitleVisible = false;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, parent, savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (subtitleVisible) {
				getActivity().getActionBar().setTitle(R.string.subtitle);
			}
		}
		
		return v;
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
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
		
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
		if (subtitleVisible && showSubtitle != null) {
			showSubtitle.setTitle(R.string.hide_subtitle);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_item_new_crime:
			Crime crime = new Crime();
			CrimeLab.getInstance(getActivity()).addCrime(crime);
			Intent i = new Intent(getActivity(), CrimePagerActivity.class);
			i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
			startActivityForResult(i, 0);
			return true;
		case R.id.menu_item_show_subtitle:
			if (getActivity().getActionBar().getSubtitle() == null) {
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
				item.setTitle(R.string.hide_subtitle);
				this.subtitleVisible = true;
			}
			else {
				getActivity().getActionBar().setSubtitle(null);
				item.setTitle(R.string.show_subtitle);
				this.subtitleVisible = false;
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
