package com.hrm.server;

import java.util.List;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.util.StringUtil;
import com.hrm.vo.BaseVo;

public class OptionSqlServlet implements BaseVo {

	private HibernateSessionDAO hibernateSessionDAO;
	public Request execute(Request request) {
		String sql = request.getParamsMap().get("sql");
		if(!sql.equals("")){
			List<Object[]> list = hibernateSessionDAO.createHqlQuery(sql);
			request.setResponse(StringUtil.newInstance().trasnformObjectList(list).toString());
			return request;
		}else{
			request.setResponse("[]");
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
