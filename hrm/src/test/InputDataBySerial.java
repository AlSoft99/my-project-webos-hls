package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.RoleInfo;
import com.hrm.entity.UserInfo;

public class InputDataBySerial {
	//必要的导入表
	private String[] need_import_node_name_delete = {"MenuLevelBinary","MenuLevelStair","ParamsType","ParamsList","RoleMenuBinary","RoleMenuStair","RoleInfo","UserInfo"};
	private String[] need_import_node_name = {"MenuLevelStair","MenuLevelBinary","ParamsType","ParamsList","RoleMenuStair","RoleMenuBinary","RoleInfo","UserInfo"};
	//必要的清除数据
	private String[] need_clear_node_name = {"DayGoodsClear","GoodsMonthStat","GoodsOutputInfo","GoodsOutputList", "GoodsStat","StoreGoodsClear","StoreInputInfo","StoreInputList","StoreOutputInfo", "StoreOutputList"};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InputDataBySerial data = new InputDataBySerial();
		BeanFactory factory = new ClassPathXmlApplicationContext("refContextImpl.xml");
		HibernateSessionDAO dao = factory.getBean("HibernateSessionDAO", HibernateSessionDAO.class);
		//data.exportDB(dao);
		data.importDB(dao);
	}

	public void exportDB(HibernateSessionDAO dao ){
		try {
            FileOutputStream fos = new FileOutputStream("db\\db");
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
	public void importDB(HibernateSessionDAO dao ){
		try {
			FileInputStream fis = new FileInputStream("db\\db");
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
}
