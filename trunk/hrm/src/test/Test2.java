package test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.hrm.util.StringUtil;

public class Test2 extends QuartzJobBean{
	public void handle(){
	    System.out.println("Test2");
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		handle();
		
	}
	public static void main(String[] args) throws Exception {
		BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext-trn.xml");
		TestBean bean = factory.getBean("testBean", TestBean.class);
		String a = "12123451231211";
		System.out.println("bean1:"+bean.getA3());
		
		StringUtil.newInstance().inputData(bean, a);
		System.out.println("bean2:"+bean.getA3());
	}
	
}
