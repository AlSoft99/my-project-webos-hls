package com.frame.dao;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseDao extends HibernateDaoSupport {
	@Resource
	private SessionFactory mySessionFactory;
	
	public void setMySessionFactory(SessionFactory mySessionFactory) {
		this.mySessionFactory = mySessionFactory;
	}
	@PostConstruct
	public void injectSessionFactory() {
		super.setSessionFactory(mySessionFactory);
	}
}
