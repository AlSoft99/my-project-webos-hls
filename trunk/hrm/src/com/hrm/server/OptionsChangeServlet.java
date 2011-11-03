package com.hrm.server;

import java.util.List;

import com.hrm.control.Request;
import com.hrm.vo.BaseVo;

public class OptionsChangeServlet implements BaseVo {

	public Request execute(Request request) {
		String result = "[[]]";
		String option = request.getParamsMap().get("option");
		String changeCode = request.getParamsMap().get("change");
		List list = null;
		if(changeCode!=null){
			list = InitData.getJsonRoleToUserList(option, "\""+changeCode+"\"");
		}else{
			list = InitData.getJsonList(option);
		}
		if(list!=null){
			result = list.toString();
		}
		request.setResponse(result);
		return request;
	}

}
