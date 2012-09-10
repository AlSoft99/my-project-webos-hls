package com.ux.vo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.servlet.ServletFactory;
import com.google.gson.Gson;
import com.ux.dao.QueryDao;
import com.ux.util.QueryUtil;

@Controller
public class QueryVo {
	@Resource
	private QueryDao queryDao;
	
	@RequestMapping(value="/query-logout.do",method=RequestMethod.GET)
	public @ResponseBody String query(@RequestParam(value="sql") String sql,@RequestParam(value="where") String where,HttpSession session) throws IOException{
		String allsql = getSql(sql, where);
		List<Map<String,Object>> list = queryDao.query(allsql);
		Map<String,Object> map = new HashMap<String,Object>();
		int count = queryDao.getCount(allsql);
		map.put("total", count);
		list.add(map);
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	@RequestMapping(value="/query-logout.do",method=RequestMethod.GET, params="start")
	public @ResponseBody String queryByPage(@RequestParam(value="start") int start,@RequestParam(value="row",required=false) int row,@RequestParam(value="sql") String sql,@RequestParam(value="where") String where,HttpSession session) throws IOException{
		String allsql = getSql(sql, where);
		List<Map<String,Object>> list = queryDao.query(allsql,start,row);
		Map<String,Object> map = new HashMap<String,Object>();
		int count = queryDao.getCount(allsql);
		map.put("total", count);
		map.put("start", start);
		map.put("rowcount",row);
		list.add(map);
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
	@RequestMapping(value="/query.do",method=RequestMethod.GET)
	public @ResponseBody String queryLogin(@RequestParam(value="sql") String sql,@RequestParam(value="where") String where,HttpSession session) throws IOException{
		String allsql = getSql(sql, where);
		List<Map<String,Object>> list = queryDao.query(allsql);
		Map<String,Object> map = new HashMap<String,Object>();
		int count = queryDao.getCount(allsql);
		map.put("total", count);
		list.add(map);
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	@RequestMapping(value="/query.do",method=RequestMethod.GET, params="start")
	public @ResponseBody String queryByPageLogin(@RequestParam(value="start") int start,@RequestParam(value="row",required=false) int row,@RequestParam(value="sql") String sql,@RequestParam(value="where") String where,HttpSession session) throws IOException{
		String allsql = getSql(sql, where);
		List<Map<String,Object>> list = queryDao.query(allsql,start,row);
		Map<String,Object> map = new HashMap<String,Object>();
		int count = queryDao.getCount(allsql);
		map.put("total", count);
		map.put("start", start);
		map.put("rowcount",row);
		list.add(map);
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
	private String getSql(String sql, String where){
		QueryUtil query = ServletFactory.newInstant().getFactory().getBean("queryUtil", QueryUtil.class);
		String allsql = query.getSql(sql).trim();
		String[] condition = where.split(",");
		for (int i = 0; i < condition.length; i++) {
			String replaceWhere = "{"+i+"}";
			allsql = allsql.replace(replaceWhere, condition[i]);
		}
		return allsql;
	}
}
