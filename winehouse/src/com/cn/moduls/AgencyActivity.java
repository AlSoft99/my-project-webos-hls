package com.cn.moduls;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.cn.R;
import com.control.DeskControl;
import com.entity.DeskEntity;
import com.google.gson.Gson;
import com.util.Util;

public class AgencyActivity extends Activity {

	int deskHeight = 120;
	String[] floor = {"全部","一楼","二楼","三楼"};
	String[] order = {"全部","未点单","已点单"};
	String dialogContext = "";
//	Map<String,String[]> desk = new HashMap<String,String[]>();
	List<List<DeskEntity>> deskList = new ArrayList<List<DeskEntity>>();
	private Spinner spinner;
	private DeskControl dc;
	private ProgressDialog pd;
	
	
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
        System.out.println(deskList); 
		
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
			dialogContext = "是否确定开台";
            showDialog(1);
		}
    }
    
    @Override  
    protected Dialog onCreateDialog(int id) {  
    	System.out.println("id:"+id);
        switch (id) {  
        case 1:  
            return Util.dialogTwoBtn(AgencyActivity.this, "确定?", dialogContext,new ConfirmDialog(),new CancelDialog());  
            
        case 2:  
            return Util.dialogOneBtn(AgencyActivity.this, "对话框", dialogContext,new SuccessConfirmDialog());  
            
        default:  
            break;  
        }  
        return super.onCreateDialog(id);  
    }
    
    class ConfirmDialog implements DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if(pd==null){
				pd=new ProgressDialog(AgencyActivity.this);  
			}
	        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
	        pd.setMessage("正在开启["+dc.getDeskNumber()+"]台，请稍候！");  
	        //显示进度条  
	        pd.show();
	        pd.hide();
	        Toast.makeText(AgencyActivity.this, dc.getDeskNumber()+"台已成功开台!", Toast.LENGTH_LONG).show();
	        //showDialog(2);
		}
    }
    class SuccessConfirmDialog implements DialogInterface.OnClickListener{
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
    	if (pd!=null) {
    		pd.dismiss();
		}
    	AgencyActivity.this.removeDialog(1);
    	AgencyActivity.this.removeDialog(2);
    	AgencyActivity.this.finish();
    }
    
    @Override
	protected void onDestroy() {
    	if (pd!=null) {
    		pd.dismiss();
		}
    	AgencyActivity.this.removeDialog(1);
    	AgencyActivity.this.removeDialog(2);
		super.onDestroy();
	}

	public static void main(String[] args) {
		/*String json = "[" +
				"[{'number':'101','order':'已点菜','owner':'官瑞林'},{'number':'102','order':'已点菜','owner':'官瑞林'},{'number':'103','order':'已点菜','owner':'官瑞林'},{'number':'104','order':'已点菜','owner':'官瑞林'},{'number':'105','order':'已点菜','owner':'官瑞林'},{'number':'106','order':'已点菜','owner':'官瑞林'},{'number':'107','order':'已点菜','owner':'官瑞林'},{'number':'108','order':'已点菜','owner':'官瑞林'},{'number':'109','order':'已点菜','owner':'官瑞林'}]" +
				"[{'number':'201','order':'已点菜','owner':'官瑞林'},{'number':'202','order':'已点菜','owner':'官瑞林'},{'number':'203','order':'已点菜','owner':'官瑞林'},{'number':'204','order':'已点菜','owner':'官瑞林'},{'number':'205','order':'已点菜','owner':'官瑞林'},{'number':'206','order':'已点菜','owner':'官瑞林'},{'number':'207','order':'已点菜','owner':'官瑞林'},{'number':'208','order':'已点菜','owner':'官瑞林'},{'number':'209','order':'已点菜','owner':'官瑞林'}]" +
				"[{'number':'301','order':'已点菜','owner':'官瑞林'},{'number':'302','order':'已点菜','owner':'官瑞林'},{'number':'303','order':'已点菜','owner':'官瑞林'},{'number':'304','order':'已点菜','owner':'官瑞林'},{'number':'305','order':'已点菜','owner':'官瑞林'},{'number':'306','order':'已点菜','owner':'官瑞林'},{'number':'307','order':'已点菜','owner':'官瑞林'},{'number':'308','order':'已点菜','owner':'官瑞林'},{'number':'309','order':'已点菜','owner':'官瑞林'}]" +
				"]";*/
    	String json = "[{'number':'101','order':'已点菜','owner':'官瑞林'},{'number':'102','order':'已点菜','owner':'官瑞林'},{'number':'103','order':'已点菜','owner':'官瑞林'},{'number':'104','order':'已点菜','owner':'官瑞林'},{'number':'105','order':'已点菜','owner':'官瑞林'},{'number':'106','order':'已点菜','owner':'官瑞林'},{'number':'107','order':'已点菜','owner':'官瑞林'},{'number':'108','order':'已点菜','owner':'官瑞林'},{'number':'109','order':'已点菜','owner':'官瑞林'}]";
		Gson gson = new Gson();
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<DeskEntity>>() {}.getType();
		List<DeskEntity> testBeanListFromJson = gson.fromJson(json, type);    
        System.out.println(testBeanListFromJson);  
	}
}
