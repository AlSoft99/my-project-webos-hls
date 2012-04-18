package com.hrm.vo;

import java.util.List;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.query.QueryUtil;
import com.hrm.util.ClsFactory;

public class ExportExcelVo implements BaseVo {
	private HibernateSessionDAO hibernateSessionDAO;
	public HibernateSessionDAO getHibernateSessionDAO() {
		return hibernateSessionDAO;
	}
	public void setHibernateSessionDAO(HibernateSessionDAO hibernateSessionDAO) {
		this.hibernateSessionDAO = hibernateSessionDAO;
	}
	@Override
	public Request execute(Request request) throws Exception {
		String sql = getSql(request);
		List list = hibernateSessionDAO.createSqlQuery(sql);
		System.out.println("===========================================");
		System.out.println(list);
		/**[
		{unitname=两, materialname=原材料11, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名11, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=11.0, lossamount=0.0, materialsum=0.0}, 
		{unitname=条, materialname=原材料22, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名11, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=22.0, lossamount=0.0, materialsum=0.0}, 
		{unitname=克, materialname=原材料13, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=3.0, monthinput=100.0, monthshouldend=100.0, footname=菜名11, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=13.0, lossamount=0.0, materialsum=0.0}, {unitname=两, materialname=原材料21, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=3.0, monthinput=100.0, monthshouldend=100.0, footname=菜名12, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=21.0, lossamount=0.0, materialsum=0.0}, {unitname=只, materialname=原材料15, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名12, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=15.0, lossamount=0.0, materialsum=0.0}, {unitname=克, materialname=原材料13, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=1.0, monthinput=100.0, monthshouldend=100.0, footname=菜名12, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=13.0, lossamount=0.0, materialsum=0.0}, {unitname=两, materialname=原材料31, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名13, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=31.0, lossamount=0.0, materialsum=0.0}, {unitname=只, materialname=原材料15, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=0.0, monthinput=100.0, monthshouldend=100.0, footname=菜名13, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=15.0, lossamount=0.0, materialsum=0.0}, {unitname=克, materialname=原材料32, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=1.0, monthinput=100.0, monthshouldend=100.0, footname=菜名21, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=32.0, lossamount=0.0, materialsum=0.0}, {unitname=条, materialname=原材料24, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=3.0, monthinput=100.0, monthshouldend=100.0, footname=菜名21, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=24.0, lossamount=0.0, materialsum=0.0}, {unitname=两, materialname=原材料31, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名21, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=31.0, lossamount=0.0, materialsum=0.0}, {unitname=只, materialname=原材料23, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=3.0, monthinput=100.0, monthshouldend=100.0, footname=菜名22, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=23.0, lossamount=0.0, materialsum=0.0}, {unitname=条, materialname=原材料22, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名22, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=22.0, lossamount=0.0, materialsum=0.0}, {unitname=条, materialname=原材料24, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名22, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=24.0, lossamount=0.0, materialsum=0.0}]
		**/
		return request;
	}
	private String getSql(Request request){
		String action = request.getParamsMap().get("action");
		String type = request.getParamsMap().get("type");
		String start = request.getParamsMap().get("start");
		String limit = request.getParamsMap().get("limit");
		String key = request.getParamsMap().get("sql");
		QueryUtil util = ClsFactory.newInstance().getFactory().getBean("queryUtil", QueryUtil.class);
		String sql = util.getSql(key);
		for(int i=0;i<99;i++){
			String where = request.getParamsMap().get("{"+i+"}");
			if(where==null){
				break;
			}
			sql = sql.replace("{"+i+"}", where);
		}
		return sql;
	}
}
