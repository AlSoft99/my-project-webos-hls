package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.ParamsList;

/**
 * A data access object (DAO) providing persistence and search support for
 * ParamsList entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hrm.entity.ParamsList
 * @author MyEclipse Persistence Tools
 */

public class ParamsListDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ParamsListDAO.class);
	// property constants
	public static final String TYPEID = "typeid";
	public static final String PARAMSCODE = "paramscode";
	public static final String PARAMSNAME = "paramsname";
	public static final String PARAMSDESC = "paramsdesc";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(ParamsList transientInstance) {
		log.debug("saving ParamsList instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ParamsList persistentInstance) {
		log.debug("deleting ParamsList instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ParamsList findById(java.lang.String id) {
		log.debug("getting ParamsList instance with id: " + id);
		try {
			ParamsList instance = (ParamsList) getHibernateTemplate().get(
					"com.hrm.entity.ParamsList", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ParamsList instance) {
		log.debug("finding ParamsList instance by example");
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
		log.debug("finding ParamsList instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ParamsList as model where model."
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
		log.debug("finding all ParamsList instances");
		try {
			String queryString = "from ParamsList";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ParamsList merge(ParamsList detachedInstance) {
		log.debug("merging ParamsList instance");
		try {
			ParamsList result = (ParamsList) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ParamsList instance) {
		log.debug("attaching dirty ParamsList instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ParamsList instance) {
		log.debug("attaching clean ParamsList instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ParamsListDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ParamsListDAO) ctx.getBean("ParamsListDAO");
	}
}