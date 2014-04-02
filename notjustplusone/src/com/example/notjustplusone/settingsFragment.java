package com.example.notjustplusone;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * The fragment which will contain the settings
 */
public class settingsFragment extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//load preference page from settingsfragment.xml
		addPreferencesFromResource(R.xml.settingsfragment);
	}
}