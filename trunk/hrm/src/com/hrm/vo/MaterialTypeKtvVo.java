package com.hrm.vo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.MaterialListKtv;
import com.hrm.entity.MaterialTypeKtv;
import com.hrm.entity.UserInfo;
import com.hrm.server.InitData;
import com.hrm.util.ClsFactory;
import com.hrm.util.StringUtil;

public class MaterialTypeKtvVo implements BaseVo {
	private HibernateSessionDAO hibernateSessionDAO;
	public Request execute(Request request) throws Exception{
		String action = request.getParamsMap().get("action");
		UserInfo userInfo = (UserInfo)request.getUserInfo();
		String result = "";
		if("insert".equals(action)){
			
			String id = request.getParamsMap().get("tree_code");
			String createId = StringUtil.newInstance().createId("KV");
			//FootType type = footTypeDAO.findById(id);
			//if(type==null){
				MaterialTypeKtv goods = new MaterialTypeKtv();
				goods.setId(createId);
				goods.setTypedesc(request.getParamsMap().get("tree_tips"));
				goods.setTypename(request.getParamsMap().get("tree_name"));
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				hibernateSessionDAO.save(goods);
				result = "{success:true,msg:'"+goods.getId()+"'}";
			//}else{
			//	result = "{failure:true,msg:'参数编号重复,请另起名字!'}";
			//}
			
			
		}else if("update".equals(action)){
			MaterialTypeKtv goods = new MaterialTypeKtv();
			goods.setId(request.getParamsMap().get("menu_id"));
			goods.setTypedesc(request.getParamsMap().get("tree_tips"));
			goods.setTypename(request.getParamsMap().get("tree_name"));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			hibernateSessionDAO.getHibernateTemplate().update(goods);
			result = "{success:true,msg:''}";
		}else if("delete".equals(action)){
			final String typeid = request.getParamsMap().get("menu_code");
			boolean flag = hibernateSessionDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){

				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					
					session.createQuery("delete from MaterialListKtv where typeid='"+typeid+"'").executeUpdate();
					int updt = session.createQuery("delete from MaterialTypeKtv where id='"+typeid+"'").executeUpdate();
					return updt>0;
				}
			});
			if(flag){
				result = "{success:true,msg:''}";
			}else{
				result = "{failure:true,msg:'删除失败!'}";
			}
		}else if("insertRecord".equals(action)){
			String createId = StringUtil.newInstance().createId("KP");
			MaterialListKtv goods = new MaterialListKtv();
			goods.setParamsdesc(request.getParamsMap().get("paramsdesc"));
			goods.setParamsname(request.getParamsMap().get("paramsname"));
			goods.setParamscode(request.getParamsMap().get("paramscode"));
			goods.setCost(Float.valueOf(request.getParamsMap().get("cost")));
			goods.setUnit("");
			goods.setId(createId);
			goods.setTypeid(request.getParamsMap().get("typeid"));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			hibernateSessionDAO.save(goods);
			result = "{success:true,msg:'"+createId+"'}";
		}else if("updateRecord".equals(action)){
			String name = request.getParamsMap().get("paramsname");
			boolean error = false;
			List<MaterialListKtv> list = hibernateSessionDAO.getHibernateTemplate().find("from MaterialListKtv where paramsname='"+name+"'");
			if(list.size()>0){
				MaterialListKtv material = list.get(0);
				if(!material.getId().equals(request.getParamsMap().get("id"))){
					error = true;
				}
			}
			if(error){
				result = "{success:true,msg:'error'}";
			}else{
				MaterialListKtv goods = new MaterialListKtv();
				goods.setId(request.getParamsMap().get("id"));
				goods.setTypeid(request.getParamsMap().get("typeid"));
				goods.setParamscode(request.getParamsMap().get("paramscode"));
				goods.setParamsdesc(request.getParamsMap().get("paramsdesc"));
				goods.setParamsname(name);
				goods.setCost(Float.valueOf(request.getParamsMap().get("cost")));
				goods.setUnit(request.getParamsMap().get("unit"));
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				hibernateSessionDAO.getHibernateTemplate().update(goods);
				result = "{success:true,msg:''}";
			}
			
		}else if("deleteRecord".equals(action)){
			final String[] id = request.getParamsMap().get("id").split(",");
			final String typeid = request.getParamsMap().get("typeid");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			final String storedate = sdf.format(new Date());
			boolean flag = hibernateSessionDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					int updt = 0;
					for (int i = 0; i < id.length; i++) {
						updt = session.createQuery("delete from MaterialListKtv where id.id ='"+id[i]+"' and id.typeid='"+typeid+"'").executeUpdate();
					}
					return updt>0;
				}
			});
			if(flag){
				result = "{success:true,msg:''}";
			}else{
				result = "{failure:true,msg:'删除失败!'}";
			}
		}
		BeanFactory factory = ClsFactory.newInstance().getFactory();
		InitData init = factory.getBean("initData", InitData.class);
		init.initConstantInfo();
		request.setResponse(result);
		return request;
	}
	public String createId(){
		return "KV"+System.currentTimeMillis()+(Math.random()+"").substring(5, 15)+"";
	}
	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}
	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}
	
}
