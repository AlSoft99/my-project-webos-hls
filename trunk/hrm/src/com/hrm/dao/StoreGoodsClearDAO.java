package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.StoreGoodsClear;
import com.hrm.entity.StoreGoodsClearId;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoreGoodsClear entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.StoreGoodsClear
 * @author MyEclipse Persistence Tools
 */

public class StoreGoodsClearDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(StoreGoodsClearDAO.class);
	// property constants
	public static final String SSTORENUMBER = "sstorenumber";
	public static final String SOUTNUMBER = "soutnumber";
	public static final String SRETURNNUMBER = "sreturnnumber";
	public static final String SLASTNUMBER = "slastnumber";
	public static final String GSTORENUMBER = "gstorenumber";
	public static final String GOUTNUMBER = "goutnumber";
	public static final String GRETURNNUMBER = "greturnnumber";
	public static final String GLASTNUMBER = "glastnumber";
	public static final String EQUALS = "equals";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(StoreGoodsClear transientInstance) {
		log.debug("saving StoreGoodsClear instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StoreGoodsClear persistentInstance) {
		log.debug("deleting StoreGoodsClear instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StoreGoodsClear findById(com.hrm.entity.StoreGoodsClearId id) {
		log.debug("getting StoreGoodsClear instance with id: " + id);
		try {
			StoreGoodsClear instance = (StoreGoodsClear) getHibernateTemplate()
					.get("com.hrm.entity.StoreGoodsClear", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(StoreGoodsClear instance) {
		log.debug("finding StoreGoodsClear instance by example");
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
		log.debug("finding StoreGoodsClear instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from StoreGoodsClear as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySstorenumber(Object sstorenumber) {
		return findByProperty(SSTORENUMBER, sstorenumber);
	}

	public List findBySoutnumber(Object soutnumber) {
		return findByProperty(SOUTNUMBER, soutnumber);
	}

	public List findBySreturnnumber(Object sreturnnumber) {
		return findByProperty(SRETURNNUMBER, sreturnnumber);
	}

	public List findBySlastnumber(Object slastnumber) {
		return findByProperty(SLASTNUMBER, slastnumber);
	}

	public List findByGstorenumber(Object gstorenumber) {
		return findByProperty(GSTORENUMBER, gstorenumber);
	}

	public List findByGoutnumber(Object goutnumber) {
		return findByProperty(GOUTNUMBER, goutnumber);
	}

	public List findByGreturnnumber(Object greturnnumber) {
		return findByProperty(GRETURNNUMBER, greturnnumber);
	}

	public List findByGlastnumber(Object glastnumber) {
		return findByProperty(GLASTNUMBER, glastnumber);
	}

	public List findByEquals(Object equals) {
		return findByProperty(EQUALS, equals);
	}

	public List findByUpdtuser(Object updtuser) {
		return findByProperty(UPDTUSER, updtuser);
	}

	public List findAll() {
		log.debug("finding all StoreGoodsClear instances");
		try {
			String queryString = "from StoreGoodsClear";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StoreGoodsClear merge(StoreGoodsClear detachedInstance) {
		log.debug("merging StoreGoodsClear instance");
		try {
			StoreGoodsClear result = (StoreGoodsClear) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StoreGoodsClear instance) {
		log.debug("attaching dirty StoreGoodsClear instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StoreGoodsClear instance) {
		log.debug("attaching clean StoreGoodsClear instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static StoreGoodsClearDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (StoreGoodsClearDAO) ctx.getBean("StoreGoodsClearDAO");
	}
}