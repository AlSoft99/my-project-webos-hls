package com;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles and retrieves the main requests
 */
@Controller
public class AjaxController {

	protected static Logger logger = Logger.getLogger("controller");

	/*
	 * @Resource(name="springService") private ArithmeticService springService;
	 */

	/**
	 * Handles and retrieves the AJAX Add page
	 */
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String getAjaxAddPage(@RequestParam(value = "usrename", required = true) String usrename, @RequestParam(value = "password", required = true) String password, Model model) {
		logger.debug("Received request to show AJAX, add page");

		// This will resolve to /WEB-INF/jsp/ajax-add-page.jsp
		System.out.println("usrename:"+usrename);
		return "ajax-add-page";
	}

	/**
	 * Handles request for adding two numbers
	 */
	@RequestMapping(value = "/add.do", method = RequestMethod.POST)
	public @ResponseBody
	Integer add(
			@RequestParam(value = "inputNumber1", required = true) Integer inputNumber1,
			@RequestParam(value = "inputNumber2", required = true) Integer inputNumber2,
			Model model) {
		logger.debug("Received request to add two numbers");
		System.out.println("add");
		// Delegate to service to do the actual adding
		Integer sum = 0;

		// @ResponseBody will automatically convert the returned value into JSON
		// format
		// You must have Jackson in your classpath
		return sum;
	}
}