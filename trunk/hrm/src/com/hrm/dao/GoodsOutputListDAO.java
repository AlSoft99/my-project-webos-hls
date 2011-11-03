package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.GoodsOutputList;

/**
 * A data access object (DAO) providing persistence and search support for
 * GoodsOutputList entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.GoodsOutputList
 * @author MyEclipse Persistence Tools
 */

public class GoodsOutputListDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(GoodsOutputListDAO.class);
	// property constants
	public static final String OUTID = "outid";
	public static final String GOODSID = "goodsid";
	public static final String GOODSNUMBER = "goodsnumber";
	public static final String RETURNNUMBER = "returnnumber";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(GoodsOutputList transientInstance) {
		log.debug("saving GoodsOutputList instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(GoodsOutputList persistentInstance) {
		log.debug("deleting GoodsOutputList instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public GoodsOutputList findById(java.lang.String id) {
		log.debug("getting GoodsOutputList instance with id: " + id);
		try {
			GoodsOutputList instance = (GoodsOutputList) getHibernateTemplate()
					.get("com.hrm.entity.GoodsOutputList", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(GoodsOutputList instance) {
		log.debug("finding GoodsOutputList instance by example");
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
		log.debug("finding GoodsOutputList instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from GoodsOutputList as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOutid(Object outid) {
		return findByProperty(OUTID, outid);
	}

	public List findByGoodsid(Object goodsid) {
		return findByProperty(GOODSID, goodsid);
	}

	public List findByGoodsnumber(Object goodsnumber) {
		return findByProperty(GOODSNUMBER, goodsnumber);
	}

	public List findByReturnnumber(Object returnnumber) {
		return findByProperty(RETURNNUMBER, returnnumber);
	}

	public List findByUpdtuser(Object updtuser) {
		return findByProperty(UPDTUSER, updtuser);
	}

	public List findAll() {
		log.debug("finding all GoodsOutputList instances");
		try {
			String queryString = "from GoodsOutputList";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public GoodsOutputList merge(GoodsOutputList detachedInstance) {
		log.debug("merging GoodsOutputList instance");
		try {
			GoodsOutputList result = (GoodsOutputList) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(GoodsOutputList instance) {
		log.debug("attaching dirty GoodsOutputList instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(GoodsOutputList instance) {
		log.debug("attaching clean GoodsOutputList instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static GoodsOutputListDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (GoodsOutputListDAO) ctx.getBean("GoodsOutputListDAO");
	}
}