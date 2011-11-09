package com.cn.moduls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.cn.R;
import com.util.ParameterCfg;

public class DeskOrderActivity extends Activity {

	private String number;
	private String order;
	private String owner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.desk_order);
		getActivityData();
		setActivityData();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,ParameterCfg.ORDER_LIST);
		AutoCompleteTextView auto = (AutoCompleteTextView)findViewById(R.id.searchOrderId);
		auto.setAdapter(adapter);
	}

	private void getActivityData(){
		Bundle bun = getIntent().getExtras();
		number = (String)bun.get("number");
		order = (String)bun.get("order");
		owner = (String)bun.get("owner");
	}
	private void setActivityData(){
//		((TextView)findViewById(R.id.deskNumber)).setText(number);
//		((TextView)findViewById(R.id.owner)).setText(owner);
	}
	public void back(View v){
		DeskOrderActivity.this.finish();
    }
	public void orderListOnclick(View v){
		Intent intent = new Intent(DeskOrderActivity.this, DeskOrderListActivity.class);
		startActivity(intent);
	}
}
