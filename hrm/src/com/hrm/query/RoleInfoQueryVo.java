package com.hrm.query;

import java.util.List;

import com.hrm.control.Request;
import com.hrm.dao.RoleInfoDAO;
import com.hrm.entity.RoleInfo;
import com.hrm.vo.BaseVo;

public class RoleInfoQueryVo implements BaseVo{
	private RoleInfoDAO roleInfoDAO;

	public RoleInfoDAO getRoleInfoDAO() {
		return roleInfoDAO;
	}

	public void setRoleInfoDAO(RoleInfoDAO roleInfoDAO) {
		this.roleInfoDAO = roleInfoDAO;
	}

	public Request execute(Request request) {
		String result = "";
		String start = request.getParamsMap().get("start");
		String limit = request.getParamsMap().get("limit");
		List<RoleInfo> list = roleInfoDAO.findByPage(new RoleInfo(), Integer.parseInt(start), Integer.parseInt(limit));
		String json = "";
		for(RoleInfo info:list){
			json += "{role_code:'"+info.getRoleCode()+"',role_desc:'"+info.getRoleDesc()+"',role_name:'"+info.getRoleName()+"',user_name:'"+info.getUserName()+"',updt_time:'"+info.getUpdtTime()+"'},";
		}
		if(list.size()>0){
			json = json.substring(0, json.length()-1);
		}
		result = "";
		result = "{totalProperty:"+roleInfoDAO.getCount("RoleInfo")+",root:["+json+"]}";
		request.setResponse(result);
		return request;
	}
	
}
