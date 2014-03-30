package com.example.notjustplusone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.content.DialogInterface;

public class mainActivity extends Activity implements OnClickListener {
	/**
	 * Main activity of the counter and the value and all that jazz
	 */

	final Context context = this;
	private counter counterObj;
	TextView counter_value;     //value view object
	TextView counter_item;      //item view object
	Button counter_increment;   //+ button object
	Button counter_decrement;   //- button object

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//OnCreate Override for mainActivity with layout in main.xml
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		counterObj = new counter(); //an object for the counter

		counter_increment = (Button) findViewById(R.id.counter_increase);
		counter_decrement = (Button) findViewById(R.id.counter_decrease);
		counter_value = (TextView) findViewById(R.id.counter_value);
		counter_item = (TextView) findViewById(R.id.counter_item);

		//set value and item text to ones stored in counter object
		counter_value.setText(String.valueOf(counterObj.getCounterValue()));
		counter_item.setText(counterObj.item);

		//ensures that if value is zero, then decrement button is disabled
		if(counterObj.getCounterValue() <= 0) {
			counter_decrement.setEnabled(false);
		}

		//click and longclick listeners for buttons and textviews
		counter_increment.setOnClickListener(this);
		counter_decrement.setOnClickListener(this);
        counter_item.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				simpleDialogMaker("item");
				return true;
			}
		});
		counter_value.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				simpleDialogMaker("value");
				return true;
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.counter_increase:
				//+ button is clicked
				int newCounterValue = counterObj.incrementValue();
				counter_value = (TextView) findViewById(R.id.counter_value);
				counter_value.setText(String.valueOf(newCounterValue));
				if(newCounterValue > 0) {
					counter_decrement.setEnabled(true);
				}
				break;
			case R.id.counter_decrease:
				//- button is clicked
				newCounterValue = counterObj.decrementValue();
				counter_value = (TextView) findViewById(R.id.counter_value);
				counter_value.setText(String.valueOf(newCounterValue));
				if(newCounterValue <= 0) {
					counter_decrement.setEnabled(false);
				}
				break;
		}
	}

	//generic function to create simple confirmation dialogs on this screen
	public void simpleDialogMaker(final String type) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						textDialogMaker(type);
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Toast.makeText(getApplicationContext(), R.string.cancelledText, Toast.LENGTH_LONG).show();
					}
				});
		if(type.equals("item")) {
			alertDialogBuilder.setMessage(R.string.dialogItemTextQuery);
		}
		if(type.equals("value")) {
			alertDialogBuilder.setMessage(R.string.dialogValueTextQuery);
		}
		alertDialogBuilder.create();
		alertDialogBuilder.show();
	}

	//function to make slightly more complex dialogs which will use custom layouts and require input
	public void textDialogMaker(String type) {
		AlertDialog.Builder editDialogBuilder = new AlertDialog.Builder(context);
		LayoutInflater dialogInflater = getLayoutInflater();
		View content = dialogInflater.inflate(R.layout.textdialog, null);
		editDialogBuilder.setView(content)
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Toast.makeText(getApplicationContext(), R.string.cancelledText, Toast.LENGTH_LONG).show();
					}
				});
		final EditText editText = (EditText) content.findViewById(R.id.dialogInput);
		if (type.equals("item")) {
			editDialogBuilder.setTitle(R.string.dialogItemText)
					.setPositiveButton("Save", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							String newItemText;
							newItemText = editText.getText().toString();
							counterObj.setItemString(newItemText);
							counter_item.setText(newItemText);
						}
					});
			editText.setInputType(InputType.TYPE_CLASS_TEXT);
		}
		if (type.equals("value")) {
			editDialogBuilder.setTitle(R.string.dialogValueText)
					.setPositiveButton("Save", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							int newValue;
							newValue = Integer.valueOf(editText.getText().toString());
							counterObj.setCounterValue(newValue);
							counter_value.setText(String.valueOf(newValue));
							if(counterObj.getCounterValue() <= 0) {
								counter_decrement.setEnabled(false);
							}
						}
					});
			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		}
		editDialogBuilder.create();
		editDialogBuilder.show();
	}
}

//Code to create toast
//Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
