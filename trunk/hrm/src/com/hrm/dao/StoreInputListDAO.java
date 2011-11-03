package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.StoreInputList;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoreInputList entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.StoreInputList
 * @author MyEclipse Persistence Tools
 */

public class StoreInputListDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(StoreInputListDAO.class);
	// property constants
	public static final String INID = "inid";
	public static final String GOODSID = "goodsid";
	public static final String GOODSNUMBER = "goodsnumber";
	public static final String GOODSPRICE = "goodsprice";
	public static final String GOODSDESC = "goodsdesc";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(StoreInputList transientInstance) {
		log.debug("saving StoreInputList instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StoreInputList persistentInstance) {
		log.debug("deleting StoreInputList instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StoreInputList findById(java.lang.String id) {
		log.debug("getting StoreInputList instance with id: " + id);
		try {
			StoreInputList instance = (StoreInputList) getHibernateTemplate()
					.get("com.hrm.entity.StoreInputList", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(StoreInputList instance) {
		log.debug("finding StoreInputList instance by example");
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
		log.debug("finding StoreInputList instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from StoreInputList as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByInid(Object inid) {
		return findByProperty(INID, inid);
	}

	public List findByGoodsid(Object goodsid) {
		return findByProperty(GOODSID, goodsid);
	}

	public List findByGoodsnumber(Object goodsnumber) {
		return findByProperty(GOODSNUMBER, goodsnumber);
	}

	public List findByGoodsprice(Object goodsprice) {
		return findByProperty(GOODSPRICE, goodsprice);
	}

	public List findByGoodsdesc(Object goodsdesc) {
		return findByProperty(GOODSDESC, goodsdesc);
	}

	public List findByUpdtuser(Object updtuser) {
		return findByProperty(UPDTUSER, updtuser);
	}

	public List findAll() {
		log.debug("finding all StoreInputList instances");
		try {
			String queryString = "from StoreInputList";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StoreInputList merge(StoreInputList detachedInstance) {
		log.debug("merging StoreInputList instance");
		try {
			StoreInputList result = (StoreInputList) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StoreInputList instance) {
		log.debug("attaching dirty StoreInputList instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StoreInputList instance) {
		log.debug("attaching clean StoreInputList instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static StoreInputListDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (StoreInputListDAO) ctx.getBean("StoreInputListDAO");
	}
}