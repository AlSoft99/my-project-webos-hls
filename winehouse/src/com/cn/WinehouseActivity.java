package com.cn;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sqlite.DatabaseHelper;
import com.util.ParameterCfg;
import com.util.ProgressActivity;

public class WinehouseActivity extends Activity {
	int version = 1;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        updateDB();
        Button btn1 = (Button)findViewById(R.id.login);
        btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent1 = new Intent(WinehouseActivity.this,ProgressActivity.class);
				WinehouseActivity.this.startActivityForResult(intent1, 10);
				new Thread(new WinehouseActivity.Test()).start();
			}
		});
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//String name = data.getExtras().get("name").toString();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	class Test implements Runnable{
		public void run() {
			try {
				Thread.sleep(2000);
				WinehouseActivity.this.finishActivity(10);
				Intent i = new Intent(WinehouseActivity.this,IndexActivity.class);
				WinehouseActivity.this.startActivity(i);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void updateDB(){
		DatabaseHelper database = new DatabaseHelper(this,version);
        SQLiteDatabase s = database.getReadableDatabase(); 
        Cursor cursor = s.query(DatabaseHelper.FOOD_TABLE_NAME, null, null, null, null, null, null);
        System.out.println("=============================================");
        while(cursor.moveToNext()){  
            String id = cursor.getString(cursor.getColumnIndex("id"));  
            String name = cursor.getString(cursor.getColumnIndex("name"));  
            String type = cursor.getString(cursor.getColumnIndex("type"));  
            String price = cursor.getString(cursor.getColumnIndex("price"));  
            System.out.println("===========:"+id+"|"+name+"|"+type+"|"+price);
            ParameterCfg.ORDER_LIST.add(id+"|"+name+"|"+type+"|"+price);
        } 
        cursor.close();
        s.close();
        database.close();
	}
	
}