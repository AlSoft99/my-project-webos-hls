package com.test.vo;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.frame.vo.BaseVo;
import com.frame.vo.Request;
import com.test.dao.UserInfoTestDao;
import com.test.entity.UserInfoTest;

@Repository
public class TestVo implements BaseVo {
	@Resource
	private UserInfoTestDao userInfoDao;
	public UserInfoTestDao getUserInfoDao() {
		return userInfoDao;
	}
	
	public void setUserInfoDAO(UserInfoTestDao userInfoDAO) {
		this.userInfoDao = userInfoDAO;
	}
	@Override
	public Request execute(Request request) throws Exception {
		UserInfoTest userInfo = (UserInfoTest)request.getEntity();
		System.out.println("test.name:"+userInfo.getUsername());
		userInfoDao.save(userInfo);
		/*userInfo.setUsername("name1");
		userInfoDao.save(userInfo);
		throw new Exception("报错!!");*/
		request.setResponse("1000分");
		return request;
	}

}
