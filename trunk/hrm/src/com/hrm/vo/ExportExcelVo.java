package com.hrm.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.hrm.control.Request;
import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.MaterialList;
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
		/**[
		{unitname=两, materialname=原材料11, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名11, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=11.0, lossamount=0.0, materialsum=0.0}, 
		{unitname=条, materialname=原材料22, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名11, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=22.0, lossamount=0.0, materialsum=0.0}, 
		{unitname=克, materialname=原材料13, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=3.0, monthinput=100.0, monthshouldend=100.0, footname=菜名11, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=13.0, lossamount=0.0, materialsum=0.0}, {unitname=两, materialname=原材料21, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=3.0, monthinput=100.0, monthshouldend=100.0, footname=菜名12, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=21.0, lossamount=0.0, materialsum=0.0}, {unitname=只, materialname=原材料15, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名12, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=15.0, lossamount=0.0, materialsum=0.0}, {unitname=克, materialname=原材料13, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=1.0, monthinput=100.0, monthshouldend=100.0, footname=菜名12, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=13.0, lossamount=0.0, materialsum=0.0}, {unitname=两, materialname=原材料31, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名13, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=31.0, lossamount=0.0, materialsum=0.0}, {unitname=只, materialname=原材料15, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=0.0, monthinput=100.0, monthshouldend=100.0, footname=菜名13, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=15.0, lossamount=0.0, materialsum=0.0}, {unitname=克, materialname=原材料32, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=1.0, monthinput=100.0, monthshouldend=100.0, footname=菜名21, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=32.0, lossamount=0.0, materialsum=0.0}, {unitname=条, materialname=原材料24, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=3.0, monthinput=100.0, monthshouldend=100.0, footname=菜名21, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=24.0, lossamount=0.0, materialsum=0.0}, {unitname=两, materialname=原材料31, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名21, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=31.0, lossamount=0.0, materialsum=0.0}, {unitname=只, materialname=原材料23, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=3.0, monthinput=100.0, monthshouldend=100.0, footname=菜名22, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=23.0, lossamount=0.0, materialsum=0.0}, {unitname=条, materialname=原材料22, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名22, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=22.0, lossamount=0.0, materialsum=0.0}, {unitname=条, materialname=原材料24, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=2.0, monthinput=100.0, monthshouldend=100.0, footname=菜名22, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=24.0, lossamount=0.0, materialsum=0.0}]
		**/
		String th[] = {"品名","菜名","原料消耗","销售单价","销售消耗","销售份数","销售金额","进货均价","上月库存","本月进货量","月末实际库存","月末理论库存","损耗率（%）"};
		CreateStatExcel t = new CreateStatExcel();
		//WritableWorkbook book = t.createFile(System.currentTimeMillis()+".xls");
		WritableWorkbook book = t.createFile("monthstat.xls");
		WritableCellFormat boldCell = t.getBoldCell(12);
		WritableCellFormat normalTlCell = t.getNormalCell(12);
		WritableCellFormat normalContentCell = t.getNormalCell(11);
		WritableSheet sheet = t.createSheet(book);
		//创建标题
		t.createTitle(sheet,boldCell,"菜品月销售报表 （"+request.getParamsMap().get("{0}")+" ~ "+request.getParamsMap().get("{1}")+"）",th);
		//创建表头
		t.createTHead(sheet,boldCell, th);
		sheet.setRowView(CreateStatExcel.TH, 500); // 设置行的高度
		List<MaterialList> type = hibernateSessionDAO.createHqlQuery("from MaterialList");
		int start = CreateStatExcel.TITLE + CreateStatExcel.TH + 1;
		for (MaterialList materialList : type) {
			String sqltemp = sql.replace("where", "where c.paramsname='"+materialList.getParamsname()+"' and ");
			List<Map<String,Object>> temp = hibernateSessionDAO.createSqlQuery(sqltemp);
			/**[
			//{unitname=条, materialname=原材料24, materialcostsum=0.0, lossmaterialsum=0.0, monthend=0.0, monthinit=0.0, materialamount=3.0, monthinput=100.0, 
			 * monthshouldend=100.0, footname=菜名21, sellpay=0.0, sellamount=0.0, lossmaterialcostsum=0.0, materialcost=24.0, lossamount=0.0, materialsum=0.0}]
			**/
			if(temp.size()>0){
				sheet.addCell(new Label(0, start, materialList.getParamsname(), normalTlCell));
				sheet.addCell(new Label(7, start, "￥"+temp.get(0).get("materialcost").toString(), normalContentCell));
				sheet.addCell(new Label(8, start, temp.get(0).get("monthinit").toString(), normalContentCell));
				sheet.addCell(new Label(9, start, temp.get(0).get("monthinput").toString(), normalContentCell));
				sheet.addCell(new Label(10, start, temp.get(0).get("monthend").toString(), normalContentCell));
				sheet.addCell(new Label(11, start, temp.get(0).get("monthshouldend").toString(), normalContentCell));
				float rate = (Float.valueOf(temp.get(0).get("monthshouldend").toString())-Float.valueOf(temp.get(0).get("monthend").toString())) / Float.valueOf(temp.get(0).get("monthshouldend").toString());
				sheet.addCell(new Label(12, start, (rate*100)+"", normalContentCell));
				sheet.mergeCells(0, start, 0,start + temp.size());
				float materialamountsum = 0F;
				float materialsumsum = 0F;
				float materialcostsumsum = 0F;
				for (int i = 0; i < temp.size(); i++) {
					Map<String, Object> map = temp.get(i);
					sheet.setRowView(start+i, 400); // 设置行的高度
					sheet.addCell(new Label(1, start+i, map.get("footname").toString(), normalContentCell));
					sheet.addCell(new Label(2, start+i, map.get("materialamount").toString()+map.get("unitname").toString(), normalContentCell));
					sheet.addCell(new Label(3, start+i, map.get("footprice").toString(), normalContentCell));
					sheet.addCell(new Label(4, start+i, map.get("materialsum").toString()+map.get("unitname").toString(), normalContentCell));
					sheet.addCell(new Label(5, start+i, map.get("sellamount").toString(), normalContentCell));
					sheet.addCell(new Label(6, start+i, "￥"+map.get("materialcostsum").toString(), normalContentCell));
					materialamountsum += Float.valueOf(map.get("materialsum").toString());
					materialsumsum += Float.valueOf(map.get("sellamount").toString());
					materialcostsumsum += Float.valueOf(map.get("materialcostsum").toString());
				}
				sheet.mergeCells(7, start, 7,start + temp.size());
				sheet.mergeCells(8, start, 7,start + temp.size());
				sheet.mergeCells(9, start, 7,start + temp.size());
				sheet.mergeCells(10, start, 7,start + temp.size());
				sheet.mergeCells(11, start, 7,start + temp.size());
				sheet.mergeCells(12, start, 7,start + temp.size());
				sheet.addCell(new Label(1, start+temp.size(), "合计", boldCell));
				sheet.addCell(new Label(4, start+temp.size(), materialamountsum+temp.get(0).get("unitname").toString(), boldCell));
				sheet.addCell(new Label(5, start+temp.size(), materialsumsum+"", boldCell));
				sheet.addCell(new Label(6, start+temp.size(), "￥"+materialcostsumsum+"", boldCell));
				sheet.setRowView(start+temp.size(), 400); // 设置行的高度
				start += temp.size()+1;
			}
			
		}
		t.write(book);
		t.close(book);
		//monthstat.xls
		request.setResponse("monthstat.xls");
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
