package com.hrm.vo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hrm.control.Request;
import com.hrm.dao.StoreInputInfoDAO;
import com.hrm.dao.StoreInputListDAO;
import com.hrm.entity.StoreInputInfo;
import com.hrm.entity.StoreInputList;
import com.hrm.entity.StoreList;
import com.hrm.entity.UserInfo;
import com.hrm.server.InitData;
import com.hrm.util.Constant;
import com.hrm.util.StringUtil;

public class StoreInputInfoVo implements BaseVo {
	private StoreInputInfoDAO storeInputInfoDAO;
	private StoreInputListDAO storeInputListDAO;
	public Request execute(Request request) throws Exception{

		String action = request.getParamsMap().get("action");
		UserInfo userInfo = (UserInfo)request.getUserInfo();
		String result = "";
		if("insert".equals(action)){
			
			boolean isRoot = Boolean.valueOf(request.getParamsMap().get("isRoot"));
			int maxIndex = getCurrentIndex(userInfo);
			String header = userInfo.getUserCode().substring(0, 5).toUpperCase();
			String createId = StringUtil.newInstance().createAccId(header, maxIndex, 5);
			StoreInputInfo goods = new StoreInputInfo();
			goods.setId(createId);
			goods.setApplydate(new Date());
			goods.setApplyuser(userInfo.getUserId());
			goods.setManguser(request.getParamsMap().get("manguser"));
			goods.setConfirmstatus("1");
			goods.setAutprice(0D);
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			goods.setPreprice(Double.valueOf(request.getParamsMap().get("preprice")));
			goods.setIsacco("0");
			goods.setIsmang("0");
			goods.setIsstore("0");
			storeInputInfoDAO.save(goods);
			result = "{success:true,msg:'"+createId+"'}";
		}else if("update".equals(action)){
			StoreInputInfo goods = storeInputInfoDAO.findById(request.getParamsMap().get("menu_id"));
			goods.setManguser(request.getParamsMap().get("manguser"));
			goods.setPreprice(Double.valueOf(request.getParamsMap().get("preprice")));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			storeInputInfoDAO.getHibernateTemplate().update(goods);
			result = "{success:true,msg:''}";
		}else if("delete".equals(action)){
			final String typeid = request.getParamsMap().get("menu_code");
			boolean flag = storeInputInfoDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){

				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					session.createQuery("delete from StoreInputList where inid='"+typeid+"'").executeUpdate();
					int updt = session.createQuery("delete from StoreInputInfo where id='"+typeid+"'").executeUpdate();
					return updt>0;
				}
			});
			if(flag){
				result = "{success:true,msg:''}";
			}else{
				result = "{failure:true,msg:'删除失败!'}";
			}
		}else if("insertRecord".equals(action)){
			String createId = StringUtil.newInstance().createId("SD");
			StoreInputInfo info = storeInputInfoDAO.findById(request.getParamsMap().get("inid"));
			if(!info.getConfirmstatus().equals("1")){
				result = "{failure:true,msg:'状态已改变,不能修改记录!'}";
			}else{
				StoreInputList goods = new StoreInputList();
				goods.setGoodsid(request.getParamsMap().get("goodsid"));
				goods.setGoodsnumber(Integer.parseInt(request.getParamsMap().get("goodsnumber")));
				goods.setId(createId);
				goods.setInid(request.getParamsMap().get("inid"));
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				storeInputListDAO.save(goods);
				result = "{success:true,msg:'"+createId+"'}";
			}
			
		}else if("updateRecord".equals(action)){
			StoreInputList goods = new StoreInputList();
			StoreInputInfo info = storeInputInfoDAO.findById(request.getParamsMap().get("inid"));
			if(!info.getConfirmstatus().equals("1")){
				result = "{failure:true,msg:'状态已改变,不能修改记录!'}";
			}else{
				goods.setGoodsid(request.getParamsMap().get("goodsid"));
				goods.setGoodsnumber(Integer.parseInt(request.getParamsMap().get("goodsnumber")));
				goods.setId(request.getParamsMap().get("id"));
				goods.setInid(request.getParamsMap().get("inid"));
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				storeInputListDAO.getHibernateTemplate().update(goods);
				result = "{success:true,msg:''}";
			}
		}else if("deleteRecord".equals(action)){
			final String[] id = request.getParamsMap().get("id").split(",");
			StoreInputInfo info = storeInputInfoDAO.findById(request.getParamsMap().get("inid"));
			if(!info.getConfirmstatus().equals("1")){
				result = "{failure:true,msg:'状态已改变,不能修改记录!'}";
			}else{
				boolean flag = storeInputListDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
	
					public Boolean doInHibernate(Session session)
							throws HibernateException, SQLException {
						int updt = 0;
						for (int i = 0; i < id.length; i++) {
							updt = session.createQuery("delete from StoreInputList where id ='"+id[i]+"'").executeUpdate();
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
		}else if("infolist".equals(action)){
			StoreInputInfo info = storeInputInfoDAO.findById(request.getParamsMap().get("id"));
			result = "{";
			result += "采购单号:'"+request.getParamsMap().get("id")+"',";
			result += "采购人名称:'"+InitData.getValue(Constant.USER_INFO, info.getApplyuser())+"',";
			result += "主管人名称:'"+InitData.getValue(Constant.USER_INFO, info.getManguser())+"',";
			result += "财务人名称:'"+InitData.getValue(Constant.USER_INFO, info.getAccouser())+"',";
			result += "仓库人名称:'"+InitData.getValue(Constant.USER_INFO, info.getStoreuser())+"',";
			result += "状态:'"+InitData.getConstantsValue(Constant.PARAMS_INFO[1]+"_"+info.getConfirmstatus())+"',";
			result += "最后更新人:'"+InitData.getValue(Constant.USER_INFO, info.getUpdtuser())+"',";
			result += "采购时间:'"+info.getApplydate()+"',";
			result += "预计采购金额:'"+info.getPreprice()+"',";
			result += "实际采购金额:'"+info.getAutprice()+"',";
			result += "最后更新时间:'"+info.getUpdttime()+"'";
			result += "}";
		}else if("confirm".equals(action)){
			final String id = request.getParamsMap().get("id");
			StoreInputInfo info = storeInputInfoDAO.findById(id);
			if(!info.getConfirmstatus().equals("1")){
				result = "{failure:true,msg:'状态已改变,不能修改记录!'}";
			}else{
				boolean flag = storeInputInfoDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
	
					public Boolean doInHibernate(Session session)
							throws HibernateException, SQLException {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						int a = session.createQuery("update StoreInputInfo set confirmstatus='2',ismang='1',updttime='"+sdf.format(new Date())+"' where id='"+id+"'").executeUpdate();
						return a>0;
					}
				});
				if(flag){
					result = "{success:true,msg:''}";
				}else{
					result = "{failure:true,msg:'操作失败,请查看日志查明原因'}";
				}
			}
		}else if("confirmfinance".equals(action)){
			final String id = request.getParamsMap().get("id");
			final String userId = userInfo.getUserId();
			StoreInputInfo info = storeInputInfoDAO.findById(id);
			if(!info.getConfirmstatus().equals("2")){
				result = "{failure:true,msg:'状态已改变,不能修改记录!'}";
			}else{
				boolean flag = storeInputInfoDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
	
					public Boolean doInHibernate(Session session)
							throws HibernateException, SQLException {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						int a = session.createQuery("update StoreInputInfo set confirmstatus='3',accouser='"+userId+"',isacco='1',updttime='"+sdf.format(new Date())+"' where id='"+id+"'").executeUpdate();
						return a>0;
					}
				});
				if(flag){
					result = "{success:true,msg:''}";
				}else{
					result = "{failure:true,msg:'操作失败,请查看日志查明原因'}";
				}
			}
		}else if("clearfinance".equals(action)){
			final String id = request.getParamsMap().get("id");
			final String userId = userInfo.getUserId();
			final String autprice = request.getParamsMap().get("autprice");
			StoreInputInfo info = storeInputInfoDAO.findById(id);
			if(!info.getConfirmstatus().equals("4")){
				result = "{failure:true,msg:'状态已改变,不能修改记录!'}";
			}else{
				boolean flag = storeInputInfoDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
	
					public Boolean doInHibernate(Session session)
							throws HibernateException, SQLException {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						int a = session.createQuery("update StoreInputInfo set confirmstatus='5',autprice="+autprice+",updttime='"+sdf.format(new Date())+"' where id='"+id+"'").executeUpdate();
						return a>0;
					}
				});
				if(flag){
					result = "{success:true,msg:''}";
				}else{
					result = "{failure:true,msg:'操作失败,请查看日志查明原因'}";
				}
			}
		}else if("confirmstore".equals(action)){
			final String id = request.getParamsMap().get("id");
			final String userId = userInfo.getUserId();
			StoreInputInfo info = storeInputInfoDAO.findById(id);
			if(!info.getConfirmstatus().equals("3")){
				result = "{failure:true,msg:'状态已改变,不能修改记录!'}";
			}else{
				 
				boolean flag = storeInputInfoDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
	
					public Boolean doInHibernate(Session session)
							throws HibernateException, SQLException {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						int a = session.createQuery("update StoreInputInfo set confirmstatus='4',storeuser='"+userId+"',isstore='1',updttime='"+sdf.format(new Date())+"' where id='"+id+"'").executeUpdate();
						return a>0;
					}
				});
				if(flag){
					List<StoreInputList> inList = storeInputListDAO.findByInid(id);
					for(StoreInputList in : inList){
						StoreList storeList = (StoreList)storeInputListDAO.getHibernateTemplate().get(StoreList.class, in.getGoodsid());
						storeList.setInnumber(storeList.getInnumber()+in.getGoodsnumber());
						storeList.setUpdttime(new Date());
						storeInputListDAO.getHibernateTemplate().update(storeList);
					}
					result = "{success:true,msg:''}";
				}else{
					result = "{failure:true,msg:'操作失败,请查看日志查明原因'}";
				}
			}
		}
		request.setResponse(result);
		return request;
	
	}
	private int getCurrentIndex(UserInfo userInfo){
		String start = StringUtil.newInstance().getCurrentDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String end = StringUtil.newInstance().dateAddNumber(new Date(), 1);
		List<StoreInputInfo> list = storeInputInfoDAO.getHibernateTemplate().find("from StoreInputInfo where applydate>'"+start+"' and applydate<'"+end+"' and applyuser='"+userInfo.getUserId()+"' order by id desc");
		int index = 0;
		if(list.size()==0){
			index = 0;
		}else{
			StoreInputInfo info = list.get(0);
			index = Integer.parseInt(info.getId().substring(13, info.getId().length()));
		}
		return ++index;
	}
	public StoreInputInfoDAO getStoreInputInfoDAO() {
		return storeInputInfoDAO;
	}
	public void setStoreInputInfoDAO(StoreInputInfoDAO storeInputInfoDAO) {
		this.storeInputInfoDAO = storeInputInfoDAO;
	}
	public StoreInputListDAO getStoreInputListDAO() {
		return storeInputListDAO;
	}
	public void setStoreInputListDAO(StoreInputListDAO storeInputListDAO) {
		this.storeInputListDAO = storeInputListDAO;
	}

}
