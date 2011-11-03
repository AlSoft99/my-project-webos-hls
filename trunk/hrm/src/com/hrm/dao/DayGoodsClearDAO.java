package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.DayGoodsClear;
import com.hrm.entity.DayGoodsClearId;

/**
 * A data access object (DAO) providing persistence and search support for
 * DayGoodsClear entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.DayGoodsClear
 * @author MyEclipse Persistence Tools
 */

public class DayGoodsClearDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(DayGoodsClearDAO.class);
	// property constants
	public static final String STORENUMBER = "storenumber";
	public static final String OUTNUMBER = "outnumber";
	public static final String LEAVENUMBER = "leavenumber";
	public static final String EQUALS = "equals";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(DayGoodsClear transientInstance) {
		log.debug("saving DayGoodsClear instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DayGoodsClear persistentInstance) {
		log.debug("deleting DayGoodsClear instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DayGoodsClear findById(com.hrm.entity.DayGoodsClearId id) {
		log.debug("getting DayGoodsClear instance with id: " + id);
		try {
			DayGoodsClear instance = (DayGoodsClear) getHibernateTemplate()
					.get("com.hrm.entity.DayGoodsClear", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DayGoodsClear instance) {
		log.debug("finding DayGoodsClear instance by example");
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
		log.debug("finding DayGoodsClear instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DayGoodsClear as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStorenumber(Object storenumber) {
		return findByProperty(STORENUMBER, storenumber);
	}

	public List findByOutnumber(Object outnumber) {
		return findByProperty(OUTNUMBER, outnumber);
	}

	public List findByLeavenumber(Object leavenumber) {
		return findByProperty(LEAVENUMBER, leavenumber);
	}

	public List findByEquals(Object equals) {
		return findByProperty(EQUALS, equals);
	}

	public List findByUpdtuser(Object updtuser) {
		return findByProperty(UPDTUSER, updtuser);
	}

	public List findAll() {
		log.debug("finding all DayGoodsClear instances");
		try {
			String queryString = "from DayGoodsClear";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DayGoodsClear merge(DayGoodsClear detachedInstance) {
		log.debug("merging DayGoodsClear instance");
		try {
			DayGoodsClear result = (DayGoodsClear) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DayGoodsClear instance) {
		log.debug("attaching dirty DayGoodsClear instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DayGoodsClear instance) {
		log.debug("attaching clean DayGoodsClear instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DayGoodsClearDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (DayGoodsClearDAO) ctx.getBean("DayGoodsClearDAO");
	}
}