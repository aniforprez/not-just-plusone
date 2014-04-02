package com.example.notjustplusone;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

//used v13 support library. damn thing has no support online. you'd think at least the android dev site would have some
//but nooo it only has v4 crap

/**
 * The main activity
 */
public class mainActivity extends FragmentActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//activity_main is a simple container activity for the fragments pager. it has only one viewpager element
		setContentView(R.layout.activity_main);
		//we find that viewpager element and stuff it with the fragmentpager we made
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		//use getSupportFragmentManager if u decide to use v4 for some goddamned reason
		fragmentPager fpager = new fragmentPager(getFragmentManager());
		viewPager.setAdapter(fpager);
		//set fragment in view with this function by passing the index of page to be displayed
		//change fragment displayed in getItem override in fragmentPager class
		viewPager.setCurrentItem(0);
	}
}