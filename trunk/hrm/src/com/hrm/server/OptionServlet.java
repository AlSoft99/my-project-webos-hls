/**
 * 
 */
package com.hrm.server;

import com.hrm.control.Request;
import com.hrm.vo.BaseVo;

/**
 * @author Guanrl
 *
 */
public class OptionServlet implements BaseVo{

	public Request execute(Request request) {
		String option = request.getParamsMap().get("option");
		String list = InitData.getJsonList(option).toString();
		request.setResponse(list);
		return request;
	}
	
}
