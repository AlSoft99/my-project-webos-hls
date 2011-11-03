package com.hrm.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.control.Request;
import com.hrm.dao.RoleInfoDAO;
import com.hrm.entity.RoleInfo;
import com.hrm.server.InitData;
import com.hrm.util.ClsFactory;

public class RoleInfoVo implements BaseVo {
	private static Logger logger = Logger.getLogger(RoleInfoVo.class);
	private RoleInfoDAO roleInfoDAO;

	public RoleInfoDAO getRoleInfoDAO() {
		return roleInfoDAO;
	}

	public void setRoleInfoDAO(RoleInfoDAO roleInfoDAO) {
		this.roleInfoDAO = roleInfoDAO;
	}
	public Request execute(Request request) throws Exception{
		String result = "";
		String resultString = "";
		try{
			String action = request.getParamsMap().get("action");
			String role_name = request.getParamsMap().get("role_name");
			String role_desc = request.getParamsMap().get("role_desc");
			String role_code = request.getParamsMap().get("role_code");
			RoleInfo info = new RoleInfo();
			
			info.setRoleDesc(role_desc);
			info.setRoleName(role_name);
			info.setUserName("guanrl");
			info.setUpdtTime(new Date());
			if (action.equals("insert")) {
				roleInfoDAO.save(info);
				reloadRole(request);
				resultString = "用户角色已成功添加一条记录,请查看记录!";
			}else if(action.equals("update")){
				info.setRoleCode(role_code);
				roleInfoDAO.update(info);
				reloadRole(request);
				resultString = "用户角色已成功修改,请查看记录!";
			}else if(action.equals("delete")){
				String[] role_code_list = role_code.split(",");
				List<RoleInfo> list = new ArrayList<RoleInfo>();
				for (int i = 0; i < role_code_list.length; i++) {
					RoleInfo del = new RoleInfo();
					del.setRoleCode(role_code_list[i]);
					list.add(del);
				}
				roleInfoDAO.deleteAll(list);
				reloadRole(request);
				resultString = "用户角色已成功删除,请查看记录!";
			}
			BeanFactory factory = ClsFactory.newInstance().getFactory();
			InitData init = factory.getBean("initData", InitData.class);
			init.initJsonRoleInfo();
			result = "{success:true,msg:'"+resultString+"'}";
			if(action.equals("delete")){
				result = "{'success':true,'msg':'"+resultString+"'}";
			}
		}catch(Exception e){
			resultString = "用户角色操作失败,请查看原因:\n"+e.getMessage()+"!";
			result = "{success:false,msg:'"+resultString+"'}";
			logger.error("RoleInfoVo的execute方法错误", e);
		}
		request.setResponse(result);
		return request;
	}
	private void reloadRole(Request request){
		BeanFactory factory = ClsFactory.newInstance().getFactory();
		factory.getBean("initData", InitData.class).initJsonRoleInfo();
	}

}
