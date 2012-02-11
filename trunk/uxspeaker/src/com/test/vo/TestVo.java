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
	private UserInfoTestDao userInfoTestDao;
	
	public UserInfoTestDao getUserInfoTestDao() {
		return userInfoTestDao;
	}

	public void setUserInfoTestDao(UserInfoTestDao userInfoTestDao) {
		this.userInfoTestDao = userInfoTestDao;
	}

	@Override
	public Request execute(Request request) throws Exception {
		UserInfoTest userInfo = (UserInfoTest)request.getEntity();
		userInfoTestDao.save(userInfo);
		/*userInfo.setUsername("name1");
		userInfoDao.save(userInfo);
		throw new Exception("报错!!");*/
		request.setResponse("1000分");
		return request;
	}

}
