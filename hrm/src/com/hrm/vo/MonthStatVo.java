/**
 * 
 */
package com.hrm.vo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.GoodsMonthStat;
import com.hrm.entity.GoodsMonthStatId;
import com.hrm.entity.UserInfo;
import com.hrm.util.ClsFactory;
import com.hrm.util.Constant;
import com.hrm.util.StringUtil;

/**
 * @author Guanrl
 *
 */
public class MonthStatVo implements BaseVo {
	private static Logger logger = Logger.getLogger(MonthStatVo.class);
	private String startDate = "";
	private String endDate = "";
	private HibernateSessionDAO hibernateSessionDAO;
	private UserInfo userInfo;
	/* (non-Javadoc)
	 * @see com.hrm.vo.BaseVo#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public Request execute(Request request) throws Exception {
		String result = "";//QueryInfoVo
		userInfo = (UserInfo)request.getUserInfo();
		BeanFactory factory = ClsFactory.newInstance().getFactory();
		BaseVo queryInfoVo = factory.getBean("queryInfoVo",BaseVo.class);
		if(check(request)){
//			statData(request,response);
		}
		statData(request);
		request = queryInfoVo.execute(request);
		return request;
	}
	/**
	 * 返回True将需要更新月报表
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean check(Request request){
		String sql = "select max(id.cleardate),max(updttime) from GoodsMonthStat";
		List<Object[]> list = hibernateSessionDAO.createHqlQuery(sql);
		Object[] date = list.get(0);
		if(date[0]!=null){
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				startDate = StringUtil.newInstance().dateAddNumber(DateFormat.getDateInstance().parse(date[0].toString()), 0);
				String updttime = sdf.format((Date)date[1]);
				if(StringUtil.newInstance().getCurrentDate().equals(updttime)){
					return false;
				}
			}catch(Exception e){
				logger.error(this.toString(), e);
			}
		}else{
			startDate = "";
		}
		return true;
	}
	/**
	 * 统计月报表数据
	 * @param request
	 * @param response
	 */
	public void statData(Request request) throws Exception{
		String where = " 1=1 ";
		if (!startDate.equals("")) {
			where += " and updttime>='"+startDate+"'";
		}
		String sql = "select min(updttime),max(updttime) from GoodsOutputList where "+where;
		List<Object[]> list = hibernateSessionDAO.createHqlQuery(sql);
		if(list.get(0)[0]!=null && list.get(0)[1]!=null){
			int datelength = StringUtil.newInstance().subDate(list.get(0)[1].toString(), list.get(0)[0].toString(), "yyyy-MM-dd");
			String inputdate = list.get(0)[0].toString();
			/*Map<String,String> initMap = new HashMap<String,String>();
			if(startDate.equals("")){
				sql = "select id.id,id.userlist,goodsnumber from GoodsList";
			}else{
				sql = "select id.goodsid,id.userlist,lastnumber from GoodsMonthStat";
			}
			List<Object[]> goodsList = hibernateSessionDAO.createHqlQuery(sql);
			for (Object[] objects2 : goodsList) {
				initMap.put(objects2[0].toString()+objects2[1], objects2[2].toString());
			}*/
			for (int i = 0; i <= datelength; i++) {
				String minDate = StringUtil.newInstance().minDate(inputdate, "yyyy-MM-dd");
				String maxDate = StringUtil.newInstance().maxDate(inputdate, "yyyy-MM-dd");
				try {
					maxDate = StringUtil.newInstance().dateAddNumber(DateFormat.getDateInstance().parse(maxDate), 1);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				sql = "select a.goodsid,c.id.userlist,sum(a.goodsnumber) from StoreOutputList a,StoreOutputInfo b,OutUserList c where b.status='2' and c.id.tag='"+Constant.TAG[0]+"' and b.outuser = c.id.outuser and a.outid = b.id and a.updttime>'"+minDate+"' and a.updttime<'"+maxDate+"' group by a.goodsid,c.id.userlist";
				List<Object[]> inList = hibernateSessionDAO.createHqlQuery(sql);
				Map<String,String> inMap = new HashMap<String,String>();
				for (Object[] objects2 : inList) {
					inMap.put(objects2[0].toString()+objects2[1], objects2[2].toString());
				}
				sql = "select sum(a.goodsnumber),sum(a.returnnumber),a.goodsid,b.id.userlist from GoodsOutputList a,OutUserList b where b.id.tag='"+Constant.TAG[0]+"' and a.updtuser=b.id.outuser and b.id.tag='"+Constant.TAG[0]+"' and a.updttime>'"+minDate+"' and a.updttime<'"+maxDate+"' group by a.goodsid,b.id.userlist";
				List<Object[]> list1 = hibernateSessionDAO.createHqlQuery(sql);
				for (Object[] objects : list1) {
					if(!objects[2].toString().equals("")){
						boolean isUpdt = true;
						GoodsMonthStat stat = new GoodsMonthStat();
						GoodsMonthStatId id = new GoodsMonthStatId();
						id.setCleardate(StringUtil.newInstance().getInputDate(minDate, "yyyy-MM-dd"));
						id.setGoodsid(objects[2].toString());
						id.setUserlist(objects[3].toString());
						stat = hibernateSessionDAO.getHibernateTemplate().get(GoodsMonthStat.class, id);
						if(stat==null){
							stat = new GoodsMonthStat();
							stat.setId(id);
							isUpdt = false;
						}
						stat.setEnddate(StringUtil.newInstance().getInputDate(maxDate, "yyyy-MM-dd"));
						String key = objects[2].toString()+objects[3].toString();
						int startnumber = 0;
						
						sql = "select lastnumber from GoodsMonthStat where id.goodsid='"+objects[2].toString()+"' and id.userlist='"+objects[3].toString()+"' and enddate='"+minDate+"'";
						List<Integer> initList = hibernateSessionDAO.createHqlQuery(sql);
						if(initList==null || initList.size()==0){
							sql = "select goodsnumber from GoodsList where id.id='"+objects[2].toString()+"' and id.userlist='"+objects[3].toString()+"'";
							initList = hibernateSessionDAO.createHqlQuery(sql);
						}
						startnumber = initList.get(0);
						/*if(initMap.get(key)!=null){
							startnumber = Integer.parseInt(initMap.get(key));
						}else{
							sql = "select goodsnumber from GoodsList where id.id='"+objects[2]+"'";
							List<Integer> initList = hibernateSessionDAO.createHqlQuery(sql);
							if(initList!=null){
								startnumber = Integer.parseInt(initList.get(0).toString());
							}
						}*/
						int innumber = 0;
						if(inMap.get(key)!=null){
							innumber = Integer.parseInt(inMap.get(key));
						}
						int lastnumber = startnumber+innumber-Integer.parseInt(objects[0].toString())+Integer.parseInt(objects[1].toString());
						if(stat.getLastnumber()!=null && lastnumber == stat.getLastnumber() ){
							continue;
						}
						stat.setLastnumber(lastnumber);
						stat.setOutnumber(Integer.parseInt(objects[0].toString()));
						stat.setInnumber(innumber);
						stat.setReturnnumber(Integer.parseInt(objects[1].toString()));
						stat.setStartdate(StringUtil.newInstance().getInputDate(minDate, "yyyy-MM-dd"));
						stat.setStartnumber(startnumber);
						stat.setUpdttime(new Date());
						stat.setUpdtuser(userInfo.getUserId());
						if(!isUpdt){
							hibernateSessionDAO.getHibernateTemplate().save(stat);
						}else{
							hibernateSessionDAO.getHibernateTemplate().update(stat);
						}
					}
				}
				inputdate = StringUtil.newInstance().dateAddMonth(StringUtil.newInstance().getInputDate(inputdate, "yyyy-MM-dd"), 1);
			}
		}
	}
	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}
	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}

}
