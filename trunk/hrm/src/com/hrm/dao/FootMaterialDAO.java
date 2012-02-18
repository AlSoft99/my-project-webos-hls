package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.FootMaterial;

/**
 * A data access object (DAO) providing persistence and search support for
 * FootMaterial entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hrm.entity.FootMaterial
 * @author MyEclipse Persistence Tools
 */

public class FootMaterialDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(FootMaterialDAO.class);

	protected void initDao() {
		// do nothing
	}

	public void save(FootMaterial transientInstance) {
		log.debug("saving FootMaterial instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FootMaterial persistentInstance) {
		log.debug("deleting FootMaterial instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FootMaterial findById(java.lang.String id) {
		log.debug("getting FootMaterial instance with id: " + id);
		try {
			FootMaterial instance = (FootMaterial) getHibernateTemplate().get(
					"com.hrm.entity.FootMaterial", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(FootMaterial instance) {
		log.debug("finding FootMaterial instance by example");
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
		log.debug("finding FootMaterial instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FootMaterial as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all FootMaterial instances");
		try {
			String queryString = "from FootMaterial";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FootMaterial merge(FootMaterial detachedInstance) {
		log.debug("merging FootMaterial instance");
		try {
			FootMaterial result = (FootMaterial) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FootMaterial instance) {
		log.debug("attaching dirty FootMaterial instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FootMaterial instance) {
		log.debug("attaching clean FootMaterial instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static FootMaterialDAO getFromApplicationContext(ApplicationContext ctx) {
		return (FootMaterialDAO) ctx.getBean("FootMaterialDAO");
	}
}