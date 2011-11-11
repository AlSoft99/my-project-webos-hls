package com.sqlite;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.entity.FoodEntity;
import com.entity.FoodTypeEntity;
import com.util.ParameterCfg;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context) {
		super(context, ParameterCfg.DATABASE_NAME, null, ParameterCfg.DATABASE_VERSION);
	}
	public DatabaseHelper(Context context,int version) {
		super(context, ParameterCfg.DATABASE_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + ParameterCfg.FOOD_TABLE_NAME + " (no integer primary key autoincrement, type varchar(20), id varchar(20), name varchar(40), price int);");
		db.execSQL("CREATE TABLE " + ParameterCfg.FOOD_TYPE_TABLE_NAME + " (no integer primary key autoincrement, type varchar(20));");
		onUpgrade(db, ParameterCfg.DATABASE_VERSION, ParameterCfg.DATABASE_VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		updateFoodInfo(db);
		updateFoodType(db);
	}
	public void updateFoodInfo(SQLiteDatabase db){
		db.delete(ParameterCfg.FOOD_TABLE_NAME, null, null);
		DBAction action = new DBAction();
		List<FoodEntity> list = action.initFoodInfoJson();
		action.insertFoodData(list,db);
	}
	
	public void updateFoodType(SQLiteDatabase db){
		db.delete(ParameterCfg.FOOD_TYPE_TABLE_NAME, null, null);
		DBAction action = new DBAction();
		List<FoodTypeEntity> list = action.initFoodTypeJson();
		action.insertFoodTypeData(list,db);
	}
}