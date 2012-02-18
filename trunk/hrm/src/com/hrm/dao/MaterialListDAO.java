package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.MaterialList;

/**
 * A data access object (DAO) providing persistence and search support for
 * MaterialList entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hrm.entity.MaterialList
 * @author MyEclipse Persistence Tools
 */

public class MaterialListDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(MaterialListDAO.class);
	// property constants
	public static final String TYPEID = "typeid";
	public static final String PARAMSCODE = "paramscode";
	public static final String PARAMSNAME = "paramsname";
	public static final String PARAMSDESC = "paramsdesc";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(MaterialList transientInstance) {
		log.debug("saving MaterialList instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MaterialList persistentInstance) {
		log.debug("deleting MaterialList instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MaterialList findById(java.lang.String id) {
		log.debug("getting MaterialList instance with id: " + id);
		try {
			MaterialList instance = (MaterialList) getHibernateTemplate().get(
					"com.hrm.entity.MaterialList", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MaterialList instance) {
		log.debug("finding MaterialList instance by example");
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
		log.debug("finding MaterialList instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from MaterialList as model where model."
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
		log.debug("finding all MaterialList instances");
		try {
			String queryString = "from MaterialList";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MaterialList merge(MaterialList detachedInstance) {
		log.debug("merging MaterialList instance");
		try {
			MaterialList result = (MaterialList) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MaterialList instance) {
		log.debug("attaching dirty MaterialList instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MaterialList instance) {
		log.debug("attaching clean MaterialList instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MaterialListDAO getFromApplicationContext(ApplicationContext ctx) {
		return (MaterialListDAO) ctx.getBean("MaterialListDAO");
	}
}