package com.hrm.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
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
			List<KtvStayInfo> list = hibernateSessionDAO.createHqlQuery("from KtvStayInfo where cardid='"+info.getCardid()+"' and state='1'");
			if(list.size()>0){
				request.setResponse("{success:false,msg:'卡号为<font color=red>"+info.getCardid()+"</font>的卡已被使用!!'}");
				return request;
			}
			UserInfo user = (UserInfo)request.getUserInfo();
			int day = info.getDay();
			Date overtime = StringUtil.newInstance().dateAdd(new Date(), day);
			info.setOvertime(overtime);
			info.setState("1");
			info.setUpdtuser(user.getUserId());
			info.setUpdttime(new Date());
			hibernateSessionDAO.save(info);
			request.setResponse("{success:true,msg:'添加成功!'}");
		}else if("queryCard".equals(action)){
			String cardid = request.getParamsMap().get("cardid");
			List<Map<String, Object>> list = hibernateSessionDAO.createHqlQuery("select new map(id as id,cardid as cardid,username as username,moblie as moblie,idcard as idcard,materialid as materialid,state as state,otherdesc as otherdesc,day as day,overtime as overtime,cash as cash) from KtvStayInfo where cardid='"+cardid+"' and state='1'");
			Gson gson = new Gson();
			request.setResponse(gson.toJson(list));
		}else if("update".equals(action)){
			KtvStayInfo info = (KtvStayInfo)request.getEntity();
			UserInfo user = (UserInfo)request.getUserInfo();
			int day = info.getDay();
			Date overtime = StringUtil.newInstance().dateAdd(new Date(), day);
			info.setOvertime(overtime);
			info.setState("1");
			info.setUpdtuser(user.getUserId());
			info.setUpdttime(new Date());
			hibernateSessionDAO.update(info);
			request.setResponse("{success:true,msg:'修改成功!'}");
		}
		return request;
	}

}
