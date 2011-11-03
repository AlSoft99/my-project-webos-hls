package com.hrm.vo;

import java.util.List;

import com.hrm.control.Request;
import com.hrm.dao.EmployeeInfoDAO;
import com.hrm.util.StringUtil;

public class EmployeeInfoQueryVo implements BaseVo {
	private EmployeeInfoDAO employeeInfoDAO;
	public Request execute(Request request) throws Exception {
		String result = "";
		String start = request.getParamsMap().get("start");
		String limit = request.getParamsMap().get("limit");
		String hql = "select a.user_Id,a.user_Name," +
				"(select b.workday from Employee_Info b where b.user_Id=a.user_Id) workday," +
				"(select c.salary from Employee_Info c where c.user_Id=a.user_Id) salary," +
				"(select d.welfare from Employee_Info d where d.user_Id=a.user_Id) welfare," +
				"(select e.tax from Employee_Info e where e.user_Id=a.user_Id) tax," +
				"(select f.pubfund from Employee_Info f where f.user_Id=a.user_Id) pubfund," +
				"(select g.safefund from Employee_Info g where g.user_Id=a.user_Id) safefund," +
				"(select h.curefund from Employee_Info h where h.user_Id=a.user_Id) curefund," +
				"(select i.otherfund from Employee_Info i where i.user_Id=a.user_Id) otherfund," +
				"(select j.integfund from Employee_Info j where j.user_Id=a.user_Id) integfund," +
				"(select k.updt_Time from Employee_Info k where k.user_Id=a.user_Id) updt_Time,a.role_code,b.role_name," +
				"(select l.worksalary from Employee_Info l where l.user_Id=a.user_Id) worksalary from User_Info a,role_info b where a.role_code=b.role_code order by a.role_code";
		List<Object[]> list1 = employeeInfoDAO.findSqlByPage(hql,Integer.parseInt(start),Integer.parseInt(limit));
		String json = "";
		for(Object[] obj:list1){
			json += "{user_id:'"+obj[0]+"',user_name:'"+obj[1]+"',workday:'"+StringUtil.newInstance().filterData(obj[2], "0")+"',salary:'"+StringUtil.newInstance().filterData(obj[3], "0")+"',welfare:'"+StringUtil.newInstance().filterData(obj[4], "0")+"',tax:'"+StringUtil.newInstance().filterData(obj[5], "0")+"'" +
						",pubfund:'"+StringUtil.newInstance().filterData(obj[6], "0")+"',safefund:'"+StringUtil.newInstance().filterData(obj[7], "0")+"',curefund:'"+StringUtil.newInstance().filterData(obj[8], "0")+"',otherfund:'"+StringUtil.newInstance().filterData(obj[9], "0")+"',integfund:'"+StringUtil.newInstance().filterData(obj[10], "0")+"'" +
							",updt_time:'"+obj[11]+"',role_code:'"+obj[12]+"',role_name:'"+obj[13]+"',worksalary:'"+StringUtil.newInstance().filterData(obj[14],"0")+"'},";
		}
		if (!json.equals("")) {
			json = json.substring(0, json.length()-1);
		}
		result = "";
		result = "{totalProperty:"+employeeInfoDAO.getCount("UserInfo")+",root:["+json+"]}";
		request.setResponse(result);
		return request;
	}
	
	public EmployeeInfoDAO getEmployeeInfoDAO() {
		return employeeInfoDAO;
	}
	public void setEmployeeInfoDAO(EmployeeInfoDAO employeeInfoDAO) {
		this.employeeInfoDAO = employeeInfoDAO;
	}

}
