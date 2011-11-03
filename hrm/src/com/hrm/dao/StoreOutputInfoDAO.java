package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.StoreOutputInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoreOutputInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.StoreOutputInfo
 * @author MyEclipse Persistence Tools
 */

public class StoreOutputInfoDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(StoreOutputInfoDAO.class);
	// property constants
	public static final String STATUS = "status";
	public static final String OUTUSER = "outuser";
	public static final String RECUSER = "recuser";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(StoreOutputInfo transientInstance) {
		log.debug("saving StoreOutputInfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StoreOutputInfo persistentInstance) {
		log.debug("deleting StoreOutputInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StoreOutputInfo findById(java.lang.String id) {
		log.debug("getting StoreOutputInfo instance with id: " + id);
		try {
			StoreOutputInfo instance = (StoreOutputInfo) getHibernateTemplate()
					.get("com.hrm.entity.StoreOutputInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(StoreOutputInfo instance) {
		log.debug("finding StoreOutputInfo instance by example");
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
		log.debug("finding StoreOutputInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from StoreOutputInfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByOutuser(Object outuser) {
		return findByProperty(OUTUSER, outuser);
	}

	public List findByRecuser(Object recuser) {
		return findByProperty(RECUSER, recuser);
	}

	public List findByUpdtuser(Object updtuser) {
		return findByProperty(UPDTUSER, updtuser);
	}

	public List findAll() {
		log.debug("finding all StoreOutputInfo instances");
		try {
			String queryString = "from StoreOutputInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StoreOutputInfo merge(StoreOutputInfo detachedInstance) {
		log.debug("merging StoreOutputInfo instance");
		try {
			StoreOutputInfo result = (StoreOutputInfo) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StoreOutputInfo instance) {
		log.debug("attaching dirty StoreOutputInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StoreOutputInfo instance) {
		log.debug("attaching clean StoreOutputInfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static StoreOutputInfoDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (StoreOutputInfoDAO) ctx.getBean("StoreOutputInfoDAO");
	}
}