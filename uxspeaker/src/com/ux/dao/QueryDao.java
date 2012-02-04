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
import com.frame.servlet.ServletFactory;
import com.ux.util.QueryUtil;

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
	public List<Map<String,Object>> queryByPage(String sql,String where){
		String allsql = getSql(sql, where);
		List<Map<String,Object>> list = query(allsql);
		return list;
	}
	public List<Map<String,Object>> queryByPage(String sql,String where,int start,int row){
		String allsql = getSql(sql, where);
		List<Map<String,Object>> list = query(allsql,start,row);
		return list;
	}
	public int getCount(String sql,String where){
		return queryByPage(sql,where).size();
	}
	private String getSql(String sql, String where){
		QueryUtil query = ServletFactory.newInstant().getFactory().getBean("queryUtil", QueryUtil.class);
		String allsql = query.getSql(sql).trim();
		String[] condition = where.split(",");
		for (int i = 0; i < condition.length; i++) {
			String replaceWhere = "{"+i+"}";
			allsql = allsql.replace(replaceWhere, condition[i]);
		}
		return allsql;
	}
}