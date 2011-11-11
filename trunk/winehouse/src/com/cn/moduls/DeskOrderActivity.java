package com.cn.moduls;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cn.R;
import com.control.ExpandControl;
import com.sqlite.DatabaseHelper;
import com.util.ParameterCfg;

public class DeskOrderActivity extends Activity {

	private String number;
	private String order;
	private String owner;
	private PopupWindow mPopupWindow;
	ExpandControl orderList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.desk_order);
		getActivityData();
		setActivityData();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,ParameterCfg.ORDER_LIST);
		AutoCompleteTextView auto = (AutoCompleteTextView)findViewById(R.id.searchOrderId);
		auto.setAdapter(adapter);
		orderList = (ExpandControl)findViewById(R.id.orderList);
		
	}

	private void getActivityData(){
		Bundle bun = getIntent().getExtras();
		number = (String)bun.get("number");
		order = (String)bun.get("order");
		owner = (String)bun.get("owner");
	}
	private void setActivityData(){
//		
	}
	public void back(View v){
		DeskOrderActivity.this.finish();
    }
	public void deskOrderDetailOnclick(View v){
		View popupWindow_view = getLayoutInflater().inflate(R.layout.desk_order_popup, null,false);  
		mPopupWindow = new PopupWindow(popupWindow_view, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT,true);  
		mPopupWindow.showAtLocation(findViewById(R.id.deskOrder),Gravity.RIGHT | Gravity.BOTTOM, 0, 0);  
        mPopupWindow.update();
        ((TextView)popupWindow_view.findViewById(R.id.deskNumber)).setText(number);
		((TextView)popupWindow_view.findViewById(R.id.owner)).setText(owner);
        Button button = (Button)popupWindow_view.findViewById(R.id.back);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
	}
	public void appendOrderOnClick(View v){
		AutoCompleteTextView tv = (AutoCompleteTextView)findViewById(R.id.searchOrderId);
		String autoValue = tv.getText().toString();
		String value = autoValue.split(ParameterCfg.ORDER_SPLIT)[0];
		DatabaseHelper database = new DatabaseHelper(this,ParameterCfg.DATABASE_VERSION);
        SQLiteDatabase s = database.getReadableDatabase(); 
        Cursor cursor = s.query(ParameterCfg.FOOD_TABLE_NAME, null, "id=?", new String[]{value}, null, null, null);
        String id="";
        String name="";
        String type="";
        String price="";
        if(cursor.moveToNext()){  
            id = cursor.getString(cursor.getColumnIndex("id"));  
            name = cursor.getString(cursor.getColumnIndex("name"));  
            type = cursor.getString(cursor.getColumnIndex("type"));  
            price = cursor.getString(cursor.getColumnIndex("price"))+"元";  
        } 
        if(!id.equals("")){
        	orderList.addExpandData(type, id+"--------"+name);
    		orderList.submit();
    		orderList.expandAll();
    		tv.getText().clear();
        }
        cursor.close();
        s.close();
        database.close();
	}
	public void orderListOnclick(View v){
		Intent intent = new Intent(DeskOrderActivity.this, DeskOrderListActivity.class);
		startActivity(intent);
	}
}
