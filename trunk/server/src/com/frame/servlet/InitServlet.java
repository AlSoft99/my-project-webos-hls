package com.frame.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class InitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3179606355003455141L;

	@Override
	public void init() throws ServletException {
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		ServletFactory.newInstant().setFactory(factory);
		super.init();
	}

}
