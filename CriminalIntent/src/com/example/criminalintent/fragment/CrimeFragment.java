package com.example.criminalintent.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.example.criminalintent.R;
import com.example.criminalintent.model.Crime;

public class CrimeFragment extends Fragment {
	
	private Crime crime;
	private EditText titleField;
	private Button dateButton;
	private CheckBox solvedCheckBox;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.crime = new Crime();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_crime, parent, false);
		initTitleField(v);
		initDateButton(v);
		initSolvedCheckBox(v);
		return v;
	}
	
	private void initTitleField(View v) {
		this.titleField = (EditText)v.findViewById(R.id.fargment_crime_crime_title);
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
		this.dateButton.setText(this.crime.getDate().toString());
		this.dateButton.setEnabled(false);
	}
	
	private void initSolvedCheckBox(View v) {
		this.solvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
		this.solvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				CrimeFragment.this.crime.setSolved(isChecked);
			}
			
		});
	}
}
