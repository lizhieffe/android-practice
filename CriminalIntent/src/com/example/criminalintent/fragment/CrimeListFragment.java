package com.example.criminalintent.fragment;

import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;

import com.example.criminalintent.R;
import com.example.criminalintent.model.Crime;
import com.example.criminalintent.model.CrimeLab;

public class CrimeListFragment extends ListFragment {
	
	private List<Crime> crimes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		
		initCrimes();
	}
	
	private void initCrimes() {
		this.crimes = CrimeLab.getInstance(getActivity()).getCrimes();
	}
}
