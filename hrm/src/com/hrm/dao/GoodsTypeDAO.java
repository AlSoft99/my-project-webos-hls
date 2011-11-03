package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.GoodsType;

/**
 * A data access object (DAO) providing persistence and search support for
 * GoodsType entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hrm.entity.GoodsType
 * @author MyEclipse Persistence Tools
 */

public class GoodsTypeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(GoodsTypeDAO.class);
	// property constants
	public static final String TYPENAME = "typename";
	public static final String TYPEDESC = "typedesc";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(GoodsType transientInstance) {
		log.debug("saving GoodsType instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	public void update(GoodsType type){
		getHibernateTemplate().update(type);
	}
	public void delete(GoodsType persistentInstance) {
		log.debug("deleting GoodsType instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public GoodsType findById(java.lang.String id) {
		log.debug("getting GoodsType instance with id: " + id);
		try {
			GoodsType instance = (GoodsType) getHibernateTemplate().get(
					"com.hrm.entity.GoodsType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(GoodsType instance) {
		log.debug("finding GoodsType instance by example");
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
		log.debug("finding GoodsType instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from GoodsType as model where model."
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
		log.debug("finding all GoodsType instances");
		try {
			String queryString = "from GoodsType";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public GoodsType merge(GoodsType detachedInstance) {
		log.debug("merging GoodsType instance");
		try {
			GoodsType result = (GoodsType) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(GoodsType instance) {
		log.debug("attaching dirty GoodsType instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(GoodsType instance) {
		log.debug("attaching clean GoodsType instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static GoodsTypeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (GoodsTypeDAO) ctx.getBean("GoodsTypeDAO");
	}
}