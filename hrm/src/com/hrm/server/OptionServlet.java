/**
 * 
 */
package com.hrm.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.hrm.control.Request;
import com.hrm.vo.BaseVo;

/**
 * @author Guanrl
 *
 */
public class OptionServlet implements BaseVo{

	public Request execute(Request request) {
		String option = request.getParamsMap().get("option");
		String list = "";
		if(option.equals("month")){
			List<List<String>> month = new ArrayList<List<String>>();
			int month_number = 6; 
			Date currentDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
			SimpleDateFormat sdfkey = new SimpleDateFormat("yyyyMM");
			for (int i = 0; i < month_number; i++) {
				List<String> tmp = new ArrayList<String>();
				Calendar   g = Calendar.getInstance();   
	            g.setTime(currentDate); 
	            g.add(Calendar.MONTH,-i);
	            Date d2 = g.getTime();
	            tmp.add(sdfkey.format(d2));
	            tmp.add(sdf.format(d2));
	            month.add(tmp);
			}
			Gson gson = new Gson();
			list = gson.toJson(month);
		}else{
			list = InitData.getJsonList(option).toString();
		}
		
		request.setResponse(list);
		return request;
	}
	
}
