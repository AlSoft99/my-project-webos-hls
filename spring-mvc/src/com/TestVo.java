package com;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestVo {
	@Resource(name="springService")
	private ArithmeticService springService;
	
	@RequestMapping(value = "/test.do", method = RequestMethod.GET) 
	public @ResponseBody String getAjaxAddPage(@RequestParam(value = "username", required = true) int username, @RequestParam(value = "password", required = true) int password,User user, Model model) {
		int result = springService.add(username, password);
		System.out.println("result==:"+user.getUsername());
		return "ajax-add-page";
	}
	/*@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String getAjaxAddPage(@RequestParam(value = "usrename", required = true) String usrename, @RequestParam(value = "password", required = true) String password, Model model) {
		System.out.println("usrename:"+usrename);
		return "ajax-add-page";
	}*/
}
