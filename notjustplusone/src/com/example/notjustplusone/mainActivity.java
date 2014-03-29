package com.example.notjustplusone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.content.DialogInterface;

public class mainActivity extends Activity implements OnClickListener {
	/**
	 * Called when the activity is first created.
	 */

	final Context context = this;
	private counter counterObj;
	TextView counter_value;
	TextView counter_item;
	Button counter_increment;
	Button counter_decrement;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		counterObj = new counter();

		counter_increment = (Button) findViewById(R.id.counter_increase);
		counter_decrement = (Button) findViewById(R.id.counter_decrease);
		counter_value = (TextView) findViewById(R.id.counter_value);
		counter_item = (TextView) findViewById(R.id.counter_item);

		counter_value.setText(String.valueOf(counterObj.getCounterValue()));
		counter_item.setText(counterObj.item);

		if(counterObj.getCounterValue() <= 0) {
			counter_decrement.setEnabled(false);
		}

		counter_increment.setOnClickListener(this);
		counter_decrement.setOnClickListener(this);
        counter_item.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder
						.setMessage("Do you really want to edit the item?")
						.setPositiveButton("Yes",
								                  new DialogInterface.OnClickListener() {
									                  public void onClick(DialogInterface dialog,int id) {
										                  Toast.makeText(getApplicationContext(),
												                                "OK", Toast.LENGTH_LONG).show();
									                  }
								                  })
						.setNegativeButton("No",
								                  new DialogInterface.OnClickListener() {
									                  public void onClick(DialogInterface dialog,int id) {
										                  Toast.makeText(getApplicationContext(),
												                                "Button is clicked",
												                                Toast.LENGTH_SHORT).show();
									                  }
								                  });
				alertDialogBuilder.create();
				alertDialogBuilder.show();
				return true;
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.counter_increase:
				int newCounterValue = counterObj.incrementValue();
				counter_value = (TextView) findViewById(R.id.counter_value);
				counter_value.setText(String.valueOf(newCounterValue));
				if(newCounterValue > 0) {
					counter_decrement.setEnabled(true);
				}
				break;
			case R.id.counter_decrease:
				newCounterValue = counterObj.decrementValue();
				counter_value = (TextView) findViewById(R.id.counter_value);
				counter_value.setText(String.valueOf(newCounterValue));
				if(newCounterValue <= 0) {
					counter_decrement.setEnabled(false);
				}
				break;
		}
	}
}
