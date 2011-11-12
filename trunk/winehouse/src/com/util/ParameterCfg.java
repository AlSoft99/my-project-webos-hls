package com.util;

import java.util.ArrayList;
import java.util.List;

import com.cn.R;

public class ParameterCfg {
	public static String ORDER_SPLIT = "---";
	public static int PROGRESS_BAR = 5;
	public static int[] INDEX_ICON = {R.drawable.agency,R.drawable.brand,R.drawable.light,R.drawable.manual,R.drawable.onstar,R.drawable.park,R.drawable.rescue,R.drawable.service,R.drawable.setting};
	public static String[] INDEX_TITLE = {"开台","点单","light","manual","onstar","park","rescue","service","扫描"};
	public static List<String> ORDER_LIST = new ArrayList<String>();
	
	public static int EXPAND_GROUP = 1;
	public static int EXPAND_CHILD = 2;
	//数据库操作
	public static final String DATABASE_NAME = "com.winehouse";
	public static final int DATABASE_VERSION = 1;
	public static final String FOOD_TABLE_NAME = "FOOD_INFO";
	public static final String FOOD_TYPE_TABLE_NAME = "FOOD_TYPE";
}
