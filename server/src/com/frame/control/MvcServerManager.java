package com.frame.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.frame.util.StringUtil;
import com.frame.vo.BaseVo;
import com.frame.vo.Request;

@Controller
public class MvcServerManager {
	@RequestMapping(value="/*.do" ,method=RequestMethod.POST)
	public void controllerPost(HttpServletRequest request,HttpServletResponse response) throws Exception{
		controllerGet(request,response);
	}
	@RequestMapping(value="/*.do" ,method=RequestMethod.GET)
	public void controllerGet(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Request req = logicAction(request,response);
		response.getWriter().println(req.getResponse().toString());
	}
	
	private Request logicAction(HttpServletRequest request,HttpServletResponse response){
		String uri = request.getServletPath();
		String springName = uri.substring(1, uri.indexOf("."));
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		BaseVo base = factory.getBean(springName, BaseVo.class);
		Request req = null;
		try {
			req =  StringUtil.newInstant().toRequest(request, response);
			req = base.execute(req);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return req;
	}
}
