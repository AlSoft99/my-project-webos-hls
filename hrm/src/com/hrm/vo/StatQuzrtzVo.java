package com.hrm.vo;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.impl.SessionFactoryImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.DayClearChk;
import com.hrm.entity.DayGoodsClear;
import com.hrm.entity.DayGoodsClearId;
import com.hrm.entity.GoodsMonthStat;
import com.hrm.entity.GoodsMonthStatId;
import com.hrm.entity.GoodsStat;
import com.hrm.entity.GoodsStatId;
import com.hrm.entity.MaterialList;
import com.hrm.entity.MaterialStoreList;
import com.hrm.entity.MaterialStoreListId;
import com.hrm.entity.StoreGoodsClear;
import com.hrm.entity.StoreGoodsClearId;
import com.hrm.entity.UserInfo;
import com.hrm.quartz.StatQuzrtzBean;
import com.hrm.util.ClsFactory;
import com.hrm.util.Constant;
import com.hrm.util.StringUtil;
/**
 * 统计月报表和日清算报表计时器
 * @author Guanrl
 *
 */
public class StatQuzrtzVo {
	private String startDate = "";
	private HibernateSessionDAO hibernateSessionDAO;
	private List<String> userlist = new ArrayList<String>();
	public void execute() throws Exception{
		//月报表
		check();
		statData();
		//清算表
		checkChk();
		if(checkClear()){
			importData();
		}else{
//			updateData();
		}
		//异常检查
		if(checkError()){
			statdate();
		}
		checkErrorProcess();
		//清算日常检查
		checkDayClear();
		checkStoreList();
	}
	
