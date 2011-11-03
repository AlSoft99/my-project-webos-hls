/**
 * 
 */
package com.hrm.vo;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.control.Request;
import com.hrm.dao.RoleInfoDAO;
import com.hrm.dao.UserInfoDAO;
import com.hrm.entity.UserInfo;
import com.hrm.server.InitData;
import com.hrm.util.ClsFactory;

/**
 * @author Guanrl
 *
 */
public class UserInfoVo implements BaseVo{
	private static Logger logger = Logger.getLogger(UserInfoVo.class);
	private RoleInfoDAO roleInfoDAO;
	private UserInfoDAO userInfoDAO;

	public UserInfoDAO getUserInfoDAO() {
		return userInfoDAO;
	}

	public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
	}

	public Request execute(Request request) throws Exception{
		
		String result = "";
		String resultString = "";
		String action = request.getParamsMap().get("action");
		String role_code = request.getParamsMap().get("role_code");
		String user_code = request.getParamsMap().get("user_code");
		String user_name = request.getParamsMap().get("user_name");
		String user_pwd = request.getParamsMap().get("user_pwd");
		String user_phone = request.getParamsMap().get("user_phone");
		String user_mail = request.getParamsMap().get("user_mail");
		String user_qq = request.getParamsMap().get("user_qq");
		
		try{
			if (action.equals("insert")) {
				UserInfo userinfo = new UserInfo();
				userinfo.setRoleInfo(roleInfoDAO.findById(role_code));
				userinfo.setUpdtTime(new Date());
				userinfo.setUserCode(user_code);
				userinfo.setUserMail(user_mail);
				userinfo.setUserName(user_name);
				userinfo.setUserPhone(user_phone);
				userinfo.setUserPwd(user_pwd);
				userinfo.setUserQq(user_qq);
				userinfo.setSyscd("0");
				userinfo.setSyscdtime(new Date());
				userinfo.setUsercd("0");
				userinfo.setUsercdtime(new Date());
				userInfoDAO.save(userinfo);
				resultString = "保存成功!请查看编号和明细!";
			}else if (action.equals("update")) {
				String syscd = request.getParamsMap().get("syscd");
				String usercd = request.getParamsMap().get("usercd");
				UserInfo userinfo = userInfoDAO.findById(request.getParamsMap().get("user_id"));
				userinfo.setRoleInfo(roleInfoDAO.findById(role_code));
				userinfo.setUpdtTime(new Date());
				userinfo.setUserCode(user_code);
				userinfo.setUserMail(user_mail);
				userinfo.setUserName(user_name);
				userinfo.setUserPhone(user_phone);
				userinfo.setUserPwd(user_pwd);
				userinfo.setUserQq(user_qq);
//				userinfo.setUserId(request.getParameter("user_id"));
				userinfo.setSyscd(syscd);
				userinfo.setUsercd(usercd);
				resultString = "修改成功!请查看编号和明细!";
				userInfoDAO.update(userinfo);
			}else if(action.equals("delete")){
				String[] user_id = request.getParamsMap().get("user_id").split(",");
				String[] role_code_list = request.getParamsMap().get("role_code").split(",");
				for (int i = 0; i < user_id.length; i++) {
					UserInfo userinfo = new UserInfo();
					userinfo.setUserId(user_id[i]);
					userInfoDAO.delete(userinfo);
				}
				resultString = "删除成功!";
				result = "{'success':true,'msg':'"+resultString+"'}";
			}
			BeanFactory factory = ClsFactory.newInstance().getFactory();
			InitData init = factory.getBean("initData", InitData.class);
			init.initJsonUserInfo();
			init.initJsonChangeUserInfo();
			result = "{success:true,msg:'"+resultString+"'}";
		}catch(Exception e){
			resultString = "操作失败!";
			result = "{success:false,msg:'"+resultString+"'}";
			logger.error("UserInfoVo的方法错误", e);
		}
		request.setResponse(result);
		return request;
	}

	public RoleInfoDAO getRoleInfoDAO() {
		return roleInfoDAO;
	}

	public void setRoleInfoDAO(RoleInfoDAO roleInfoDAO) {
		this.roleInfoDAO = roleInfoDAO;
	}
	
}
