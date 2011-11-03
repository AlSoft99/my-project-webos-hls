package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.GoodsMonthStat;
import com.hrm.entity.GoodsMonthStatId;

/**
 * A data access object (DAO) providing persistence and search support for
 * GoodsMonthStat entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.GoodsMonthStat
 * @author MyEclipse Persistence Tools
 */

public class GoodsMonthStatDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(GoodsMonthStatDAO.class);
	// property constants
	public static final String STARTNUMBER = "startnumber";
	public static final String OUTNUMBER = "outnumber";
	public static final String RETURNNUMBER = "returnnumber";
	public static final String LASTNUMBER = "lastnumber";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(GoodsMonthStat transientInstance) {
		log.debug("saving GoodsMonthStat instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(GoodsMonthStat persistentInstance) {
		log.debug("deleting GoodsMonthStat instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public GoodsMonthStat findById(com.hrm.entity.GoodsMonthStatId id) {
		log.debug("getting GoodsMonthStat instance with id: " + id);
		try {
			GoodsMonthStat instance = (GoodsMonthStat) getHibernateTemplate()
					.get("com.hrm.entity.GoodsMonthStat", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(GoodsMonthStat instance) {
		log.debug("finding GoodsMonthStat instance by example");
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
		log.debug("finding GoodsMonthStat instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from GoodsMonthStat as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStartnumber(Object startnumber) {
		return findByProperty(STARTNUMBER, startnumber);
	}

	public List findByOutnumber(Object outnumber) {
		return findByProperty(OUTNUMBER, outnumber);
	}

	public List findByReturnnumber(Object returnnumber) {
		return findByProperty(RETURNNUMBER, returnnumber);
	}

	public List findByLastnumber(Object lastnumber) {
		return findByProperty(LASTNUMBER, lastnumber);
	}

	public List findByUpdtuser(Object updtuser) {
		return findByProperty(UPDTUSER, updtuser);
	}

	public List findAll() {
		log.debug("finding all GoodsMonthStat instances");
		try {
			String queryString = "from GoodsMonthStat";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public GoodsMonthStat merge(GoodsMonthStat detachedInstance) {
		log.debug("merging GoodsMonthStat instance");
		try {
			GoodsMonthStat result = (GoodsMonthStat) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(GoodsMonthStat instance) {
		log.debug("attaching dirty GoodsMonthStat instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(GoodsMonthStat instance) {
		log.debug("attaching clean GoodsMonthStat instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static GoodsMonthStatDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (GoodsMonthStatDAO) ctx.getBean("GoodsMonthStatDAO");
	}
}