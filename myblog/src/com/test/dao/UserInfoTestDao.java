package com.test.dao;

import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;
import com.test.entity.UserInfoTest;
@Repository
public class UserInfoTestDao extends BaseDao{
	public void save(UserInfoTest userInfo){
		getHibernateTemplate().save(userInfo);
	}
}
