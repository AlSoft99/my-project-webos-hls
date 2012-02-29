package com.hrm.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;

import test.DBEntity;
import test.InputDataBySerial;

import com.hrm.dao.HibernateSessionDAO;

public class InitDB {
	//必要的导入表
	private String[] need_import_node_name_delete = {"MenuLevelBinary","MenuLevelStair","ParamsType","ParamsList","RoleMenuBinary","RoleMenuStair","RoleInfo","UserInfo"};
	private String[] need_import_node_name = {"MenuLevelStair","MenuLevelBinary","ParamsType","ParamsList","RoleMenuStair","RoleMenuBinary","RoleInfo","UserInfo"};
	//必要的清除数据
	private String[] need_clear_node_name = {"DayGoodsClear","GoodsMonthStat","GoodsOutputInfo","GoodsOutputList", "GoodsStat","StoreGoodsClear","StoreInputInfo","StoreInputList","StoreOutputInfo", "StoreOutputList"};

	public void exportDB(HibernateSessionDAO dao, String filepath ){
		try {
            FileOutputStream fos = new FileOutputStream(filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            DBEntity entity = createEntity(dao);
            oos.writeObject(entity);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	private DBEntity createEntity(HibernateSessionDAO dao) throws ClassNotFoundException{
		DBEntity dbentity = new DBEntity();
		for (int i = 0; i < need_import_node_name.length; i++) {
			dbentity.db_save.put(need_import_node_name[i],dao.createHqlQuery("from "+need_import_node_name[i]+" "));
		}
		return dbentity;
	}
	public void importDB(HibernateSessionDAO dao, String filepath ){
		try {
			FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            DBEntity entity = (DBEntity)ois.readObject();
            for (int i = 0; i < need_import_node_name.length; i++) {
            	List listEntity = entity.db_save.get(need_import_node_name[i]);
            	for (int j = 0; j < listEntity.size(); j++) {
            		dao.getHibernateTemplate().save(listEntity.get(j));
				}
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	public void clearDB(HibernateSessionDAO dao ){
		for (int i = 0; i < need_clear_node_name.length; i++) {
			dao.createHqlExcute("delete from "+need_clear_node_name[i]);
		}
		
		/*dao.getHibernateTemplate().execute(new HibernateCallback<Boolean>() {

			@Override
			public Boolean doInHibernate(Session session) throws HibernateException,
					SQLException {
				for (int i = 0; i < need_clear_node_name.length; i++) {
					session.createQuery("delete from ")
				}
				return null;
			}
		});*/
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InitDB data = new InitDB();
		BeanFactory factory = new ClassPathXmlApplicationContext("refContextImpl.xml");
		HibernateSessionDAO dao = factory.getBean("HibernateSessionDAO", HibernateSessionDAO.class);
		//导出数据
		//data.exportDB(dao);
		//导入数据
		//data.importDB(dao);
		//清除日常的数据
		//data.clearDB(dao);
	}

}
