package com.ux.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;
import com.ux.entity.ArticleInfo;

@Repository
public class ArticleDao extends BaseDao{
	public void save(ArticleInfo entity){
		getHibernateTemplate().save(entity);
	}
	public List<ArticleInfo> queryArticle(final int first, final int result){
		//List<ArticleInfo> list = getHibernateTemplate().find("from ArticleInfo where userid='"+userid+"' order by firstDate desc");
		List<ArticleInfo> list = getHibernateTemplate().executeFind(new HibernateCallback<List<ArticleInfo>>(){

			@Override
			public List<ArticleInfo> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query q = session.createQuery("from ArticleInfo order by firstDate desc").setFirstResult(first).setMaxResults(result);
				return q.list();
			}
			
		});
		return list;
	}
	
	public ArticleInfo queryEntity(String id){
		List<ArticleInfo> list = getHibernateTemplate().find("from ArticleInfo where id='"+id+"'");
		return list.get(0);
	}
}
