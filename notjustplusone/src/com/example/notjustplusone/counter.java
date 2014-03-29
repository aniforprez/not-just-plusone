package com.example.notjustplusone;

/**
 * Class to hold value of the counter and various settings
 */
public class counter {
	int value;
	//Time timer;
	String item;

	public counter() {
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

	public void setCounterValue(int newval) { value = newval; }

	public String setItemString(String i) {
		item = i;
		return item;
	}
}
