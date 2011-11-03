package com.hrm.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.util.StringUtil;
import com.hrm.vo.BaseVo;

public class QueryInfoVo implements BaseVo {
	private HibernateSessionDAO hibernateSessionDAO;
	private String startDate = "";
	private String endDate = "";
	public Request execute(Request request) {
		String result = "";
		String action = request.getParamsMap().get("action");
		String type = request.getParamsMap().get("type");
		String start = request.getParamsMap().get("start");
		String limit = request.getParamsMap().get("limit");
		com.hrm.util.ClsFactory.newInstance().info("[action]:==>["+action+"]");
		com.hrm.util.ClsFactory.newInstance().info("[type]:==>["+type+"]");
		com.hrm.util.ClsFactory.newInstance().info("[startPage]:==>["+start+"]");
		com.hrm.util.ClsFactory.newInstance().info("[limitPage]:==>["+limit+"]");
		if ("hql".equals(action)) {
			if ("map".equals(type)) {
				result = queryHqlMap(request);
			}else if ("entity".equals(type)){
				result = queryHqlEntity(request);
			}else if("mapdate".equals(type)){
				startDate = request.getParamsMap().get("startDate");
				endDate = request.getParamsMap().get("endDate");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					endDate = StringUtil.newInstance().dateAddNumber(sdf.parse(endDate), 1);
				} catch (ParseException e) {
					com.hrm.util.ClsFactory.newInstance().error("QueryInfoVo错误:", e);
				}
				result = queryHqlMap(request);
			}
		}else if ("sql".equals(action)) {
			
		}
		request.setResponse(result);
		return request;
	}
	/**
	 * 通过query查询hql语句
	 * @param request
	 * @param response
	 * @return
	 */
	public String queryHqlMap(Request request){
		String result = "";
		String sql = request.getParamsMap().get("sql");
		String start = request.getParamsMap().get("start");
		String limit = request.getParamsMap().get("limit");
		if(request.getParamsMap().get("type").equals("mapdate")){
			sql = sql.replace("{1}", startDate);
			sql = sql.replace("{2}", endDate);
		}
		if (start==null) {
			List<Map<String,Object>> list = hibernateSessionDAO.createHqlQuery(sql);
			result = "{totalProperty:"+list.size()+",";
			result += "root:[";
			for(Map<String,Object> map:list){
				result += StringUtil.newInstance().formatMapToJson(map)+",";
			}
			if (list.size()>0) {
				result = result.substring(0, result.length()-1);
			}
			result += "]}";
		}else{
			List<Map<String,Object>> list = hibernateSessionDAO.createHqlQuery(sql, Integer.parseInt(start), Integer.parseInt(limit));
			int count = hibernateSessionDAO.getHibernateTemplate().find(sql).size();
			result = "{totalProperty:"+count+",";
			result += "root:[";
			for(Map<String,Object> map:list){
				result += StringUtil.newInstance().formatMapToJson(map)+",";
			}
			if (list.size()>0) {
				result = result.substring(0, result.length()-1);
			}
			result += "]}";
		}
		return result;
	}
	/**
	 * 通过实体转换
	 * @param request
	 * @param response
	 * @return
	 */
	public String queryHqlEntity(Request request){
		String result = "";
		String sql = request.getParamsMap().get("sql");
		String start = request.getParamsMap().get("start");
		String limit = request.getParamsMap().get("limit");
		if (start==null) {
			List<Object> list = hibernateSessionDAO.createHqlQuery(sql);
			result = "{totalProperty:"+list.size()+",";
			result += "root:[";
			for(Object obj:list){
				result += StringUtil.newInstance().transeObjectToJson(obj)+",";
			}
			if (list.size()>0) {
				result = result.substring(0, result.length()-1);
			}
			result += "]}";
		}else{
			List<Object> list = hibernateSessionDAO.createHqlQuery(sql, Integer.parseInt(start), Integer.parseInt(limit));
			int count = hibernateSessionDAO.getHibernateTemplate().find(sql).size();
			result = "{totalProperty:"+count+",";
			result += "root:[";
			for(Object obj:list){
				result += StringUtil.newInstance().transeObjectToJson(obj)+",";
			}
			if (list.size()>0) {
				result = result.substring(0, result.length()-1);
			}
			result += "]}";
		}
		return result;
	}
	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}
	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}

}
