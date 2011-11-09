package com.cn.moduls;

import android.app.Activity;
import android.content.Intent;
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
import com.util.ParameterCfg;

public class DeskOrderActivity extends Activity {

	private String number;
	private String order;
	private String owner;
	private PopupWindow mPopupWindow;
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
	public void orderListOnclick(View v){
		Intent intent = new Intent(DeskOrderActivity.this, DeskOrderListActivity.class);
		startActivity(intent);
	}
}
