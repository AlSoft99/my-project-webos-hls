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
import com.hrm.util.InitDB;

public class InputDataBySerial {/**
	 * @param args
	 */
	public static void main(String[] args) {
		InitDB data = new InitDB();
		BeanFactory factory = new ClassPathXmlApplicationContext("refContextImpl.xml");
		HibernateSessionDAO dao = factory.getBean("HibernateSessionDAO", HibernateSessionDAO.class);
		//导出数据
		data.exportDB(dao);
		//导入数据
		//data.importDB(dao);
		//清除必要的数据
		//data.clearDB(dao);
	}
}
