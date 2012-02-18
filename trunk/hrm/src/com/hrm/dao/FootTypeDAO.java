package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.FootType;

/**
 * A data access object (DAO) providing persistence and search support for
 * FootType entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hrm.entity.FootType
 * @author MyEclipse Persistence Tools
 */

public class FootTypeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(FootTypeDAO.class);
	// property constants
	public static final String TYPENAME = "typename";
	public static final String TYPEDESC = "typedesc";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(FootType transientInstance) {
		log.debug("saving FootType instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FootType persistentInstance) {
		log.debug("deleting FootType instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FootType findById(java.lang.String id) {
		log.debug("getting FootType instance with id: " + id);
		try {
			FootType instance = (FootType) getHibernateTemplate().get(
					"com.hrm.entity.FootType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(FootType instance) {
		log.debug("finding FootType instance by example");
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
		log.debug("finding FootType instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FootType as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTypename(Object typename) {
		return findByProperty(TYPENAME, typename);
	}

	public List findByTypedesc(Object typedesc) {
		return findByProperty(TYPEDESC, typedesc);
	}

	public List findByUpdtuser(Object updtuser) {
		return findByProperty(UPDTUSER, updtuser);
	}

	public List findAll() {
		log.debug("finding all FootType instances");
		try {
			String queryString = "from FootType";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FootType merge(FootType detachedInstance) {
		log.debug("merging FootType instance");
		try {
			FootType result = (FootType) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FootType instance) {
		log.debug("attaching dirty FootType instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FootType instance) {
		log.debug("attaching clean FootType instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static FootTypeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (FootTypeDAO) ctx.getBean("FootTypeDAO");
	}
}