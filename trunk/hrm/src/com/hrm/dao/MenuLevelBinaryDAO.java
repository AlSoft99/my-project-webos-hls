package com.hrm.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.MenuLevelBinary;

/**
 * A data access object (DAO) providing persistence and search support for
 * MenuLevelBinary entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.MenuLevelBinary
 * @author MyEclipse Persistence Tools
 */

public class MenuLevelBinaryDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(MenuLevelBinaryDAO.class);
	// property constants
	public static final String MENU_CODE = "menuCode";
	public static final String ROLE_CODE = "roleCode";
	public static final String MENU_TREE_NAME = "menuTreeName";
	public static final String MENU_TREE_TIPS = "menuTreeTips";
	public static final String MENU_PAGE = "menuPage";
	public static final String USER_NAME = "userName";

	protected void initDao() {
		// do nothing
	}

	public void save(MenuLevelBinary transientInstance) {
		log.debug("saving MenuLevelBinary instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MenuLevelBinary persistentInstance) {
		log.debug("deleting MenuLevelBinary instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MenuLevelBinary findById(java.lang.String id) {
		log.debug("getting MenuLevelBinary instance with id: " + id);
		try {
			MenuLevelBinary instance = (MenuLevelBinary) getHibernateTemplate()
					.get("com.hrm.entity.MenuLevelBinary", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MenuLevelBinary instance) {
		log.debug("finding MenuLevelBinary instance by example");
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
		log.debug("finding MenuLevelBinary instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MenuLevelBinary as model where model."
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

	public List findByMenuTreeName(Object menuTreeName) {
		return findByProperty(MENU_TREE_NAME, menuTreeName);
	}

	public List findByMenuTreeTips(Object menuTreeTips) {
		return findByProperty(MENU_TREE_TIPS, menuTreeTips);
	}

	public List findByMenuPage(Object menuPage) {
		return findByProperty(MENU_PAGE, menuPage);
	}

	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	public List findAll() {
		log.debug("finding all MenuLevelBinary instances");
		try {
			String queryString = "from MenuLevelBinary";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MenuLevelBinary merge(MenuLevelBinary detachedInstance) {
		log.debug("merging MenuLevelBinary instance");
		try {
			MenuLevelBinary result = (MenuLevelBinary) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	public void deleteList(List<MenuLevelBinary> list){
		getHibernateTemplate().deleteAll(list);
	}
	public void attachDirty(MenuLevelBinary instance) {
		log.debug("attaching dirty MenuLevelBinary instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	public void update(MenuLevelBinary instance){
		getHibernateTemplate().update(instance);
	}
	public List findByHql(String hql,String[] where){
		return getHibernateTemplate().find(hql, where);
	}
	public void attachClean(MenuLevelBinary instance) {
		log.debug("attaching clean MenuLevelBinary instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MenuLevelBinaryDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (MenuLevelBinaryDAO) ctx.getBean("MenuLevelBinaryDAO");
	}
}