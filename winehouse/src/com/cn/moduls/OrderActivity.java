package com.cn.moduls;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.entity.DeskEntity;
import com.google.gson.Gson;
import com.util.Util;

public class OrderActivity extends Activity {

	int deskHeight = 120;
	String[] floor = {"全部","一楼","二楼","三楼"};
	String[] order = {"全部","未点单","已点单"};
	String dialogContext = "";
//	Map<String,String[]> desk = new HashMap<String,String[]>();
	List<List<DeskEntity>> deskList = new ArrayList<List<DeskEntity>>();
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
		String json = "[" +
				"[{'number':'101','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'102','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'103','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'104','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'105','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'106','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'107','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'108','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'109','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'}]," +
				"[{'number':'201','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'202','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'203','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'204','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'205','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'206','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'207','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'208','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'209','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'}]," +
				"[{'number':'301','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'302','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'303','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'304','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'305','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'306','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'307','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'308','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'},{'number':'309','order':'已点菜','owner':'官瑞林','numberColor':'#404040','orderColor':'#FFFFFF','ownerColor':'#DFD1CB'}]" +
				"]";
		Gson gson = new Gson();
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<List<DeskEntity>>>() {}.getType();
		deskList = gson.fromJson(json, type);    
		
		initSpinner();
		initOrderSpinner();
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
	public void initOrderSpinner(){
		spinner = (Spinner) findViewById(R.id.order_spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,order); 
		//设置下拉列表的风格  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        //将adapter 添加到spinner中  
        spinner.setAdapter(adapter);  
        //添加事件Spinner事件监听    
        spinner.setOnItemSelectedListener(new OrderSpinnerSelectedListener());  
        //设置默认值  
        spinner.setVisibility(View.VISIBLE); 
	}
	//使用数组形式操作  
    class SpinnerSelectedListener implements OnItemSelectedListener{  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int index,  
                long arg3) { 
        	((LinearLayout)findViewById(R.id.floor_desk)).removeAllViews();
            if(index==0){
            	initDesk();
            }else{
            	createDesk(deskList.get(index-1));
            }
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }
  //使用数组形式操作  
    class OrderSpinnerSelectedListener implements OnItemSelectedListener{  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int index,  
                long arg3) { 
        	((LinearLayout)findViewById(R.id.floor_desk)).removeAllViews();
            if(index==0){
            	initDesk();
            }else{
            	createDesk(deskList.get(index-1));
            }
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }
    
    public void initDesk(){
    	for (int i = 0 ; i < deskList.size();i++) {
			List<DeskEntity> floor = deskList.get(i);
			createDesk(floor);
		}
    }
    private void createDesk(List<DeskEntity> desk){
    	LinearLayout ll = null;
    	for (int i = 0; i < desk.size(); i++) {
			if (i%3==0) {
				ll = new LinearLayout(this);
				ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
				((LinearLayout)findViewById(R.id.floor_desk)).addView(ll);
			}
			DeskControl tv = new DeskControl(this);
			LinearLayout.LayoutParams temp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, deskHeight,1);
			temp.setMargins(5, 5, 5, 5);
			tv.setLayoutParams(temp);
			tv.setDeskNumber(desk.get(i).getNumber());
			tv.setGravity(Gravity.CENTER);
			tv.setBackgroundResource(R.drawable.box_blue);
//			tv.setBackgroundColor(Color.GRAY);
			tv.setOnClickListener(new openDeskModules());
			tv.setOrder(desk.get(i).getOrder());
			tv.setDeskOwner(desk.get(i).getOwner());
			tv.setDeskColor(desk.get(i).getNumberColor());
			tv.setOrderColor(desk.get(i).getOrderColor());
			tv.setDeskOwnerColor(desk.get(i).getNumberColor());
			ll.addView(tv);
		}
    }
    
    class openDeskModules implements OnClickListener{
		@Override
		public void onClick(View v) {
			dc = (DeskControl)v;
			dialogContext = "是否确定开"+dc.getDeskNumber()+"台";
			ProgressDialog pd=new ProgressDialog(OrderActivity.this);  
	        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
	        pd.setMessage("数据载入中，请稍候！");  
	        //显示进度条  
	        pd.show();
	        pd.hide();
	        Intent i = new Intent(OrderActivity.this,DeskOrderActivity.class);
	        i.putExtra("number", dc.getDeskNumber());
	        i.putExtra("order", dc.getOrder());
	        i.putExtra("owner", dc.getDeskOwner());
	        OrderActivity.this.startActivity(i);
		}
    }
    
    @Override  
    protected Dialog onCreateDialog(int id) {  
        switch (id) {  
        case 1:  
            return Util.dialogTwoBtn(OrderActivity.this, "确定?", dialogContext,new ConfirmDialog(),new CancelDialog());  
  
        default:  
            break;  
        }  
        return super.onCreateDialog(id);  
    }
    
    class ConfirmDialog implements DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
		}
    }
    class CancelDialog implements DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
		}
    }
    
    public void back(View v){
    	OrderActivity.this.finish();
    }
}
