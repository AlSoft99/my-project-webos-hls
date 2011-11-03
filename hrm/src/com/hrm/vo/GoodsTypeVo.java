package com.hrm.vo;

import java.util.Date;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.control.Request;
import com.hrm.dao.GoodsListDAO;
import com.hrm.dao.GoodsTypeDAO;
import com.hrm.entity.GoodsList;
import com.hrm.entity.GoodsListId;
import com.hrm.entity.GoodsStat;
import com.hrm.entity.GoodsStatId;
import com.hrm.entity.UserInfo;

public class GoodsTypeVo implements BaseVo {
	private GoodsTypeDAO goodsTypeDAO;
	private GoodsListDAO goodsListDAO;
	
	public Request execute(Request request) throws Exception{
		String action = request.getParamsMap().get("action");
		UserInfo userInfo = (UserInfo)request.getUserInfo();
		String result = "";
		if("insert".equals(action)){
			
			/*boolean isRoot = Boolean.valueOf(request.getParameter("isRoot"));
			String outuser = request.getParameter("outuser");
			String createId = createId();
			if (isRoot) {
				GoodsType goods = new GoodsType();
				goods.setId(createId);
				goods.setTypedesc(request.getParameter("tree_tips"));
				goods.setTypename(request.getParameter("tree_name"));
				goods.setOutuser(outuser);
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				goodsTypeDAO.save(goods);
			}else{
				String parentNodeId = request.getParameter("parentNodeId");
				GoodsList goods = new GoodsList();
				goods.setGoodsdesc(request.getParameter("tree_tips"));
				goods.setGoodsname(request.getParameter("tree_name"));
				goods.setGoodsnumber(Integer.parseInt(request.getParameter("tree_number")));
				goods.setId(createId);
				goods.setPrice(Long.valueOf(request.getParameter("tree_price")));
				goods.setTypeid(parentNodeId);
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				goodsListDAO.save(goods);
			}
			result = "{success:true,msg:'"+createId+"'}";*/
		}else if("update".equals(action)){
			/*GoodsType goods = goodsTypeDAO.findById(request.getParameter("menu_id"));
			goods.setTypedesc(request.getParameter("tree_tips"));
			goods.setTypename(request.getParameter("tree_name"));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			goodsTypeDAO.update(goods);
			result = "{success:true,msg:''}";*/
		}else if("delete".equals(action)){
			/*final String typeid = request.getParameter("menu_code");
			GoodsType goods = new GoodsType();
			goods.setId(typeid);
			boolean flag = goodsTypeDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){

				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					session.createQuery("delete from GoodsList where typeid='"+typeid+"'").executeUpdate();
					int updt = session.createQuery("delete from GoodsType where id='"+typeid+"'").executeUpdate();
					return updt>0;
				}
			});
			if(flag){
				result = "{success:true,msg:''}";
			}else{
				result = "{failure:true,msg:'删除失败!'}";
			}*/
		}else if("insertRecord".equals(action)){
			/*String createId = StringUtil.newInstance().createId("GD");
			GoodsList goods = new GoodsList();
			goods.setGoodsdesc(request.getParameter("goodsdesc"));
			goods.setGoodsname(request.getParameter("goodsname"));
			goods.setGoodsnumber(Integer.parseInt(request.getParameter("goodsnumber")));
			goods.setPrice(Long.valueOf(request.getParameter("price")));
			goods.setTypeid(request.getParameter("typeid"));
			goods.setId(createId);
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			goodsListDAO.save(goods);
			result = "{success:true,msg:'"+createId+"'}";*/
		}else if("updateRecord".equals(action)){
			GoodsList goods = null;
			GoodsListId id = new GoodsListId();
			id.setId(request.getParamsMap().get("id"));
			id.setUserlist(request.getParamsMap().get("userlist"));
			goods = goodsListDAO.getHibernateTemplate().get(GoodsList.class, id);
			if (goods==null) {
				goods = new GoodsList();
				goods.setId(id);
				goods.setInnumber(0);
				goods.setOutnumber(0);
			}
			goods.setGoodsdesc(request.getParamsMap().get("goodsdesc"));
			goods.setGoodsname(request.getParamsMap().get("goodsname"));
			goods.setGoodsnumber(Integer.parseInt((request.getParamsMap().get("goodsnumber").equals("")?"0":request.getParamsMap().get("goodsnumber"))));
			goods.setPrice(Double.valueOf(request.getParamsMap().get("price")));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			goodsListDAO.attachDirty(goods);
			GoodsStatId idstat = new GoodsStatId();
			idstat.setGoodsid(request.getParamsMap().get("id"));
			idstat.setUserlist(request.getParamsMap().get("userlist"));
			GoodsStat stat = goodsListDAO.getHibernateTemplate().get(GoodsStat.class, idstat);
			if(stat==null){
				stat = new GoodsStat();
				stat.setId(idstat);
				stat.setOutnumber(0);
				stat.setReturnnumber(0);
				stat.setStorenumber(0);
				stat.setUpdttime(new Date());
				stat.setUpdtuser(userInfo.getUserId());
				goodsListDAO.getHibernateTemplate().save(stat);
			}
			result = "{success:true,msg:''}";
		}else if("deleteRecord".equals(action)){
			/*final String[] id = request.getParameter("id").split(",");
			boolean flag = goodsTypeDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){

				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					int updt = 0;
					for (int i = 0; i < id.length; i++) {
						updt = session.createQuery("delete from GoodsList where id ='"+id[i]+"'").executeUpdate();
					}
					
					return updt>0;
				}
			});
			if(flag){
				result = "{success:true,msg:''}";
			}else{
				result = "{failure:true,msg:'删除失败!'}";
			}*/
		}
//		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
/*		InitData init = factory.getBean("initData", InitData.class);
		init.initJsonGoodsList();
		init.initJsonGoodsTypeInfo();
		init.initJsonChangeGoodsTypeInfo();*/
		request.setResponse(result);
		return request;
	}
	public String createId(){
		return "GS"+System.currentTimeMillis()+(Math.random()+"").substring(5, 15)+"";
	}
	public GoodsTypeDAO getGoodsTypeDAO() {
		return goodsTypeDAO;
	}
	public void setGoodsTypeDAO(GoodsTypeDAO goodsTypeDAO) {
		this.goodsTypeDAO = goodsTypeDAO;
	}
	public GoodsListDAO getGoodsListDAO() {
		return goodsListDAO;
	}
	public void setGoodsListDAO(GoodsListDAO goodsListDAO) {
		this.goodsListDAO = goodsListDAO;
	}

}
