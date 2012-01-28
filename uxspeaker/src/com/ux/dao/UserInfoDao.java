package com.ux.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;
import com.ux.entity.UserInfo;
@Repository
public class UserInfoDao extends BaseDao{
	public void save(UserInfo entity){
		getHibernateTemplate().save(entity);
	}
	public boolean checkId(String email){
		List list = getHibernateTemplate().find("select id from UserInfo where email='"+email+"'");
		if (list.size()==0) {
			return true;
		}
		return false;
	}
	public List<UserInfo> checkIsExist(String email,String password){
		List<UserInfo> list = getHibernateTemplate().find("from UserInfo where email='"+email+"' and password='"+password+"'");
		return list;
	}
	public void update(UserInfo userinfo){
		getHibernateTemplate().update(userinfo);
	}
}
