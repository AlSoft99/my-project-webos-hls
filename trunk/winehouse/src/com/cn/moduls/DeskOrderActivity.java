package com.cn.moduls;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cn.R;

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
	}

	private void getActivityData(){
		Bundle bun = getIntent().getExtras();
		number = (String)bun.get("number");
		order = (String)bun.get("order");
		owner = (String)bun.get("owner");
	}
	private void setActivityData(){
		((TextView)findViewById(R.id.deskNumber)).setText(number);
		((TextView)findViewById(R.id.owner)).setText(owner);
	}
}
