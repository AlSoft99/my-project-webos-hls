package com.ux.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
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
	public boolean deleteByCommentId(final String id){
		return getHibernateTemplate().execute(new HibernateCallback<Boolean>() {
			@Override
			public Boolean doInHibernate(Session session)
					throws HibernateException, SQLException {
				int flag = session.createQuery("delete from CommentInfo where articleid='"+id+"'").executeUpdate();
				if(flag==0){
					return false;
				}
				return true;
			}
			
		});
	}
}
