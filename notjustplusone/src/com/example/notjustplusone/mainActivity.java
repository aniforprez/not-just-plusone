package com.example.notjustplusone;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

/**
 * The main activity
 */
public class mainActivity extends FragmentActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		fragmentPager fpager = new fragmentPager(getSupportFragmentManager());
		viewPager.setAdapter(fpager);
		viewPager.setCurrentItem(0);
	}
}