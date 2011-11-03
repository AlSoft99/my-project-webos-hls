package com.hrm.vo;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.dao.UserInfoDAO;
import com.hrm.entity.UserInfo;

public class UserAddVo extends HttpServlet {
	private static Logger logger = Logger.getLogger(UserAddVo.class);
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String user_name = req.getParameter("user_name");
		String user_id = req.getParameter("user_id");
		UserInfo info = new UserInfo();
		String result = "";
		boolean isSuccess = true;
		String msg = "保存成功";
		try{
			if(!user_id.equals("<font color=\"red\">系统将自动生成Id</font>")){
				info.setUserId(user_id);
			}
			info.setUpdtTime(new Date());
			info.setUserName(user_name);
//			info.setUserPassword("2222");
			BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getSession().getServletContext());
			UserInfoDAO dao = factory.getBean("UserInfoDAO", UserInfoDAO.class);
			dao.attachDirty(info);
		}catch(Exception e){
			isSuccess = false;
			msg = e.getMessage();
			logger.error("UserAddVo的service方法错误", e);
		}
		result = "{failure:"+isSuccess+",msg:'"+msg+"'}";
		resp.getWriter().write(result);
	}
	
}
