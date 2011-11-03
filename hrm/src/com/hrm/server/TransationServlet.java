package com.hrm.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.vo.BaseVo;

public class TransationServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(TransationServlet.class);
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result = "";
		try{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String uri = request.getServletPath();
			String springName = uri.substring(1, uri.indexOf("."));
			BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
			BaseVo base = factory.getBean(springName, BaseVo.class);
//			result = base.execute(request, response);
			response.getWriter().write(result);
		}catch(Exception e){
			
			if(!result.equals("")){
				response.getWriter().write(result);
			}else if(result!=null && e.getMessage()!=null){
				response.getWriter().write(e.getMessage());
			}else{
				response.getWriter().write(e.toString());
			}
			logger.error("TransationServlet的转发方法出错:", e);
		}
	}

}