	public void checkStoreList(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String storedate = sdf.format(new Date());
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
				store.setUpdtuser("system");
				store.setUpdttime(new Date());
				hibernateSessionDAO.save(store);
			}
		}
	}
	/**
	 * 月报表检查
	 * @return
	 */
	public boolean check(){
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
				com.hrm.util.ClsFactory.newInstance().error(this.toString(), e);
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
	public void statData(){
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
						if(Integer.valueOf(lastnumber).equals(stat.getLastnumber())){
							continue;
						}
						stat.setLastnumber(lastnumber);
						stat.setOutnumber(Integer.parseInt(objects[0].toString()));
						stat.setInnumber(innumber);
						stat.setReturnnumber(Integer.parseInt(objects[1].toString()));
						stat.setStartdate(StringUtil.newInstance().getInputDate(minDate, "yyyy-MM-dd"));
						stat.setStartnumber(startnumber);
						stat.setUpdttime(new Date());
						stat.setUpdtuser("system");
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
	public void checkChk(){
		String sql = "select clearequals,cleardesc from DayClearChk where cleardate='"+StringUtil.newInstance().dateAddNumber(new Date(), -1)+"'";
		List<Object[]> clear = hibernateSessionDAO.createHqlQuery(sql);
		if(clear.size()!=0 && clear.get(0)[0].toString().equals("0")){
			sql = "delete from DayGoodsClear where cleardate = '"+StringUtil.newInstance().dateAddNumber(new Date(), -1)+"'";
			hibernateSessionDAO.createHqlExcute(sql);
			sql = "delete from DayClearChk where cleardate='"+StringUtil.newInstance().dateAddNumber(new Date(), -1)+"'";
			hibernateSessionDAO.createHqlExcute(sql);
		}
	}
	/**
	 * 返回True将需要更新清算表
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean checkClear(){
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
				com.hrm.util.ClsFactory.newInstance().error("StatQuzrtzVo的checkClear方法错误:", e);
			}
		}else{
			startDate = "";
		}
		sql = "select clearequals,cleardesc from DayClearChk where cleardate='"+StringUtil.newInstance().dateAddNumber(new Date(), -1)+"'";
		List<Object[]> clear = hibernateSessionDAO.createHqlQuery(sql);
		if(clear.size()==0){
			return true;
		}else{
			if(clear.get(0)[0].toString().equals("0")){
				return true;
			}else{
				return false;
			}
		}
	}
	/**
	 * 数据到清算表
	 * @param request
	 * @param response
	 */
	public void importData(){
		String sql = "select a.outdate,b.id.userlist from GoodsOutputInfo a,OutUserList b where a.outuser=b.id.outuser and b.id.tag='"+Constant.TAG[0]+"'";
		if(!startDate.equals("")){
			sql += " and a.outdate>'"+startDate+"'";
		}
		sql += " order by a.outdate,b.id.userlist asc";
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
						clear.setUpdtuser("system");
						hibernateSessionDAO.getHibernateTemplate().save(clear);
					}
				}
			}catch(Exception e){
				com.hrm.util.ClsFactory.newInstance().error("StatQuzrtzVo的importData方法错误:", e);
			}
		}
		hibernateSessionDAO.getHibernateTemplate().flush();
	}
	public void updateData(){
		String sql = "select a.outdate,b.id.userlist from GoodsOutputInfo a,OutUserList b where a.outuser=b.id.outuser and b.id.tag='"+Constant.TAG[0]+"'";
		sql += " and a.outdate>'"+StringUtil.newInstance().dateAddNumber(new Date(), -1)+"'";
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
						clear.setUpdtuser("system");
						hibernateSessionDAO.getHibernateTemplate().update(clear);
					}
				}
			}catch(Exception e){
				com.hrm.util.ClsFactory.newInstance().error("DayGoodsClearVo的importData方法错误:", e);
			}
		}
		hibernateSessionDAO.getHibernateTemplate().flush();
	}
	
	/**==================================异常检查开始=================================*/
	/**
	 * 检查今天数据是否需要统计
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean checkError(){
		List<Object[]> list = hibernateSessionDAO.createHqlQuery("select max(id.cleardate) from StoreGoodsClear");
		if (list.get(0)!=null && list.size()>0) {
			String dbdate = StringUtil.newInstance().formatDate(list.get(0)+"", "yyyy-MM-dd");
			if(dbdate.equals(StringUtil.newInstance().getCurrentDate())){
				return false;
			}
		}
		return true;
	}
	/**
	 * 统计数据
	 * @param request
	 * @param response
	 */
	private void statdate(){
		final String checkdate = "select max(updttime) from GoodsStat"; 
		Date date = (Date)hibernateSessionDAO.createHqlQuery(checkdate).get(0);
		final String userSql = "select distinct id.userlist from OutUserList where id.tag='"+Constant.TAG[0]+"'";
		userlist = hibernateSessionDAO.createHqlQuery(userSql);
		String startDate = "";
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			startDate = sdf.format(date);
		}
		String endDate = StringUtil.newInstance().dayAddNumber(new Date(), 0);
		if(!StringUtil.newInstance().getCurrentDate().equals(startDate)){
			//历史统计入库
			statHisTable(startDate,endDate);
		}
	}
	/**
	 * 历史统计
	 * @param startDate
	 * @param endDate
	 */
	public void statHisTable(String startDate,String endDate){
		String where1 = "";
		String where2 = "";
		String where3 = "";
		if(startDate.equals("")){
			where1 = " d.updttime<'"+endDate+"'";
			where2 = " e.updttime<'"+endDate+"'";
			where3 = " f.updttime<'"+endDate+"'";
		}else{
			where1 = " d.updttime>'"+startDate+"' and d.updttime<'"+endDate+"'";
			where2 = " e.updttime>'"+startDate+"' and e.updttime<'"+endDate+"'";
			where3 = " f.updttime>'"+startDate+"' and f.updttime<'"+endDate+"'";
		}
		
		
		for(int i = 0 ; i < userlist.size(); i++){
			final String his = "select a.id," +
				"(select sum(c.goodsnumber) from store_output_list c,store_output_info d where c.goodsid=a.id and c.outid=d.id and d.status='2' and d.outuser in (select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+userlist.get(i).toString()+"') and "+where1+") as storenumber," +
				"(select sum(e.goodsnumber) from goods_output_list e where e.goodsid=a.id and e.updtuser in (select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+userlist.get(i).toString()+"') and "+where2+") as outnumber," +
				"(select sum(f.returnnumber) from goods_output_list f where f.goodsid=a.id and f.updtuser in (select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+userlist.get(i).toString()+"') and "+where3+") as returnnumber " +
				"from goods_list a where a.userlist='"+userlist.get(i).toString()+"'";
			List<Object[]> list = hibernateSessionDAO.getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>(){

				public List<Object[]> doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query sql = session.createSQLQuery(his);
					return sql.list();
				}
			});
			
			for(Object[] obj : list){
				GoodsStatId id = new GoodsStatId();
				id.setGoodsid(obj[0].toString());
				id.setUserlist(userlist.get(i).toString());
				GoodsStat stat = (GoodsStat) hibernateSessionDAO.getHibernateTemplate().get("com.hrm.entity.GoodsStat", id);
				if(stat==null){
					stat = new GoodsStat();
					stat.setId(id);
					stat.setStorenumber(Integer.parseInt(obj[1]==null?"0":obj[1].toString()));
					stat.setOutnumber(Integer.parseInt(obj[2]==null?"0":obj[2].toString()));
					stat.setReturnnumber(Integer.parseInt(obj[3]==null?"0":obj[3].toString()));
					stat.setUpdttime(new Date());
					stat.setUpdtuser("system");
					hibernateSessionDAO.getHibernateTemplate().save(stat);
				}else{
					stat.setStorenumber(Integer.parseInt(obj[1]==null?"0":obj[1].toString())+stat.getStorenumber());
					stat.setOutnumber(Integer.parseInt(obj[2]==null?"0":obj[2].toString())+stat.getOutnumber());
					stat.setReturnnumber(Integer.parseInt(obj[3]==null?"0":obj[3].toString())+stat.getReturnnumber());
					stat.setUpdttime(new Date());
					stat.setUpdtuser("system");
					hibernateSessionDAO.getHibernateTemplate().update(stat);
				}
			}
		}
		hibernateSessionDAO.getHibernateTemplate().flush();
	}
	private Map<String,List<Integer>> sStat(){
		String where0 = "";
		String where1 = "(select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist=a.userlist and outuser=d.outuser) is not null and ";
		String where2 = "(select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist=a.userlist and outuser=e.updtuser) is not null and ";
		String where3 = "(select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist=a.userlist and outuser=f.updtuser) is not null and ";
//		String where4 = " and outuser in (select outuser from out_user_list where tag='"+Constant.TAG[0]+"') ";
		BeanFactory factory =  ClsFactory.newInstance().getFactory();
		SessionFactoryImpl bean = factory.getBean("sessionFactory", SessionFactoryImpl.class);
		String dialect = bean.getDialect().toString();
		String nulltype = "";
		if(dialect.indexOf("MySQL")!=-1){
			nulltype = "ifnull";
		}else{
			nulltype = "isnull";
		}
		final String hql = "select a.id,a.userlist,c.goodsname," +
				"("+nulltype+"((select sum(c.goodsnumber) from store_output_list c,store_output_info d where "+where1+" c.goodsid=a.id and c.outid=d.id and d.status='2' and d.updttime>'"+StringUtil.newInstance().getCurrentDate()+"' and d.updttime<'"+StringUtil.newInstance().dayAddNumber(new Date(), 1)+"'),0))+(select storenumber from goods_stat where goodsid=a.id ) as storenumber," +
				"b.id,b.typename,a.goodsnumber," +
				"("+nulltype+"((select sum(e.goodsnumber) from goods_output_list e where "+where2+" e.goodsid=a.id and e.updttime>'"+StringUtil.newInstance().getCurrentDate()+"' and e.updttime<'"+StringUtil.newInstance().dayAddNumber(new Date(), 1)+"'),0))+(select outnumber from goods_stat where goodsid=a.id ) as outnumber," +
				"("+nulltype+"((select sum(f.returnnumber) from goods_output_list f where "+where3+" f.goodsid=a.id and f.updttime>'"+StringUtil.newInstance().getCurrentDate()+"' and f.updttime<'"+StringUtil.newInstance().dayAddNumber(new Date(), 1)+"'),0))+(select returnnumber from goods_stat where goodsid=a.id ) as returnnumber " +
				"from goods_list a,store_type b,store_list c where a.id=c.id and c.typeid=b.id "+where0;
		
		
		List<Object[]> list = hibernateSessionDAO.getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>(){

			public List<Object[]> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query sql = session.createSQLQuery(hql);
				return sql.list();
			}
			
		});
		Map<String,List<Integer>> sMap = new HashMap<String,List<Integer>>();
		for (Object[] obj : list) {
			//0:allnumber 1:outnumber 2:lastnumber
			List<Integer> listTmp = new ArrayList<Integer>();
			int allnumber = Integer.parseInt(obj[3].toString())+Integer.parseInt(obj[6].toString());
			int outnumber = Integer.parseInt(obj[7].toString());
			int returnnumber = Integer.parseInt(obj[8].toString());
			listTmp.add(allnumber);
			listTmp.add(outnumber-returnnumber);
			listTmp.add(allnumber-outnumber+returnnumber);
			sMap.put(obj[0].toString()+","+obj[1].toString(), listTmp);
		}
		return sMap;
	}
	private Map<String,List<Integer>> gStat(){
		List<Object[]> list = hibernateSessionDAO.createHqlQuery("select id.id||','||id.userlist,goodsnumber+innumber,outnumber,goodsnumber+innumber-outnumber from GoodsList");
		Map<String,List<Integer>> gMap = new HashMap<String,List<Integer>>();
		for (Object[] obj : list) {
			//0:allnumber 1:outnumber 2:lastnumber
			List<Integer> listTmp = new ArrayList<Integer>();
			int allnumber = Integer.parseInt(obj[1].toString());
			int outnumber = Integer.parseInt(obj[2].toString());
			listTmp.add(allnumber);
			listTmp.add(outnumber);
			listTmp.add(Integer.parseInt(obj[3].toString()));
			gMap.put(obj[0].toString(), listTmp);
		}
		return gMap;
	}
	private void checkErrorProcess(){
		Map<String,List<Integer>> sMap = sStat();
		Map<String,List<Integer>> gMap = gStat();
		for(Iterator<String> it = sMap.keySet().iterator();it.hasNext();){
			String key = it.next();
			String[] keys = key.split(",");
			boolean isUpdt = true;
			boolean isOper = false;
			StoreGoodsClear clear = new StoreGoodsClear();
			StoreGoodsClearId id = new StoreGoodsClearId();
			id.setCleardate(StringUtil.newInstance().getInputDate(StringUtil.newInstance().getCurrentDate(), "yyyy-MM-dd"));
			id.setGoodsid(keys[0]);
			id.setUserlist(keys[1]);
			clear.setId(id);
			clear = hibernateSessionDAO.getHibernateTemplate().get(StoreGoodsClear.class, id);
			List<Integer> sVal = sMap.get(key);
			List<Integer> gVal = gMap.get(key);
			if (clear==null) {
				clear = new StoreGoodsClear();
				clear.setId(id);
				isUpdt = false;
				isOper = true;
			}else{
				if(!clear.getSlastnumber().equals(sVal.get(2)) || !clear.getGlastnumber().equals(gVal.get(2))){
					isOper = true;
				}else{
					continue;
				}
			}
			clear.setSstorenumber(sVal.get(0));
			clear.setSoutnumber(sVal.get(1));
			clear.setSlastnumber(sVal.get(2));
			
			clear.setGstorenumber(gVal.get(0));
			clear.setGoutnumber(gVal.get(1));
			clear.setGlastnumber(gVal.get(2));
			if (sVal.get(0).equals(gVal.get(0)) && sVal.get(1).equals(gVal.get(1)) && sVal.get(2).equals(gVal.get(2))) {
				clear.setEquals("1");
			}else{
				clear.setEquals("0");
			}
			clear.setUpdtuser("system");
			clear.setUpdttime(new Date());
			if(isOper){
				if (isUpdt) {
					hibernateSessionDAO.getHibernateTemplate().update(clear);
				}else{
					hibernateSessionDAO.getHibernateTemplate().save(clear);
				}
			}
		}
	}
	/**=======================异常检查结束================================**/
	public void checkDayClear(){
		com.hrm.util.ClsFactory.newInstance().info("日清算检查开始");
		String cleardate = StringUtil.newInstance().dateAddNumber(new Date(), -1);
		List<DayClearChk> clearlist = hibernateSessionDAO.createHqlQuery("from DayClearChk where cleardate='"+cleardate+"'");
		if(clearlist.size()==0){
			List<String> list1 = hibernateSessionDAO.createHqlQuery("select distinct id.userlist from OutUserList where id.tag='1'");
			Map<String,Integer> map1 = new HashMap<String, Integer>();
			Map<String,Integer> map2 = new HashMap<String, Integer>();
			for (String userlist : list1) {
				if(userlist!=null){
					List<Object[]> list2 = hibernateSessionDAO.createHqlQuery("select a.lastnumber,a.id.goodsid from DayGoodsClear a where a.id.cleardate='"+cleardate+"' and a.id.userlist='"+userlist+"'");
					for (Object[] objects2 : list2) {
						if(objects2[0]!=null){
							int lastnumber = Integer.parseInt(objects2[0].toString());
							String goodsid = objects2[1].toString();
							map1.put(userlist+goodsid, lastnumber);
						}
					}
					List<Object[]> list3 = hibernateSessionDAO.createHqlQuery("select sum(a.goodsnumber)-sum(a.returnnumber) as actualnumber,a.goodsid as goodsid from GoodsOutputList a,StoreList b where a.goodsid=b.id and (select id.userlist from OutUserList where id.outuser=a.updtuser and id.tag='1') is not null and a.updttime>='"+cleardate+"' and a.updttime<'"+StringUtil.newInstance().getCurrentDate()+"' group by a.goodsid,b.goodsname");
					for (Object[] objects3 : list3) {
						if(objects3[0]!=null){
							int lastnumber = Integer.parseInt(objects3[0].toString());
							String goodsid = objects3[1].toString();
							map2.put(userlist+goodsid, lastnumber);
						}
					}
				}
			}
			String cleardesc = "";
			if(map1.size()!=0){
				boolean isError = false;
				for(Iterator<String> it = map1.keySet().iterator();it.hasNext();){
					String key = it.next();
					Integer value1 = map1.get(key);
					Integer value2 = map2.get(key);
					if(value1==null || value2==null || !value1.equals(value2)){
						isError = true;
						cleardesc += key+"|"+value1+"|"+value2+"$";
					}
				}
				if(isError){
					ClsFactory.newInstance().setCleardate(StringUtil.newInstance().getCurrentDate());
					ClsFactory.newInstance().setEquals("0");
				}else{
					ClsFactory.newInstance().setCleardate(StringUtil.newInstance().getCurrentDate());
					ClsFactory.newInstance().setEquals("1");
				}
			}else{
				ClsFactory.newInstance().setCleardate(StringUtil.newInstance().getCurrentDate());
				ClsFactory.newInstance().setEquals("-1");
			}
			if(cleardesc.equals("")){
				cleardesc = "无";
			}else{
				if(cleardesc.length()>500){
					cleardesc = cleardesc.substring(0, 500);
				}
			}
			DayClearChk chk = new DayClearChk();
			chk.setCleardate(StringUtil.newInstance().getInputDate(cleardate, "yyyy-MM-dd"));
			chk.setCleardesc(cleardesc);
			chk.setClearequals(ClsFactory.newInstance().getEquals());
			chk.setStoreclear("");
			chk.setStoredesc("");
			chk.setUpdttime(new Date());
			hibernateSessionDAO.getHibernateTemplate().save(chk);
		}else{
			ClsFactory.newInstance().setCleardate(clearlist.get(0).getCleardate()+"");
			ClsFactory.newInstance().setEquals(clearlist.get(0).getClearequals());
		}
		com.hrm.util.ClsFactory.newInstance().info("日清算检查结束");
	}
	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}
	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}
}
