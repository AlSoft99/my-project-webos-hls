package com.hrm.ws;

import com.cn.webservice.server.schma.Person;
import com.hrm.control.Request;
import com.hrm.vo.BaseVo;

public class Ws110Vo implements BaseVo {

	public Request execute(Request request) throws Exception {
		System.out.println("进入Ws110Vo类");
		Person ps = new Person();
		ps.setName("guanrc");
		request.setResponse(ps);
		return request;
	}

}
