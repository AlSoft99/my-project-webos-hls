package com.ux.dao;

import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;
import com.ux.entity.ArticleInfo;

@Repository
public class ArticleDao extends BaseDao{
	public void save(ArticleInfo entity){
		getHibernateTemplate().save(entity);
	}
}
