package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.FootList;

/**
 * A data access object (DAO) providing persistence and search support for
 * FootList entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hrm.entity.FootList
 * @author MyEclipse Persistence Tools
 */

public class FootListDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(FootListDAO.class);
	// property constants
	public static final String TYPEID = "typeid";
	public static final String PARAMSCODE = "paramscode";
	public static final String PARAMSNAME = "paramsname";
	public static final String PARAMSDESC = "paramsdesc";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(FootList transientInstance) {
		log.debug("saving FootList instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FootList persistentInstance) {
		log.debug("deleting FootList instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FootList findById(java.lang.String id) {
		log.debug("getting FootList instance with id: " + id);
		try {
			FootList instance = (FootList) getHibernateTemplate().get(
					"com.hrm.entity.FootList", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(FootList instance) {
		log.debug("finding FootList instance by example");
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
		log.debug("finding FootList instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FootList as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTypeid(Object typeid) {
		return findByProperty(TYPEID, typeid);
	}

	public List findByParamscode(Object paramscode) {
		return findByProperty(PARAMSCODE, paramscode);
	}

	public List findByParamsname(Object paramsname) {
		return findByProperty(PARAMSNAME, paramsname);
	}

	public List findByParamsdesc(Object paramsdesc) {
		return findByProperty(PARAMSDESC, paramsdesc);
	}

	public List findByUpdtuser(Object updtuser) {
		return findByProperty(UPDTUSER, updtuser);
	}

	public List findAll() {
		log.debug("finding all FootList instances");
		try {
			String queryString = "from FootList";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FootList merge(FootList detachedInstance) {
		log.debug("merging FootList instance");
		try {
			FootList result = (FootList) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FootList instance) {
		log.debug("attaching dirty FootList instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FootList instance) {
		log.debug("attaching clean FootList instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static FootListDAO getFromApplicationContext(ApplicationContext ctx) {
		return (FootListDAO) ctx.getBean("FootListDAO");
	}
}