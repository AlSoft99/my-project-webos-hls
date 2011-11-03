package com.hrm.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Request {
	//存放所有请求参数
	private Map<String,String> paramsMap ;
	//存放session用户信息
	private Object userInfo ;
	//接受页面参数实体类
	private Object entity;
	//应答报文
	private Object response;
	//request
	private HttpServletRequest httpRequest;
	//response
	private HttpServletResponse httpResponse;
	//通讯使用对象
	private Map<String,Object> connMap = new HashMap<String,Object>();
	
	public void setAttribute(String attr,Object obj){
		connMap.put(attr, obj);
	}
	public Object getAttribute(String attr){
		return connMap.get(attr);
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	public Map<String, String> getParamsMap() {
		return paramsMap;
	}
	public void setParamsMap(Map<String, String> paramsMap) {
		this.paramsMap = paramsMap;
	}
	public Object getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(Object userInfo) {
		this.userInfo = userInfo;
	}
	public Object getEntity() {
		return entity;
	}
	public void setEntity(Object entity) {
		this.entity = entity;
	}
	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	public void setHttpResponse(HttpServletResponse httpResponse) {
		this.httpResponse = httpResponse;
	}
	public void removeSession(String userInfo){
		httpRequest.getSession().removeAttribute(userInfo);
	}
	
}
