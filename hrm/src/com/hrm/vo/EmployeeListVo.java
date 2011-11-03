package com.hrm.vo;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hrm.control.Request;
import com.hrm.dao.EmployeeListDAO;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.EmployeeInfo;
import com.hrm.entity.EmployeeList;
import com.hrm.entity.EmployeeListId;

public class EmployeeListVo implements BaseVo {
	private HibernateSessionDAO hibernateSessionDAO;
	private static Logger logger = Logger.getLogger(EmployeeListVo.class);
	private EmployeeListDAO employeeListDAO;
	
	public Request execute(Request request) throws Exception{
		String result = "";
		String action = request.getParamsMap().get("action");
		if("welfare".equals(action)){
			result = getPropertyView(request);
		}else if("insert".equals(action)){
			result = insertRecord(request);
		}else if("update".equals(action)){
			result = updateRecord(request);
		}else if("delete".equals(action)){
			result = deleteRecord(request);
		}
		request.setResponse(result);
		return request;
	}
	public String getPropertyView(Request request){
		String result = "{";
		String user_id = request.getParamsMap().get("user_id");
		String user_name = request.getParamsMap().get("user_name");
		List<EmployeeInfo> list = hibernateSessionDAO.getHibernateTemplate().find("from EmployeeInfo where userId=?",new String[]{user_id});
		if(list.size()>0){
			result += "姓名:'"+user_name+"'"+
					",工作天数:'"+((EmployeeInfo)list.get(0)).getWorkday()+"'"+
					",基本工资:'"+((EmployeeInfo)list.get(0)).getSalary()+"'"+
					",上岗工资:'"+((EmployeeInfo)list.get(0)).getWorksalary()+"'"+
					",福利补贴:'"+((EmployeeInfo)list.get(0)).getWelfare()+"'"+
					",个人所得税:'"+((EmployeeInfo)list.get(0)).getTax()+"'"+
					",公积金:'"+((EmployeeInfo)list.get(0)).getPubfund()+"'"+
					",养老保险金:'"+((EmployeeInfo)list.get(0)).getSafefund()+"'"+
					",医疗保险金:'"+((EmployeeInfo)list.get(0)).getCurefund()+"'"+
					",综合保险金:'"+((EmployeeInfo)list.get(0)).getIntegfund()+"'"+
					",其他福利保险:'"+((EmployeeInfo)list.get(0)).getOtherfund()+"'";
		}
		result += "}";
		return result;
	}
	
	public String insertRecord(Request request){
		String result = "";
		try {
			String userId = request.getParamsMap().get("userId");
			String salarydate = request.getParamsMap().get("salarydate");
			String othersmart = request.getParamsMap().get("othersmart");
			String smartreason = request.getParamsMap().get("smartreason");
			String factsalary = request.getParamsMap().get("factsalary");
			String issend = Boolean.valueOf(request.getParamsMap().get("issend"))?"1":"0";
			EmployeeList entity = new EmployeeList();
			EmployeeListId id = new EmployeeListId();
			id.setUserId(userId);
			id.setSalarydate(DateFormat.getDateInstance().parse(salarydate));
			entity.setFactsalary(Double.valueOf(factsalary));
			entity.setIssend(issend);
			entity.setOthersmart(Double.valueOf(othersmart));
			entity.setSmartreason(smartreason);
			entity.setUpdtTime(new Date());
			entity.setId(id);
			employeeListDAO.save(entity);
		} catch (ParseException e) {
			logger.error("EmployeeListVo的insertRecord方法错误", e);
		}
		
		return result;
	}
	public String updateRecord(Request request){
		String result = "";
		try {
			String userId = request.getParamsMap().get("userId");
			String salarydate = request.getParamsMap().get("salarydate");
			String othersmart = request.getParamsMap().get("othersmart");
			String smartreason = request.getParamsMap().get("smartreason");
			String factsalary = request.getParamsMap().get("factsalary");
			String issend = Boolean.valueOf(request.getParamsMap().get("issend"))?"1":"0";
			EmployeeList entity = new EmployeeList();
			EmployeeListId id = new EmployeeListId();
			id.setUserId(userId);
			id.setSalarydate(DateFormat.getDateInstance().parse(salarydate));
			entity.setFactsalary(Double.valueOf(factsalary));
			entity.setIssend(issend);
			entity.setOthersmart(Double.valueOf(othersmart));
			entity.setSmartreason(smartreason);
			entity.setUpdtTime(new Date());
			entity.setId(id);
			employeeListDAO.attachDirty(entity);
		} catch (ParseException e) {
			logger.error("EmployeeListVo的updateRecord方法错误", e);
		}
		
		return result;
	}
	public String deleteRecord(Request request){
		String result = "";
		String[] userId = request.getParamsMap().get("userId").split(",");
		String[] salarydate = request.getParamsMap().get("salarydate").split(",");
		try {
		List<EmployeeList> list = new ArrayList<EmployeeList>();
			for (int i = 0; i < userId.length; i++) {
				EmployeeList employee = new EmployeeList();
				EmployeeListId id = new EmployeeListId();
				id.setSalarydate(DateFormat.getDateInstance().parse(salarydate[i]));
				id.setUserId(userId[i]);
				employee.setId(id);
				list.add(employee);
			}
			employeeListDAO.deleteList(list);
		} catch (ParseException e) {
			logger.error("EmployeeListVo的deleteRecord方法错误", e);
		}
		return result;
	}
	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}
	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}
	public EmployeeListDAO getEmployeeListDAO() {
		return employeeListDAO;
	}
	public void setEmployeeListDAO(EmployeeListDAO employeeListDAO) {
		this.employeeListDAO = employeeListDAO;
	}
	public static void main(String[] args) {
	}
}
