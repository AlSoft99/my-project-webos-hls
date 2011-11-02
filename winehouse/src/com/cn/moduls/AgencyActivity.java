package com.cn.moduls;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.cn.R;

public class AgencyActivity extends Activity {

	String[] floor = {"全部","一楼","二楼","三楼"};
	Map<String,String[]> desk = new HashMap<String,String[]>();
	private Spinner spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moduls_agency);
		initData();
	}
	
	public void initData(){
		//测试数据
		String floor1[] = {"101","102","104","105","106","107","109","111","112","113","115"};
		String floor2[] = {"201","202","204","205","206","207","209","211","212","213","215"};
		String floor3[] = {"301","302","304","305","306","307","309","311","312","313","315"};
		desk.put("一楼", floor1);
		desk.put("二楼", floor2);
		desk.put("三楼", floor3);
		
		initSpinner();
//		initDesk();
	}
	public void initSpinner(){
		spinner = (Spinner) findViewById(R.id.floor_spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,floor); 
		//设置下拉列表的风格  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        //将adapter 添加到spinner中  
        spinner.setAdapter(adapter);  
        //添加事件Spinner事件监听    
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());  
        //设置默认值  
        spinner.setVisibility(View.VISIBLE); 
	}
	//使用数组形式操作  
    class SpinnerSelectedListener implements OnItemSelectedListener{  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int index,  
                long arg3) { 
        	System.out.println("index:"+index);
        	((LinearLayout)findViewById(R.id.floor_desk)).removeAllViews();
            if(index==0){
            	initDesk();
            }else{
            	createDesk(desk.get(floor[index]));
            }
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }
    
    public void initDesk(){
    	for (String key : desk.keySet()) {
			String[] floor = desk.get(key);
			createDesk(floor);
		}
    }
    private void createDesk(String[] desk){
    	LinearLayout ll = null;
    	for (int i = 0; i < desk.length; i++) {
			if (i%3==0) {
				ll = new LinearLayout(this);
				ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
				((LinearLayout)findViewById(R.id.floor_desk)).addView(ll);
			}
			Button tv = new Button(this);
			LinearLayout.LayoutParams temp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100,1);
			temp.setMargins(5, 5, 5, 5);
			tv.setLayoutParams(temp);
			tv.setText(desk[i]);
			tv.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
			ll.addView(tv);
		}
    }

    public void back(View v){
    	AgencyActivity.this.finish();
    }
}
