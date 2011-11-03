package com.hrm.user;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.RoleInfo;
import com.hrm.entity.UserInfo;
import com.hrm.util.StringUtil;

public class LoginServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String userCode = request.getParameter("user_code");
		String userPwd = request.getParameter("user_pwd");
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		HibernateSessionDAO user = factory.getBean("HibernateSessionDAO", HibernateSessionDAO.class);
		List<UserInfo> list = user.createHqlQuery("from UserInfo a where  a.userCode='"+userCode+"'");
		
		if(list.size()>0){
			UserInfo userInfo = list.get(0);
			long time = StringUtil.newInstance().subDate(new Date(), userInfo.getSyscdtime(), Calendar.HOUR);
			if(userPwd.equals(userInfo.getUserPwd()) && userInfo.getUsercd().equals("0") ){
				if(Integer.parseInt(userInfo.getSyscd())<3 || (Integer.parseInt(userInfo.getSyscd())>=3 && time>=2)){
					List<RoleInfo> roleList = user.createHqlQuery("from RoleInfo where roleCode='"+userInfo.getRoleInfo().getRoleCode()+"'");
					userInfo.setRoleInfo(roleList.get(0));
					userInfo.setSyscd("0");
					user.getHibernateTemplate().update(userInfo);
					request.getSession().setAttribute("userInfo", userInfo);
					response.getWriter().print("{success:true,msg:'登陆成功'}");
				}else{
					response.getWriter().print("{failure:true,msg:'该用户已被冻结！请管理员解冻或者过两小时后再进行登陆！'}");
				}
			}else if(Integer.parseInt(userInfo.getSyscd())>=3 || userInfo.getUsercd().equals("1")){
				response.getWriter().print("{failure:true,msg:'该用户已被冻结！请管理员解冻或者过两小时后再进行登陆！'}");
			}else{
				userInfo.setSyscd((Integer.parseInt(userInfo.getSyscd())+1)+""); 
				userInfo.setSyscdtime(new Date());
				user.getHibernateTemplate().update(userInfo);
				response.getWriter().print("{failure:true,msg:'无此用户或者密码错误，密码连续输错3次将被冻结！'}");
			}
		}else{
			response.getWriter().print("{failure:true,msg:'无此用户或者密码错误，密码连续输错3次将被冻结！'}");
		}
	}
	
}
