package com.ux.ws;

import org.springframework.beans.factory.BeanFactory;

import com.frame.servlet.ServletFactory;

public class WebServiceImpl implements WebService {

	@Override
	public String send(String classname, String[] params) {
		BeanFactory factory = ServletFactory.newInstant().getFactory();
		System.out.println("factoruy:========================classname:"+classname+"   params:"+params);
		return "factoruy";
	}

}
