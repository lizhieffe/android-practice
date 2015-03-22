package com.example.criminalintent.activity;

import java.util.UUID;
import android.support.v4.app.Fragment;
import com.example.criminalintent.fragment.CrimeFragment;


public class CrimeActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		return CrimeFragment.newInstance(crimeId);
	}
}
