package com.hrm.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.MenuLevelBinary;
import com.hrm.entity.RoleMenuBinary;
import com.hrm.entity.RoleMenuBinaryId;

/**
 * A data access object (DAO) providing persistence and search support for
 * RoleMenuBinary entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.RoleMenuBinary
 * @author MyEclipse Persistence Tools
 */

public class RoleMenuBinaryDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(RoleMenuBinaryDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(RoleMenuBinary transientInstance) {
		log.debug("saving RoleMenuBinary instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RoleMenuBinary persistentInstance) {
		log.debug("deleting RoleMenuBinary instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public void deleteList(List<RoleMenuBinary> list){
		getHibernateTemplate().deleteAll(list);
	}
	public int deleteHql(final String hql){
		return getHibernateTemplate().bulkUpdate(hql);
	}
	public RoleMenuBinary findById(com.hrm.entity.RoleMenuBinaryId id) {
		log.debug("getting RoleMenuBinary instance with id: " + id);
		try {
			RoleMenuBinary instance = (RoleMenuBinary) getHibernateTemplate()
					.get("com.hrm.entity.RoleMenuBinary", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(RoleMenuBinary instance) {
		log.debug("finding RoleMenuBinary instance by example");
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
		log.debug("finding RoleMenuBinary instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from RoleMenuBinary as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all RoleMenuBinary instances");
		try {
			String queryString = "from RoleMenuBinary";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public RoleMenuBinary merge(RoleMenuBinary detachedInstance) {
		log.debug("merging RoleMenuBinary instance");
		try {
			RoleMenuBinary result = (RoleMenuBinary) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RoleMenuBinary instance) {
		log.debug("attaching dirty RoleMenuBinary instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RoleMenuBinary instance) {
		log.debug("attaching clean RoleMenuBinary instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	public List findByHql(String hql,String[] where){
		return getHibernateTemplate().find(hql, where);
	}
	public static RoleMenuBinaryDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (RoleMenuBinaryDAO) ctx.getBean("RoleMenuBinaryDAO");
	}
}