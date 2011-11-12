package com.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.cn.R;
import com.util.ParameterCfg;

public class ExpandControl extends LinearLayout{
	public static String GROUP = "group";
	public static String GROUP_POSITION = "groupPosition";
	public static String CHILD = "child";
	public static String CHILD_POSITION = "childPosition";
	private ExpandableListView control;  
	private List<Map<String,String>> groups = new ArrayList<Map<String,String>>();;  
	private List<List<Map<String,String>>> childs = new ArrayList<List<Map<String,String>>>();;  
	private SimpleExpandableListAdapter adapter; 
	private Context context;
	
	public ExpandControl(Context context) {
		this(context, null);
	}

	public ExpandControl(Context context, AttributeSet attrs) {
		super(context, attrs);    
		this.context = context;
        // 导入布局    
        LayoutInflater.from(context).inflate(R.layout.expand_control, this, true);    
        initExpandLayout();
	}
	
	private void initExpandLayout(){
		control = (ExpandableListView)findViewById(R.id.expandControl);
		adapter = new SimpleExpandableListAdapter(context, groups, R.layout.expand_control_group, new String[]{GROUP,GROUP_POSITION}, new int[]{R.id.groupId,R.id.groupPosition},childs, R.layout.expand_control_child, new String[]{CHILD,CHILD_POSITION}, new int[]{R.id.childId,R.id.childPosition});
		control.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		control.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int arg2, long arg3) {
				System.out.println("==============");
				System.out.println(adapterView);
				System.out.println(view);
				System.out.println(arg2);
				System.out.println(arg3);
				LinearLayout child = (LinearLayout)view;
				System.out.println("control.tag();:"+view.getTag());
				System.out.println(((TextView)view.findViewById(R.id.childPosition)).getText());
				System.out.println("==============child.getTag():"+child.getTag(ParameterCfg.EXPAND_GROUP));
				return false;
			}
			
		});
	}
	public void setOnItemLongClickListener(OnItemLongClickListener listener){
		control.setOnItemLongClickListener(listener);
	}
	/**
	 * 初始化数据
	 * @param group	组名
	 * @param child 成员名. for example: new String[]{'child1','child2'}
	 */
	public void initExpandData(String group, String[] child){
		Map<String,String> groupMap = new HashMap<String, String>();
		int groupPosition = groups.size();
		groupMap.put(GROUP, group);
		groupMap.put(GROUP_POSITION, groupPosition+"");
		groups.add(groupMap);
		List<Map<String,String>> childList = new ArrayList<Map<String,String>>();
		for (int i = 0; i < child.length; i++) {
			Map<String,String> tmp = new HashMap<String, String>();
			tmp.put(CHILD, child[i]);
			tmp.put(CHILD_POSITION, groupPosition+","+childList.size());
			childList.add(tmp);
		}
		childs.add(childList);
	}
	/**
	 * 添加一组数据. 如果group已存在, 将不会添加新的组
	 * @param group
	 * @param child
	 */
	public void addExpandData(String group,String[] child){
		int groupPosition = -1;
		for (int i = 0; i < groups.size(); i++) {
			Map<String,String> map = groups.get(i);
			if(map.containsValue(group)){
				groupPosition = i;
				break;
			}
		}
		for (int i = 0; i < child.length; i++) {
			addExpandData(group,child[i],groupPosition);
		}
	}
	/**
	 * 添加一个数据. 如果group已存在, 将不会添加新的组
	 * @param group
	 * @param child
	 */
	public void addExpandData(String group,String child){
		int groupPosition = -1;
		for (int i = 0; i < groups.size(); i++) {
			Map<String,String> map = groups.get(i);
			if(map.containsValue(group)){
				groupPosition = i;
				break;
			}
		}
		addExpandData(group,child,groupPosition);
	}
	public void addExpandData(String group,String child,int position){
		if (position==-1) {
			initExpandData(group,new String[]{child});
		}else{
			List<Map<String, String>> childList = childs.get(position);
			Map<String,String> tmp = new HashMap<String, String>();
			tmp.put(CHILD, child);
			tmp.put(CHILD_POSITION, position+","+childList.size());
			childList.add(tmp);
		}
	}
	/**
	 * 提交数据变更
	 */
	public void submit(){
		adapter.notifyDataSetChanged();
	}
	public void expandAll(){
		for (int i = 0; i < adapter.getGroupCount(); i++) {
			control.expandGroup(i);
		}
	}
}
