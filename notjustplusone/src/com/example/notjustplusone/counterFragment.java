package com.example.notjustplusone;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
		final Context context = getActivity();
		final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

		counterClassObj = new counterClass(); //an object for the counterClass
		getSettings(context);

        counter_increment = (Button) v.findViewById(R.id.counter_increase);
		counter_decrement = (Button) v.findViewById(R.id.counter_decrease);

		counter_value = (TextView) v.findViewById(R.id.counter_value);
		counter_item = (TextView) v.findViewById(R.id.counter_item);

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
				if(counterClassObj.getSettingsBool("vibrate_plus") || counterClassObj.getSettingsBool("vibrate_all")) {
					vibrator.vibrate(50);
				}
			}
		});

		if(counterClassObj.getSettingsBool("button_minus")) {
			counter_decrement.setVisibility(View.VISIBLE);
			counter_decrement.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//- button is clicked
					int newCounterValue = counterClassObj.decrementValue();
					counter_value.setText(String.valueOf(newCounterValue));
					if (newCounterValue <= 0) {
						counter_decrement.setEnabled(false);
					}
					if (counterClassObj.getSettingsBool("vibrate_minus") || counterClassObj.getSettingsBool("vibrate_all")) {
						vibrator.vibrate(50);
					}
				}
			});
		}
		else {
			counter_decrement.setVisibility(View.GONE);
		}

		if(counterClassObj.getSettingsBool("edit_item")) {
			counter_item.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View view) {
					simpleDialogMaker("item", context);
					return true;
				}
			});
		}

		if(counterClassObj.getSettingsBool("edit_value")) {
			counter_value.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View view) {
					simpleDialogMaker("value", context);
					return true;
				}
			});
		}

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		//simple onActivityCreated ovverride to pass on arguments to super
		super.onActivityCreated(savedInstanceState);
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
		View content = dialogInflater.inflate(R.layout.textdialog, null);
		editDialogBuilder.setView(content)
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Toast.makeText(context, R.string.cancelledText, Toast.LENGTH_SHORT).show();
					}
				});
		//this is why view was inflated first. otherwise this wouldn't work
		final EditText editText = (EditText) content.findViewById(R.id.dialogInput);
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
                                counter_value.setText(counterClassObj.setCounterValue(newValue));
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

	public void getSettings(final Context context) {
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);  //the preference object
		counterSettingsClass tempSettings = new counterSettingsClass();

		tempSettings.button_minus = settings.getBoolean("button_minus", false);
		tempSettings.edit_item = settings.getBoolean("edit_item", false);
		tempSettings.edit_value = settings.getBoolean("edit_value", false);
		tempSettings.vibrate_plus = settings.getBoolean("vibrate_plus", false);
		tempSettings.vibrate_minus = settings.getBoolean("vibrate_minus", false);
		tempSettings.vibrate_all = settings.getBoolean("vibrate_all", false);

		counterClassObj.setSettings(tempSettings);
	}
}