package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hrm.query.TreeInfoQuery;

public class TestCaseMain extends TestCase {
	public void voTest(){
		BeanFactory factory = new ClassPathXmlApplicationContext("application*.xml");
		TreeInfoQuery query = factory.getBean("treeInfoQuery", TreeInfoQuery.class);
		Map map = new HashMap();
		map.put("node", "select new map(roleCode as id,roleName as text,roleDesc as qtip) from RoleInfo");
		map.put("child", "select new map(userId as id,userName as text,userName as qtip,roleInfo.roleCode as nodeId) from UserInfo");
		String result = query.test(map);
		System.out.println("result:\n"+result);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse("2010-06-01 06:59:00 000"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long time1 = cal.getTimeInMillis();
		long time2 = System.currentTimeMillis();
		long time = time2-time1;
		System.out.println("time:"+time/1000/60/60);
		System.out.println(Calendar.HOUR);
		
		String s = "ABCD";
		Pattern p = Pattern.compile("([A-Z]{1,2})([A-Z]{1,2})");
		Matcher matcher = p.matcher(s);
		System.out.println(matcher.matches());
		System.out.println(matcher.groupCount());;
		String[] a = p.split(s);
		for (int i = 1; i <= matcher.groupCount(); i++) {
			System.out.println("===:"+matcher.group(i));
		}
		System.out.println(a.length);
//		System.out.println(a[0]+"===="+a[1]);
	}

}
