package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.StoreInputInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoreInputInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.StoreInputInfo
 * @author MyEclipse Persistence Tools
 */

public class StoreInputInfoDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(StoreInputInfoDAO.class);
	// property constants
	public static final String PREPRICE = "preprice";
	public static final String AUTPRICE = "autprice";
	public static final String MANGUSER = "manguser";
	public static final String ISMANG = "ismang";
	public static final String ACCOUSER = "accouser";
	public static final String ISACCO = "isacco";
	public static final String STOREUSER = "storeuser";
	public static final String ISSTORE = "isstore";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(StoreInputInfo transientInstance) {
		log.debug("saving StoreInputInfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StoreInputInfo persistentInstance) {
		log.debug("deleting StoreInputInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StoreInputInfo findById(java.lang.String id) {
		log.debug("getting StoreInputInfo instance with id: " + id);
		try {
			StoreInputInfo instance = (StoreInputInfo) getHibernateTemplate()
					.get("com.hrm.entity.StoreInputInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(StoreInputInfo instance) {
		log.debug("finding StoreInputInfo instance by example");
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
		log.debug("finding StoreInputInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from StoreInputInfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPreprice(Object preprice) {
		return findByProperty(PREPRICE, preprice);
	}

	public List findByAutprice(Object autprice) {
		return findByProperty(AUTPRICE, autprice);
	}

	public List findByManguser(Object manguser) {
		return findByProperty(MANGUSER, manguser);
	}

	public List findByIsmang(Object ismang) {
		return findByProperty(ISMANG, ismang);
	}

	public List findByAccouser(Object accouser) {
		return findByProperty(ACCOUSER, accouser);
	}

	public List findByIsacco(Object isacco) {
		return findByProperty(ISACCO, isacco);
	}

	public List findByStoreuser(Object storeuser) {
		return findByProperty(STOREUSER, storeuser);
	}

	public List findByIsstore(Object isstore) {
		return findByProperty(ISSTORE, isstore);
	}

	public List findByUpdtuser(Object updtuser) {
		return findByProperty(UPDTUSER, updtuser);
	}

	public List findAll() {
		log.debug("finding all StoreInputInfo instances");
		try {
			String queryString = "from StoreInputInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StoreInputInfo merge(StoreInputInfo detachedInstance) {
		log.debug("merging StoreInputInfo instance");
		try {
			StoreInputInfo result = (StoreInputInfo) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StoreInputInfo instance) {
		log.debug("attaching dirty StoreInputInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StoreInputInfo instance) {
		log.debug("attaching clean StoreInputInfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static StoreInputInfoDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (StoreInputInfoDAO) ctx.getBean("StoreInputInfoDAO");
	}
}