package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.MenuLevelStair;

/**
 * A data access object (DAO) providing persistence and search support for
 * MenuLevelStair entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.MenuLevelStair
 * @author MyEclipse Persistence Tools
 */

public class MenuLevelStairDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(MenuLevelStairDAO.class);
	// property constants
	public static final String MENU_CODE = "menuCode";
	public static final String ROLE_CODE = "roleCode";
	public static final String MENU_NAME = "menuName";
	public static final String MENU_TIPS = "menuTips";
	public static final String USER_NAME = "userName";

	protected void initDao() {
		// do nothing
	}

	public void save(MenuLevelStair transientInstance) {
		log.debug("saving MenuLevelStair instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MenuLevelStair persistentInstance) {
		log.debug("deleting MenuLevelStair instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public void update(MenuLevelStair entity){
		getHibernateTemplate().update(entity);
	}
	public MenuLevelStair findById(java.lang.String id) {
		log.debug("getting MenuLevelStair instance with id: " + id);
		try {
			MenuLevelStair instance = (MenuLevelStair) getHibernateTemplate()
					.get("com.hrm.entity.MenuLevelStair", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MenuLevelStair instance) {
		log.debug("finding MenuLevelStair instance by example");
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
		log.debug("finding MenuLevelStair instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MenuLevelStair as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMenuCode(Object menuCode) {
		return findByProperty(MENU_CODE, menuCode);
	}

	public List findByRoleCode(Object roleCode) {
		return findByProperty(ROLE_CODE, roleCode);
	}

	public List findByMenuName(Object menuName) {
		return findByProperty(MENU_NAME, menuName);
	}

	public List findByMenuTips(Object menuTips) {
		return findByProperty(MENU_TIPS, menuTips);
	}

	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	public List findAll() {
		log.debug("finding all MenuLevelStair instances");
		try {
			String queryString = "from MenuLevelStair";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public List findByHql(String hql,String[] where){
		return getHibernateTemplate().find(hql, where);
	}
	public MenuLevelStair merge(MenuLevelStair detachedInstance) {
		log.debug("merging MenuLevelStair instance");
		try {
			MenuLevelStair result = (MenuLevelStair) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MenuLevelStair instance) {
		log.debug("attaching dirty MenuLevelStair instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MenuLevelStair instance) {
		log.debug("attaching clean MenuLevelStair instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MenuLevelStairDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (MenuLevelStairDAO) ctx.getBean("MenuLevelStairDAO");
	}
}