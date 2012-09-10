package com.test.dao;

import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;
import com.test.entity.TestEntity;
@Repository
public class TestDao extends BaseDao{
	public void save(TestEntity entity){
		getHibernateTemplate().save(entity);
	}
}
