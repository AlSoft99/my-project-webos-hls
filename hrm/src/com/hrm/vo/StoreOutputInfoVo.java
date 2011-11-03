package com.hrm.vo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hrm.control.Request;
import com.hrm.dao.StoreOutputInfoDAO;
import com.hrm.dao.StoreOutputListDAO;
import com.hrm.entity.GoodsList;
import com.hrm.entity.GoodsListId;
import com.hrm.entity.GoodsStat;
import com.hrm.entity.GoodsStatId;
import com.hrm.entity.StoreList;
import com.hrm.entity.StoreOutputInfo;
import com.hrm.entity.StoreOutputList;
import com.hrm.entity.UserInfo;
import com.hrm.server.InitData;
import com.hrm.util.Constant;
import com.hrm.util.StringUtil;

public class StoreOutputInfoVo implements BaseVo {
	private StoreOutputInfoDAO storeOutputInfoDAO;
	private StoreOutputListDAO storeOutputListDAO;
	UserInfo userInfo;
	private static Logger logger = Logger.getLogger(StoreOutputInfoVo.class);
	 
	public Request execute(Request request) throws Exception {
		String action = request.getParamsMap().get("action");
		userInfo = (UserInfo)request.getUserInfo();
		String result = "";
		if("insert".equals(action)){
			if(InitData.getUserList(userInfo.getUserId()+Constant.TAG[0])==null){
//				result = "{failure:true,msg:'请先让管理员设置菜单<font color=\"red\">出仓参数维护</font>,添加您为一个出仓仓库!'}";
				result = "{failure:true,msg:\"请先让管理员在菜单<font color='red'>出仓仓库维护</font>, 添加您为一个出仓仓库\"}";
				throw new Exception(result);
			}
			boolean isRoot = Boolean.valueOf(request.getParamsMap().get("isRoot"));
			int maxIndex = getCurrentIndex(userInfo);
			String header = userInfo.getUserCode().substring(0, 5).toUpperCase();
			String createId = StringUtil.newInstance().createAccId(header, maxIndex, 5);
			StoreOutputInfo goods = new StoreOutputInfo();
			goods.setId(createId);
			goods.setOutdate(new Date());
			goods.setOutuser(userInfo.getUserId());
			goods.setRecuser(request.getParamsMap().get("recuser"));
			goods.setStatus("1");
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			storeOutputInfoDAO.save(goods);
			result = "{success:true,msg:'"+createId+"'}";
		}else if("update".equals(action)){
			StoreOutputInfo goods = storeOutputInfoDAO.findById(request.getParamsMap().get("menu_id"));
			goods.setRecuser(request.getParamsMap().get("recuser"));
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			storeOutputInfoDAO.getHibernateTemplate().update(goods);
			result = "{success:true,msg:''}";
		}else if("delete".equals(action)){
			final String typeid = request.getParamsMap().get("menu_code");
			boolean flag = storeOutputInfoDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){

				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					session.createQuery("delete from StoreOutputList where outid='"+typeid+"'").executeUpdate();
					int updt = session.createQuery("delete from StoreOutputInfo where id='"+typeid+"'").executeUpdate();
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
			StoreOutputInfo info = storeOutputInfoDAO.findById(request.getParamsMap().get("outid"));
			if(!info.getStatus().equals("1")){
				result = "{failure:true,msg:'状态已改变,不能修改记录!'}";
			}else{
				StoreOutputList goods = new StoreOutputList();
				goods.setGoodsid(request.getParamsMap().get("goodsid"));
				goods.setGoodsnumber(Integer.parseInt(request.getParamsMap().get("goodsnumber")));
				goods.setId(createId);
				goods.setOutid(request.getParamsMap().get("outid"));
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				storeOutputListDAO.save(goods);
				result = "{success:true,msg:'"+createId+"'}";
			}
			
		}else if("updateRecord".equals(action)){
			StoreOutputList goods = storeOutputListDAO.findById(request.getParamsMap().get("id"));
			StoreOutputInfo info = storeOutputInfoDAO.findById(request.getParamsMap().get("outid"));
			if(!info.getStatus().equals("1")){
				result = "{failure:true,msg:'状态已改变,不能修改记录!'}";
			}else{
				chkGoodsList(request); 
				chkGoodsStat(request);
				goods.setGoodsid(request.getParamsMap().get("goodsid"));
				goods.setGoodsnumber(Integer.parseInt(request.getParamsMap().get("goodsnumber")));
				goods.setOutid(request.getParamsMap().get("outid"));
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				storeOutputListDAO.getHibernateTemplate().update(goods);
				
				result = "{success:true,msg:''}";
			}
		}else if("deleteRecord".equals(action)){
			final String[] id = request.getParamsMap().get("id").split(",");
			StoreOutputInfo info = storeOutputInfoDAO.findById(request.getParamsMap().get("outid"));
			if(!info.getStatus().equals("1")){
				result = "{failure:true,msg:'状态已改变,不能修改记录!'}";
			}else{
				boolean flag = storeOutputListDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
	
					public Boolean doInHibernate(Session session)
							throws HibernateException, SQLException {
						int updt = 0;
						for (int i = 0; i < id.length; i++) {
							updt = session.createQuery("delete from StoreOutputList where id ='"+id[i]+"'").executeUpdate();
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
			StoreOutputInfo info = storeOutputInfoDAO.findById(request.getParamsMap().get("id"));
			result = "{";
			result += "出仓单号:'"+request.getParamsMap().get("id")+"',";
			result += "申请出仓人:'"+InitData.getValue(Constant.USER_INFO, info.getOutuser())+"',";
			result += "仓库接收人:'"+InitData.getValue(Constant.USER_INFO, info.getRecuser())+"',";
			result += "状态:'"+InitData.getConstantsValue(Constant.PARAMS_INFO[0]+"_"+info.getStatus())+"',";
			result += "最后更新人:'"+InitData.getValue(Constant.USER_INFO, info.getUpdtuser())+"',";
			result += "申请出仓时间:'"+info.getOutdate()+"',";
			result += "最后更新时间:'"+info.getUpdttime()+"'";
			result += "}";
		}else if("confirm".equals(action)){
			final String id = request.getParamsMap().get("id");
			
			List<StoreOutputList> list = storeOutputListDAO.findByOutid(id);
			StoreOutputInfo info = storeOutputInfoDAO.findById(id);
			Map<String,Integer> tmp = new HashMap<String,Integer>();
			for (StoreOutputList out : list) {
				StoreList store = storeOutputListDAO.getHibernateTemplate().get(StoreList.class, out.getGoodsid());
				int autLastnumber = store.getInitnumber()+store.getInnumber()-store.getOutnumber();
				int lastnumber = autLastnumber-out.getGoodsnumber();
				if(lastnumber<0){
					result = "{failure:true,msg:'"+store.getGoodsname()+"剩余库存不足,剩余库存为<font color=\"red\">"+autLastnumber+"</font>,需要取出量为<font color=\"red\">"+out.getGoodsnumber()+"</font>'}";
					throw new Exception(result);
				}
				store.setOutnumber(store.getOutnumber()+out.getGoodsnumber());
				storeOutputListDAO.getHibernateTemplate().update(store);
				if(tmp.get(out.getGoodsid())==null){
					tmp.put(out.getGoodsid(), out.getGoodsnumber());
				}else{
					int goodsnumber = tmp.get(out.getGoodsid())+out.getGoodsnumber();
					tmp.put(out.getGoodsid(), goodsnumber);
				}
			}
			for(Iterator<String> it = tmp.keySet().iterator();it.hasNext();){
				String key = it.next();
				GoodsListId goodsid = new GoodsListId();
				goodsid.setId(key);
				goodsid.setUserlist(InitData.getUserList(info.getOutuser()+Constant.TAG[0]));
				GoodsList goodslist = storeOutputInfoDAO.getHibernateTemplate().get(GoodsList.class, goodsid);
				goodslist.setInnumber(goodslist.getInnumber()+tmp.get(key));
				goodslist.setUpdttime(new Date());
				storeOutputInfoDAO.getHibernateTemplate().update(goodslist);
			}
			boolean flag = storeOutputInfoDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){

				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					int a = session.createQuery("update StoreOutputInfo set status='2',updttime='"+sdf.format(new Date())+"' where id='"+id+"'").executeUpdate();
					return a>0;
				}
			});
			if(flag){
				result = "{success:true,msg:''}";
			}else{
				result = "{failure:true,msg:'操作失败,请查看日志查明原因'}";
			}
		}
		request.setResponse(result);
		return request;
	}
	private int getCurrentIndex(UserInfo userInfo){
		String start = StringUtil.newInstance().getCurrentDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String end = StringUtil.newInstance().dateAddNumber(new Date(), 1);
		List<StoreOutputInfo> list = storeOutputInfoDAO.getHibernateTemplate().find("from StoreOutputInfo where outdate>'"+start+"' and outdate<'"+end+"' and outuser='"+userInfo.getUserId()+"' order by id desc");
		int index = 0;
		if(list.size()==0){
			index = 0;
		}else{
			StoreOutputInfo info = list.get(0);
			index = Integer.parseInt(info.getId().substring(13, info.getId().length()));
		}
		return ++index;
	}
	/**
	 * 检查GoodsList表是否有记录,如没有,将插入一条记录
	 * @param request
	 */
	private void chkGoodsList(Request request){
		GoodsListId id = new GoodsListId();
		id.setId(request.getParamsMap().get("goodsid"));
		id.setUserlist(InitData.getUserList(userInfo.getUserId()+Constant.TAG[0]));
		GoodsList goods = storeOutputInfoDAO.getHibernateTemplate().get(GoodsList.class, id);
		if(goods==null){
			goods = new GoodsList();
			goods.setId(id);
			goods.setInnumber(0);
			goods.setOutnumber(0);
			goods.setGoodsdesc("系统生成");
			goods.setGoodsname("系统生成");
			goods.setGoodsnumber(0);
			goods.setPrice(0D);
			goods.setUpdttime(new Date());
			goods.setUpdtuser(userInfo.getUserId());
			storeOutputInfoDAO.getHibernateTemplate().save(goods);
		}
	}
	/**
	 * 检查GoodsStat表是否有记录,如没有,将插入一条记录
	 * @param request
	 */
	private void chkGoodsStat(Request request){
		GoodsStatId id = new GoodsStatId();
		id.setGoodsid(request.getParamsMap().get("goodsid"));
		id.setUserlist(InitData.getUserList(userInfo.getUserId()+Constant.TAG[0]));
		GoodsStat goods = storeOutputInfoDAO.getHibernateTemplate().get(GoodsStat.class, id);
		if(goods==null){
			goods = new GoodsStat();
			goods.setId(id);
			goods.setOutnumber(0);
			goods.setReturnnumber(0);
			goods.setStorenumber(0);
			goods.setUpdtuser(userInfo.getUserId());
			storeOutputInfoDAO.getHibernateTemplate().save(goods);
		}
	}
	public StoreOutputInfoDAO getStoreOutputInfoDAO() {
		return storeOutputInfoDAO;
	}
	public void setStoreOutputInfoDAO(StoreOutputInfoDAO storeOutputInfoDAO) {
		this.storeOutputInfoDAO = storeOutputInfoDAO;
	}
	public StoreOutputListDAO getStoreOutputListDAO() {
		return storeOutputListDAO;
	}
	public void setStoreOutputListDAO(StoreOutputListDAO storeOutputListDAO) {
		this.storeOutputListDAO = storeOutputListDAO;
	}

}
