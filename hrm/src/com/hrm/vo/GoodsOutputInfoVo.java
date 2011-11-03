package com.hrm.vo;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hrm.control.Request;
import com.hrm.dao.GoodsOutputInfoDAO;
import com.hrm.dao.GoodsOutputListDAO;
import com.hrm.entity.GoodsList;
import com.hrm.entity.GoodsListId;
import com.hrm.entity.GoodsMonthStat;
import com.hrm.entity.GoodsMonthStatId;
import com.hrm.entity.GoodsOutputInfo;
import com.hrm.entity.GoodsOutputList;
import com.hrm.entity.GoodsStat;
import com.hrm.entity.GoodsStatId;
import com.hrm.entity.UserInfo;
import com.hrm.server.InitData;
import com.hrm.util.Constant;
import com.hrm.util.StringUtil;

public class GoodsOutputInfoVo implements BaseVo {
	private GoodsOutputListDAO goodsOutputListDAO;
	private GoodsOutputInfoDAO goodsOutputInfoDAO;
	
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
			boolean isRoot = Boolean.valueOf(request.getParamsMap().get("isRoot"));
			final String userlist = InitData.getUserList(userInfo.getUserId()+Constant.TAG[0]);
			if(userlist==null){ 
				result = "{failure:true,msg:\"请先让管理员在菜单<font color='red'>出仓仓库维护</font>添加您为一个出仓仓库\"}";
				throw new Exception(result);
			}
			List<GoodsOutputInfo> list = goodsOutputInfoDAO.createHqlQuery("from GoodsOutputInfo where outdate>'"+StringUtil.newInstance().getCurrentDate()+"' and outdate<'"+StringUtil.newInstance().dayAddNumber(new Date(), 1)+"' order by id desc", -1, -1);
			if(list.size()==0){
				createId = StringUtil.newInstance().createAccId(header, 1, 5);
			}else{
				GoodsOutputInfo info = list.get(0);
				String id = info.getId();
				String idNumber = id.substring(13, id.length());
				int number = Integer.parseInt(idNumber);
				createId = StringUtil.newInstance().createAccId(header, ++number, 5);
			}
			
