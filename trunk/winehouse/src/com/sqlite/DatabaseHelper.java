package com.sqlite;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.entity.FoodEntity;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "com.winehouse";
	private static final int DATABASE_VERSION = 1;
	public static final String FOOD_TABLE_NAME = "FOOD_INFO";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public DatabaseHelper(Context context,int version) {
		super(context, DATABASE_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + FOOD_TABLE_NAME
				+ " (no integer primary key autoincrement, type varchar(20), id varchar(20), name varchar(40), price int);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("oldVersion:"+oldVersion+"   newVersion:"+newVersion);
		db.execSQL("DROP TABLE IF EXISTS "+FOOD_TABLE_NAME);
		onCreate(db);
		DBAction action = new DBAction();
		List<FoodEntity> list = action.initJson();
		action.insertFoodData(list,db);
	}
	
}