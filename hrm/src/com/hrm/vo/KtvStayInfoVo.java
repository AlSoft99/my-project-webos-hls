package com.hrm.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.KtvStayInfo;
import com.hrm.entity.KtvStayList;
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
			List<KtvStayInfo> list = hibernateSessionDAO.createHqlQuery("from KtvStayInfo where cardid='"+info.getCardid()+"' and (state='1' or state='3')");
			if(list.size()>0){
				request.setResponse("{success:false,msg:'卡号为<font color=red>"+info.getCardid()+"</font>的卡已被使用或者已过期!!'}");
				return request;
			}
			
			int day = info.getDay();
			Date overtime = StringUtil.newInstance().dateAdd(new Date(), day);
			info.setOvertime(overtime);
			info.setState("1");
			info.setUpdtuser(user.getUserId());
			info.setUpdttime(new Date());
			hibernateSessionDAO.save(info);
			String materialid = request.getParamsMap().get("materialid");
			String count = request.getParamsMap().get("count");
			String[] materiallist = materialid.split(",");
			String[] countlist = count.split(",");
			for(int i = 0 ; i < materiallist.length; i++ ){
				KtvStayList temp = new KtvStayList();
				temp.setCount(Integer.valueOf(countlist[i]));
				temp.setKtvid(info.getId());
				temp.setMaterialid(materiallist[i]);
				temp.setUpdtuser(user.getUserId());
				temp.setUpdttime(new Date());
				hibernateSessionDAO.save(temp);
			}
			request.setResponse("{success:true,msg:'卡号为<font color=red>"+info.getCardid()+"</font>已添加成功!'}");
		}else if("queryCard".equals(action)){
			String cardid = request.getParamsMap().get("cardid");
			String material_t = "";
			String count_t = "";
			List<Map<String, Object>> list = hibernateSessionDAO.createHqlQuery("select new map(id as id,cardid as cardid,username as username,moblie as moblie,idcard as idcard,state as state,otherdesc as otherdesc,day as day,overtime as overtime,cash as cash) from KtvStayInfo where cardid='"+cardid+"' and state='1'");
			List<KtvStayList> material = hibernateSessionDAO.createHqlQuery("from KtvStayList where ktvid='"+list.get(0).get("id")+"' order by id desc");
			for(int i = 0; i < material.size(); i++){
				material_t += material.get(i).getMaterialid()+",";
				count_t += material.get(i).getCount()+",";
			}
			material_t = material_t.substring(0, material_t.length()-1);
			count_t = count_t.substring(0, count_t.length()-1);
			Map<String, Object> map1 = new HashMap<String,Object>();
			map1.put("materialidlist", material_t);
			Map<String, Object> map2 = new HashMap<String,Object>();
			map2.put("countlist", count_t);
			list.add(map1);
			list.add(map2);
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
			hibernateSessionDAO.createHqlExcute("delete from KtvStayList where ktvid='"+list.get(0).getId()+"'");
			String materialid = request.getParamsMap().get("materialid");
			String count = request.getParamsMap().get("count");
			String[] materiallist = materialid.split(",");
			String[] countlist = count.split(",");
			for(int i = 0 ; i < materiallist.length; i++ ){
				KtvStayList temp = new KtvStayList();
				temp.setCount(Integer.valueOf(countlist[i]));
				temp.setKtvid(info.getId());
				temp.setMaterialid(materiallist[i]);
				temp.setUpdtuser(user.getUserId());
				temp.setUpdttime(new Date());
				hibernateSessionDAO.save(temp);
			}
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
				List<KtvStayInfo> listOver = hibernateSessionDAO.createHqlQuery("from KtvStayInfo where cardid='"+cardid+"' and state='3'");
				if(listOver.size()>0){
					if(!listOver.get(0).getPassword().equals(password)){
						request.setResponse("{success:true,msg:'卡号为<font color=red>"+cardid+"</font>的密码错误!'}");
						return request;
					}
					request.setResponse("{success:true,msg:'overtime'}");
					return request;
				}
				
				request.setResponse("{success:true,msg:'卡号为<font color=red>"+cardid+"</font>并未保存物品!'}");
				return request;
			}else if(!list.get(0).getPassword().equals(password)){
				request.setResponse("{success:true,msg:'卡号为<font color=red>"+cardid+"</font>的密码错误!'}");
				return request;
			}
			KtvStayInfo info = list.get(0);
			
			info.setState("2");
			info.setUpdtuser(user.getUserId());
			info.setUpdttime(new Date());
			hibernateSessionDAO.update(info);
			request.setResponse("{success:true,msg:'卡号为<font color=red>"+cardid+"</font>的卡已成功取出!!'}");
		}else if("changeState".equals(action)){
			String cardid = request.getParamsMap().get("cardid");
			String state = request.getParamsMap().get("state");
			List<KtvStayInfo> list = hibernateSessionDAO.createHqlQuery("from KtvStayInfo where cardid='"+cardid+"' and state='3'");
			KtvStayInfo ktv = list.get(0);
			ktv.setState(state);
			ktv.setUpdtuser(user.getUserId());
			ktv.setUpdttime(new Date());
			hibernateSessionDAO.update(ktv);
			request.setResponse("卡号为<font color=red>"+cardid+"</font>的卡操作成功!!");
		}else if("treeLoader".equals(action)){
			request.setResponse("[" +
					"{\"text\": \"To Do\", \"cls\": \"folder\",\"expanded\": true," +
						"\"children\": " +
							"[" +
								"{\"text\": \"Go jogging\",\"leaf\": true,\"checked\": true}," +
								"{\"text\": \"Take a nap\",\"leaf\": true,\"checked\": false}," +
								"{\"text\": \"Climb Everest\",\"leaf\": true,\"checked\": false}" +
							"]" +
					"}," +
					"{\"text\": \"Grocery List\",\"cls\": \"folder\",\"children\": " +
						"[" +
							"{\"text\": \"Bananas\",\"leaf\": true,\"checked\": false}," +
							"{\"text\": \"Milk\",\"leaf\": true,\"checked\": false}," +
							"{\"text\": \"Cereal\",\"leaf\": true,\"checked\": false}," +
							"{\"text\": \"Energy foods\",\"cls\": \"folder\",\"children\": " +
								"[" +
									"{\"text\": \"Coffee\",\"leaf\": true,\"checked\": false}," +
									"{\"text\": \"Red Bull\",\"leaf\": true,\"checked\": false}" +
								"]" +
							"}" +
						"]" +
					"}" +
					"]");
		}else if("queryMaterial".equals(action)){
			String id = request.getParamsMap().get("id");
			List<Map<String,Object>> list = hibernateSessionDAO.createHqlQuery("select new map(a.id as id, a.ktvid as ktvid,a.materialid as materialid,a.count as count,b.paramsname as materialname,c.paramsname as unitname) from KtvStayList a,MaterialListKtv b,ParamsList c where b.unit=c.paramscode and c.typeid='KTV_UNIT' and a.materialid=b.id and a.ktvid='"+id+"' order by a.id desc");
			Gson g = new Gson();
			request.setResponse(g.toJson(list));
		}else if("surePsd".equals(action)){
			String password = request.getParamsMap().get("password");
			String cardid = request.getParamsMap().get("cardid");
			List<KtvStayInfo> list = hibernateSessionDAO.createHqlQuery("from KtvStayInfo where cardid='"+cardid+"' and state='1'");
			if(list.size()==0){
				List<KtvStayInfo> listOver = hibernateSessionDAO.createHqlQuery("from KtvStayInfo where cardid='"+cardid+"' and state='3'");
				if(listOver.size()>0){
					if(!listOver.get(0).getPassword().equals(password)){
						request.setResponse("{success:true,msg:'卡号为<font color=red>"+cardid+"</font>的密码错误!'}");
						return request;
					}
					request.setResponse("{success:true,msg:'overtime'}");
					return request;
				}
				
				request.setResponse("{success:true,msg:'卡号为<font color=red>"+cardid+"</font>并未保存物品!'}");
				return request;
			}else if(!list.get(0).getPassword().equals(password)){
				request.setResponse("{success:true,msg:'卡号为<font color=red>"+cardid+"</font>的密码错误!'}");
				return request;
			}
			request.setResponse("{success:true,msg:'success'}");
		}
		return request;
	}

}
