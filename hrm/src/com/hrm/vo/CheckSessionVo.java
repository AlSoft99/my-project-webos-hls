package com.hrm.vo;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.UserInfo;

public class CheckSessionVo implements BaseVo {
	private HibernateSessionDAO hibernateSessionDAO;
	public Request execute(Request request) throws Exception {
		String result = "";
		UserInfo userInfo = (UserInfo)request.getUserInfo();
		String action = request.getParamsMap().get("action");
		if(action.equals("check")){
			result = "{success:true,msg:''}";
		}else if(action.equals("logout")){
			request.removeSession("userInfo");
			result = "{success:true,msg:''}";
		}else if(action.equals("modpwd")){
			String user_pwd = request.getParamsMap().get("user_pwd");
			String new_pwd = request.getParamsMap().get("new_pwd");
			if(userInfo.getUserPwd().equals(user_pwd)){
				int flag = hibernateSessionDAO.createHqlExcute("update UserInfo set userPwd='"+new_pwd+"' where userId='"+userInfo.getUserId()+"'");
				if(flag>0){
					result = "{success:true,msg:'修改密码成功!'}";
				}else{
					result = "{failure:true,msg:'修改密码失败!'}";
				}
			}else{
				result = "{failure:true,msg:'原密码不正确!'}";
			}
		}
		request.setResponse(result);
		return request;
	}
	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}
	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}

}
