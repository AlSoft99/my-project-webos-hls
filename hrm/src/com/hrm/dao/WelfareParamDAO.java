package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.WelfareParam;

/**
 * A data access object (DAO) providing persistence and search support for
 * WelfareParam entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.WelfareParam
 * @author MyEclipse Persistence Tools
 */

public class WelfareParamDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(WelfareParamDAO.class);
	// property constants
	public static final String PUBFUND = "pubfund";
	public static final String SAFEFUND = "safefund";
	public static final String CUREFUND = "curefund";
	public static final String INTEGFUND = "integfund";
	public static final String TAX = "tax";

	protected void initDao() {
		// do nothing
	}

	public void save(WelfareParam transientInstance) {
		log.debug("saving WelfareParam instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(WelfareParam persistentInstance) {
		log.debug("deleting WelfareParam instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public WelfareParam findById(java.lang.String id) {
		log.debug("getting WelfareParam instance with id: " + id);
		try {
			WelfareParam instance = (WelfareParam) getHibernateTemplate().get(
					"com.hrm.entity.WelfareParam", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(WelfareParam instance) {
		log.debug("finding WelfareParam instance by example");
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
		log.debug("finding WelfareParam instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from WelfareParam as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPubfund(Object pubfund) {
		return findByProperty(PUBFUND, pubfund);
	}

	public List findBySafefund(Object safefund) {
		return findByProperty(SAFEFUND, safefund);
	}

	public List findByCurefund(Object curefund) {
		return findByProperty(CUREFUND, curefund);
	}

	public List findByIntegfund(Object integfund) {
		return findByProperty(INTEGFUND, integfund);
	}

	public List findByTax(Object tax) {
		return findByProperty(TAX, tax);
	}

	public List findAll() {
		log.debug("finding all WelfareParam instances");
		try {
			String queryString = "from WelfareParam";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public WelfareParam merge(WelfareParam detachedInstance) {
		log.debug("merging WelfareParam instance");
		try {
			WelfareParam result = (WelfareParam) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(WelfareParam instance) {
		log.debug("attaching dirty WelfareParam instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public void attachClean(WelfareParam instance) {
		log.debug("attaching clean WelfareParam instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	public void update(WelfareParam instance){
		getHibernateTemplate().update(instance);
	}
	public static WelfareParamDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (WelfareParamDAO) ctx.getBean("WelfareParamDAO");
	}
}