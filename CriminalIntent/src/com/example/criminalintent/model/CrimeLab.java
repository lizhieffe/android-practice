package com.example.criminalintent.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import android.content.Context;

public class CrimeLab {
	
	private static CrimeLab crimeLab;
	private Context appContext;
	private List<Crime> crimes;
	
	private CrimeLab(Context appContext) {
		this.appContext = appContext;
		this.crimes = new ArrayList<Crime>();
//		for (int i = 0; i < 100; ++i) {
//			Crime c = new Crime();
//			c.setTitle("Crime # " + i);
//			c.setSolved(i % 2 == 0);
//			crimes.add(c);
//		}
	}
	
	public static CrimeLab getInstance(Context c) {
		if (crimeLab == null)
			crimeLab = new CrimeLab(c);
		return crimeLab;
	}
	
	public Context getAppContext() {
		return this.appContext;
	}
	
	public List<Crime> getCrimes() {
		return this.crimes;
	}
	
	public Crime getCrime(UUID id) {
		for (Crime c : this.crimes)
			if (c.getId().equals(id))
				return c;
		return null;
	}
	
	public void addCrime(Crime c) {
		crimes.add(c);
	}
}
