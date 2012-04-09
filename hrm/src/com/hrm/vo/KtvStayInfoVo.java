package com.hrm.vo;

import java.util.Calendar;
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
		UserInfo user = (UserInfo)request.getUserInfo();
		if("insert".equals(action)){
			KtvStayInfo info = (KtvStayInfo)request.getEntity();
			List<KtvStayInfo> list = hibernateSessionDAO.createHqlQuery("from KtvStayInfo where cardid='"+info.getCardid()+"' and state='1'");
			if(list.size()>0){
				request.setResponse("{success:false,msg:'卡号为<font color=red>"+info.getCardid()+"</font>的卡已被使用!!'}");
				return request;
			}
			
			int day = info.getDay();
			Date overtime = StringUtil.newInstance().dateAdd(new Date(), day);
			info.setOvertime(overtime);
			info.setState("1");
			info.setUpdtuser(user.getUserId());
			info.setUpdttime(new Date());
			hibernateSessionDAO.save(info);
			request.setResponse("{success:true,msg:'卡号为<font color=red>"+info.getCardid()+"</font>已添加成功!'}");
		}else if("queryCard".equals(action)){
			String cardid = request.getParamsMap().get("cardid");
			List<Map<String, Object>> list = hibernateSessionDAO.createHqlQuery("select new map(id as id,cardid as cardid,username as username,moblie as moblie,idcard as idcard,materialid as materialid,state as state,otherdesc as otherdesc,day as day,overtime as overtime,cash as cash) from KtvStayInfo where cardid='"+cardid+"' and state='1'");
			Gson gson = new Gson();
			request.setResponse(gson.toJson(list));
		}else if("update".equals(action)){
			KtvStayInfo info = (KtvStayInfo)request.getEntity();
			List<KtvStayInfo> list = hibernateSessionDAO.createHqlQuery("from KtvStayInfo where cardid='"+info.getCardid()+"' and state='1'");
			int day = info.getDay();
			Date overtime = StringUtil.newInstance().dateAdd(new Date(), day);
			info.setPassword(list.get(0).getPassword());
			info.setOvertime(overtime);
			info.setState("1");
			info.setUpdtuser(user.getUserId());
			info.setUpdttime(new Date());
			hibernateSessionDAO.update(info);
			request.setResponse("{success:true,msg:'卡号为<font color=red>"+info.getCardid()+"</font>已修改成功!'}");
		}else if("delete".equals(action)){
			String cardid = request.getParamsMap().get("cardid");
			List<KtvStayInfo> list = hibernateSessionDAO.createHqlQuery("from KtvStayInfo where cardid='"+cardid+"' and state='1'");
			if(list.size()==0){
				request.setResponse("卡号为<font color=red>"+cardid+"</font>的卡并未被使用! 无法删除!!");
				return request;
			}
			KtvStayInfo ktv = list.get(0);
			ktv.setState("0");
			ktv.setUpdtuser(user.getUserId());
			ktv.setUpdttime(new Date());
			hibernateSessionDAO.update(ktv);
			request.setResponse("卡号为<font color=red>"+cardid+"</font>的卡已删除成功!!");
		}else if("confirmPsd".equals(action)){
			String password = request.getParamsMap().get("password");
			String cardid = request.getParamsMap().get("cardid");
			List<KtvStayInfo> list = hibernateSessionDAO.createHqlQuery("from KtvStayInfo where cardid='"+cardid+"' and state='1'");
			if(list.size()==0){
				request.setResponse("{success:true,msg:'卡号为<font color=red>"+cardid+"</font>不存在!'}");
				return request;
			}else if(!list.get(0).getPassword().equals(password)){
				request.setResponse("{success:true,msg:'卡号为<font color=red>"+cardid+"</font>的密码错误!'}");
				return request;
			}
			KtvStayInfo info = list.get(0);
			long v = StringUtil.newInstance().subDate(new Date(), info.getOvertime(), Calendar.HOUR);
			if(v>0){
				request.setResponse("{success:true,msg:'卡号为<font color=red>"+cardid+"</font>的已过期!'}");
				return request;
			}
			info.setState("2");
			info.setUpdtuser(user.getUserId());
			info.setUpdttime(new Date());
			hibernateSessionDAO.update(info);
			request.setResponse("{success:true,msg:'卡号为<font color=red>"+cardid+"</font>的卡已成功取出!!'}");
		}
		return request;
	}

}
