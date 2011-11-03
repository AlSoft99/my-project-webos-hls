package com.hrm.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hrm.dao.RoleInfoDAO;
import com.hrm.entity.DayClearChk;
import com.hrm.util.ClsFactory;
import com.hrm.util.Constant;
import com.hrm.util.StringUtil;

public class InitData {
	//Json格式的Map, 会加"
	private static HashMap<String, List<List<String>>> initJsonMap = new HashMap<String, List<List<String>>>();
	//字典表
	private static HashMap<String,String> initConstant = new HashMap<String,String>();
	//存放改变的Map
	private static HashMap<String, Map<String,List<List<String>>>> initChangeMap = new HashMap<String, Map<String,List<List<String>>>>();
	//存放OUT_USER_LIST表结构,key=outuser+tag,value=userlist
	private static HashMap<String, String> outuserMap = new HashMap<String, String>();
	private RoleInfoDAO roleInfoDAO;
	public RoleInfoDAO getRoleInfoDAO() {
		return roleInfoDAO;
	}

	public void setRoleInfoDAO(RoleInfoDAO roleInfoDAO) {
		this.roleInfoDAO = roleInfoDAO;
	}
	public void init(){
		com.hrm.util.ClsFactory.newInstance().info("开始初始化InitData类中的数据");
		initJsonRoleInfo();
		initJsonUserInfo();
		initJsonStoreList();
		initJsonStoreTypeInfo();
		initJsonChangeUserInfo();
		initJsonChangeStoreTypeInfo();
		initConstantInfo();
		initOutuserInfo();
		com.hrm.util.ClsFactory.newInstance().info("InitData初始化结束");
		
	}
	public void initJsonRoleInfo(){
		String hql = "select roleCode,roleName from RoleInfo";
		List<List<String>> roleInfo = roleInfoDAO.findByHql(hql);
		initJsonMap.put(Constant.ROLE_INFO, roleInfo);
	}
	public void initJsonUserInfo(){
		String hql = "select userId,userName,roleInfo.roleCode from UserInfo order by roleInfo.roleCode";
		List<List<String>> roleInfo = roleInfoDAO.findByHql(hql);
		initJsonMap.put(Constant.USER_INFO, roleInfo);
	}
	public void initJsonStoreTypeInfo(){
		String hql = "select id,typename from StoreType order by id";
		List<List<String>> roleInfo = roleInfoDAO.findByHql(hql);
		initJsonMap.put(Constant.STORE_TYPE, roleInfo);
	}
	public void initJsonStoreList(){
		String hql = "select a.id,a.goodsname,a.typeid,c.id.outuser from StoreList a,StoreType b,OutUserList c where c.id.tag='"+Constant.TAG[1]+"' and a.typeid=b.id and c.id.userlist=b.storelist order by typeid";
		List<List<String>> roleInfo = roleInfoDAO.findByHql(hql);
		initJsonMap.put(Constant.STORE_LIST, roleInfo);
	}
	public void initConstantInfo(){
		String hql = "select typeid,paramscode,paramsname from ParamsList";
		List<List<String>> roleInfo = roleInfoDAO.findByHql(hql);
		for(List<String> params: roleInfo){
			initConstant.put(params.get(0).replaceAll("\"", "")+"_"+params.get(1).replaceAll("\"", ""), params.get(2));
		}
	}
	public void initJsonChangeUserInfo(){
		List<List<String>> userInfo = initJsonMap.get(Constant.USER_INFO);
		Map<String,List<List<String>>> map = new HashMap<String,List<List<String>>>();
		List<List<String>> changlist = new ArrayList<List<String>>();
		if(userInfo != null ){
			for(List<String> list : userInfo){
				String role_code = list.get(2);
				changlist = map.get(role_code);
				if(changlist==null){
					changlist = new ArrayList<List<String>>();
				}
				changlist.add(list);
				map.put(role_code, changlist);
			}
		}
		initChangeMap.put(Constant.USER_INFO, map);
	}
	public void initJsonChangeStoreTypeInfo(){
		List<List<String>> goodsList = initJsonMap.get(Constant.STORE_LIST);
		Map<String,List<List<String>>> map = new HashMap<String,List<List<String>>>();
		List<List<String>> changlist = new ArrayList<List<String>>();
		if(goodsList!=null){
			for(List<String> list : goodsList){
				String typeid = list.get(2).replace("\"", "");
				String outuser = list.get(3).replace("\"", "");
				String key = "\""+typeid+outuser+"\"";
				changlist = map.get(key);
				if(changlist==null){
					changlist = new ArrayList<List<String>>();
				}
				changlist.add(list);
				map.put(key, changlist);
			}
		}
		initChangeMap.put(Constant.STORE_LIST, map);
	}
	public void initOutuserInfo(){
		String hql = "select id.outuser,id.tag,id.userlist from OutUserList order by id.outuser";
		List<Object[]> roleInfo = roleInfoDAO.getHibernateTemplate().find(hql);
		if (roleInfo!=null) {
			for(Object[] list : roleInfo){
				outuserMap.put(list[0].toString()+list[1].toString(), list[2].toString());
			}
		}
	}
	/**
	 * 取得Json的List
	 * @param info
	 * @return
	 */
	public static List<List<String>> getJsonList(String info){
		return initJsonMap.get(info);
	}
	/**
	 * 根据改变的值获取相应的列表
	 */
	public static List<List<String>> getJsonRoleToUserList(String info,String roleCode){
		return initChangeMap.get(info).get(roleCode);
	}
	/**
	 * 获取List中的通过key获取value
	 * @param mapkey
	 * @param code
	 * @return
	 */
	public static String getValue(String mapkey,String code){
		String name = "";
		List<List<String>> list = initJsonMap.get(mapkey);
		for(List<String> temp : list){
			if(temp.get(0).equals("\""+code+"\"")){
				return temp.get(1);
			}
		}
		return name;
	}
	/**
	 * 获取常量表中的value
	 * @param key
	 * @return
	 */
	public static String getConstantsValue(String key){
		return initConstant.get(key);
	}
	/**
	 * 获取OUT_USER_LIST表的userlist
	 * @param key
	 * @return
	 */
	public static String getUserList(String key){
		return outuserMap.get(key);
	}
	
}
