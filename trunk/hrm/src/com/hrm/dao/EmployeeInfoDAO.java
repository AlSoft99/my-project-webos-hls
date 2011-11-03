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

import com.hrm.entity.EmployeeInfo;
import com.hrm.entity.UserInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * EmployeeInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.EmployeeInfo
 * @author MyEclipse Persistence Tools
 */

public class EmployeeInfoDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(EmployeeInfoDAO.class);
	// property constants
	public static final String WORKDAY = "workday";
	public static final String SALARY = "salary";
	public static final String WELFARE = "welfare";
	public static final String TAX = "tax";
	public static final String PUBFUND = "pubfund";
	public static final String SAFEFUND = "safefund";
	public static final String CUREFUND = "curefund";
	public static final String OTHERFUND = "otherfund";
	public static final String INTEGFUND = "integfund";

	protected void initDao() {
		// do nothing
	}

	public void save(EmployeeInfo transientInstance) {
		log.debug("saving EmployeeInfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(EmployeeInfo persistentInstance) {
		log.debug("deleting EmployeeInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EmployeeInfo findById(java.lang.String id) {
		log.debug("getting EmployeeInfo instance with id: " + id);
		try {
			EmployeeInfo instance = (EmployeeInfo) getHibernateTemplate().get(
					"com.hrm.entity.EmployeeInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(EmployeeInfo instance) {
		log.debug("finding EmployeeInfo instance by example");
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
		log.debug("finding EmployeeInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from EmployeeInfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByWorkday(Object workday) {
		return findByProperty(WORKDAY, workday);
	}

	public List findBySalary(Object salary) {
		return findByProperty(SALARY, salary);
	}

	public List findByWelfare(Object welfare) {
		return findByProperty(WELFARE, welfare);
	}

	public List findByTax(Object tax) {
		return findByProperty(TAX, tax);
	}

	public List findByPubfund(Object pubfund) {
		return findByProperty(PUBFUND, pubfund);
	}

	public List findBySafefund(Object safefund) {
		return findByProperty(SAFEFUND, safefund);
	}

	public List findByCurefund(Object curefund) {
		return findByProperty(CUREFUND, curefund);
	}

	public List findByOtherfund(Object otherfund) {
		return findByProperty(OTHERFUND, otherfund);
	}

	public List findByIntegfund(Object integfund) {
		return findByProperty(INTEGFUND, integfund);
	}

	public List findAll() {
		log.debug("finding all EmployeeInfo instances");
		try {
			String queryString = "from EmployeeInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public EmployeeInfo merge(EmployeeInfo detachedInstance) {
		log.debug("merging EmployeeInfo instance");
		try {
			EmployeeInfo result = (EmployeeInfo) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(EmployeeInfo instance) {
		log.debug("attaching dirty EmployeeInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(EmployeeInfo instance) {
		log.debug("attaching clean EmployeeInfo instance");
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
	public List findHqlByPage(final String hql){
		List list = getHibernateTemplate().executeFind(new HibernateCallback<List>(){

			public List doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(hql).list();
			}
		});
		return list;
	}
	public List findSqlByPage(final String sql,final int start, final int limit){
		List list = getHibernateTemplate().executeFind(new HibernateCallback<List>(){
			public List doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(sql).setFirstResult(start).setMaxResults(limit).list();
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
	public static EmployeeInfoDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (EmployeeInfoDAO) ctx.getBean("EmployeeInfoDAO");
	}
}