package com.hrm.dao;

import java.sql.SQLException;
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

import com.hrm.entity.RoleMenuStair;

/**
 * A data access object (DAO) providing persistence and search support for
 * RoleMenuStair entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.RoleMenuStair
 * @author MyEclipse Persistence Tools
 */

public class RoleMenuStairDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(RoleMenuStairDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(RoleMenuStair transientInstance) {
		log.debug("saving RoleMenuStair instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RoleMenuStair persistentInstance) {
		log.debug("deleting RoleMenuStair instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public int deleteHql(final String hql){
		return getHibernateTemplate().bulkUpdate(hql);
	}

	public RoleMenuStair findById(com.hrm.entity.RoleMenuStairId id) {
		log.debug("getting RoleMenuStair instance with id: " + id);
		try {
			RoleMenuStair instance = (RoleMenuStair) getHibernateTemplate()
					.get("com.hrm.entity.RoleMenuStair", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(RoleMenuStair instance) {
		log.debug("finding RoleMenuStair instance by example");
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
		log.debug("finding RoleMenuStair instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from RoleMenuStair as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all RoleMenuStair instances");
		try {
			String queryString = "from RoleMenuStair";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public RoleMenuStair merge(RoleMenuStair detachedInstance) {
		log.debug("merging RoleMenuStair instance");
		try {
			RoleMenuStair result = (RoleMenuStair) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RoleMenuStair instance) {
		log.debug("attaching dirty RoleMenuStair instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RoleMenuStair instance) {
		log.debug("attaching clean RoleMenuStair instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List findByHql(String hql,String[] where){
		return getHibernateTemplate().find(hql, where);
	}

	public static RoleMenuStairDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (RoleMenuStairDAO) ctx.getBean("RoleMenuStairDAO");
	}
}