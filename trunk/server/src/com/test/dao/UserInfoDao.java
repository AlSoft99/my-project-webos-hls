package com.test.dao;

import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;
import com.test.entity.UserInfo;
@Repository
public class UserInfoDao extends BaseDao{
	public void save(UserInfo userInfo){
		getHibernateTemplate().save(userInfo);
	}
}
