package com.hrm.vo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.impl.SessionFactoryImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.GoodsStat;
import com.hrm.entity.GoodsStatId;
import com.hrm.entity.UserInfo;
import com.hrm.util.ClsFactory;
import com.hrm.util.Constant;
import com.hrm.util.StringUtil;

public class MainLoadVo implements BaseVo {

	private HibernateSessionDAO hibernateSessionDAO;
	private List<String> userlist = new ArrayList<String>();
	private UserInfo userInfo = new UserInfo();
	public Request execute(Request request) throws Exception {
		String action = request.getParamsMap().get("action");
		String result = "";
		userInfo = (UserInfo)request.getUserInfo();
		if(action.equals("main1")){
			result = "{totalProperty:100,root:[" +
				"{id:0,name:'firefox火狐浏览器',verson:'3.5.5',page:'brower/firefox/FirefoxChinaEdition-latest.exe'}," +
				"{id:1,name:'chrome谷歌浏览器',verson:'5.0.4',page:'brower/chrome/chrome_installer.exe'}," +
				"{id:2,name:'Adobe flash插件',verson:'9.0.0',page:'brower/flash/flashplayer_9_ax_debug.exe'}," +
				"{id:3,name:'Adobe AIR插件',verson:'9.0.0',page:'brower/flash/AdobeAIRInstaller.exe'}," +
				"{id:4,name:'魔域传说4',verson:'1.0.0',page:'brower/game/FAV4.rar'}," +
				"{id:5,name:'泡泡龙',verson:'1.0.0',page:'brower/game/paopaolong.exe'}" +
				"]}";

		}else if(action.equals("main1_1")){
			result = "[{    task:'Project: Shopping',    duration:13.25,    user:'Tommy Maintz',    iconCls:'task-folder',    expanded: true,    children:[{        task:'Housewares',        duration:1.25,        user:'Tommy Maintz',        iconCls:'task-folder',        children:[{            task:'Kitchen supplies',            duration:0.25,            user:'Tommy Maintz',            leaf:true,            iconCls:'task'        },{            task:'Groceries',            duration:.4,            user:'Tommy Maintz',            leaf:true,            iconCls:'task'        },{            task:'Cleaning supplies',            duration:.4,            user:'Tommy Maintz',            leaf:true,            iconCls:'task'        },{            task: 'Office supplies',            duration: .2,            user: 'Tommy Maintz',            leaf: true,            iconCls: 'task'        }]    }, {        task:'Remodeling',        duration:12,        user:'Tommy Maintz',        iconCls:'task-folder',        expanded: true,        children:[{            task:'Retile kitchen',            duration:6.5,            user:'Tommy Maintz',            leaf:true,            iconCls:'task'        },{            task:'Paint bedroom',            duration: 2.75,            user:'Tommy Maintz',            iconCls:'task-folder',            children: [{                task: 'Ceiling',                duration: 1.25,                user: 'Tommy Maintz',                iconCls: 'task',                leaf: true            }, {                task: 'Walls',                duration: 1.5,                user: 'Tommy Maintz',                iconCls: 'task',                leaf: true            }]        },{            task:'Decorate living room',            duration:2.75,            user:'Tommy Maintz',            leaf:true,            iconCls:'task'        },{            task: 'Fix lights',            duration: .75,            user: 'Tommy Maintz',            leaf: true,            iconCls: 'task'        }, {            task: 'Reattach screen door',            duration: 2,            user: 'Tommy Maintz',            leaf: true,            iconCls: 'task'        }]    }]}]";

		}else if(action.equals("main2")){ 
			result = "{totalProperty:100,root:[" +
				"{id:1,name:'版本上线',text:'人力资源管理系统v2.0终于上线啦!!</br></br>',time:'2010-05-21 12:01:56'}," +
				"{id:2,name:'下载浏览器',text:'请用户下载左边的下载按钮安装浏览器，以便得到最高的体验效果！！</br></br>',time:'2010-05-21 12:02:35'}," +
				"{id:3,name:'下载flash',text:'该系统的图形展示需要flash进行支持，请下载最新的flash版本或者下载左边的flash以便观看！</br></br>',time:'2010-05-21 12:02:35'}," +
				"{id:4,name:'用户使用手册',text:'用户使用手册将在近期进行编写并且发布，尽请期待！</br></br>',time:'2010-05-21 12:04:25'}," +
				"{id:5,name:'使用事项',text:'系统上线，将可能会出现一些不可预计的问题，请用户协调进行测试管理，完善该系统！</br></br>',time:'2010-05-21 12:07:56'}," +
				"{id:6,name:'问题解决一',text:'如果出现浏览异常，请尝试对浏览器的历史记录进行清除！</br>以下以firefox浏览器为例子!</br>具体解决步骤：</br>点击工具->清除历史记录->最近一小时->立即清除</br>然后重新登陆系统,即可!</br></br>',time:'2010-05-21 12:05:22'}," +
				"{id:7,name:'问题解决二',text:'如果出现系统操作无法操作下去，请记录好问题原因或者通知问题管理员进行协调操作！</br></br>',time:'2010-05-21 12:06:21'}" +
				"]}";
 
		}else if(action.equals("main3")){
			result = "{totalProperty:100,root:[" +
				"{id:1,name:'日销售图',picture:'chart-reload.gif',time:'2010-05-21 12:01:56'}," +
				"{id:2,name:'月销售图',picture:'charts.gif',time:'2010-05-21 12:02:35'}," +
				"{id:3,name:'库存数量图',picture:'chart-stacked.gif',time:'2010-05-21 12:02:35'}," +
				"{id:4,name:'库存数据校验',picture:'chart-pie.gif',time:'2010-05-21 12:04:25'}" +
				"]}";
		}else if(action.equals("main4")){
			String date =request.getParamsMap().get("date");
			List<Object[]> list = hibernateSessionDAO.createHqlQuery("select b.goodsname,a.lastnumber from DayGoodsClear a,StoreList b where a.id.goodsid = b.id and a.id.cleardate='"+date+"'");
			String chart= "";
			for (Object[] objects : list) {
				chart += "{goodsname:'"+objects[0]+"',sellnumber:"+objects[1]+"},";
			}
			if(!chart.equals("")){
				chart = chart.substring(0, chart.length()-1);
			}
			result = "{totalProperty:100,root:[" + chart+ "]}";
		}else if(action.equals("main5")){
			String date =request.getParamsMap().get("date");
			String userlist = request.getParamsMap().get("userlist");
			String typeid = request.getParamsMap().get("typeid");
			List<Object[]> list = hibernateSessionDAO.createHqlQuery("select b.goodsname,a.outnumber-a.returnnumber,a.lastnumber from GoodsMonthStat a,StoreList b where b.typeid='"+typeid+"' and a.id.goodsid = b.id and a.id.userlist = '"+userlist+"' and a.startdate<='"+date+"' and a.enddate>'"+date+"'");
			String chart= "";
			for (Object[] objects : list) {
				chart += "{goodsname:'"+objects[0]+"',sellnumber:"+objects[1]+",lastnumber:"+objects[2]+"},";
			}
			if(!chart.equals("")){
				chart = chart.substring(0, chart.length()-1);
			}
			result = "{totalProperty:100,root:[" + chart+ "]}";
		}else if(action.equals("main6")){
			String userlist = request.getParamsMap().get("userlist");
			String typeid = request.getParamsMap().get("typeid");
			List<Object[]> list = hibernateSessionDAO.createHqlQuery("select b.goodsname,a.goodsnumber+a.innumber,a.outnumber,a.goodsnumber+a.innumber-a.outnumber from GoodsList a,StoreList b where b.typeid='"+typeid+"' and a.id.id = b.id and a.id.userlist = '"+userlist+"'");
			String chart= "";
			for (Object[] objects : list) {
				chart += "{goodsname:'"+objects[0]+"',innumber:"+objects[1]+",outnumber:"+objects[2]+",lastnumber:"+objects[3]+"},";
			}
			if(!chart.equals("")){
				chart = chart.substring(0, chart.length()-1);
			}
			result = "{totalProperty:100,root:[" + chart+ "]}";
		}else if(action.equals("hql")){
			/*if(check(request,response)){
				statdate(request,response);
			}
			Map<String,List<Integer>> sMap = sStat(request,response);
			Map<String,List<Integer>> gMap = gStat(request,response);
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
				clear.setUpdtuser(userInfo.getUserId());
				clear.setUpdttime(new Date());
				if(isOper){
					if (isUpdt) {
						hibernateSessionDAO.getHibernateTemplate().update(clear);
					}else{
						hibernateSessionDAO.getHibernateTemplate().save(clear);
					}
				}
			}*/
			BeanFactory factory =ClsFactory.newInstance().getFactory();
			BaseVo query = factory.getBean("queryInfoVo", BaseVo.class);
			result = query.execute(request).getResponse().toString();
			/*String userlist = request.getParameter("userlist");
			List<Object[]> list = hibernateSessionDAO.createHqlQuery("select b.goodsname,a.goodsnumber+a.innumber,a.outnumber,a.goodsnumber+a.innumber-a.outnumber from GoodsList a,StoreList b where a.id.id = b.id and a.id.userlist = '"+userlist+"'");
			String chart= "";
			for (Object[] objects : list) {
				chart += "{goodsname:'"+objects[0]+"',innumber:"+objects[1]+",outnumber:"+objects[2]+",lastnumber:"+objects[3]+"},";
			}
			if(!chart.equals("")){
				chart = chart.substring(0, chart.length()-1);
			}
			result = "{totalProperty:100,root:[" + chart+ "]}";*/
		}
		request.setResponse(result);
		return request;
	}
	/**
	 * 检查今天数据是否需要统计
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean check(HttpServletRequest request, HttpServletResponse response){
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
	private void statdate(HttpServletRequest request, HttpServletResponse response){
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
	private Map<String,List<Integer>> sStat(HttpServletRequest request, HttpServletResponse response){
		String where0 = "";
		String where1 = "(select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist=a.userlist and outuser=d.outuser) is not null and ";
		String where2 = "(select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist=a.userlist and outuser=e.updtuser) is not null and ";
		String where3 = "(select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist=a.userlist and outuser=f.updtuser) is not null and ";
//		String where4 = " and outuser in (select outuser from out_user_list where tag='"+Constant.TAG[0]+"') ";
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
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
	private Map<String,List<Integer>> gStat(HttpServletRequest request, HttpServletResponse response){
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
					stat.setUpdtuser(userInfo.getUserId());
					hibernateSessionDAO.getHibernateTemplate().save(stat);
				}else{
					stat.setStorenumber(Integer.parseInt(obj[1]==null?"0":obj[1].toString())+stat.getStorenumber());
					stat.setOutnumber(Integer.parseInt(obj[2]==null?"0":obj[2].toString())+stat.getOutnumber());
					stat.setReturnnumber(Integer.parseInt(obj[3]==null?"0":obj[3].toString())+stat.getReturnnumber());
					stat.setUpdttime(new Date());
					stat.setUpdtuser(userInfo.getUserId());
					hibernateSessionDAO.getHibernateTemplate().update(stat);
				}
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
