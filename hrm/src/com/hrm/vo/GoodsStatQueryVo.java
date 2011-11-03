package com.hrm.vo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.hrm.server.InitData;
import com.hrm.util.ClsFactory;
import com.hrm.util.Constant;
import com.hrm.util.StringUtil;

public class GoodsStatQueryVo implements BaseVo {
	private HibernateSessionDAO hibernateSessionDAO;
	List userlist = new ArrayList();
	UserInfo userInfo = new UserInfo();
	public Request execute(Request request) throws Exception{
		String result = "";
		final String start = request.getParamsMap().get("start");
		final String limit = request.getParamsMap().get("limit");
		final String outuser = request.getParamsMap().get("outuser");
		final String userlists = request.getParamsMap().get("userlist");
		userInfo = (UserInfo)request.getUserInfo();
/*		final String hql = "select a.goodsid,b.goodsname,sum(a.goodsnumber) storenumber,b.typeid,c.typename,b.goodsnumber as initnumber,b.goodsnumber+sum(a.goodsnumber) allnumber," +
				"(select sum(goodsnumber) goodsnumber from goods_output_list where goodsid=a.goodsid group by goodsid) outnumber," +
				"(select sum(returnnumber) returnnumber from goods_output_list where goodsid=a.goodsid group by goodsid) returnnumber," +
				"(b.goodsnumber+sum(a.goodsnumber)-(select sum(goodsnumber) goodsnumber from goods_output_list where goodsid=a.goodsid group by goodsid)+(select sum(returnnumber) returnnumber from goods_output_list where goodsid=a.goodsid group by goodsid)) lastnumber " +
				"from store_output_list a,goods_list b,goods_type c,store_output_info d " +
				"where a.goodsid = b.id and b.typeid=c.id and a.outid=d.id and d.status='2'  " +
				"group by a.goodsid,b.goodsname,b.typeid,c.typename,b.goodsnumber" ;*///2292//110355//
		String where0 = "";
		String where1 = "";
		String where2 = "";
		String where3 = "";
//		String where4 = "";
		String username = "'全部'";
		if(outuser!=null &&!outuser.equals("")){
			where0 = " and a.userlist='"+InitData.getUserList(outuser+Constant.TAG[0])+"' ";
			where1 = "(select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+InitData.getUserList(outuser+Constant.TAG[0])+"' and outuser=d.outuser) is not null and ";
			where2 = "(select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+InitData.getUserList(outuser+Constant.TAG[0])+"' and outuser=e.updtuser) is not null and ";
			where3 = "(select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+InitData.getUserList(outuser+Constant.TAG[0])+"' and outuser=f.updtuser) is not null and ";
//			where4 = " and outuser in (select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+InitData.getUserList(outuser+Constant.TAG[0])+"') ";
//			username = InitData.getValue(Constant.USER_INFO,outuser);//HibernateTransactionManager
			username = "\""+InitData.getUserList(outuser+Constant.TAG[0])+"\"";
		}
		if(userlists!=null&&!userlists.equals("")){
			where0 = " and a.userlist='"+userlists+"' ";
			where1 = "(select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+userlists+"' and outuser=d.outuser) is not null and ";
			where2 = "(select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+userlists+"' and outuser=e.updtuser) is not null and ";
			where3 = "(select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+userlists+"' and outuser=f.updtuser) is not null and ";
//			where4 = " and outuser in (select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+userlists+"') ";
			username = "\""+userlists+"\"";//HibernateTransactionManager
		}
		BeanFactory factory = ClsFactory.newInstance().getFactory();
		SessionFactoryImpl bean = factory.getBean("sessionFactory", SessionFactoryImpl.class);
		String dialect = bean.getDialect().toString();
		String nulltype = "";
		if(dialect.indexOf("MySQL")!=-1){
			nulltype = "ifnull";
		}else{
			nulltype = "isnull";
		}
		final String hql = "select a.id,c.goodsname," +
				"("+nulltype+"((select sum(c.goodsnumber) from store_output_list c,store_output_info d where "+where1+" c.goodsid=a.id and c.outid=d.id and d.status='2' and d.updttime>'"+StringUtil.newInstance().getCurrentDate()+"' and d.updttime<'"+StringUtil.newInstance().dayAddNumber(new Date(), 1)+"'),0))+(select storenumber from goods_stat where goodsid=a.id ) as storenumber," +
				"b.id,b.typename,a.goodsnumber," +
				"("+nulltype+"((select sum(e.goodsnumber) from goods_output_list e where "+where2+" e.goodsid=a.id and e.updttime>'"+StringUtil.newInstance().getCurrentDate()+"' and e.updttime<'"+StringUtil.newInstance().dayAddNumber(new Date(), 1)+"'),0))+(select outnumber from goods_stat where goodsid=a.id ) as outnumber," +
				"("+nulltype+"((select sum(f.returnnumber) from goods_output_list f where "+where3+" f.goodsid=a.id and f.updttime>'"+StringUtil.newInstance().getCurrentDate()+"' and f.updttime<'"+StringUtil.newInstance().dayAddNumber(new Date(), 1)+"'),0))+(select returnnumber from goods_stat where goodsid=a.id ) as returnnumber " +
				"from goods_list a,store_type b,store_list c where a.id=c.id and c.typeid=b.id "+where0;
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
		
		List<Object[]> list = hibernateSessionDAO.getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>(){

			public List<Object[]> doInHibernate(Session session)
					throws HibernateException, SQLException {
				int startList = Integer.parseInt(start);
				int limitList = Integer.parseInt(limit);
				Query sql = session.createSQLQuery(hql);
				if(startList>0){
					sql = sql.setFirstResult(startList);
				}
				if(limitList>0){
					sql = sql.setMaxResults(limitList);
				}
				return sql.list();
			}
			
		});
		String tmpUserlist = request.getParamsMap().get("userlist");
		if(tmpUserlist==null){
			tmpUserlist = InitData.getUserList(request.getParamsMap().get("outuser")+Constant.TAG[0]);
		}
		String json = "";
		for(Object[] obj:list){
			json += "{goodsid:'"+obj[0]+"',goodsname:'"+obj[1]+"',storenumber:"+obj[2]+",typeid:'"+obj[3]+"',typename:'"+obj[4]+"',initnumber:"+obj[5]+",outnumber:"+obj[6]+",returnnumber:"+obj[7]+",allnumber:"+(Integer.parseInt((obj[2]==null?"0":obj[2].toString()))+Integer.parseInt((obj[5]==null?"0":obj[5].toString())))+",lastnumber:"+(Integer.parseInt((obj[2]==null?"0":obj[2].toString()))+Integer.parseInt((obj[5]==null?"0":obj[5].toString()))-Integer.parseInt((obj[6]==null?"0":obj[6].toString()))+Integer.parseInt((obj[7]==null?"0":obj[7].toString())))+",username:"+username+"},";
		}
		if (!json.equals("")) {
			json = json.substring(0, json.length()-1);
		}
		result = "{totalProperty:"+hibernateSessionDAO.getHibernateTemplate().find("select count(*) from GoodsList where id.userlist='"+tmpUserlist+"'").get(0)+",root:["+json+"]}";
		request.setResponse(result);
		return request;
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
				"(select sum(c.goodsnumber) from store_output_list c,store_output_info d where c.goodsid=a.id and c.outid=d.id and d.status='2' and (select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+userlist.get(i).toString()+"' and outuser=d.outuser) is not null and "+where1+") as storenumber," +
				"(select sum(e.goodsnumber) from goods_output_list e where e.goodsid=a.id and  (select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+userlist.get(i).toString()+"' and outuser=e.updtuser) is not null and "+where2+") as outnumber," +
				"(select sum(f.returnnumber) from goods_output_list f where f.goodsid=a.id and  (select outuser from out_user_list where tag='"+Constant.TAG[0]+"' and userlist='"+userlist.get(i).toString()+"' and outuser=f.updtuser) is not null and "+where3+") as returnnumber " +
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
