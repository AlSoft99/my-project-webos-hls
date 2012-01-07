package com.ux.vo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ux.dao.UserInfoDao;

@Controller
public class CheckVo {
	@Resource
	private UserInfoDao userInfoDao;
	
	@RequestMapping(value="/checkid.do",method=RequestMethod.GET)
	public @ResponseBody String checkId(@RequestParam(value="userid",required=true) String userid){
		if(userInfoDao.checkId(userid)){
			return "success";
		}
		return "该邮箱已被使用,请重新输入!";
	}
}
