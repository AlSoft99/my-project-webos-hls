package com.hrm.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.util.StringUtil;
import com.hrm.vo.BaseVo;

public class TreeInfoQuery implements BaseVo{
	private static Logger logger = Logger.getLogger(TreeInfoQuery.class);
	private HibernateSessionDAO hibernateSessionDAO;

	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}

	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}
	/**
	 * 统一树生成<br>
	 * 页面传入参数例子<br>
	 *baseParams :{<br>
	 *	action:"select",
		node: "select new map(roleCode as id,roleName as text,roleDesc as qtip) from RoleInfo",<br>
		child: "select new map(userId as id,userName as text,userName as qtip,roleInfo.roleCode as nodeId) from UserInfo"<br>
	  }<br>
	 */
	public Request execute(Request request) {
		String result = "";
		String action = request.getParamsMap().get("action");
		if ("select".equals(action)) {
			result = selectTreeNode(request);
		}else if("single".equals(action)){
			result = selectSingleTreeNode(request);
		}else if("singledate".equals(action)){
			result = selectSingleDateTreeNode(request);
		}
		request.setResponse(result);
		return request;
	}
	public String test(Map<String,String> map){
		String result = "[";
		return result;
	}
	/**
	 * node和child的规则<br>
	 * node是节点hql,第一个字段是id,第二个字段是name,第三个是qtip<br>
	 * child是子节点hql,第一个字段是id,第二个字段是name,第三个是qtip,第四个为对应父节点id<br>
	 */
	public String selectTreeNode(Request request){
		String node = request.getParamsMap().get("parent");
		String child = request.getParamsMap().get("child");
		List<Map<String,Object>> nodeList = hibernateSessionDAO.getHibernateTemplate().find(node);
		List<Map<String,Object>> childList = hibernateSessionDAO.getHibernateTemplate().find(child);
		String result = "[";
		Map<String, List<Map<String, Object>>> childMap = StringUtil.newInstance().formatTreeChild(childList);
		for(Map<String,Object> nodeMap : nodeList){
			String id = nodeMap.get("id").toString();
			result += "{";
			for(Iterator<String> it = nodeMap.keySet().iterator();it.hasNext();){
				String key = it.next();
				result += key+":'"+nodeMap.get(key)+"',";
			}
			result += "children:[";
			List<Map<String, Object>> childCollection = childMap.get(id);
			if(childCollection!=null){
				for(Map<String, Object> childCollectionMap:childCollection){
					childCollectionMap.put("leaf", "true");
					result += StringUtil.newInstance().formatMapToJson(childCollectionMap)+",";
				}
				if (childCollection.size()!=0) {
					result = result.substring(0,result.length()-1);
				}
			}
			result += "]},";
		}
		if (nodeList.size()!=0) {
			result = result.substring(0, result.length()-1);
		}
		result += "]";
		return result;
	}
	/**
	 * 单节点输出
	 * @param request
	 * @param response
	 * @return
	 */
	public String selectSingleTreeNode(Request request){
		String node = request.getParamsMap().get("parent");
		List<Map<String,Object>> nodeList = hibernateSessionDAO.getHibernateTemplate().find(node);
		String result = "[";
		for(Map<String,Object> nodeMap : nodeList){
			nodeMap.put("leaf", "true");
			result += StringUtil.newInstance().formatMapToJson(nodeMap)+",";
		}
		if (nodeList.size()!=0) {
			result = result.substring(0, result.length()-1);
		}
		result += "]";
		return result;
	}
	public String selectSingleDateTreeNode(Request request){
		String result = "[";
		String node = request.getParamsMap().get("parent");
		String startDate = request.getParamsMap().get("start");
		String endDate = request.getParamsMap().get("end");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			endDate = StringUtil.newInstance().dayAddNumber(sdf.parse(endDate), 1);
			node = node.replace("{1}", startDate);
			node = node.replace("{2}", endDate);
			List<Map<String,Object>> nodeList = hibernateSessionDAO.getHibernateTemplate().find(node);
			for(Map<String,Object> nodeMap : nodeList){
				nodeMap.put("leaf", "true");
				result += StringUtil.newInstance().formatMapToJson(nodeMap)+",";
			}
			if (nodeList.size()!=0) {
				result = result.substring(0, result.length()-1);
			}
		} catch (ParseException e) {
			logger.error("TreeInfoQuery错误:", e);
		}
		result += "]";
		
		return result;
	}
}
