package com.test.vo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestMvcVo {
	@RequestMapping(value="/test.do",method=RequestMethod.GET, params="method=add")
	public @ResponseBody String test(@RequestParam(value="username",required=true) String username){
		System.out.println("test start!username:"+username);
		return "test";
	}
}
