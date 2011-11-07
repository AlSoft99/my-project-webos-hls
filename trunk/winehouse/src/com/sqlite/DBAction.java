package com.sqlite;

import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.entity.FoodEntity;
import com.google.gson.Gson;

public class DBAction {
	private String foodData = "";
	
	public DBAction(){
		foodData = "[" +
				"{'type':'冷菜','id':'001','name':'豆腐1','price':10},{'type':'冷菜','id':'002','name':'豆腐2','price':10},{'type':'冷菜','id':'003','name':'豆腐3','price':10},{'type':'冷菜','id':'004','name':'豆腐4','price':10},{'type':'冷菜','id':'005','name':'豆腐5','price':10}," +
				"{'type':'热菜','id':'101','name':'豆腐11','price':10},{'type':'热菜','id':'102','name':'豆腐12','price':10},{'type':'热菜','id':'103','name':'豆腐13','price':10},{'type':'热菜','id':'104','name':'豆腐14','price':10},{'type':'热菜','id':'105','name':'豆腐15','price':10}," +
				"{'type':'酒水','id':'201','name':'豆腐21','price':10},{'type':'酒水','id':'202','name':'豆腐22','price':10},{'type':'酒水','id':'203','name':'豆腐23','price':10},{'type':'酒水','id':'204','name':'豆腐24','price':10},{'type':'酒水','id':'205','name':'豆腐25','price':10}" +
				"]";
	}
	public List<FoodEntity> initJson(){
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<FoodEntity>>() {}.getType();
		Gson gson = new Gson();
		return gson.fromJson(foodData, type);
	}
	public void insertFoodData(List<FoodEntity> list,SQLiteDatabase db){
		String nullColumnHack = "no";
		for (FoodEntity foodEntity : list) {
			ContentValues values = new ContentValues();
			values.put("id", foodEntity.getId());
			values.put("name", foodEntity.getName());
			values.put("type", foodEntity.getType());
			values.put("price", foodEntity.getPrice());
			long id = db.insert(DatabaseHelper.FOOD_TABLE_NAME,
			nullColumnHack, values);
			System.out.println("id:"+id);
		}

	}
}
