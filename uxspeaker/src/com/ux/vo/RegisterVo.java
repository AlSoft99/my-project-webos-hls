package com.ux.vo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ux.dao.UserInfoDao;
import com.ux.entity.UserInfo;

@Controller
public class RegisterVo {
	@Resource
	private UserInfoDao userInfoDao;
	
	@RequestMapping(value="/register.do",method=RequestMethod.GET, params="method=add")
	public @ResponseBody String test(@RequestParam(value="username",required=true) String username,UserInfo userInfo){
		System.out.println("userInfo start!username:"+username);
		userInfoDao.save(userInfo);
		return "test";
	}
}
