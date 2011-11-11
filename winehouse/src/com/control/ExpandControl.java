package com.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;

import com.cn.R;

public class ExpandControl extends LinearLayout{
	public static String GROUP = "group";
	public static String CHILD = "child";
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
		adapter = new SimpleExpandableListAdapter(context, groups, R.layout.expand_control_group, new String[]{GROUP}, new int[]{R.id.groupId},childs, R.layout.expand_control_child, new String[]{CHILD}, new int[]{R.id.childId});
		control.setAdapter(adapter);
	}
	/**
	 * 初始化数据
	 * @param group	组名
	 * @param child 成员名. for example: new String[]{'child1','child2'}
	 */
	public void initExpandData(String group, String[] child){
		Map<String,String> groupMap = new HashMap<String, String>();
		groupMap.put(GROUP, group);
		groups.add(groupMap);
		List<Map<String,String>> childList = new ArrayList<Map<String,String>>();
		for (int i = 0; i < child.length; i++) {
			Map<String,String> tmp = new HashMap<String, String>();
			tmp.put(CHILD, child[i]);
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
