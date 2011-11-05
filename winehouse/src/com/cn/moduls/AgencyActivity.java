package com.cn.moduls;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.cn.R;
import com.control.DeskControl;
import com.util.Util;

public class AgencyActivity extends Activity {

	String[] floor = {"全部","一楼","二楼","三楼"};
	String dialogContext = "";
	Map<String,String[]> desk = new HashMap<String,String[]>();
	private Spinner spinner;
	private DeskControl dc;
	
	
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
			DeskControl tv = new DeskControl(this);
			LinearLayout.LayoutParams temp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100,1);
			temp.setMargins(5, 5, 5, 5);
			tv.setLayoutParams(temp);
			tv.setDeskNumber(desk[i]);
			tv.setGravity(Gravity.CENTER);
//			tv.setBackgroundResource(R.drawable.box_blue);
			tv.setBackgroundColor(Color.GRAY);
			tv.setOnClickListener(new openDeskModules());
			ll.addView(tv);
		}
    }
    
    class openDeskModules implements OnClickListener{
		@Override
		public void onClick(View v) {
			System.out.println(v);
			dc = (DeskControl)v;
			dialogContext = "是否确定开"+dc.getDeskNumber()+"台";
            showDialog(1);
		}
    }
    
    @Override  
    protected Dialog onCreateDialog(int id) {  
        switch (id) {  
        case 1:  
            return Util.dialogTwoBtn(AgencyActivity.this, "确定?", dialogContext,new ConfirmDialog(),new CancelDialog());  
  
        default:  
            break;  
        }  
        return super.onCreateDialog(id);  
    }
    
    class ConfirmDialog implements DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
			ProgressDialog pd=new ProgressDialog(AgencyActivity.this);  
	        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
	        pd.setMessage("数据载入中，请稍候！");  
	        //显示进度条  
	        pd.show();
	        pd.hide();
	        Intent i = new Intent(AgencyActivity.this,DeskOrderActivity.class);
	        i.putExtra("number", dc.getDeskNumber());
	        i.putExtra("order", dc.getOrder());
	        i.putExtra("owner", dc.getDeskOwner());
	        AgencyActivity.this.startActivity(i);
		}
    }
    class CancelDialog implements DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
		}
    }
    
    public void back(View v){
    	AgencyActivity.this.finish();
    }
}
