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

import com.hrm.entity.GoodsOutputInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * GoodsOutputInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hrm.entity.GoodsOutputInfo
 * @author MyEclipse Persistence Tools
 */

public class GoodsOutputInfoDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(GoodsOutputInfoDAO.class);
	// property constants
	public static final String OUTUSER = "outuser";
	public static final String GOODSDESC = "goodsdesc";
	public static final String UPDTUSER = "updtuser";

	protected void initDao() {
		// do nothing
	}

	public void save(GoodsOutputInfo transientInstance) {
		log.debug("saving GoodsOutputInfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(GoodsOutputInfo persistentInstance) {
		log.debug("deleting GoodsOutputInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public GoodsOutputInfo findById(java.lang.String id) {
		log.debug("getting GoodsOutputInfo instance with id: " + id);
		try {
			GoodsOutputInfo instance = (GoodsOutputInfo) getHibernateTemplate()
					.get("com.hrm.entity.GoodsOutputInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(GoodsOutputInfo instance) {
		log.debug("finding GoodsOutputInfo instance by example");
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
		log.debug("finding GoodsOutputInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from GoodsOutputInfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOutuser(Object outuser) {
		return findByProperty(OUTUSER, outuser);
	}

	public List findByGoodsdesc(Object goodsdesc) {
		return findByProperty(GOODSDESC, goodsdesc);
	}

	public List findByUpdtuser(Object updtuser) {
		return findByProperty(UPDTUSER, updtuser);
	}

	public List findAll() {
		log.debug("finding all GoodsOutputInfo instances");
		try {
			String queryString = "from GoodsOutputInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public GoodsOutputInfo merge(GoodsOutputInfo detachedInstance) {
		log.debug("merging GoodsOutputInfo instance");
		try {
			GoodsOutputInfo result = (GoodsOutputInfo) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(GoodsOutputInfo instance) {
		log.debug("attaching dirty GoodsOutputInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(GoodsOutputInfo instance) {
		log.debug("attaching clean GoodsOutputInfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	public List createHqlQuery(final String hql,final int start,final int limit){
		return getHibernateTemplate().execute(new HibernateCallback<List>(){

			public List doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (start>=0) {
					query.setFirstResult(start);
				}
				if(limit>=0){
					query.setMaxResults(limit);
				}
				return query.list();
			}
			
		});
	}
	public static GoodsOutputInfoDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (GoodsOutputInfoDAO) ctx.getBean("GoodsOutputInfoDAO");
	}
}