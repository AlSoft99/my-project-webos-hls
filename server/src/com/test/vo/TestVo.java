package com.test.vo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frame.vo.BaseVo;
import com.frame.vo.Request;
import com.test.dao.UserInfoDAO;
import com.test.entity.UserInfo;

@Repository
public class TestVo implements BaseVo {
	@Autowired
	private UserInfoDAO userInfoDAO;
	public UserInfoDAO getUserInfoDAO() {
		return userInfoDAO;
	}
	public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
	}
	@Override
	public Request execute(Request request) throws Exception {
		UserInfo userInfo = (UserInfo)request.getEntity();
		System.out.println("test.name:"+userInfo.getUsername());
		userInfoDAO.save(userInfo);
		request.setResponse("1000分");
		return request;
	}

}
