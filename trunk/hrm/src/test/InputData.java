package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.hrm.dao.HibernateSessionDAO;

public class InputData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InputData data = new InputData();
//		Document doc = data.getXmlDocument(data.parseXml());
		Document doc = data.getFileXmlDocment("db\\hrm7.xml");
		BeanFactory factory = new ClassPathXmlApplicationContext("refContextImpl.xml");
		HibernateSessionDAO dao = factory.getBean("HibernateSessionDAO", HibernateSessionDAO.class);
		data.importData(dao, doc);
		data.clearData(dao, doc);
	}
	//必要的导入表
	private String[] need_import_node_name = {"menu_level_stair","menu_level_binary","params_type","params_list","role_menu_stair","role_menu_binary"};
	//必要的清除数据
	private String[] need_clear_node_name = {"day_goods_clear","goods_month_stat","goods_output_info","goods_output_list",
			"goods_stat","store_goods_clear","store_input_info","store_input_list","store_output_info",
			"store_output_list"};
	public String parseXml(){
		BufferedReader br = null;
		String result = "";
		String line = "";
		try {
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream("db\\hrm7.xml"),"UTF-8"));
			while( (line = br.readLine())!=null){
				result += line;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if (br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	/**
	 * 得到xml的document对象
	 * @param xml
	 * @return
	 */
	public Document getXmlDocument(String xml){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			StringReader sr = new StringReader(xml);
			InputSource is = new InputSource(sr);
			document = builder.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
	/**
	 * 通过文件获取xml的document对象
	 * @param filepath
	 * @return
	 */
	public Document getFileXmlDocment(String filepath){
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(filepath));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
		
	}
	public Integer execute(HibernateSessionDAO dao,final String sql){
		return dao.getHibernateTemplate().execute(new HibernateCallback<Integer>(){
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(sql).executeUpdate();
			}
		});
	}
	/**
	 * 导入数据表need_import_node_name
	 * @param dao
	 * @param doc
	 */
	public void importData(HibernateSessionDAO dao,Document doc){
		for (int i = 0; i < need_import_node_name.length; i++) {
			NodeList node = doc.getElementsByTagName(need_import_node_name[i]);
			final String del = "delete from "+need_import_node_name[i];
			execute(dao, del);
			for (int j = 0; j < node.getLength(); j++) {
				if(node.item(j)!=null){
					NodeList temp = node.item(j).getChildNodes();
					if(temp!=null){
						for (int k = 0; k < temp.getLength(); k++) {
							NodeList cTmp = temp.item(k).getChildNodes();
							if(cTmp!=null){
								String sql1 = "";
								String sql2 = "";
								for (int l = 0; l < cTmp.getLength(); l++) {
									if(cTmp.item(l)!=null){
										String nodename = cTmp.item(l).getNodeName();
										String text = cTmp.item(l).getTextContent();
										if(!nodename.equals("#text")){
											sql1+= nodename+",";
											sql2+= "'"+text+"',";
										}
									}
								}
								if(!sql1.equals("") && !sql2.equals("")){
									sql1 = sql1.substring(0, sql1.length()-1);
									sql2 = sql2.substring(0, sql2.length()-1);
									final String sql = "insert into "+need_import_node_name[i]+" ("+sql1+") values("+sql2+")";
									int result = execute(dao, sql);
									if(result==1){
										System.out.println(need_import_node_name[i]+"插入成功");
									}else{
										System.out.println(need_import_node_name[i]+"插入失败");
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void clearData(HibernateSessionDAO dao,Document doc){
		for (int i = 0; i < need_clear_node_name.length; i++) {
			final String sql = "delete from "+need_clear_node_name[i];
			int result = execute(dao, sql);
			if(result>0){
				System.out.println(need_clear_node_name[i]+"删除成功!");
			}else{
				System.out.println(need_clear_node_name[i]+"删除失败,或者无数据可删除!");
			}

		}
	}
}
