package com.hrm.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;

public class ClsFactory {
	private static ClsFactory clsFactory = new ClsFactory();
	private BeanFactory factory;
	//清算日期,用于检查清算表
	private String cleardate = "";
	//是否正常
	private String equals = "";
	private static Log debug = LogFactory.getLog("debug");
	private static Log error = LogFactory.getLog("error");
	private static Log warn = LogFactory.getLog("warn");
	private static Log info = LogFactory.getLog("info");
	/**
	 * 日志info级别
	 * @param msg
	 */
	public void info(String msg){
		info.info(msg);
	}
	/**
	 * 日志debug级别
	 * @param msg
	 */
	public void debug(String msg){
		debug.debug(msg);
	}
	/**
	 * 日志error级别
	 * @param msg
	 * @param e
	 */
	public void error(String msg,Throwable e){
		error.error(msg, e);
	}
	/**
	 * 日志warn级别
	 * @param msg
	 */
	public void warn(String msg){
		warn.warn(msg);
	}
	
	
	
	public String getCleardate() {
		return cleardate;
	}
	public void setCleardate(String cleardate) {
		this.cleardate = cleardate;
	}
	public String getEquals() {
		return equals;
	}
	public void setEquals(String equals) {
		this.equals = equals;
	}
	public static ClsFactory newInstance(){
		return clsFactory;
	}
	public BeanFactory getFactory() {
		return factory;
	}
	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}
	
}
