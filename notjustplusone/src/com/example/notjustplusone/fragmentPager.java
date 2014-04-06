package com.example.notjustplusone;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

public class fragmentPager extends FragmentPagerAdapter {
	/**
	 * Pager to manage the fragments and choose which one is displayed
	 */
	//this is so i can have access to the counter fragment in the main activity to pass data
	private counterFragment counterFragmentObj;

	//pass along the cheese! or in this case, args to the super
	public fragmentPager(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int Index) {
		//override to choose which fragment gets returned for which page
		//for the value that view is set in, the fragment we choose must be returned
		//this is changed by setCurrentItem in mainActivity
		switch (Index) {
			case 0:
				counterFragmentObj = new counterFragment();
				return counterFragmentObj;
			case 1:
				return new settingsFragment();
//			case 2:
//				break;
		}
		return null;
//		return new counterFragment();
	}

	@Override
	public int getCount() {
		//this is the number of pages. change as needed
		return 2;
	}

	public counterFragment getCounterFragment() {
		return counterFragmentObj;
	}
}

//Code to create toast
//Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
