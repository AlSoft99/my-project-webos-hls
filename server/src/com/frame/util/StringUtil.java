package com.frame.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.entity.Test;

import com.frame.vo.Request;
import com.google.gson.Gson;

public class StringUtil {
	private static StringUtil util = new StringUtil();
	
	public static StringUtil newInstant(){
		return util;
	}
	
	public Request toRequest(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,String[]> map = request.getParameterMap();
		Map<String,String> paramsMap = new HashMap<String,String>();
		String entity = request.getParameter("entity");
		Request req = new Request();
		try {
			Object classObj = null;
			for(Iterator<String> it = map.keySet().iterator();it.hasNext();){
				String key = it.next();
				String[] value = map.get(key);
				paramsMap.put(key, value[0]);
			}
			if(entity!=null){
				Gson gson = new Gson();  
				classObj = Class.forName(entity).newInstance();
				
				java.lang.reflect.Type type = classObj.getClass().getGenericSuperclass();
				Test o = gson.fromJson("{username:\"test\",password:\"word\",entity:\"test.entity.Test\"}", type);
				System.out.println("userame:"+o.getUsername());
			}
//			req.setEntity(classObj);
			req.setParamsMap(paramsMap);
			req.setUserInfo(request.getSession().getAttribute("userInfo"));
			req.setHttpRequest(request);
			req.setHttpResponse(response);
		}catch (Exception e) {
			if(!"".equals(req.getResponse()) && req.getResponse()!=null){
				response.getWriter().write(req.getResponse().toString());
			}else if(req.getResponse()!=null && e.getMessage()!=null){
				response.getWriter().write(e.getMessage());
			}else{
				response.getWriter().write(e.toString());
			}
			e.printStackTrace();
		} 
		
		return req;
	}
}
