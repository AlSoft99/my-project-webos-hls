package com.hrm.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cn.socket.SocketManager;
import com.hrm.util.ClsFactory;
import com.hrm.vo.StatQuzrtzVo;

public class InitServlet extends HttpServlet {
	
	@Override
	public void init() throws ServletException {
		ClsFactory.newInstance().info("InitServlet启动");
		super.init();
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		ClsFactory.newInstance().setFactory(factory);
		StatQuzrtzVo base = factory.getBean("statQuzrtzVo", StatQuzrtzVo.class);
		try {
			base.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
//		ClsFactory.newInstance().info("InitServlet启动");
	}
}
