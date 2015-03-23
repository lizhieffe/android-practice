package com.example.criminalintent.fragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.example.criminalintent.R;

public class DatePickerFragment extends DialogFragment {
	
	public static final String EXTRA_DATE = DatePickerFragment.class.getName() + ".date";
	
	private Date date;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		this.date = (Date)getArguments().getSerializable(EXTRA_DATE);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONDAY);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		View v = getActivity().getLayoutInflater()
				.inflate(R.layout.dialog_date, null);
		DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
		datePicker.init(year, month, day, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int year, int month,
					int day) {
				DatePickerFragment.this.date = new GregorianCalendar(year, month, day).getTime();
				
				// Update argument to preserve selected value on rotation
				getArguments().putSerializable(EXTRA_DATE, date);
			}
			
		});
		
		return new AlertDialog.Builder(getActivity())
				.setTitle(R.string.date_picker_title)
				.setView(v)
				.setPositiveButton(android.R.string.ok, null)
				.create();
	}
	
	public static DatePickerFragment newInstance(Date date) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, date);
		
		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
}
