package com.hrm.query;

import java.util.List;

import com.hrm.control.Request;
import com.hrm.dao.UserInfoDAO;
import com.hrm.vo.BaseVo;

public class UserInfoQueryVo implements BaseVo{
	private UserInfoDAO userInfoDAO;

	public UserInfoDAO getUserInfoDAO() {
		return userInfoDAO;
	}

	public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
	}

	public Request execute(Request request) {
		String result = "";
		String start = request.getParamsMap().get("start");
		String limit = request.getParamsMap().get("limit");
//		List<UserInfo> list = userInfoDAO.findByPage(new UserInfo(), Integer.parseInt(start), Integer.parseInt(limit));
		List<Object[]> list1 = userInfoDAO.findHqlByPage("select userId,userCode,userName,userPwd,userPhone,userMail,userQq,updtTime,roleInfo.roleCode,roleInfo.roleName,syscd,usercd,syscdtime,usercdtime from UserInfo", Integer.parseInt(start), Integer.parseInt(limit));
		String json = "";
		for(Object[] obj:list1){
			json += "{user_id:'"+obj[0]+"',user_code:'"+obj[1]+"',user_name:'"+obj[2]+"',user_pwd:'"+obj[3]+"',user_phone:'"+obj[4]+"',user_mail:'"+obj[5]+"',user_qq:'"+obj[6]+"',updt_time:'"+obj[7]+"',role_code:'"+obj[8]+"',role_name:'"+obj[9]+"',syscd:'"+((obj[10].toString().equals("0"))?"0":"1")+"',usercd:'"+obj[11]+"',syscdtime:'"+obj[12]+"',usercdtime:'"+obj[13]+"'},";
		}
		if (!json.equals("")) {
			json = json.substring(0, json.length()-1);
		}
		result = "";
		result = "{totalProperty:"+userInfoDAO.getCount("UserInfo")+",root:["+json+"]}";
		request.setResponse(result);
		return request;
	}
	
}
