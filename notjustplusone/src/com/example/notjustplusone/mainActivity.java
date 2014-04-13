package com.example.notjustplusone;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

//used v13 support library. damn thing has no support online. you'd think at least the android dev site would have some
//but nooo it only has v4 crap

/**
 * The main activity
 */
public class mainActivity extends FragmentActivity implements settingsFragment.settingsChanged{
	private fragmentPager fpager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//activity_main is a simple container activity for the fragments pager. it has only one viewpager element
		setContentView(R.layout.activity_main);
		//we find that viewpager element and stuff it with the fragmentpager we made
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		//use getSupportFragmentManager if u decide to use v4 for some goddamned reason
		fpager = new fragmentPager(getFragmentManager());
		viewPager.setAdapter(fpager);
		//set fragment in view with this function by passing the index of page to be displayed
		//change fragment displayed in getItem override in fragmentPager class
		viewPager.setCurrentItem(0);
	}

	public void onSettingsChanged(String key) {
		//seriously why the fuck doesn't android allow me easy access to fragments in a viewpager? this is not a safe method
        counterFragment tempCounterFragment = fpager.getCounterFragment();
		tempCounterFragment.counterSettingsChanged(key);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		Log.i("action_bar", "apparently this is being called!");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.add_counter:
				counterFragment tempCounterFragment = fpager.getCounterFragment();
				tempCounterFragment.newCounter();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}