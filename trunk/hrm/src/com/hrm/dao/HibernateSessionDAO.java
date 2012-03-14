package com.hrm.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateSessionDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(HibernateSessionDAO.class);
	
	/**
	 * 执行更新操作的hql语句
	 * @param hql
	 * @return	返回更新语句行数
	 */
	public Integer createHqlExcute(final String hql){
		Integer result = getHibernateTemplate().execute(new HibernateCallback<Integer>(){

			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(hql).executeUpdate();
			}
			
		});
		return result;
	}
	/**
	 * 查询
	 * @param hql
	 * @return
	 */
	public List createHqlQuery(final String hql){
		return createHqlQuery(hql,-1,-1);
	}
	/**
	 * 分页查询
	 * @param hql
	 * @param start
	 * @param limit
	 * @return
	 */
	public List createHqlQuery(final String hql,final int start,final int limit){
		return getHibernateTemplate().execute(new HibernateCallback<List>(){

			public List doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (start>=0) {
					query.setFirstResult(start);
				}
				if(limit>=0){
					query.setMaxResults(limit);
				}
				return query.list();
			}
			
		});
	}
	/**
	 * 查询
	 * @param hql
	 * @return
	 */
	public List createSqlQuery(final String hql){
		return createSqlQuery(hql,-1,-1);
	}
	public List createSqlQuery(final String hql,final int start,final int limit){
		return getHibernateTemplate().execute(new HibernateCallback<List>(){

			public List doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				if (start>=0) {
					query.setFirstResult(start);
				}
				if(limit>=0){
					query.setMaxResults(limit);
				}
				return query.list();
			}
			
		});
	}
	
	public void save(Object o){
		getHibernateTemplate().save(o);
	}
	
	public void update(Object o){
		getHibernateTemplate().update(o);
	}
	
	public void saveOrUpdate(Object o){
		getHibernateTemplate().saveOrUpdate(o);
	}
}
