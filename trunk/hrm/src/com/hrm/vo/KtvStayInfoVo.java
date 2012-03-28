package com.hrm.vo;

import java.util.Date;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.KtvStayInfo;
import com.hrm.entity.UserInfo;
import com.hrm.util.StringUtil;

public class KtvStayInfoVo implements BaseVo {
	private HibernateSessionDAO hibernateSessionDAO;
	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}
	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}
	@Override
	public Request execute(Request request) throws Exception {
		String action = request.getParamsMap().get("action");
		if("insert".equals(action)){
			KtvStayInfo info = (KtvStayInfo)request.getEntity();
			UserInfo user = (UserInfo)request.getUserInfo();
			int day = info.getDay();
			Date overtime = StringUtil.newInstance().dateAdd(new Date(), day);
			info.setOvertime(overtime);
			info.setState("1");
			info.setUpdtuser(user.getUserId());
			info.setUpdttime(new Date());
			hibernateSessionDAO.save(info);
			request.setResponse("{success:true,msg:'添加成功!'}");
		}
		return request;
	}

}
