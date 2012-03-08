package com.hrm.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.MaterialList;
import com.hrm.entity.MaterialStoreList;
import com.hrm.entity.MaterialStoreListId;
import com.hrm.entity.UserInfo;

public class MaterialStoreVo implements BaseVo {
	private HibernateSessionDAO hibernateSessionDAO;
	
	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}

	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}

	@Override
	public Request execute(Request request) throws Exception {
		String storedate = request.getParamsMap().get("storedate");
		String action = request.getParamsMap().get("action");
		if(action.equals("check")){
			if (storedate.equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				storedate = sdf.format(new Date());
			}
			List<Long> list = hibernateSessionDAO.createHqlQuery("select count(*) from MaterialStoreList where id.storedate='"+storedate+"'");
			if(list.get(0)==0){
				List<MaterialList> mlist = hibernateSessionDAO.createHqlQuery("from MaterialList");
				for (MaterialList materialList : mlist) {
					MaterialStoreList store = new MaterialStoreList();
					MaterialStoreListId id = new MaterialStoreListId();
					id.setId(materialList.getId());
					id.setStoredate(storedate);
					store.setId(id);
					store.setCost(materialList.getCost());
					store.setInitsum(0F);
					store.setTypeid(materialList.getTypeid());
					store.setUpdtuser(((UserInfo)request.getUserInfo()).getUserId());
					store.setUpdttime(new Date());
					hibernateSessionDAO.save(store);
				}
			}
			request.setResponse("");
		}else if (action.equals("updateRecord")) {
			
		}
		
		return request;
	}

}
