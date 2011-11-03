package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.StoreParamsInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoreParamsInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.StoreParamsInfo
 * @author MyEclipse Persistence Tools
 */

public class StoreParamsInfoDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(StoreParamsInfoDAO.class);
	// property constants
	public static final String OUTUSER = "outuser";
	public static final String MIN = "min";
	public static final String MAX = "max";

	protected void initDao() {
		// do nothing
	}

	public void save(StoreParamsInfo transientInstance) {
		log.debug("saving StoreParamsInfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StoreParamsInfo persistentInstance) {
		log.debug("deleting StoreParamsInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StoreParamsInfo findById(java.lang.String id) {
		log.debug("getting StoreParamsInfo instance with id: " + id);
		try {
			StoreParamsInfo instance = (StoreParamsInfo) getHibernateTemplate()
					.get("com.hrm.entity.StoreParamsInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(StoreParamsInfo instance) {
		log.debug("finding StoreParamsInfo instance by example");
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
		log.debug("finding StoreParamsInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from StoreParamsInfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOutuser(Object outuser) {
		return findByProperty(OUTUSER, outuser);
	}

	public List findByMin(Object min) {
		return findByProperty(MIN, min);
	}

	public List findByMax(Object max) {
		return findByProperty(MAX, max);
	}

	public List findAll() {
		log.debug("finding all StoreParamsInfo instances");
		try {
			String queryString = "from StoreParamsInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StoreParamsInfo merge(StoreParamsInfo detachedInstance) {
		log.debug("merging StoreParamsInfo instance");
		try {
			StoreParamsInfo result = (StoreParamsInfo) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StoreParamsInfo instance) {
		log.debug("attaching dirty StoreParamsInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StoreParamsInfo instance) {
		log.debug("attaching clean StoreParamsInfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static StoreParamsInfoDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (StoreParamsInfoDAO) ctx.getBean("StoreParamsInfoDAO");
	}
}