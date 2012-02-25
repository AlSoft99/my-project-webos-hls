package com.hrm.control;


import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.util.StringUtil;
import com.hrm.vo.BaseVo;
/**
 * 使用该控制器,需要在页面传输时加入entity=类(全路径)
 * 如:test.do?entity=com.entity.Emp
 * @author Administrator
 *
 */
@Controller
public class MainController {
	private static Logger logger = Logger.getLogger(MainController.class);
	@RequestMapping(value="/*.do" ,method=RequestMethod.POST)
	public void controllerPost(HttpServletRequest request,HttpServletResponse response) throws Exception{
		managerData(request,response);
	}
	@RequestMapping(value="/*.do" ,method=RequestMethod.GET)
	public void controllerGet(HttpServletRequest request,HttpServletResponse response) throws Exception{
		managerData(request,response);
	}
	private void managerData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map<String,String[]> map = request.getParameterMap();
		Map<String,String> paramsMap = new HashMap<String,String>();
		String entity = request.getParameter("entity");
		Request req = new Request();
		try {
			req = StringUtil.newInstance().toRequest(request, response);
			
			MessageSource resources = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
//			String message = resources.getMessage("welcome", null, "Default", null);
			req = toLogicAction(request,response,req);
			if(req.getResponse() instanceof String){
				response.getWriter().println((String)req.getResponse());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage()!=null){
				response.getWriter().write(e.getMessage());
			}else if(!"".equals(req.getResponse())){
				response.getWriter().write(req.getResponse().toString());
			}else{
				response.getWriter().write(e.toString());
			}
			logger.error("MainController的转发方法出错:", e);
		} 
	}
	private Request toLogicAction(HttpServletRequest request,HttpServletResponse response,Request req) throws Exception{
		String uri = request.getServletPath();
		String springName = uri.substring(1, uri.indexOf("."));
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		BaseVo base = factory.getBean(springName, BaseVo.class);
		req = base.execute(req);
		return req;
	}
	/**
	 * 实体属性存放列表
	 * @param obj
	 */
	private void toEntityString(Object obj){
		String result = "";
		PropertyDescriptor[] property = PropertyUtils.getPropertyDescriptors(obj);
		for (int i = 0; i < property.length; i++) {
			String propertyTarget = property[i].getName();
			String propertyValue;
			try {
				propertyValue = PropertyUtils.getProperty(obj, propertyTarget)+"";
				result += propertyTarget+":"+propertyValue+" ";
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		System.out.println("实体:"+result);
	}
	//数据类型绑定
	/*@InitBinder
	public void initBinder(WebRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),false));
	}*/
	
	
}
