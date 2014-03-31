package com.example.notjustplusone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class fragmentPager extends FragmentPagerAdapter {
	/**
	 * Main activity of the counter and the value and all that jazz
	 */

	public fragmentPager(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int Index) {
//		switch (Index) {
//			case 0:
//				return new counterFragment();
//			case 1:
//				break;
//			case 2:
//				break;
//		}
//		return null;
		return new counterFragment();
	}

	@Override
	public int getCount() {
		return 1;
	}
}

//Code to create toast
//Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
