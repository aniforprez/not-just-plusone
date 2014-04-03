package com.example.notjustplusone;

/**
 * Class to hold value of the counter and various settings
 */
public class counterClass {
	private int value;
	//Time timer;
	private String item;
	private counterSettingsClass settings;

	public counterClass() {
		value = 0;
		item = "Sheep";

//		timer.hour = 0;
//		timer.minute = 0;
//		timer.second = 0;
	}

	public int incrementValue() {
		++value;
		return value;
	}

	public int decrementValue() {
		if(value >0)
			--value;
		return value;
	}

	public int getCounterValue() {
		return value;
	}

	public int setCounterValue(int newval) {
		value = newval;
		return value;
	}

	public String setItemString(String i) {
		item = i;
		return item;
	}

	public String getItemString() {
		return item;
	}

	public void setSettings(counterSettingsClass temp) {
		settings = temp;
	}

	public boolean getSettingsBool(String settingsItem) {
		if (settingsItem.equals("vibrate_plus"))
			return settings.vibrate_plus;
		else if (settingsItem.equals("vibrate_minus"))
			return settings.vibrate_minus;
		else if (settingsItem.equals("vibrate_all"))
			return settings.vibrate_all;
		else if (settingsItem.equals("button_minus"))
			return settings.button_minus;
		else if (settingsItem.equals("edit_item"))
			return settings.edit_item;
		else if (settingsItem.equals("edit_value"))
			return settings.edit_value;
		else
			return false;
	}
}
