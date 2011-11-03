package com.hrm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.RoleInfo;
import com.hrm.util.StringUtil;

/**
 * A data access object (DAO) providing persistence and search support for
 * RoleInfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hrm.entity.RoleInfo
 * @author MyEclipse Persistence Tools
 */

public class RoleInfoDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(RoleInfoDAO.class);
	// property constants
	public static final String ROLE_NAME = "roleName";
	public static final String ROLE_DESC = "roleDesc";
	public static final String USER_NAME = "userName";

	protected void initDao() {
		// do nothing
	}
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
	public void save(RoleInfo transientInstance) {
		log.debug("saving RoleInfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RoleInfo persistentInstance) {
		log.debug("deleting RoleInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public void deleteAll(List<RoleInfo> list){
		getHibernateTemplate().deleteAll(list);
	}
	public RoleInfo findById(java.lang.String id) {
		log.debug("getting RoleInfo instance with id: " + id);
		try {
			RoleInfo instance = (RoleInfo) getHibernateTemplate().get(
					"com.hrm.entity.RoleInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(RoleInfo instance) {
		log.debug("finding RoleInfo instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding RoleInfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from RoleInfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByRoleName(Object roleName) {
		return findByProperty(ROLE_NAME, roleName);
	}

	public List findByRoleDesc(Object roleDesc) {
		return findByProperty(ROLE_DESC, roleDesc);
	}

	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	public List findAll() {
		log.debug("finding all RoleInfo instances");
		try {
			String queryString = "from RoleInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	/**
	 * 返回List<List<String>>
	 * @param hql
	 * @return
	 */
	public List<List<String>> findByHql(String hql){
		List<Object[]> list = getHibernateTemplate().find(hql);
		return StringUtil.newInstance().trasnformObjectList(list);
	}
	
	public RoleInfo merge(RoleInfo detachedInstance) {
		log.debug("merging RoleInfo instance");
		try {
			RoleInfo result = (RoleInfo) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RoleInfo instance) {
		log.debug("attaching dirty RoleInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RoleInfo instance) {
		log.debug("attaching clean RoleInfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	public List findByPage(RoleInfo roleInfo,int start,int limit){
		return getHibernateTemplate().findByExample(roleInfo, start, limit);
	}
	public int getCount(String table){
		return Integer.parseInt(getHibernateTemplate().find("select count(*) from "+table).get(0).toString());
	}
	public void update(RoleInfo info){
		getHibernateTemplate().update(info);
	}
	public static RoleInfoDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RoleInfoDAO) ctx.getBean("RoleInfoDAO");
	}
}