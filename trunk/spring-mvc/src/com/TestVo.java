package com;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestVo {
	@RequestMapping(value = "/test", method = RequestMethod.GET) 
	public @ResponseBody String controllerGet(@RequestParam(value = "username", required = true) String username) throws Exception{
//		System.out.println("username:"+username);
		//response.getWriter().print("1234");
		System.out.println(1234);
		return "1234";
	}
}
