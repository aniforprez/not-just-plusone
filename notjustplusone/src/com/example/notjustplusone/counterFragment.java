package com.example.notjustplusone;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

/**
 * The Fragment that displays the counter
 */
public class counterFragment extends Fragment {
	private counterClass counterClassObj;
	TextView counter_value;     //value view object
	TextView counter_item;      //item view object
	Button counter_increment;   //+ button object
	Button counter_decrement;   //- button object

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		//inflate the fragment with counterfragment layout resource
		View v = inflater.inflate(R.layout.counterfragment, container, false);
		counter_increment = (Button) v.findViewById(R.id.counter_increase);
		counter_decrement = (Button) v.findViewById(R.id.counter_decrease);

		counter_value = (TextView) v.findViewById(R.id.counter_value);
		counter_item = (TextView) v.findViewById(R.id.counter_item);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		//simple onActivityCreated ovverride to pass on arguments to super
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		DBHelper db = new DBHelper(getActivity());
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

		List<counterClass> counterList = db.getAllCounters();

		if (counterList.isEmpty()) {
			Random randomgen = new Random();
			int counterID = randomgen.nextInt(Integer.MAX_VALUE);
			counterClass newCounter = new counterClass(counterID);
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("default_counter", counterID);
			editor.commit();
			db.addCounter(newCounter);
			initCounter(newCounter);
		}
		else {
			int counterID = settings.getInt("default_counter", 0);
			for (counterClass counterItem : counterList) {
				if(counterID == counterItem.getId()) {
					initCounter(counterItem);
					break;
				}
			}
		}
	}

	public void initCounter(counterClass counterArg) {
		final Context context = getActivity();
		final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

		counterClassObj = counterArg; //an object for the counterClass
//		getSettings(context);

		//set value and item text to ones stored in counter object
		counter_value.setText(String.valueOf(counterClassObj.getCounterValue()));
		counter_item.setText(counterClassObj.getItemString());

		//ensures that if value is zero, then decrement button is disabled
		if(counterClassObj.getCounterValue() <= 0) {
			counter_decrement.setEnabled(false);
		}

		//click and longclick listeners for buttons and textviews
		counter_increment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//+ button is clicked
				int newCounterValue = counterClassObj.incrementValue();
				counter_value.setText(String.valueOf(newCounterValue));
				if(newCounterValue > 0) {
					counter_decrement.setEnabled(true);
				}
				if(settings.getBoolean("vibrate_plus", false) || settings.getBoolean("vibrate_all", false)) {
					vibrator.vibrate(50);
				}
			}
		});

		counter_decrement.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//- button is clicked
				int newCounterValue = counterClassObj.decrementValue();
				counter_value.setText(String.valueOf(newCounterValue));
				if (newCounterValue <= 0) {
					counter_decrement.setEnabled(false);
				}
				if (settings.getBoolean("vibrate_minus", false) || settings.getBoolean("vibrate_all", false)) {
					vibrator.vibrate(50);
				}
			}
		});

		if(settings.getBoolean("button_minus", false)) {
			counter_decrement.setVisibility(View.VISIBLE);
		}
		else {
			counter_decrement.setVisibility(View.INVISIBLE);
		}

		if(settings.getBoolean("edit_item", false)) {
			counter_item.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View view) {
					simpleDialogMaker("item", context);
					return true;
				}
			});
		}

		if(settings.getBoolean("edit_value", false)) {
			counter_value.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View view) {
					simpleDialogMaker("value", context);
					return true;
				}
			});
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		DBHelper db = new DBHelper(getActivity());

		db.updateCounter(counterClassObj);
	}


	//generic function to create simple confirmation dialogs on this screen
	public void simpleDialogMaker(final String type, final Context context) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder
				//If yes is clicked then another dialog to enter input is called namely setDialogMaker function
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						textDialogMaker(type, context);
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//If user presses no button then a toast is shown and he is returned
						Toast.makeText(context, R.string.cancelledText, Toast.LENGTH_SHORT).show();
					}
				});
		//complex if-else statement because fuck if switch for strings isn't supported
		if(type.equals("item")) {
			alertDialogBuilder.setMessage(R.string.dialogItemTextQuery);
		}
		else if(type.equals("value")) {
			alertDialogBuilder.setMessage(R.string.dialogValueTextQuery);
		}
		//dialog is created and displayed
		alertDialogBuilder.create();
		alertDialogBuilder.show();
	}

	//function to make slightly more complex dialogs which will use custom layouts and require input
	public void textDialogMaker(String type, final Context context) {
		AlertDialog.Builder editDialogBuilder = new AlertDialog.Builder(context);
		LayoutInflater dialogInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//layout inflated to view THEN to dialog to allow for searching for views inside layout
		View dialogView = dialogInflater.inflate(R.layout.textdialog, null);
		editDialogBuilder.setView(dialogView)
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Toast.makeText(context, R.string.cancelledText, Toast.LENGTH_SHORT).show();
					}
				});
		//this is why view was inflated first. otherwise this wouldn't work
		final EditText editText = (EditText) dialogView.findViewById(R.id.dialogInput);
		if (type.equals("item")) {
			editDialogBuilder.setTitle(R.string.dialogItemText)
					.setPositiveButton("Save", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							String newItemText;
							//get the string inside and check if it's valid.
							//assign to counterClassObj and update textview if fine
							newItemText = editText.getText().toString();
							if(newItemText == null || newItemText.trim().length() == 0)
								Toast.makeText(context, "Invalid Item Name", Toast.LENGTH_SHORT).show();
							else {
								counterClassObj.setItemString(newItemText);
								counter_item.setText(newItemText);
								Toast.makeText(context, R.string.savedText, Toast.LENGTH_SHORT).show();
							}

						}
					});
			//since counterClass items are characters
			editText.setInputType(InputType.TYPE_CLASS_TEXT);
		}
		else if (type.equals("value")) {
			editDialogBuilder.setTitle(R.string.dialogValueText)
					.setPositiveButton("Save", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							int newValue;
							//get the integer inside and check if it's valid.
							//assign to counterClassObj and update textview if fine
							newValue = Integer.valueOf(editText.getText().toString());
							if(newValue < 0)
								Toast.makeText(context, "Invalid Item Name", Toast.LENGTH_SHORT).show();
							else {
                                counter_value.setText(Integer.toString(counterClassObj.setCounterValue(newValue)));
								if(newValue <= 0) {
									counter_decrement.setEnabled(false);
								}
								if(newValue > 0) {
									counter_decrement.setEnabled(true);
								}
								Toast.makeText(context, R.string.savedText, Toast.LENGTH_SHORT).show();
							}
						}
					});
			//duh, how will values be chars
			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		}
		//the dialog is created then displayed
		editDialogBuilder.create();
		editDialogBuilder.show();
	}

