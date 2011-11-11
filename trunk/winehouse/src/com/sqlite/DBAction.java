package com.sqlite;

import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.entity.FoodEntity;
import com.entity.FoodTypeEntity;
import com.google.gson.Gson;
import com.util.ParameterCfg;

public class DBAction {
	private String foodData = "";
	private String foodType = "";
	public DBAction(){
		foodData = "[" +
				"{'type':'冷菜','id':'001','name':'豆腐1','price':10},{'type':'冷菜','id':'002','name':'豆腐2','price':10},{'type':'冷菜','id':'003','name':'豆腐3','price':10},{'type':'冷菜','id':'004','name':'豆腐4','price':10},{'type':'冷菜','id':'005','name':'豆腐5','price':10},{'type':'冷菜','id':'006','name':'豆腐6','price':10},{'type':'冷菜','id':'007','name':'豆腐7','price':10},{'type':'冷菜','id':'008','name':'豆腐8','price':10},{'type':'冷菜','id':'009','name':'豆腐9','price':10},{'type':'冷菜','id':'010','name':'豆腐10','price':10}," +
				"{'type':'热菜','id':'101','name':'豆腐11','price':10},{'type':'热菜','id':'102','name':'豆腐12','price':10},{'type':'热菜','id':'103','name':'豆腐13','price':10},{'type':'热菜','id':'104','name':'豆腐14','price':10},{'type':'热菜','id':'105','name':'豆腐15','price':10}," +
				"{'type':'酒水','id':'201','name':'豆腐21','price':10},{'type':'酒水','id':'202','name':'豆腐22','price':10},{'type':'酒水','id':'203','name':'豆腐23','price':10},{'type':'酒水','id':'204','name':'豆腐24','price':10},{'type':'酒水','id':'205','name':'豆腐25','price':10}" +
				"]";
		foodType = "[{'no':'1','type':'冷菜'},{'no':'2','type':'热菜'},{'no':'3','type':'酒水'}]";
	}
	public List<FoodEntity> initFoodInfoJson(){
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<FoodEntity>>() {}.getType();
		Gson gson = new Gson();
		return gson.fromJson(foodData, type);
	}
	public List<FoodTypeEntity> initFoodTypeJson(){
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<FoodTypeEntity>>() {}.getType();
		Gson gson = new Gson();
		return gson.fromJson(foodType, type);
	}
	public void insertFoodData(List<FoodEntity> list,SQLiteDatabase db){
		String nullColumnHack = "no";
		for (FoodEntity foodEntity : list) {
			ContentValues values = new ContentValues();
			values.put("id", foodEntity.getId());
			values.put("name", foodEntity.getName());
			values.put("type", foodEntity.getType());
			values.put("price", foodEntity.getPrice());
			long id = db.insert(ParameterCfg.FOOD_TABLE_NAME, nullColumnHack, values);
			System.out.println("FoodEntity--id:"+id);
		}

	}
	public void insertFoodTypeData(List<FoodTypeEntity> list,SQLiteDatabase db){
		String nullColumnHack = "no";
		for (FoodTypeEntity foodEntity : list) {
			ContentValues values = new ContentValues();
			values.put("type", foodEntity.getType());
			long id = db.insert(ParameterCfg.FOOD_TYPE_TABLE_NAME, nullColumnHack, values);
			System.out.println("FoodTypeEntity---id:"+id);
		}

	}
}
