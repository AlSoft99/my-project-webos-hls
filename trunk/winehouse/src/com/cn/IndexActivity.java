package com.cn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.moduls.AgencyActivity;
import com.cn.moduls.OrderActivity;
import com.cn.moduls.ScannerActivity;
import com.util.ParameterCfg;

public class IndexActivity extends Activity {

	int rowColumn = 3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		
		initView();
	}
	public void initView(){  
		LinearLayout ll = null;
		for (int i = 0; i < ParameterCfg.INDEX_ICON.length; i++) {
			if(i%rowColumn==0){
				ll = new LinearLayout(IndexActivity.this);
				ll.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
				ll.setOrientation(LinearLayout.HORIZONTAL);
				((LinearLayout)findViewById(R.id.indexBody)).addView(ll);
			}
			LinearLayout temp = getElementInLayout(i);
			ll.addView(temp);
		}
	}
	
	public LinearLayout getElementInLayout(final int i){
		LinearLayout temp = new LinearLayout(IndexActivity.this);
		temp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
		temp.setOrientation(LinearLayout.VERTICAL);
		temp.setGravity(Gravity.CENTER);
		
		ImageView iv = new ImageView(IndexActivity.this);
		iv.setImageResource(ParameterCfg.INDEX_ICON[i]);
		iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		iv.setPadding(5, 15, 5, 5);
		temp.addView(iv);
		
		TextView tv = new TextView(IndexActivity.this);
		tv.setText(ParameterCfg.INDEX_TITLE[i]);
		tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		temp.addView(tv);
		temp.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				enterModuals(ParameterCfg.INDEX_ICON[i]);
			}
		});
		return temp;
	}

	private void enterModuals(int modulsIcon){
		if(modulsIcon==R.drawable.agency){
			Intent i = new Intent(IndexActivity.this,AgencyActivity.class);
			this.startActivity(i);
		}else if(modulsIcon==R.drawable.brand){
			Intent i = new Intent(IndexActivity.this,OrderActivity.class);
			this.startActivity(i);
		}else if(modulsIcon==R.drawable.setting){
			Intent i = new Intent(IndexActivity.this,ScannerActivity.class);
			this.startActivity(i);
		}
	}
	public void logout(View v){
		IndexActivity.this.finish();
	}
}
