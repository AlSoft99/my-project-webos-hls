package com.ux.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;
import com.ux.entity.ArticleInfo;
import com.ux.entity.TagInfo;

@Repository
public class TagInfoDao extends BaseDao{
	public void save(TagInfo entity){
		getHibernateTemplate().save(entity);
	}
	
	public ArticleInfo queryEntity(String id){
		List<ArticleInfo> list = getHibernateTemplate().find("from TagInfo where articleid='"+id+"'");
		return list.get(0);
	}
}
