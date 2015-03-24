package com.example.criminalintent.fragment;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.example.criminalintent.R;
import com.example.criminalintent.model.Crime;
import com.example.criminalintent.model.CrimeLab;

public class CrimeFragment extends Fragment {
	
	public static final String EXTRA_CRIME_ID = CrimeFragment.class.getName() + ".crime_id";
	private static final String DIALOG_DATE = CrimeFragment.class.getName() + ".dialog_date";
	private static final int REQUEST_DATE = 0;
	
	private Crime crime;
	private EditText titleField;
	private Button dateButton;
	private CheckBox solvedCheckBox;
	
	public static CrimeFragment newInstance(UUID crimeId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);
		
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
		this.crime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);
		
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_crime, parent, false);
		initTitleField(v);
		initDateButton(v);
		initSolvedCheckBox(v);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityIntent(getActivity()) != null)
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		return v;
	}
	
	/**
	 * Called by the DatePickerFragment to update the date. 
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK)
			return;
		if (requestCode == REQUEST_DATE) {
			Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			crime.setDate(date);
			updateDate();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home:
			if (NavUtils.getParentActivityIntent(getActivity()) != null)
				NavUtils.navigateUpFromSameTask(getActivity());
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void initTitleField(View v) {
		this.titleField = (EditText)v.findViewById(R.id.fargment_crime_crime_title);
		this.titleField.setText(this.crime.getTitle());
		this.titleField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence c, int start, int before,
					int count) {
				crime.setTitle(c.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterTextChanged(Editable c) {
				
			}
		});
	}
	
	private void initDateButton(View v) {
		this.dateButton = (Button)v.findViewById(R.id.crime_date);
		updateDate();
		this.dateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment.newInstance(crime.getDate());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
			}
		});
	}
	
	private void initSolvedCheckBox(View v) {
		this.solvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
		this.solvedCheckBox.setChecked(this.crime.isSolved());
		this.solvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				CrimeFragment.this.crime.setSolved(isChecked);
			}
			
		});
	}
	
	private void updateDate() {
		this.dateButton.setText(crime.getDate().toString());
	}
}
