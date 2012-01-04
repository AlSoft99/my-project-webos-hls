package com;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student.do")
public class TestVo {
	//@RequestParam(value = "username", required = true) String username
	@RequestMapping(params = "method=add")
	public void add(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{
		System.out.println("student_add111");
		response.getWriter().print("12345");
	}
}
