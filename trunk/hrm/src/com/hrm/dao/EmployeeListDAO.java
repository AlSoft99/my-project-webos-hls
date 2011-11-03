package com.hrm.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hrm.entity.EmployeeList;
import com.hrm.entity.EmployeeListId;
import com.hrm.entity.UserInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * EmployeeList entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.EmployeeList
 * @author MyEclipse Persistence Tools
 */

public class EmployeeListDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(EmployeeListDAO.class);
	// property constants
	public static final String OTHERSMART = "othersmart";
	public static final String FACTSALARY = "factsalary";
	public static final String ISSEND = "issend";

	protected void initDao() {
		// do nothing
	}

	public void save(EmployeeList transientInstance) {
		log.debug("saving EmployeeList instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(EmployeeList persistentInstance) {
		log.debug("deleting EmployeeList instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public void deleteList(List<EmployeeList> list){
		getHibernateTemplate().deleteAll(list);
	}

	public EmployeeList findById(com.hrm.entity.EmployeeListId id) {
		log.debug("getting EmployeeList instance with id: " + id);
		try {
			EmployeeList instance = (EmployeeList) getHibernateTemplate().get(
					"com.hrm.entity.EmployeeList", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(EmployeeList instance) {
		log.debug("finding EmployeeList instance by example");
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
		log.debug("finding EmployeeList instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from EmployeeList as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOthersmart(Object othersmart) {
		return findByProperty(OTHERSMART, othersmart);
	}

	public List findByFactsalary(Object factsalary) {
		return findByProperty(FACTSALARY, factsalary);
	}

	public List findByIssend(Object issend) {
		return findByProperty(ISSEND, issend);
	}

	public List findAll() {
		log.debug("finding all EmployeeList instances");
		try {
			String queryString = "from EmployeeList";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public EmployeeList merge(EmployeeList detachedInstance) {
		log.debug("merging EmployeeList instance");
		try {
			EmployeeList result = (EmployeeList) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(EmployeeList instance) {
		log.debug("attaching dirty EmployeeList instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(EmployeeList instance) {
		log.debug("attaching clean EmployeeList instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	public List findHqlByPage(final String hql ,final int start ,final int limit){
		List list = getHibernateTemplate().executeFind(new HibernateCallback<List>(){

			public List doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
			}
		});
		return list;
	}
	public List findByPage(UserInfo userInfo,int start,int limit){
		return getHibernateTemplate().findByExample(userInfo, start, limit);
	}
	public int getCount(String table){
		return Integer.parseInt(getHibernateTemplate().find("select count(*) from "+table).get(0).toString());
	}

	public static EmployeeListDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (EmployeeListDAO) ctx.getBean("EmployeeListDAO");
	}
}