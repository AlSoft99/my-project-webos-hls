package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.OutUserList;
import com.hrm.entity.OutUserListId;

/**
 * A data access object (DAO) providing persistence and search support for
 * OutUserList entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.OutUserList
 * @author MyEclipse Persistence Tools
 */

public class OutUserListDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(OutUserListDAO.class);
	// property constants
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(OutUserList transientInstance) {
		log.debug("saving OutUserList instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(OutUserList persistentInstance) {
		log.debug("deleting OutUserList instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public OutUserList findById(com.hrm.entity.OutUserListId id) {
		log.debug("getting OutUserList instance with id: " + id);
		try {
			OutUserList instance = (OutUserList) getHibernateTemplate().get(
					"com.hrm.entity.OutUserList", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(OutUserList instance) {
		log.debug("finding OutUserList instance by example");
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
		log.debug("finding OutUserList instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from OutUserList as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUpdtuser(Object updtuser) {
		return findByProperty(UPDTUSER, updtuser);
	}

	public List findAll() {
		log.debug("finding all OutUserList instances");
		try {
			String queryString = "from OutUserList";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public OutUserList merge(OutUserList detachedInstance) {
		log.debug("merging OutUserList instance");
		try {
			OutUserList result = (OutUserList) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(OutUserList instance) {
		log.debug("attaching dirty OutUserList instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(OutUserList instance) {
		log.debug("attaching clean OutUserList instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static OutUserListDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (OutUserListDAO) ctx.getBean("OutUserListDAO");
	}
}