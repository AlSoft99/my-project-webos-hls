package com.ux.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;
import com.ux.entity.ParamsType;

@Repository
public class ParamsTypeDao extends BaseDao{
	public void save(ParamsType entity){
		getHibernateTemplate().save(entity);
	}
	public boolean isEmpty(){
		List<Long> list = getHibernateTemplate().find("select count(*) from ParamsType");
		long count = list.get(0);
		if(count==0){
			return true;
		}else{
			return false;
		}
	}
	public boolean delete(final String where){
		return getHibernateTemplate().execute(new HibernateCallback<Boolean>() {
			@Override
			public Boolean doInHibernate(Session session)
					throws HibernateException, SQLException {
				int update = session.createQuery("delete from ParamsType "+where).executeUpdate();
				if(update==0){
					return false;
				}else{
					return true;
				}
			}
		});
	}
}
