package com.hrm.query;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.dao.UserInfoDAO;

public class QueryServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String sql = req.getParameter("sql");
		String start = req.getParameter("start");
		String limit = req.getParameter("limit");
		String result = "{totalProperty:100," +
							"root:[" +
								"{user_id:'36f7f356269d516c01269d516e030001',user_name:'guanrl',user_password:'1111',updt_time:'02/05/2010'}" +
							"]" +
						"}";
		try{
			BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getSession().getServletContext());
			UserInfoDAO userInfoDao = factory.getBean("UserInfoDAO", UserInfoDAO.class);
			/*List<UserInfo> list = userInfoDao.findByPage(new UserInfo(), Integer.parseInt(start), Integer.parseInt(limit));
			String json = "";
			for(UserInfo info:list){
//				json += "{user_id:'"+info.getUserId()+"',user_name:'"+info.getUserName()+"',user_password:'"+info.getUserPassword()+"',updt_time:'"+info.getUpdtTime().toString().substring(0, 10)+"'},";
			}
			json = json.substring(0, json.length()-1);
			result = "";
			result = "{totalProperty:"+userInfoDao.getCount("UserInfo")+",root:["+json+"]}";*/
			resp.getWriter().write(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
