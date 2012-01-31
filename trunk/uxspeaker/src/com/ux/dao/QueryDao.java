package com.ux.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;

@Repository
public class QueryDao extends BaseDao{
	public List<Map<String,Object>> query(String sql){
		List<Map<String,Object>> list = getHibernateTemplate().find(sql);
		return list;
	}
	public List<Map<String,Object>> query(final String sql,final int start,final int row){
		List<Map<String,Object>> list = getHibernateTemplate().executeFind(new HibernateCallback<List<Map<String,Object>>>() {

			@Override
			public List<Map<String,Object>> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(sql).setFirstResult(start).setMaxResults(row);
				return query.list();
			}
			
		});
		return list;
	}
	
	public int getCount(String sql){
		List list = getHibernateTemplate().find(sql);
		return list.size();
	}
}