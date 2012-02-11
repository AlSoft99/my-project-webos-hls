package com.test.vo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.dao.TestDao;
import com.test.entity.TestEntity;

@Controller
public class TestMvcVo {
	@Resource
	private TestDao testDao;
	
	@RequestMapping(value="/test.do",method=RequestMethod.GET, params="method=add")
	public @ResponseBody String test(@RequestParam(value="username",required=true) String username){
		TestEntity userInfo = new TestEntity();
		userInfo.setUser("guanrl");
		userInfo.setPsw("guanrlguanrl");
		testDao.save(userInfo);
		return "test";
	}
}
