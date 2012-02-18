package com.hrm.vo;

import java.sql.SQLException;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hrm.control.Request;
import com.hrm.dao.MaterialListDAO;
import com.hrm.dao.MaterialTypeDAO;
import com.hrm.entity.MaterialList;
import com.hrm.entity.MaterialType;
import com.hrm.entity.UserInfo;
import com.hrm.server.InitData;
import com.hrm.util.ClsFactory;
import com.hrm.util.StringUtil;

public class MaterialTypeVo implements BaseVo {
	private MaterialTypeDAO materialTypeDAO;
	private MaterialListDAO materialListDAO;
	public Request execute(Request request) throws Exception{
		String action = request.getParamsMap().get("action");
		UserInfo userInfo = (UserInfo)request.getUserInfo();
		String result = "";
		if("insert".equals(action)){
			
			String id = request.getParamsMap().get("tree_code");
			//FootType type = footTypeDAO.findById(id);
			//if(type==null){
				MaterialType goods = new MaterialType();
//				goods.setId(id);
				goods.setTypedesc(request.getParamsMap().get("tree_tips"));
				goods.setTypename(request.getParamsMap().get("tree_name"));
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				materialTypeDAO.save(goods);
				result = "{success:true,msg:'"+goods.getId()+"'}";
			//}else{
			//	result = "{failure:true,msg:'参数编号重复,请另起名字!'}";
			//}
			
			
		}else if("update".equals(action)){
			MaterialType goods = new MaterialType();
			goods.setId(request.getParamsMap().get("menu_id"));
			goods.setTypedesc(request.getParamsMap().get("tree_tips"));
			goods.setTypename(request.getParamsMap().get("tree_name"));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			materialTypeDAO.getHibernateTemplate().update(goods);
			result = "{success:true,msg:''}";
		}else if("delete".equals(action)){
			final String typeid = request.getParamsMap().get("menu_code");
			boolean flag = materialTypeDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){

				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					session.createQuery("delete from MaterialList where typeid='"+typeid+"'").executeUpdate();
					int updt = session.createQuery("delete from MaterialType where id='"+typeid+"'").executeUpdate();
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
			MaterialList goods = new MaterialList();
			goods.setParamsdesc(request.getParamsMap().get("paramsdesc"));
			goods.setParamsname(request.getParamsMap().get("paramsname"));
			goods.setParamscode(request.getParamsMap().get("paramscode"));
			goods.setId(createId);
			goods.setTypeid(request.getParamsMap().get("typeid"));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			materialListDAO.save(goods);
			result = "{success:true,msg:'"+createId+"'}";
		}else if("updateRecord".equals(action)){
			MaterialList goods = new MaterialList();
			goods.setId(request.getParamsMap().get("id"));
			goods.setTypeid(request.getParamsMap().get("typeid"));
			goods.setParamscode(request.getParamsMap().get("paramscode"));
			goods.setParamsdesc(request.getParamsMap().get("paramsdesc"));
			goods.setParamsname(request.getParamsMap().get("paramsname"));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			materialListDAO.getHibernateTemplate().update(goods);
			result = "{success:true,msg:''}";
		}else if("deleteRecord".equals(action)){
			final String[] id = request.getParamsMap().get("id").split(",");
			final String typeid = request.getParamsMap().get("typeid");
			boolean flag = materialTypeDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					int updt = 0;
					for (int i = 0; i < id.length; i++) {
						updt = session.createQuery("delete from MaterialList where id.id ='"+id[i]+"' and id.typeid='"+typeid+"'").executeUpdate();
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
	public MaterialTypeDAO getMaterialTypeDAO() {
		return materialTypeDAO;
	}
	public void setMaterialTypeDAO(MaterialTypeDAO materialTypeDAO) {
		this.materialTypeDAO = materialTypeDAO;
	}
	public MaterialListDAO getMaterialListDAO() {
		return materialListDAO;
	}
	public void setMaterialListDAO(MaterialListDAO materialListDAO) {
		this.materialListDAO = materialListDAO;
	}
	public String createId(){
		return "MU"+System.currentTimeMillis()+(Math.random()+"").substring(5, 15)+"";
	}
}
