package com.ux.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.ux.dao.UserInfoDao;
import com.ux.entity.UserInfo;

@Controller
public class RegisterVo {
	@Resource
	private UserInfoDao userInfoDao;
	
	@RequestMapping(value="/register.do",method=RequestMethod.GET, params="method=add")
	public @ResponseBody String register(@RequestParam(value="username",required=true) String username,UserInfo userInfo){
		Date date = new Date();
		userInfo.setRole("16");
		userInfo.setCurrentdate(date);
		userInfoDao.save(userInfo);
		return "success";
	}
	@RequestMapping(value="/register.do",method=RequestMethod.GET, params="method=login")
	public @ResponseBody String login(UserInfo userInfo, HttpServletRequest request){
		List<UserInfo> list = userInfoDao.checkIsExist(userInfo.getEmail(), userInfo.getPassword());
		Map<String,String> map = new HashMap<String, String>();
		if(list.size()>0){
			String jobname = userInfoDao.getJobname(list.get(0).getJob());
			list.get(0).setJobname(jobname);
			request.getSession().setAttribute("userinfo", list.get(0));
			map.put("status", "success");
			map.put("username", list.get(0).getUsername());
			map.put("userid", list.get(0).getId()+"");
		}else{
			map.put("status", "failure");
		}
		Gson gson = new Gson();
		String m = gson.toJson(map);
		return m;
	}
	@RequestMapping(value="/register.do",method=RequestMethod.GET, params="method=logout")
	public @ResponseBody String register(HttpServletRequest request){
		request.getSession().removeAttribute("userinfo");
		return "success";
	}
}
