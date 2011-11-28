package com.test.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.test.entity.UserInfo;
public class UserInfoDAO extends HibernateDaoSupport {
	public void save(UserInfo userInfo){
		getHibernateTemplate().save(userInfo);
	}
}
