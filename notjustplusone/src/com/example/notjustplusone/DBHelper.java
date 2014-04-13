package com.example.notjustplusone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * The handler for the database to store counter objects
 * shamelessly copied from http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 * some really good tutorials in there. the fragment stuff was from there too
 */
public class DBHelper extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
    // Database Name
	private static final String DATABASE_NAME = "counterListr";
    // Counters table name
	private static final String TABLE_COUNTERS = "counters";
	//Counters table columns
	private static final String KEY_ID = "ID";
	private static final String KEY_VALUE = "VALUE";
	private static final String KEY_ITEM = "ITEM";

	//calling super
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_COUNTERS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY, " + KEY_ITEM + " TEXT, "
				+ KEY_VALUE + " INTEGER" + ")";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTERS);
	}

	public void addCounter(counterClass counter) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, counter.getId());
		values.put(KEY_ITEM, counter.getItemString());
		values.put(KEY_VALUE, counter.getCounterValue());

		db.insert(TABLE_COUNTERS, null, values);
		db.close();
	}

	public List<counterClass> getAllCounters() {
		List<counterClass> counterClassList = new ArrayList<counterClass>();

		String selectQuery = "SELECT * FROM " + TABLE_COUNTERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				counterClass counter = new counterClass();
				counter.setId(Integer.parseInt(cursor.getString(0)));
				counter.setItemString(cursor.getString(1));
				counter.setCounterValue(Integer.parseInt(cursor.getString(2)));

				counterClassList.add(counter);
			} while (cursor.moveToNext());
		}
		return counterClassList;
	}

	public int updateCounter(counterClass counter) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_VALUE, counter.getCounterValue());
		values.put(KEY_ITEM, counter.getItemString());

		return db.update(TABLE_COUNTERS, values, KEY_ID + " = ?", new String[] { String.valueOf(counter.getId()) });
	}

	public int deleteCounter(counterClass counter) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(TABLE_COUNTERS, KEY_ID + " = ?", new String[] { String.valueOf(counter.getId()) });
	}
}