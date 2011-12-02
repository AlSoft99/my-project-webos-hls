package com.frame.servlet;

import org.springframework.beans.factory.BeanFactory;

public class ServletFactory {
	public static ServletFactory servlet = new ServletFactory();
	private BeanFactory factory;
	public static ServletFactory newInstant(){
		return servlet;
	}
	public BeanFactory getFactory() {
		return factory;
	}
	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}
}
