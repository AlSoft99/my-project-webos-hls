package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.DayClearChk;

/**
 * A data access object (DAO) providing persistence and search support for
 * DayClearChk entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.DayClearChk
 * @author MyEclipse Persistence Tools
 */

public class DayClearChkDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(DayClearChkDAO.class);
	// property constants
	public static final String CLEAREQUALS = "clearequals";
	public static final String CLEARDESC = "cleardesc";
	public static final String STORECLEAR = "storeclear";
	public static final String STOREDESC = "storedesc";

	protected void initDao() {
		// do nothing
	}

	public void save(DayClearChk transientInstance) {
		log.debug("saving DayClearChk instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DayClearChk persistentInstance) {
		log.debug("deleting DayClearChk instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DayClearChk findById(java.lang.String id) {
		log.debug("getting DayClearChk instance with id: " + id);
		try {
			DayClearChk instance = (DayClearChk) getHibernateTemplate().get(
					"com.hrm.entity.DayClearChk", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DayClearChk instance) {
		log.debug("finding DayClearChk instance by example");
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
		log.debug("finding DayClearChk instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from DayClearChk as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByClearequals(Object clearequals) {
		return findByProperty(CLEAREQUALS, clearequals);
	}

	public List findByCleardesc(Object cleardesc) {
		return findByProperty(CLEARDESC, cleardesc);
	}

	public List findByStoreclear(Object storeclear) {
		return findByProperty(STORECLEAR, storeclear);
	}

	public List findByStoredesc(Object storedesc) {
		return findByProperty(STOREDESC, storedesc);
	}

	public List findAll() {
		log.debug("finding all DayClearChk instances");
		try {
			String queryString = "from DayClearChk";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DayClearChk merge(DayClearChk detachedInstance) {
		log.debug("merging DayClearChk instance");
		try {
			DayClearChk result = (DayClearChk) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DayClearChk instance) {
		log.debug("attaching dirty DayClearChk instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DayClearChk instance) {
		log.debug("attaching clean DayClearChk instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DayClearChkDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (DayClearChkDAO) ctx.getBean("DayClearChkDAO");
	}
}