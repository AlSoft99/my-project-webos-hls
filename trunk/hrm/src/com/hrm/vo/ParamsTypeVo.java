package com.hrm.vo;

import java.sql.SQLException;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.control.Request;
import com.hrm.dao.ParamsListDAO;
import com.hrm.dao.ParamsTypeDAO;
import com.hrm.entity.ParamsList;
import com.hrm.entity.ParamsType;
import com.hrm.entity.UserInfo;
import com.hrm.server.InitData;
import com.hrm.util.ClsFactory;
import com.hrm.util.StringUtil;

public class ParamsTypeVo implements BaseVo {
	private ParamsTypeDAO paramsTypeDAO;
	private ParamsListDAO paramsListDAO;
	public Request execute(Request request) throws Exception{
		String action = request.getParamsMap().get("action");
		UserInfo userInfo = (UserInfo)request.getUserInfo();
		String result = "";
		if("insert".equals(action)){
			
			String id = request.getParamsMap().get("tree_code");
			ParamsType type = paramsTypeDAO.findById(id);
			if(type==null){
				ParamsType goods = new ParamsType();
				goods.setId(id);
				goods.setTypedesc(request.getParamsMap().get("tree_tips"));
				goods.setTypename(request.getParamsMap().get("tree_name"));
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				paramsTypeDAO.save(goods);
				result = "{success:true,msg:'"+request.getParamsMap().get("tree_code")+"'}";
			}else{
				result = "{failure:true,msg:'参数编号重复,请另起名字!'}";
			}
			
			
		}else if("update".equals(action)){
			ParamsType goods = new ParamsType();
			goods.setId(request.getParamsMap().get("menu_id"));
			goods.setTypedesc(request.getParamsMap().get("tree_tips"));
			goods.setTypename(request.getParamsMap().get("tree_name"));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			paramsTypeDAO.getHibernateTemplate().update(goods);
			result = "{success:true,msg:''}";
		}else if("delete".equals(action)){
			final String typeid = request.getParamsMap().get("menu_code");
			boolean flag = paramsTypeDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){

				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					session.createQuery("delete from ParamsList where typeid='"+typeid+"'").executeUpdate();
					int updt = session.createQuery("delete from ParamsType where id='"+typeid+"'").executeUpdate();
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
			ParamsList goods = new ParamsList();
			goods.setParamsdesc(request.getParamsMap().get("paramsdesc"));
			goods.setParamsname(request.getParamsMap().get("paramsname"));
			goods.setParamscode(request.getParamsMap().get("paramscode"));
			goods.setId(createId);
			goods.setTypeid(request.getParamsMap().get("typeid"));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			paramsListDAO.save(goods);
			result = "{success:true,msg:'"+createId+"'}";
		}else if("updateRecord".equals(action)){
			ParamsList goods = new ParamsList();
			goods.setId(request.getParamsMap().get("id"));
			goods.setTypeid(request.getParamsMap().get("typeid"));
			goods.setParamscode(request.getParamsMap().get("paramscode"));
			goods.setParamsdesc(request.getParamsMap().get("paramsdesc"));
			goods.setParamsname(request.getParamsMap().get("paramsname"));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			paramsListDAO.getHibernateTemplate().update(goods);
			result = "{success:true,msg:''}";
		}else if("deleteRecord".equals(action)){
			final String[] id = request.getParamsMap().get("id").split(",");
			final String typeid = request.getParamsMap().get("typeid");
			boolean flag = paramsTypeDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					int updt = 0;
					for (int i = 0; i < id.length; i++) {
						updt = session.createQuery("delete from ParamsList where id.id ='"+id[i]+"' and id.typeid='"+typeid+"'").executeUpdate();
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
	public ParamsTypeDAO getParamsTypeDAO() {
		return paramsTypeDAO;
	}
	public void setParamsTypeDAO(ParamsTypeDAO paramsTypeDAO) {
		this.paramsTypeDAO = paramsTypeDAO;
	}
	public ParamsListDAO getParamsListDAO() {
		return paramsListDAO;
	}
	public void setParamsListDAO(ParamsListDAO paramsListDAO) {
		this.paramsListDAO = paramsListDAO;
	}

}
