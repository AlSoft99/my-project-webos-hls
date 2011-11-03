package com.hrm.filter;

import com.hrm.control.Request;
import com.hrm.vo.BaseVo;

public class CheckLogin implements BaseVo {

	public Request execute(Request request) {
		com.hrm.util.ClsFactory.newInstance().info("进入login的过滤器");
		return null;
	}

}
