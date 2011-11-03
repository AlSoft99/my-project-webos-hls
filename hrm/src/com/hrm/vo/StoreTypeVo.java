package com.hrm.vo;

import java.sql.SQLException;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.control.Request;
import com.hrm.dao.StoreListDAO;
import com.hrm.dao.StoreTypeDAO;
import com.hrm.entity.StoreList;
import com.hrm.entity.StoreType;
import com.hrm.entity.UserInfo;
import com.hrm.server.InitData;
import com.hrm.util.ClsFactory;
import com.hrm.util.StringUtil;

public class StoreTypeVo implements BaseVo{

	private StoreTypeDAO storeTypeDAO;
	private StoreListDAO storeListDAO;
	
	public Request execute(Request request) throws Exception{
		String action = request.getParamsMap().get("action");
		UserInfo userInfo = (UserInfo)request.getUserInfo();
		BeanFactory factory = ClsFactory.newInstance().getFactory();
		InitData init = factory.getBean("initData", InitData.class);
		String result = "";
		if("insert".equals(action)){
			
			boolean isRoot = Boolean.valueOf(request.getParamsMap().get("isRoot"));
			String outuser = request.getParamsMap().get("outuser");
			String createId = createId();
			if (isRoot) {
				StoreType store = new StoreType();
				store.setId(createId);
				store.setTypedesc(request.getParamsMap().get("tree_tips"));
				store.setTypename(request.getParamsMap().get("tree_name"));
//				store.setStorelist(InitData.getUserList(outuser+Constant.TAG[1]));
				store.setStorelist(outuser);
				store.setUpdttime(new Date());
				store.setUpdtuser(userInfo.getUserId());
				storeTypeDAO.save(store);
			}else{
				String parentNodeId = request.getParamsMap().get("parentNodeId");
				StoreList store = new StoreList();
				store.setGoodsdesc(request.getParamsMap().get("tree_tips"));
				store.setGoodsname(request.getParamsMap().get("tree_name"));
				store.setInitnumber(Integer.parseInt(request.getParamsMap().get("tree_number")));
				store.setId(createId);
				store.setPrice(Double.valueOf(request.getParamsMap().get("tree_price")));
				store.setTypeid(parentNodeId);
				store.setUpdttime(new Date());
				store.setUpdtuser(userInfo.getUserId());
				storeListDAO.save(store);
			}
			init.initJsonStoreTypeInfo();
			result = "{success:true,msg:'"+createId+"'}";
		}else if("update".equals(action)){
			StoreType store = storeTypeDAO.findById(request.getParamsMap().get("menu_id"));
			store.setTypedesc(request.getParamsMap().get("tree_tips"));
			store.setTypename(request.getParamsMap().get("tree_name"));
			store.setUpdttime(new Date());
			store.setUpdtuser(userInfo.getUserId());
			storeTypeDAO.getHibernateTemplate().update(store);
			init.initJsonStoreTypeInfo();
			result = "{success:true,msg:''}";
		}else if("delete".equals(action)){
			final String typeid = request.getParamsMap().get("menu_code");
			StoreType store = new StoreType();
			store.setId(typeid);
			boolean flag = storeTypeDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){

				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					session.createQuery("delete from StoreList where typeid='"+typeid+"'").executeUpdate();
					int updt = session.createQuery("delete from StoreType where id='"+typeid+"'").executeUpdate();
					return updt>0;
				}
			});
			if(flag){
				result = "{success:true,msg:''}";
			}else{
				result = "{failure:true,msg:'删除失败!'}";
			}
			init.initJsonStoreTypeInfo();
		}else if("insertRecord".equals(action)){
			String createId = StringUtil.newInstance().createId("GD");
			StoreList store = new StoreList();
			store.setGoodsdesc(request.getParamsMap().get("goodsdesc"));
			store.setGoodsname(request.getParamsMap().get("goodsname"));
			store.setInitnumber(Integer.parseInt(request.getParamsMap().get("initnumber")));
			store.setPrice(Double.valueOf(request.getParamsMap().get("price")));
			store.setTypeid(request.getParamsMap().get("typeid"));
			store.setInnumber(0);
			store.setOutnumber(0);
			store.setId(createId);
			store.setUpdttime(new Date());
			store.setUpdtuser(userInfo.getUserId());
			storeListDAO.save(store);
			init.initJsonStoreList();
			result = "{success:true,msg:'"+createId+"'}";
		}else if("updateRecord".equals(action)){
			StoreList store = storeListDAO.findById(request.getParamsMap().get("id"));
			store.setGoodsdesc(request.getParamsMap().get("goodsdesc"));
			store.setGoodsname(request.getParamsMap().get("goodsname"));
			store.setInitnumber(Integer.parseInt(request.getParamsMap().get("initnumber")));
			store.setPrice(Double.valueOf(request.getParamsMap().get("price")));
			store.setTypeid(request.getParamsMap().get("typeid"));
			store.setUpdttime(new Date());
			store.setUpdtuser(userInfo.getUserId());
			storeListDAO.getHibernateTemplate().update(store);
			init.initJsonStoreList();
			result = "{success:true,msg:''}";
		}else if("deleteRecord".equals(action)){
			final String[] id = request.getParamsMap().get("id").split(",");
			boolean flag = storeTypeDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){

				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					int updt = 0;
					for (int i = 0; i < id.length; i++) {
						updt = session.createQuery("delete from StoreList where id ='"+id[i]+"'").executeUpdate();
					}
					
					return updt>0;
				}
			});
			init.initJsonStoreList();
			if(flag){
				result = "{success:true,msg:''}";
			}else{
				result = "{failure:true,msg:'删除失败!'}";
			}
		}
		init.initJsonChangeStoreTypeInfo();
		request.setResponse(result);
		return request;
	}
	public String createId(){
		return "IS"+System.currentTimeMillis()+(Math.random()+"").substring(5, 15)+"";
	}
	public StoreTypeDAO getStoreTypeDAO() {
		return storeTypeDAO;
	}
	public void setStoreTypeDAO(StoreTypeDAO storeTypeDAO) {
		this.storeTypeDAO = storeTypeDAO;
	}
	public StoreListDAO getStoreListDAO() {
		return storeListDAO;
	}
	public void setStoreListDAO(StoreListDAO storeListDAO) {
		this.storeListDAO = storeListDAO;
	}

}