			GoodsOutputInfo info = new GoodsOutputInfo();
			info.setGoodsdesc(request.getParamsMap().get("tree_tips"));
			info.setId(createId);
			info.setOutdate(new Date());
			info.setOutuser(userInfo.getUserId());
			info.setUpdttime(new Date());
			info.setUpdtuser(userInfo.getUserId());
			goodsOutputInfoDAO.save(info);
			result = "{success:true,msg:'"+createId+"'}";
		}else if("update".equals(action)){
			String id = request.getParamsMap().get("menu_id");
			String current = StringUtil.newInstance().getCurrentDate("yyyyMMdd");
			String outdate = id.substring(5, 13);
			if(!outdate.equals(current)){
				result = "{failure:true,msg:'不是今天账单不能增删改!'}";
			}else{
				GoodsOutputInfo info = goodsOutputInfoDAO.findById(id);
				info.setGoodsdesc(request.getParamsMap().get("tree_tips"));
				info.setOutuser(userInfo.getUserId());
				info.setUpdttime(new Date());
				info.setUpdtuser(userInfo.getUserId());
				goodsOutputInfoDAO.getHibernateTemplate().update(info);
				result = "{success:true,msg:''}";
			}
		}else if("delete".equals(action)){
			final String typeid = request.getParamsMap().get("menu_code");
			String current = StringUtil.newInstance().getCurrentDate("yyyyMMdd");
			String outdate = typeid.substring(5, 13);
			if(!outdate.equals(current)){
				result = "{failure:true,msg:'不是今天账单不能增删改!'}";
			}else{
				final String userlist = InitData.getUserList(userInfo.getUserId()+Constant.TAG[0]);
				if(userlist==null){
					throw new Exception("{failure:true,msg:\"请先在<font color='red'>出仓仓库维护</font>中加入该用户\"}");
				}
				boolean flag = goodsOutputInfoDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
					public Boolean doInHibernate(Session session)
							throws HibernateException, SQLException {
						
						List<Object[]> list = session.createQuery("select goodsid,goodsnumber,returnnumber from GoodsOutputList where outid='"+typeid+"'").list();
						Map<String,Integer> map = new HashMap<String,Integer>();
						for (Object[] obj : list) {
							if(map.get(obj[0].toString())!=null){
								int lastnumber = map.get(obj[0])+Integer.parseInt(obj[1].toString())-Integer.parseInt(obj[2].toString());
								map.put(obj[0].toString(), lastnumber);
							}else{
								int lastnumber = Integer.parseInt(obj[1].toString())-Integer.parseInt(obj[2].toString());
								map.put(obj[0].toString(), lastnumber);
							}
						}
						for (Iterator<String> it = map.keySet().iterator();it.hasNext();) {
							String key = it.next();
							GoodsListId id = new GoodsListId();
							id.setId(key);
							id.setUserlist(userlist);
							GoodsList tmp = (GoodsList)session.get(GoodsList.class, id);
							if(tmp!=null){
								tmp.setOutnumber(tmp.getOutnumber()-map.get(key));
								tmp.setUpdttime(new Date());
								session.update(tmp);
							}
						}
						session.createQuery("delete from GoodsOutputList where outid='"+typeid+"'").executeUpdate();
						int updt = session.createQuery("delete from GoodsOutputInfo where id='"+typeid+"'").executeUpdate();
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
				GoodsOutputList goods = new GoodsOutputList();
				goods.setGoodsid(request.getParamsMap().get("goodsid"));
				goods.setGoodsnumber(Integer.parseInt(request.getParamsMap().get("goodsnumber")));
				goods.setId(createId);
				goods.setOutid(request.getParamsMap().get("outid"));
				goods.setReturnnumber(Integer.parseInt(request.getParamsMap().get("returnnumber")));
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				goodsOutputListDAO.save(goods);
				result = "{success:true,msg:'"+createId+"'}";
			}
		}else if("updateRecord".equals(action)){
			String outid = request.getParamsMap().get("outid");
			String current = StringUtil.newInstance().getCurrentDate("yyyyMMdd");
			String outdate = outid.substring(5, 13);
			if(!outdate.equals(current)){
				result = "{failure:true,msg:'不是今天账单不能增删改!'}";
			}else{
				GoodsOutputList goods = goodsOutputListDAO.findById(request.getParamsMap().get("id"));
				String userlist = InitData.getUserList(userInfo.getUserId()+Constant.TAG[0]);
				if(userlist==null){
					throw new Exception("{failure:true,msg:\"请先在<font color='red'>出仓仓库维护</font>中加入该用户\"}");
				}
				//更新GoodsList表的outnumber数量
				if(!goods.getGoodsid().equals(request.getParamsMap().get("goodsid")) && !goods.getGoodsid().equals("")){
					GoodsListId id = new GoodsListId();
					id.setId(goods.getGoodsid());
					id.setUserlist(userlist);
					GoodsList list = goodsOutputInfoDAO.getHibernateTemplate().get(GoodsList.class, id);
					list.setOutnumber(list.getOutnumber()-goods.getGoodsnumber()+goods.getReturnnumber());
					goodsOutputInfoDAO.getHibernateTemplate().update(list);
					
					GoodsListId id1 = new GoodsListId();
					id1.setId(request.getParamsMap().get("goodsid"));
					id1.setUserlist(userlist);
					GoodsList list1 = goodsOutputListDAO.getHibernateTemplate().get(GoodsList.class, id1);
					int outnumber = list1.getOutnumber()+Integer.parseInt(request.getParamsMap().get("goodsnumber"))-Integer.parseInt(request.getParamsMap().get("returnnumber"));
					list1.setOutnumber(outnumber);
					if(list1.getGoodsnumber()+list1.getInnumber()-outnumber<0){
						result = "{failure:true,msg:\"仓库库存不足,仓库库存为<font color='red'>"+(list1.getGoodsnumber()+list1.getInnumber())+"</font>,销售数量为<font color='red'>"+outnumber+"</font>!\"}";
						throw new Exception(result);
					}
					goodsOutputListDAO.getHibernateTemplate().update(list1);
				}else{
					//实际修改数量-记录默认数量
					int lastnumber = Integer.parseInt(request.getParamsMap().get("goodsnumber"))-goods.getGoodsnumber();
					int lastreturn = Integer.parseInt(request.getParamsMap().get("returnnumber"))-goods.getReturnnumber();
					GoodsListId id = new GoodsListId();
					id.setId(request.getParamsMap().get("goodsid"));
					id.setUserlist(userlist);
					GoodsList list = goodsOutputListDAO.getHibernateTemplate().get(GoodsList.class, id);
					int outnumber = list.getOutnumber()+lastnumber-lastreturn;
					int orgnumber = list.getGoodsnumber()+list.getInnumber()-list.getOutnumber();
					list.setOutnumber(outnumber);
					if(list.getGoodsnumber()+list.getInnumber()-outnumber<0){
						result = "{failure:true,msg:\"仓库库存不足,仓库库存为<font color='red'>"+orgnumber+"</font>,销售数量为<font color='red'>"+request.getParamsMap().get("goodsnumber")+"</font>!\"}";
						throw new Exception(result);
					}
					goodsOutputListDAO.getHibernateTemplate().update(list);
				}
				//更新销售表
				goods.setGoodsid(request.getParamsMap().get("goodsid"));
				goods.setGoodsnumber(Integer.parseInt(request.getParamsMap().get("goodsnumber")));
				goods.setOutid(request.getParamsMap().get("outid"));
				goods.setReturnnumber(Integer.parseInt(request.getParamsMap().get("returnnumber")));
				goods.setUpdttime(new Date());
				goods.setUpdtuser(userInfo.getUserId());
				goodsOutputListDAO.getHibernateTemplate().update(goods);
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
				final String userlist = InitData.getUserList(userInfo.getUserId()+Constant.TAG[0]);
				if(userlist==null){
					throw new Exception("{failure:true,msg:\"请先在<font color='red'>出仓仓库维护</font>中加入该用户\"}");
				}
				boolean flag = goodsOutputListDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){
					public Boolean doInHibernate(Session session)
							throws HibernateException, SQLException {
						int updt = 0;
						for (int i = 0; i < id.length; i++) {
							GoodsOutputList outlist = (GoodsOutputList)session.get(GoodsOutputList.class, id[i]);
							if(!outlist.getGoodsid().equals("")){
								GoodsListId listid = new GoodsListId();
								listid.setId(outlist.getGoodsid());
								listid.setUserlist(userlist);
								GoodsList list = (GoodsList)session.get(GoodsList.class, listid);
								list.setOutnumber(list.getOutnumber()-outlist.getGoodsnumber()+outlist.getReturnnumber());
								session.save(list);
							}
							updt = session.createQuery("delete from GoodsOutputList where id ='"+id[i]+"'").executeUpdate();
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
		}else if("updateAfterRecord".equals(action)){//管理员修改的销售货物
			String goodsid = request.getParamsMap().get("goodsid");
			int outnumber = Integer.parseInt(request.getParamsMap().get("goodsnumber"));
			int returnnumber = Integer.parseInt(request.getParamsMap().get("returnnumber"));
			String id = request.getParamsMap().get("id");
			GoodsOutputList goodsOutputList = goodsOutputListDAO.findById(id);
			Date current = StringUtil.newInstance().getInputDate(StringUtil.newInstance().getCurrentDate(), "yyyy-MM-dd");
			if(goodsOutputList.getUpdttime().compareTo(current)>=0){
				result = "{failure:true,msg:'删除失败!'}";
			}else{
				String outuser = goodsOutputList.getUpdtuser();
				String userlist = InitData.getUserList(outuser+Constant.TAG[0]);
				String cleardate = StringUtil.newInstance().dateAddNumber(goodsOutputList.getUpdttime(), 0);
				int initoutnumber = goodsOutputList.getGoodsnumber();
				int initreturnnumber = goodsOutputList.getReturnnumber();
				goodsOutputList.setGoodsnumber(outnumber);
				goodsOutputList.setReturnnumber(returnnumber);
				goodsOutputListDAO.getHibernateTemplate().update(goodsOutputList);
				//更新 GoodsStat
				GoodsStatId statid = new GoodsStatId();
				statid.setGoodsid(goodsid);
				statid.setUserlist(userlist);
				GoodsStat goodsStat = goodsOutputListDAO.getHibernateTemplate().get(GoodsStat.class, statid);
				goodsStat.setOutnumber(goodsStat.getOutnumber()+outnumber-initoutnumber);
				goodsStat.setReturnnumber(goodsStat.getReturnnumber()+returnnumber-initreturnnumber);
				goodsOutputListDAO.getHibernateTemplate().update(goodsStat);
				//更新GoodsList
				GoodsListId goodslistid = new GoodsListId();
				goodslistid.setId(goodsid);
				goodslistid.setUserlist(userlist);
				GoodsList goodslist = goodsOutputListDAO.getHibernateTemplate().get(GoodsList.class, goodslistid);
				goodslist.setOutnumber(goodslist.getOutnumber()+(outnumber-returnnumber)-(initoutnumber-initreturnnumber));
				goodsOutputListDAO.getHibernateTemplate().update(goodslist);
				//更新GoodsMonthStat
				GoodsMonthStat month = new GoodsMonthStat();
				GoodsMonthStatId monthid = new GoodsMonthStatId();
				monthid.setCleardate(DateFormat.getDateInstance().parse(StringUtil.newInstance().minDate(cleardate, "yyyy-MM-dd") ));
				monthid.setGoodsid(goodsid);
				monthid.setUserlist(userlist);
				month.setId(monthid);
				GoodsMonthStat monthstat = goodsOutputListDAO.getHibernateTemplate().get(GoodsMonthStat.class, monthid);
				int monthout = monthstat.getOutnumber()+outnumber-initoutnumber;
				int monthret = monthstat.getReturnnumber()+returnnumber-initreturnnumber;
				int lastnumber = monthout-monthret;
				monthstat.setOutnumber(monthout);
				monthstat.setReturnnumber(monthret);
				monthstat.setLastnumber(lastnumber);
				goodsOutputListDAO.getHibernateTemplate().update(monthstat);
				result = "{success:true,msg:''}";
			}
		}
		request.setResponse(result);
		return request;
	}

	public GoodsOutputListDAO getGoodsOutputListDAO() {
		return goodsOutputListDAO;
	}

	public void setGoodsOutputListDAO(GoodsOutputListDAO goodsOutputListDAO) {
		this.goodsOutputListDAO = goodsOutputListDAO;
	}

	public GoodsOutputInfoDAO getGoodsOutputInfoDAO() {
		return goodsOutputInfoDAO;
	}

	public void setGoodsOutputInfoDAO(GoodsOutputInfoDAO goodsOutputInfoDAO) {
		this.goodsOutputInfoDAO = goodsOutputInfoDAO;
	}

}
