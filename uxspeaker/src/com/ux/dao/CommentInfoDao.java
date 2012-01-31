package com.ux.dao;

import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;
import com.ux.entity.CommentInfo;
@Repository
public class CommentInfoDao extends BaseDao{
	public void save(CommentInfo entity){
		getHibernateTemplate().save(entity);
	}
}
