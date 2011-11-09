package com.sqlite;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.entity.FoodEntity;
import com.entity.FoodTypeEntity;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "com.winehouse";
	private static final int DATABASE_VERSION = 1;
	public static final String FOOD_TABLE_NAME = "FOOD_INFO";
	public static final String FOOD_TYPE_TABLE_NAME = "FOOD_TYPE";

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
		db.execSQL("CREATE TABLE " + FOOD_TYPE_TABLE_NAME
				+ " (no integer primary key autoincrement, type varchar(20));");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("oldVersion:"+oldVersion+"   newVersion:"+newVersion);
		
	}
	public void updateFoodInfo(SQLiteDatabase db){
		db.execSQL("DELETE FROM "+FOOD_TABLE_NAME);
//		onCreate(db);
		DBAction action = new DBAction();
		List<FoodEntity> list = action.initFoodInfoJson();
		action.insertFoodData(list,db);
	}
	
	public void updateFoodType(SQLiteDatabase db){
		db.execSQL("DELETE FROM "+FOOD_TYPE_TABLE_NAME);
//		onCreate(db);
		DBAction action = new DBAction();
		List<FoodTypeEntity> list = action.initFoodTypeJson();
		action.insertFoodTypeData(list,db);
	}
}