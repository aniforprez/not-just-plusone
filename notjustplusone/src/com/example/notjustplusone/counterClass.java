package com.example.notjustplusone;

/**
 * Class to hold value of the counter and various settings
 */
public class counterClass {
	private int id;
	private int value;
	//Time timer;
	private String item;
//	private counterSettingsClass settings;

	public counterClass() {
		id = 1;
		value = 0;
		item = "Sheep";
//		settings = new counterSettingsClass();

//		timer.hour = 0;
//		timer.minute = 0;
//		timer.second = 0;
	}

	public counterClass(int id) {
		this.id = id;
		value = 0;
		item = "Sheep";
	}

	public counterClass(int id, int value, String item) {
		this.id = id;
		this.value = value;
		this.item = item;
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

	public int getId() {
		return id;
	}

	public int setId(int idarg) {
		id = idarg;
		return id;
	}

//	public void setSettingsBool(String key, boolean temp) {
//		if (key.equals("vibrate_plus"))
//			settings.vibrate_plus = temp;
//		else if (key.equals("vibrate_minus"))
//			settings.vibrate_minus = temp;
//		else if (key.equals("vibrate_all"))
//			settings.vibrate_all = temp;
//		else if (key.equals("button_minus"))
//			settings.button_minus = temp;
//		else if (key.equals("edit_item"))
//			settings.edit_item = temp;
//		else if (key.equals("edit_value"))
//			settings.edit_value = temp;
//	}

//	public boolean getSettingsBool(String settingsItem) {
//		if (settingsItem.equals("vibrate_plus"))
//			return settings.vibrate_plus;
//		else if (settingsItem.equals("vibrate_minus"))
//			return settings.vibrate_minus;
//		else if (settingsItem.equals("vibrate_all"))
//			return settings.vibrate_all;
//		else if (settingsItem.equals("button_minus"))
//			return settings.button_minus;
//		else if (settingsItem.equals("edit_item"))
//			return settings.edit_item;
//		else if (settingsItem.equals("edit_value"))
//			return settings.edit_value;
//		else
//			return false;
//	}
}
