package com.hrm.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.read.biff.BiffException;

import com.hrm.dao.HibernateSessionDAO;
import com.hrm.entity.FootList;
import com.hrm.entity.OrderOutputInfo;
import com.hrm.entity.OrderOutputList;
import com.hrm.entity.UserInfo;
import com.hrm.util.ClsFactory;
import com.hrm.util.ExcelUtil;
import com.hrm.util.FileEntity;
import com.hrm.util.FileUpload;
import com.hrm.util.StringUtil;

public class FileUploadServer extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileUpload file = new FileUpload();
		String createId = "";
		List<FileEntity> list = file.upload(request, response, "file");
		UserInfo userinfo = (UserInfo)request.getSession().getAttribute("userInfo");
		if(list.size()!=0){
			try {
				FileEntity entity = list.get(0);
				String filename = System.getProperty("webapp.root")+entity.getUploadurl()+"/" + entity.getUploadname();
				System.out.println("filename:"+filename);
				String[][] content = ExcelUtil.parse(filename);
				if(content.length!=0){
					List<FileUploadServer.FootEntity> store = parseExcel(content);
					InitData vo = ClsFactory.newInstance().getFactory().getBean("initData", InitData.class);
					HibernateSessionDAO dao = vo.getHibernateSessionDAO();
					createId = createId(dao);
					insertOrderOutputInfo(dao,userinfo,createId,content[4][2]+"~"+content[4][4]);
					List<FootList> temp = dao.createHqlQuery("from FootList");
					Map<String, FootList> map = new HashMap<String, FootList>();
					for(FootList footList: temp){
						map.put(footList.getParamsname(), footList);
					}
					for( FileUploadServer.FootEntity foot : store){
						OrderOutputList goods = new OrderOutputList();
						String gdId = StringUtil.newInstance().createId("GD");
						String checkyn = "";
						String goodsid = "";
						if(map.get(foot.getName())!=null){
							checkyn = "1";
							goodsid = map.get(foot.getName()).getId();
						}else{
							checkyn = "0";
							goodsid = foot.getName();
						}
						
						goods.setConsumetype("1");
						goods.setOptiontype("0");
						goods.setCheckyn(checkyn);
						goods.setGoodsid(goodsid);
						goods.setActuallypay(foot.getSellamount());
						goods.setShouldpay(foot.getSellamount());
						goods.setGoodsnumber(foot.getSellnumber());
						//goods.setGoodsnumber(Float.valueOf(request.getParamsMap().get("goodsnumber")));
						goods.setId(gdId);
						goods.setOutid(createId);
						goods.setReturnnumber(0F);
						goods.setUpdttime(new Date());
						goods.setUpdtuser(userinfo.getUserId());
						dao.save(goods);
					}
					
				}
			} catch (BiffException e) {
				e.printStackTrace();
			}
		}
		System.out.println("success!!!!");
		response.setContentType("text/html");
		response.getWriter().println("{success:true,msg:'"+createId+"'}");
	}
	private void insertOrderOutputInfo(HibernateSessionDAO dao,UserInfo userinfo,String id,String desc){
		System.out.println("========"+userinfo);
		OrderOutputInfo info = new OrderOutputInfo();
		info.setOrderdesc(desc);
		info.setId(id);
		info.setOutdate(new Date());
		info.setOutuser(userinfo.getUserId());
		info.setUpdttime(new Date());
		info.setUpdtuser(userinfo.getUserId());
		dao.save(info);
	}
	private String createId(HibernateSessionDAO dao){
		String createId = "";
		String header = "FAUTO";
		List<OrderOutputInfo> list = dao.createHqlQuery("from OrderOutputInfo where outdate>'"+StringUtil.newInstance().getCurrentDate()+"' and outdate<'"+StringUtil.newInstance().dayAddNumber(new Date(), 1)+"' order by id desc", -1, -1);
		if(list.size()==0){
			createId = StringUtil.newInstance().createAccId(header, 1, 5);
		}else{
			OrderOutputInfo info = list.get(0);
			String id = info.getId();
			String idNumber = id.substring(13, id.length());
			int number = Integer.parseInt(idNumber);
			createId = StringUtil.newInstance().createAccId(header, ++number, 5);
		}
		return createId;
	}
	public List<FileUploadServer.FootEntity> parseExcel(String[][] content){
		int col = content.length;
		int row = content[0].length;
		List<Integer> rowInt = new ArrayList<Integer>();
		for(int i = 0; i < row; i++){
			String text = content[0][i];
			if(!"".equals(text) && text!=null && StringUtil.newInstance().isNumeric1(text)){
				rowInt.add(i);
			}
		}
		System.out.println(rowInt);
		List<FileUploadServer.FootEntity> list = new ArrayList<FileUploadServer.FootEntity>();
		FileUploadServer util = new FileUploadServer();
		for(int i = 0; i< rowInt.size(); i++){
			FileUploadServer.FootEntity entity = util.new FootEntity();
			for(int j = 0 ; j < col ; j++ ){
				String rowContent = content[j][rowInt.get(i)];
				if(!"".equals(rowContent)){
					if(j==0){
						entity.setSerial(Integer.valueOf(rowContent));
					}else if(j==2){
						entity.setName(rowContent);
					}else if(j==8){
						entity.setSellnumber(Float.valueOf(rowContent));
					}else if(j==11){
						entity.setSellamount(Float.valueOf(rowContent.replace(",", "")));
					}else if(j==14){
						entity.setCostamount(Float.valueOf(rowContent));
					}else if(j==18){
						entity.setGrossamount(Float.valueOf(rowContent.replace(",", "")));
					}else if(j==22){
						entity.setGrossrate(Float.valueOf(rowContent.replace(",", "")));
					}else if(j==25){
						entity.setType(rowContent.replace(",", ""));
					}
				}
			}
			list.add(entity);
		}
		return list;
	}
	
	public class FootEntity{
		//序号
		private int serial;
		//菜品名称
		private String name;
		//销售数量
		private float sellnumber;
		//销售金额
		private float sellamount;
		//成本金额
		private float costamount;
		//毛利润
		private float grossamount;
		//毛利率
		private float grossrate;
		//菜品类别
		private String type;
		public int getSerial() {
			return serial;
		}
		public void setSerial(int serial) {
			this.serial = serial;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public float getSellnumber() {
			return sellnumber;
		}
		public void setSellnumber(float sellnumber) {
			this.sellnumber = sellnumber;
		}
		public float getSellamount() {
			return sellamount;
		}
		public void setSellamount(float sellamount) {
			this.sellamount = sellamount;
		}
		public float getCostamount() {
			return costamount;
		}
		public void setCostamount(float costamount) {
			this.costamount = costamount;
		}
		public float getGrossamount() {
			return grossamount;
		}
		public void setGrossamount(float grossamount) {
			this.grossamount = grossamount;
		}
		public float getGrossrate() {
			return grossrate;
		}
		public void setGrossrate(float grossrate) {
			this.grossrate = grossrate;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
	}
}
