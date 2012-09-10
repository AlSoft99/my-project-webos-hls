package com.ux.vo;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.util.RandomValidateCode;
import com.ux.dao.UserInfoDao;

@Controller
public class CheckVo {
	@Resource
	private UserInfoDao userInfoDao;
	
	@RequestMapping(value="/checkid.do",method=RequestMethod.GET)
	public @ResponseBody String checkId(@RequestParam(value="email",required=true) String userid){
		if(userInfoDao.checkId(userid)){
			return "success";
		}
		return "该邮箱已被使用,请重新输入!";
	}
	@RequestMapping(value="/checkcode-logout.do",method=RequestMethod.GET)
	public @ResponseBody String checkcode(@RequestParam(value="checkcode",required=true) String checkcode,HttpSession session){
		String randomKey = (String)session.getAttribute(RandomValidateCode.RANDOMCODEKEY);
		if(randomKey==null || "".equals(randomKey)){
			return "请输入验证码";
		}else{
			if(!randomKey.equals(checkcode)){
				return "输入的验证码有误";
			}else{
				return "success";
			}
		}
	}
}
