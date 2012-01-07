package com.ux.dao;

import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;
import com.ux.entity.UserInfo;
@Repository
public class UserInfoDao extends BaseDao{
	public void save(UserInfo entity){
		getHibernateTemplate().save(entity);
	}
}