//	public void getSettings(final Context context) {
//		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);  //the preference object
//
//		counterClassObj.setSettingsBool("button_minus", settings.getBoolean("button_minus", false));
//		counterClassObj.setSettingsBool("edit_item", settings.getBoolean("edit_item", false));
//		counterClassObj.setSettingsBool("edit_value", settings.getBoolean("edit_value", false));
//		counterClassObj.setSettingsBool("vibrate_plus", settings.getBoolean("vibrate_plus", false));
//		counterClassObj.setSettingsBool("vibrate_minus", settings.getBoolean("vibrate_minus", false));
//		counterClassObj.setSettingsBool("vibrate_all", settings.getBoolean("vibrate_all", false));
//	}

	public void counterSettingsChanged(String key) {
		final Context context = getActivity();
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		if (key.equals("button_minus")) {
			boolean booltemp = settings.getBoolean("button_minus", false);
			if(booltemp)
				counter_decrement.setVisibility(View.VISIBLE);
			else
				counter_decrement.setVisibility(View.INVISIBLE);
		}
		else if (key.equals("edit_item")) {
			boolean booltemp = settings.getBoolean("edit_item", false);
			if(booltemp)
				counter_item.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View view) {
						simpleDialogMaker("item", context);
						return true;
					}
				});
			else
				counter_item.setOnLongClickListener(null);
		}
		else if (key.equals("edit_value")) {
			boolean booltemp = settings.getBoolean("edit_value", false);
			if(booltemp)
				counter_value.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View view) {
						simpleDialogMaker("value", context);
						return true;
					}
				});
			else
				counter_value.setOnLongClickListener(null);
		}
	}

	public void newCounter() {
		Random randomgen = new Random();
		DBHelper db = new DBHelper(getActivity());
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

		List<counterClass> counterList = db.getAllCounters();

		Boolean counterUnique;
		int counterID;
		do {
			counterUnique = true;
			counterID = randomgen.nextInt(Integer.MAX_VALUE);
			for(counterClass counterItem : counterList) {
				if (counterItem.getId() == counterID) {
					counterUnique = false;
					break;
				}
			}
		} while (!counterUnique);
		counterClass newCounter = new counterClass(counterID);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("default_counter", counterID);
		editor.commit();
		db.addCounter(newCounter);
		initCounter(newCounter);
	}

}