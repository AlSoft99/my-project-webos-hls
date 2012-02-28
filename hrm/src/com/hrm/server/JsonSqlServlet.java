package com.hrm.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.util.StringUtil;
import com.hrm.vo.BaseVo;

public class JsonSqlServlet implements BaseVo {

	private HibernateSessionDAO hibernateSessionDAO;
	public Request execute(Request request) {
		String sql = request.getParamsMap().get("sql");
		if(!sql.equals("")){
			List<Object[]> list = hibernateSessionDAO.createHqlQuery(sql);
			String json = "{";
			for (Object[] objects : list) {
				json +="\""+objects[0]+"\":\""+objects[1]+"\",";
			}
			if(list.size()!=0){
				json = json.substring(0, json.length()-1);
			}
			
			json += "}";
			request.setResponse(json);
			return request;
		}else{
			request.setResponse("{}");
			return request;
		}
	}
	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}
	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}

}
