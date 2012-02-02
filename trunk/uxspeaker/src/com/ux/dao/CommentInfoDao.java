package com.ux.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;
import com.ux.entity.CommentInfo;
@Repository
public class CommentInfoDao extends BaseDao{
	public void save(CommentInfo entity){
		getHibernateTemplate().save(entity);
	}
	public List<CommentInfo> getCommentInfo(CommentInfo info){
		List<CommentInfo> list = getHibernateTemplate().find("from CommentInfo where id="+info.getId()+" and userid='"+info.getUserid()+"'");
		return list;
	}
	public void update(CommentInfo info){
		getHibernateTemplate().update(info);
	}
	public void delete(CommentInfo info){
		getHibernateTemplate().delete(info);
	}
}
