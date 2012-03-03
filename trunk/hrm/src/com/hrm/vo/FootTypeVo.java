package com.hrm.vo;

import java.sql.SQLException;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hrm.control.Request;
import com.hrm.dao.FootListDAO;
import com.hrm.dao.FootMaterialDAO;
import com.hrm.dao.FootTypeDAO;
import com.hrm.entity.FootList;
import com.hrm.entity.FootMaterial;
import com.hrm.entity.FootType;
import com.hrm.entity.UserInfo;
import com.hrm.server.InitData;
import com.hrm.util.ClsFactory;
import com.hrm.util.StringUtil;

public class FootTypeVo implements BaseVo {
	private FootTypeDAO footTypeDAO;
	private FootListDAO footListDAO;
	private FootMaterialDAO footMaterialDAO;
	public Request execute(Request request) throws Exception{
		String action = request.getParamsMap().get("action");
		UserInfo userInfo = (UserInfo)request.getUserInfo();
		String result = "";
		if("insert".equals(action)){
			
			String id = request.getParamsMap().get("tree_code");
			//FootType type = footTypeDAO.findById(id);
			//if(type==null){
				FootType goods = new FootType();
//				goods.setId(id);
				goods.setTypedesc(request.getParamsMap().get("tree_tips"));
				goods.setTypename(request.getParamsMap().get("tree_name"));
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				footTypeDAO.save(goods);
				result = "{success:true,msg:'"+goods.getId()+"'}";
			//}else{
			//	result = "{failure:true,msg:'参数编号重复,请另起名字!'}";
			//}
			
			
		}else if("update".equals(action)){
			FootType goods = new FootType();
			goods.setId(request.getParamsMap().get("menu_id"));
			goods.setTypedesc(request.getParamsMap().get("tree_tips"));
			goods.setTypename(request.getParamsMap().get("tree_name"));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			footTypeDAO.getHibernateTemplate().update(goods);
			result = "{success:true,msg:''}";
		}else if("delete".equals(action)){
			final String typeid = request.getParamsMap().get("menu_code");
			boolean flag = footTypeDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){

				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					session.createQuery("delete from FootList where typeid='"+typeid+"'").executeUpdate();
					int updt = session.createQuery("delete from FootType where id='"+typeid+"'").executeUpdate();
					return updt>0;
				}
			});
			if(flag){
				result = "{success:true,msg:''}";
			}else{
				result = "{failure:true,msg:'删除失败!'}";
			}
		}else if("insertRecord".equals(action)){
			String createId = StringUtil.newInstance().createId("PD");
			FootList goods = new FootList();
			goods.setParamsdesc(request.getParamsMap().get("paramsdesc"));
			goods.setParamsname(request.getParamsMap().get("paramsname"));
			goods.setParamscode(request.getParamsMap().get("paramscode"));
			goods.setId(createId);
			goods.setTypeid(request.getParamsMap().get("typeid"));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			footListDAO.save(goods);
			result = "{success:true,msg:'"+createId+"'}";
		}else if("updateRecord".equals(action)){
			FootList goods = footListDAO.findById(request.getParamsMap().get("id"));
			goods.setTypeid(request.getParamsMap().get("typeid"));
			goods.setParamscode(request.getParamsMap().get("paramscode"));
			goods.setParamsdesc(request.getParamsMap().get("paramsdesc"));
			goods.setParamsname(request.getParamsMap().get("paramsname"));
			System.out.println("request.getParamsMap().get(\"cost\"):"+request.getParamsMap().get("cost")+"    "+request.getParamsMap().get("price"));
			goods.setCost(Float.valueOf(request.getParamsMap().get("cost")));
			goods.setPrice(Float.valueOf(request.getParamsMap().get("price")));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			footListDAO.getHibernateTemplate().update(goods);
			result = "{success:true,msg:''}";
		}else if("deleteRecord".equals(action)){
			final String[] id = request.getParamsMap().get("id").split(",");
			final String typeid = request.getParamsMap().get("typeid");
			boolean flag = footTypeDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					int updt = 0;
					for (int i = 0; i < id.length; i++) {
						updt = session.createQuery("delete from FootList where id ='"+id[i]+"' and typeid='"+typeid+"'").executeUpdate();
						session.createQuery("delete from FootMaterial where footid ='"+id[i]+"'").executeUpdate();
					}
					return updt>0;
				}
			});
			if(flag){
				result = "{success:true,msg:''}";
			}else{
				result = "{failure:true,msg:'删除失败!'}";
			}
		}else if("insertFootMaterial".equals(action)){
			FootMaterial goods =  (FootMaterial)request.getEntity();
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			footMaterialDAO.save(goods);
			result = "{success:true,msg:'"+goods.getId()+"'}";
		}else if("updateFootMaterial".equals(action)){
			FootMaterial goods =  (FootMaterial)request.getEntity();
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			footListDAO.getHibernateTemplate().update(goods);
			result = "{success:true,msg:''}";
		}else if("deleteFootMaterial".equals(action)){
			final String[] id = request.getParamsMap().get("id").split(",");
			final String footid = request.getParamsMap().get("footid");
			boolean flag = footTypeDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					int updt = 0;
					for (int i = 0; i < id.length; i++) {
						updt = session.createQuery("delete from FootMaterial where id ='"+id[i]+"' and footid='"+footid+"'").executeUpdate();
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
	public FootTypeDAO getFootTypeDAO() {
		return footTypeDAO;
	}
	public void setFootTypeDAO(FootTypeDAO footTypeDAO) {
		this.footTypeDAO = footTypeDAO;
	}
	public FootListDAO getFootListDAO() {
		return footListDAO;
	}
	public void setFootListDAO(FootListDAO footListDAO) {
		this.footListDAO = footListDAO;
	}
	public String createId(){
		return "MU"+System.currentTimeMillis()+(Math.random()+"").substring(5, 15)+"";
	}
	public FootMaterialDAO getFootMaterialDAO() {
		return footMaterialDAO;
	}
	public void setFootMaterialDAO(FootMaterialDAO footMaterialDAO) {
		this.footMaterialDAO = footMaterialDAO;
	}
}
