package com.ux.util;

import java.util.Map;

public class QueryUtil {
	private Map<String,String> querylist ;

	public Map<String, String> getQuerylist() {
		return querylist;
	}

	public void setQuerylist(Map<String, String> querylist) {
		this.querylist = querylist;
	}
	public String getSql(String key){
		return querylist.get(key);
	}
}
