package com.cn.moduls;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.cn.R;
import com.util.ParameterCfg;

public class DeskOrderListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.desk_order_list);
		int height = getWindowManager().getDefaultDisplay().getHeight();
		int width = getWindowManager().getDefaultDisplay().getWidth();
		LinearLayout layout = (LinearLayout)findViewById(R.id.deskOrderListLayout);
		LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams((int)(width*.9), (int)(height*.9));
		layout.setLayoutParams(linearParams);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,ParameterCfg.ORDER_LIST);
		AutoCompleteTextView auto = (AutoCompleteTextView)findViewById(R.id.searchOrderId);
		auto.setAdapter(adapter);
	}

}
