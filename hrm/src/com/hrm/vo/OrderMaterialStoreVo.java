package com.hrm.vo;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.GoodsList;
import com.hrm.entity.GoodsListId;
import com.hrm.entity.GoodsMonthStat;
import com.hrm.entity.GoodsMonthStatId;
import com.hrm.entity.GoodsOutputList;
import com.hrm.entity.GoodsStat;
import com.hrm.entity.GoodsStatId;
import com.hrm.entity.OrderMaterialStoreInfo;
import com.hrm.entity.OrderMaterialStoreList;
import com.hrm.entity.OrderOutputList;
import com.hrm.entity.OrderSecondMaterialId;
import com.hrm.entity.OrderSecondMaterialList;
import com.hrm.entity.UserInfo;
import com.hrm.server.InitData;
import com.hrm.util.Constant;
import com.hrm.util.StringUtil;

public class OrderMaterialStoreVo implements BaseVo {
	private HibernateSessionDAO hibernateSessionDAO;
	
	/* (non-Javadoc)
	 * @see com.hrm.vo.BaseVo#execute(com.hrm.control.Request)
	 */
	public Request execute(Request request) throws Exception{
		String action = request.getParamsMap().get("action");
		final UserInfo userInfo = (UserInfo)request.getUserInfo();
		String result = "";
		if("insert".equals(action)){
			String createId = "";
			String header = userInfo.getUserCode().substring(0, 5).toUpperCase();
			List<OrderMaterialStoreInfo> list = hibernateSessionDAO.createHqlQuery("from OrderMaterialStoreInfo where outdate>'"+StringUtil.newInstance().getCurrentDate()+"' and outdate<'"+StringUtil.newInstance().dayAddNumber(new Date(), 1)+"' order by id desc", -1, -1);
			if(list.size()==0){
				createId = StringUtil.newInstance().createAccId(header, 1, 5);
			}else{
				OrderMaterialStoreInfo info = list.get(0);
				String id = info.getId();
				String idNumber = id.substring(13, id.length());
				int number = Integer.parseInt(idNumber);
				createId = StringUtil.newInstance().createAccId(header, ++number, 5);
			}
			
			OrderMaterialStoreInfo info = new OrderMaterialStoreInfo();
			info.setOrderdesc(request.getParamsMap().get("tree_tips"));
			info.setId(createId);
			info.setOutdate(new Date());
			info.setOutuser(userInfo.getUserId());
			info.setUpdttime(new Date());
			info.setUpdtuser(userInfo.getUserId());
			hibernateSessionDAO.save(info);
			result = "{success:true,msg:'"+createId+"'}";
		}else if("update".equals(action)){
			String id = request.getParamsMap().get("menu_id");
			String current = StringUtil.newInstance().getCurrentDate("yyyyMMdd");
			String outdate = id.substring(5, 13);
			if(!outdate.equals(current)){
				result = "{failure:true,msg:'不是今天账单不能增删改!'}";
			}else{
				OrderMaterialStoreInfo info = (OrderMaterialStoreInfo)hibernateSessionDAO.getHibernateTemplate().get("com.hrm.entity.OrderMaterialStoreInfo", id);;
				info.setOrderdesc(request.getParamsMap().get("tree_tips"));
				info.setOutuser(userInfo.getUserId());
				info.setUpdttime(new Date());
				info.setUpdtuser(userInfo.getUserId());
				hibernateSessionDAO.getHibernateTemplate().update(info);
				result = "{success:true,msg:'"+id+"'}";
			}
		}else if("delete".equals(action)){
			final String typeid = request.getParamsMap().get("menu_code");
			String current = StringUtil.newInstance().getCurrentDate("yyyyMMdd");
			String outdate = typeid.substring(5, 13);
			if(!outdate.equals(current)){
				result = "{failure:true,msg:'不是今天账单不能增删改!'}";
			}else{
				
				boolean flag = hibernateSessionDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
					public Boolean doInHibernate(Session session)
							throws HibernateException, SQLException {
						session.createQuery("delete from OrderMaterialStoreList where outid='"+typeid+"'").executeUpdate();
						int updt = session.createQuery("delete from OrderMaterialStoreInfo where id='"+typeid+"'").executeUpdate();
						return updt>0;
					}
				});
				if(flag){
					result = "{success:true,msg:''}";
				}else{
					result = "{failure:true,msg:'删除失败!'}";
				}
			}
		}else if("insertRecord".equals(action)){
			String createId = StringUtil.newInstance().createId("GD");
			String outid = request.getParamsMap().get("outid");
			String current = StringUtil.newInstance().getCurrentDate("yyyyMMdd");
			String outdate = outid.substring(5, 13);
			if(!outdate.equals(current)){
				result = "{failure:true,msg:'不是今天账单不能增删改!'}";
			}else{
				OrderMaterialStoreList goods = new OrderMaterialStoreList();
				goods.setId(createId);
				goods.setOutid(outid);
				goods.setMaterialid("");
				goods.setActuallypay(0F);
				goods.setShouldpay(0F);
				goods.setSum(Float.valueOf(request.getParamsMap().get("sum")));
				//goods.setGoodsnumber(Float.valueOf(request.getParamsMap().get("goodsnumber")));
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				hibernateSessionDAO.save(goods);
				result = "{success:true,msg:'"+createId+"'}";
			}
		}else if("updateRecord".equals(action)){
			String outid = request.getParamsMap().get("outid");
			String current = StringUtil.newInstance().getCurrentDate("yyyyMMdd");
			String outdate = outid.substring(5, 13);
			if(!outdate.equals(current)){
				result = "{failure:true,msg:'不是今天账单不能增删改!'}";
			}else{
				OrderMaterialStoreList goods = (OrderMaterialStoreList)hibernateSessionDAO.getHibernateTemplate().get("com.hrm.entity.OrderMaterialStoreList", request.getParamsMap().get("id"));
				String shouldpay = request.getParamsMap().get("shouldpay");
				String actuallypay = request.getParamsMap().get("actuallypay");
				if(shouldpay==null){
					shouldpay = "0";
				}
				if(actuallypay==null){
					actuallypay = "0";
				}
				goods.setShouldpay(Float.valueOf(shouldpay));
				goods.setActuallypay(Float.valueOf(actuallypay));
				goods.setSum(Float.valueOf(request.getParamsMap().get("sum")));
				goods.setMaterialid(request.getParamsMap().get("materialid"));
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				hibernateSessionDAO.update(goods);
				result = "{success:true,msg:''}";
			}
		}else if("deleteRecord".equals(action)){
			String outid = request.getParamsMap().get("outid");
			String current = StringUtil.newInstance().getCurrentDate("yyyyMMdd");
			String outdate = outid.substring(5, 13);
			if(!outdate.equals(current)){
				result = "{failure:true,msg:'不是今天账单不能增删改!'}";
			}else{
				final String[] id = request.getParamsMap().get("id").split(",");
				/*final String userlist = InitData.getUserList(userInfo.getUserId()+Constant.TAG[0]);
				if(userlist==null){
					throw new Exception("{failure:true,msg:\"请先在<font color='red'>出仓仓库维护</font>中加入该用户\"}");
				}*/
				boolean flag = hibernateSessionDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
					public Boolean doInHibernate(Session session)
							throws HibernateException, SQLException {
						int updt = 0;
						for (int i = 0; i < id.length; i++) {
							/*GoodsOutputList outlist = (GoodsOutputList)session.get(GoodsOutputList.class, id[i]);
							if(!outlist.getGoodsid().equals("")){
								GoodsListId listid = new GoodsListId();
								listid.setId(outlist.getGoodsid());
								listid.setUserlist(userlist);
								GoodsList list = (GoodsList)session.get(GoodsList.class, listid);
								list.setOutnumber(list.getOutnumber()-outlist.getGoodsnumber()+outlist.getReturnnumber());
								session.save(list);
							}*/
							updt = session.createQuery("delete from OrderMaterialStoreList where id ='"+id[i]+"'").executeUpdate();
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
		}
		request.setResponse(result);
		return request;
	}

	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}

	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}

	
}
