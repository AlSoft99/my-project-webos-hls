package com.hrm.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.control.Request;
import com.hrm.util.StringUtil;
import com.hrm.vo.BaseVo;

public class MenuSerlvet extends HttpServlet {
//1524
	private static Logger logger = Logger.getLogger(MenuSerlvet.class);
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String menu = "[" +
						"{id:\"role_manager\",text:\"角色管理\",children:[" +
							"{id:\"role_add\",text:\"角色管理\",leaf:true,page:\"page/role/role_manager.jsp\"}," +
							"{id:\"menu_manager\",text:\"菜单管理\",leaf:true,page:\"page/role/menu_manager.jsp\"}" +
						"]}," +
						"{id:\"user_manager\",text:\"用户管理\",children:[" +
							"{id:\"user_add\",text:\"用户管理\",leaf:true,page:\"page/user/user_manager.jsp\"}," +
							"{id:\"user_mod\",text:\"用户修改\",leaf:true,page:\"page/user/user_mod.jsp\"}" +
						"]}" +
					  "]";
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getSession().getServletContext());
		BaseVo role = factory.getBean("menuRoleVo", BaseVo.class);
		try {
			Request request = StringUtil.newInstance().toRequest(req, resp);
			menu = role.execute(request).getResponse().toString();
		} catch (Exception e) {
			logger.error("MenuSerlvet菜单查询方法出错:", e);
		}
		resp.getWriter().print(menu);
	}
	
}
