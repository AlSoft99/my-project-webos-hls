package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.GoodsList;

/**
 * A data access object (DAO) providing persistence and search support for
 * GoodsList entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hrm.entity.GoodsList
 * @author MyEclipse Persistence Tools
 */

public class GoodsListDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(GoodsListDAO.class);
	// property constants
	public static final String TYPEID = "typeid";
	public static final String GOODSNAME = "goodsname";
	public static final String GOODSDESC = "goodsdesc";
	public static final String GOODSNUMBER = "goodsnumber";
	public static final String PRICE = "price";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(GoodsList transientInstance) {
		log.debug("saving GoodsList instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	public void update(GoodsList list){
		getHibernateTemplate().update(list);
	}

	public void delete(GoodsList persistentInstance) {
		log.debug("deleting GoodsList instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public GoodsList findById(java.lang.String id) {
		log.debug("getting GoodsList instance with id: " + id);
		try {
			GoodsList instance = (GoodsList) getHibernateTemplate().get(
					"com.hrm.entity.GoodsList", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(GoodsList instance) {
		log.debug("finding GoodsList instance by example");
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
		log.debug("finding GoodsList instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from GoodsList as model where model."
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

	public List findByGoodsnumber(Object goodsnumber) {
		return findByProperty(GOODSNUMBER, goodsnumber);
	}

	public List findByPrice(Object price) {
		return findByProperty(PRICE, price);
	}

	public List findByUpdtuser(Object updtuser) {
		return findByProperty(UPDTUSER, updtuser);
	}

	public List findAll() {
		log.debug("finding all GoodsList instances");
		try {
			String queryString = "from GoodsList";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public GoodsList merge(GoodsList detachedInstance) {
		log.debug("merging GoodsList instance");
		try {
			GoodsList result = (GoodsList) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(GoodsList instance) {
		log.debug("attaching dirty GoodsList instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(GoodsList instance) {
		log.debug("attaching clean GoodsList instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static GoodsListDAO getFromApplicationContext(ApplicationContext ctx) {
		return (GoodsListDAO) ctx.getBean("GoodsListDAO");
	}
}