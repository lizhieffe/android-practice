package com.example.criminalintent.activity;

import java.util.List;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.criminalintent.R;
import com.example.criminalintent.fragment.CrimeFragment;
import com.example.criminalintent.model.Crime;
import com.example.criminalintent.model.CrimeLab;

public class CrimePagerActivity extends FragmentActivity {

	private ViewPager viewPager;
	private List<Crime> crimes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initViewPager();
	}
	
	private void initViewPager() {
		this.viewPager = new ViewPager(this);
		this.viewPager.setId(R.id.viewPager);
		setContentView(this.viewPager);
		
		crimes = CrimeLab.getInstance(this).getCrimes();
				
		FragmentManager fm = this.getSupportFragmentManager();
		viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

			@Override
			public Fragment getItem(int pos) {
				Crime c = CrimePagerActivity.this.crimes.get(pos);
				return CrimeFragment.newInstance(c.getId());
			}

			@Override
			public int getCount() {
				return CrimePagerActivity.this.crimes.size();
			}
			
		});
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				Crime crime = crimes.get(pos);
				if (crime.getTitle() != null) {
					setTitle(crime.getTitle());
				}
			}
			
			@Override
			public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		
		UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		for (int i = 0; i < crimes.size(); ++i) {
			if (crimeId.equals(crimes.get(i).getId())) {
				viewPager.setCurrentItem(i);
				break;
			}
		}
	}

}
