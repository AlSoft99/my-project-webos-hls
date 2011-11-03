package com.hrm.vo;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hrm.control.Request;
import com.hrm.dao.EmployeeInfoDAO;
import com.hrm.entity.EmployeeInfo;

public class EmployeeInfoVo implements BaseVo {
	private EmployeeInfoDAO employeeInfoDAO;
	private static Logger logger = Logger.getLogger(EmployeeInfoVo.class);
	public Request execute(Request request) throws Exception {
		String result = "";
		String action = request.getParamsMap().get("action");
		if("update".equals(action)){
			result = updateParams(request);
		}
		request.setResponse(result);
		return request;
	}
	public String updateParams(Request request){
		String result = "";
		EmployeeInfo info = new EmployeeInfo();
		try{
			String user_id = request.getParamsMap().get("user_id");
			info.setUserId(user_id);
			info.setCurefund(Float.valueOf(request.getParamsMap().get("curefund")));
			info.setSalary(Float.valueOf(request.getParamsMap().get("salary")));
			info.setWorksalary(Float.valueOf(request.getParamsMap().get("worksalary")));
			info.setWelfare(Float.valueOf(request.getParamsMap().get("welfare")));
			info.setTax(Float.valueOf(request.getParamsMap().get("tax")));
			info.setPubfund(Float.valueOf(request.getParamsMap().get("pubfund")));
			info.setSafefund(Float.valueOf(request.getParamsMap().get("safefund")));
			info.setIntegfund(Float.valueOf(request.getParamsMap().get("integfund")));
			info.setOtherfund(Float.valueOf(request.getParamsMap().get("otherfund")));
			info.setWorkday(Integer.valueOf(request.getParamsMap().get("workday")));
			info.setUpdtTime(new Date());
			employeeInfoDAO.attachDirty(info);
			result = "{success:true,msg:'保存成功'}";
		}catch(Exception e){
			result = "{failure:true,msg:'保存失败,原因:"+e.getMessage()+"'}";
			logger.error("EmployeeInfoVo的updateParams方法错误:", e);
		}
		return result;
	}
	public EmployeeInfoDAO getEmployeeInfoDAO() {
		return employeeInfoDAO;
	}
	public void setEmployeeInfoDAO(EmployeeInfoDAO employeeInfoDAO) {
		this.employeeInfoDAO = employeeInfoDAO;
	}

}
