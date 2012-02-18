package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.MaterialType;

/**
 * A data access object (DAO) providing persistence and search support for
 * MaterialType entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hrm.entity.MaterialType
 * @author MyEclipse Persistence Tools
 */

public class MaterialTypeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(MaterialTypeDAO.class);
	// property constants
	public static final String TYPENAME = "typename";
	public static final String TYPEDESC = "typedesc";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(MaterialType transientInstance) {
		log.debug("saving MaterialType instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MaterialType persistentInstance) {
		log.debug("deleting MaterialType instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MaterialType findById(java.lang.String id) {
		log.debug("getting MaterialType instance with id: " + id);
		try {
			MaterialType instance = (MaterialType) getHibernateTemplate().get(
					"com.hrm.entity.MaterialType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MaterialType instance) {
		log.debug("finding MaterialType instance by example");
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
		log.debug("finding MaterialType instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from MaterialType as model where model."
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
		log.debug("finding all MaterialType instances");
		try {
			String queryString = "from MaterialType";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MaterialType merge(MaterialType detachedInstance) {
		log.debug("merging MaterialType instance");
		try {
			MaterialType result = (MaterialType) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MaterialType instance) {
		log.debug("attaching dirty MaterialType instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MaterialType instance) {
		log.debug("attaching clean MaterialType instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MaterialTypeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (MaterialTypeDAO) ctx.getBean("MaterialTypeDAO");
	}
}