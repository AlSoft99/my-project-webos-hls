package com.control;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.R;

public class DeskControl extends LinearLayout {

	TextView deskNumber;
	TextView order;
	TextView deskOwner;
	
	public DeskControl(Context context) {
		this(context, null);
	}

	public DeskControl(Context context, AttributeSet attrs) {
		super(context, attrs);    
        // 导入布局    
        LayoutInflater.from(context).inflate(R.layout.desk_control, this, true);    
        deskNumber = (TextView) findViewById(R.id.deskNumber); 
        order = (TextView) findViewById(R.id.order); 
        deskOwner = (TextView) findViewById(R.id.deskOwner); 
	}
	
	public void setDeskNumber(String number){
		deskNumber.setText(number);
	}
	
	public void setOrder(String value){
		order.setText(value);
	}
	public void setDeskOwner(String value){
		deskOwner.setText(value);
	}
	
	public String getDeskNumber(){
		return deskNumber.getText().toString();
	}
	
	public String getOrder(){
		return order.getText().toString();
	}
	public String getDeskOwner(){
		return deskOwner.getText().toString();
	}
	
	public void setDeskColor(String colorValue){
		deskNumber.setBackgroundColor(Color.parseColor(colorValue));
	}
}
