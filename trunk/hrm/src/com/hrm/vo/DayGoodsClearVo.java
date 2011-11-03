package com.hrm.vo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.DayGoodsClear;
import com.hrm.entity.DayGoodsClearId;
import com.hrm.entity.UserInfo;
import com.hrm.util.Constant;
import com.hrm.util.StringUtil;

public class DayGoodsClearVo implements BaseVo {
	private static Logger logger = Logger.getLogger(DayGoodsClearVo.class);
	private HibernateSessionDAO hibernateSessionDAO;
	private String startDate = "";
	private String endDate = "";
	UserInfo userInfo = new UserInfo();
	public Request execute(Request request) throws Exception {
		String result = "";
		String action = request.getParamsMap().get("action");
		String type = request.getParamsMap().get("type");
		userInfo = (UserInfo)request.getUserInfo();
		if(action.equals("hql")){
			if(check(request)){
				importData(request);
			}
			result = queryHqlMap(request);
		}else if(action.equals("update")){
			String userlist = request.getParamsMap().get("userlist");
			String goodsid = request.getParamsMap().get("goodsid");
			String cleardate = request.getParamsMap().get("cleardate");
			String goodsdesc = request.getParamsMap().get("goodsdesc");
			String equals = request.getParamsMap().get("equals");
			DayGoodsClearId id = new DayGoodsClearId();
			try {
				id.setCleardate(DateFormat.getDateInstance().parse(cleardate));
				id.setGoodsid(goodsid);
				id.setUserlist(userlist);
			} catch (ParseException e) {
				logger.error("DayGoodsClearVo的excute方法错误:", e);
			}
			DayGoodsClear clear = (DayGoodsClear)hibernateSessionDAO.getHibernateTemplate().get("com.hrm.entity.DayGoodsClear", id);
			clear.setEquals((equals.equals("false")?"0":"1"));
			clear.setGoodsdesc(goodsdesc);
			hibernateSessionDAO.getHibernateTemplate().update(clear);
		}
		request.setResponse(result);
		return request;
	}
	/**
	 * 通过query查询hql语句
	 * @param request
	 * @param response
	 * @return
	 */
	public String queryHqlMap(Request request){
		String result = "";
		String sql = request.getParamsMap().get("sql");
		String start = request.getParamsMap().get("start");
		String limit = request.getParamsMap().get("limit");
		if(request.getParamsMap().get("type").equals("mapdate")){
			sql = sql.replace("{1}", startDate);
			sql = sql.replace("{2}", endDate);
		}
		if (start==null) {
			List<Map<String,Object>> list = hibernateSessionDAO.createHqlQuery(sql);
			result = "{totalProperty:"+list.size()+",";
			result += "root:[";
			for(Map<String,Object> map:list){
				result += StringUtil.newInstance().formatMapToJson(map)+",";
			}
			if (list.size()>0) {
				result = result.substring(0, result.length()-1);
			}
			result += "]}";
		}else{
			List<Map<String,Object>> list = hibernateSessionDAO.createHqlQuery(sql, Integer.parseInt(start), Integer.parseInt(limit));
			int count = hibernateSessionDAO.getHibernateTemplate().find(sql).size();
			result = "{totalProperty:"+count+",";
			result += "root:[";
			for(Map<String,Object> map:list){
				result += StringUtil.newInstance().formatMapToJson(map)+",";
			}
			if (list.size()>0) {
				result = result.substring(0, result.length()-1);
			}
			result += "]}";
		}
		return result;
	}
	/**
	 * 通过实体转换
	 * @param request
	 * @param response
	 * @return
	 */
	public String queryHqlEntity(HttpServletRequest request,
			HttpServletResponse response){
		String result = "";
		String sql = request.getParameter("sql");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		if (start==null) {
			List<Object> list = hibernateSessionDAO.createHqlQuery(sql);
			result = "{totalProperty:"+list.size()+",";
			result += "root:[";
			for(Object obj:list){
				result += StringUtil.newInstance().transeObjectToJson(obj)+",";
			}
			if (list.size()>0) {
				result = result.substring(0, result.length()-1);
			}
			result += "]}";
		}else{
			List<Object> list = hibernateSessionDAO.createHqlQuery(sql, Integer.parseInt(start), Integer.parseInt(limit));
			int count = hibernateSessionDAO.getHibernateTemplate().find(sql).size();
			result = "{totalProperty:"+count+",";
			result += "root:[";
			for(Object obj:list){
				result += StringUtil.newInstance().transeObjectToJson(obj)+",";
			}
			if (list.size()>0) {
				result = result.substring(0, result.length()-1);
			}
			result += "]}";
		}
		return result;
	}
	/**
	 * 返回True将需要更新清算表
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean check(Request request){
		String sql = "select max(id.cleardate),max(updttime) from DayGoodsClear";
		List<Object[]> list = hibernateSessionDAO.createHqlQuery(sql);
		Object[] date = list.get(0);
		if(date[0]!=null){
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				startDate = StringUtil.newInstance().dateAddNumber(DateFormat.getDateInstance().parse(date[0].toString()), 1);
				String updttime = sdf.format((Date)date[1]);
				if(StringUtil.newInstance().getCurrentDate().equals(updttime)){
					return false;
				}
			}catch(Exception e){
				logger.error("DayGoodsClearVo的check方法错误:", e);
			}
		}else{
			startDate = "";
		}
		return true;
	}
	/**
	 * 数据到清算表
	 * @param request
	 * @param response
	 */
	public void importData(Request request){
		String sql = "select a.outdate,b.id.userlist from GoodsOutputInfo a,OutUserList b where a.outuser=b.id.outuser and b.id.tag='"+Constant.TAG[0]+"'";
		if(!startDate.equals("")){
			sql += " and a.outdate>'"+startDate+"'";
		}
		sql += " order by a.outdate asc";
		List<Object[]> list = hibernateSessionDAO.createHqlQuery(sql);
		String tempDate = "";
		String tempUser = "";
		for( Object[] obj : list ){
			String ourdate = StringUtil.newInstance().formatDate(obj[0].toString(), "yyyy-MM-dd");
			String outuser = obj[1].toString();
			try{
				if(tempDate.equals(ourdate) && tempUser.equals(outuser)){
					continue;
				}else{
					//initsql = "select a.id,a.goodsnumber+ifnull((select sum(b.goodsnumber) from Store_Output_List b,Store_Output_Info c where c.id=b.outid and b.goodsid=a.id and c.outdate<'2010-04-14'),0)-ifnull((select sum(d.goodsnumber) from Goods_Output_List d where a.id=d.goodsid and updttime<'2010-04-14'),0)+ifnull((select sum(e.returnnumber) from goods_output_list e where e.goodsid=a.id and updttime<'2010-04-14'),0) as storenumber  from Goods_List a";
					tempDate = ourdate;
					tempUser = outuser;
					String endate = StringUtil.newInstance().dateAddNumber(DateFormat.getDateInstance().parse(ourdate), 1);
					String sql1 = "select a.goodsid as goodsid," +
						"sum(a.goodsnumber) as outnumber," +
						"sum(a.returnnumber) as returnnumber," +
						"sum(a.goodsnumber)-sum(a.returnnumber) as actualnumber," +
						"b.goodsname as goodsname " +
						"from GoodsOutputList a,StoreList b " +
						"where a.goodsid=b.id and a.updttime>'"+ourdate+"' and a.updttime<'"+endate+"' and (select id.outuser from OutUserList where id.userlist='"+outuser+"' and id.tag='"+Constant.TAG[0]+"' and outuser=a.updtuser) is not null " +
						"group by a.goodsid,b.goodsname";
					List<Object[]> list1 = hibernateSessionDAO.createHqlQuery(sql1);
					for(Object[] obj1 : list1 ){
						DayGoodsClear clear = new DayGoodsClear();
						DayGoodsClearId id = new DayGoodsClearId();
						id.setCleardate(DateFormat.getDateInstance().parse(ourdate));
						id.setGoodsid(obj1[0].toString());
						id.setUserlist(outuser);
						clear.setId(id);
						clear.setEquals("0");
						clear.setGoodsdesc("无");
						clear.setLastnumber(Integer.parseInt(obj1[3].toString()));
						clear.setOutnumber(Integer.parseInt(obj1[1].toString()));
						clear.setReturnnumber(Integer.parseInt(obj1[2].toString()));
						clear.setUpdttime(new Date());
						clear.setUpdtuser(userInfo.getUserId());
						hibernateSessionDAO.getHibernateTemplate().save(clear);
					}
				}
			}catch(Exception e){
				logger.error("DayGoodsClearVo的importData方法错误:", e);
			}
		}
		hibernateSessionDAO.getHibernateTemplate().flush();
	}
	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}
	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}

}
