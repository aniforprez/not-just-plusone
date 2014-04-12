package com.example.notjustplusone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * The fragment which will contain the settings
 */
public class settingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	settingsChanged settingsChanged;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//load preference page from settingsfragment.xml
		addPreferencesFromResource(R.xml.settingsfragment);

		onSharedPreferenceChanged(null, "");
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPrefs, String key) {
		if(!key.equals("default_counter"))
			settingsChanged.onSettingsChanged(key);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		//this ensures the parent activity can implement onSettingsSelected
		settingsChanged = (settingsChanged) activity;
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

	}

	@Override
	public void onPause() {
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		super.onPause();
	}

	public interface settingsChanged {
		public void onSettingsChanged(String key);
	}
}