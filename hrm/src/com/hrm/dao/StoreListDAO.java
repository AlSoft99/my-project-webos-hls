package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.StoreList;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoreList entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hrm.entity.StoreList
 * @author MyEclipse Persistence Tools
 */

public class StoreListDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(StoreListDAO.class);
	// property constants
	public static final String TYPEID = "typeid";
	public static final String GOODSNAME = "goodsname";
	public static final String GOODSDESC = "goodsdesc";
	public static final String INITNUMBER = "initnumber";
	public static final String OUTNUMBER = "outnumber";
	public static final String LASTNUMBER = "lastnumber";
	public static final String PRICE = "price";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(StoreList transientInstance) {
		log.debug("saving StoreList instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StoreList persistentInstance) {
		log.debug("deleting StoreList instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StoreList findById(java.lang.String id) {
		log.debug("getting StoreList instance with id: " + id);
		try {
			StoreList instance = (StoreList) getHibernateTemplate().get(
					"com.hrm.entity.StoreList", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(StoreList instance) {
		log.debug("finding StoreList instance by example");
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
		log.debug("finding StoreList instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from StoreList as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTypeid(Object typeid) {
		return findByProperty(TYPEID, typeid);
	}

	public List findByGoodsname(Object goodsname) {
		return findByProperty(GOODSNAME, goodsname);
	}

	public List findByGoodsdesc(Object goodsdesc) {
		return findByProperty(GOODSDESC, goodsdesc);
	}

	public List findByInitnumber(Object initnumber) {
		return findByProperty(INITNUMBER, initnumber);
	}

	public List findByOutnumber(Object outnumber) {
		return findByProperty(OUTNUMBER, outnumber);
	}

	public List findByLastnumber(Object lastnumber) {
		return findByProperty(LASTNUMBER, lastnumber);
	}

	public List findByPrice(Object price) {
		return findByProperty(PRICE, price);
	}

	public List findByUpdtuser(Object updtuser) {
		return findByProperty(UPDTUSER, updtuser);
	}

	public List findAll() {
		log.debug("finding all StoreList instances");
		try {
			String queryString = "from StoreList";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StoreList merge(StoreList detachedInstance) {
		log.debug("merging StoreList instance");
		try {
			StoreList result = (StoreList) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StoreList instance) {
		log.debug("attaching dirty StoreList instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StoreList instance) {
		log.debug("attaching clean StoreList instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static StoreListDAO getFromApplicationContext(ApplicationContext ctx) {
		return (StoreListDAO) ctx.getBean("StoreListDAO");
	}
}