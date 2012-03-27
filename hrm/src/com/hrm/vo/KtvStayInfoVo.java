package com.hrm.vo;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;

public class KtvStayInfoVo implements BaseVo {
	private HibernateSessionDAO hibernateSessionDAO;
	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}
	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}
	@Override
	public Request execute(Request request) throws Exception {
		
		return request;
	}

}
