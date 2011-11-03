package com.hrm.vo;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hrm.control.Request;
import com.hrm.dao.WelfareParamDAO;
import com.hrm.entity.WelfareParam;
import com.hrm.util.StringUtil;

public class WelfareParamsVo implements BaseVo{
	private static Logger logger = Logger.getLogger(WelfareParamsVo.class);
	private WelfareParamDAO welfareParamDAO;
	public Request execute(Request request) throws Exception{
		String result = "";
		String action = request.getParamsMap().get("action");
		if ("select".equals(action)) {//得到参数菜单
			result = selectParams(request);
		}else if("insert".equals(action)){
			result = insertParams(request);
		}
		request.setResponse(result);
		return request;
	}
	/**
	 * 初始化查询
	 * @param request
	 * @param response
	 * @return
	 */
	public String selectParams(Request request){
		String result = "({";
		List<WelfareParam> list = welfareParamDAO.findAll();
		for(WelfareParam fare:list){ 
			result += "'"+fare.getId()+"':"+StringUtil.newInstance().transeObjectToJson(fare)+",";
		}
		if(list.size()>0){
			result = result.substring(0, result.length()-1);
		}
		result += "})";
		return result;
	}
	public String insertParams(Request request){
		String result = "";
		WelfareParam fare ;
		try{
			String id = request.getParamsMap().get("id");
			fare = welfareParamDAO.findById(id);
			
			if(fare==null){
				fare = new WelfareParam();
				fare.setId(id);
				fare.setWorkday(Integer.valueOf(request.getParamsMap().get("workday")));
				fare.setWelfare(Double.valueOf(request.getParamsMap().get("welfare")));
				fare.setCurefund(Double.valueOf(request.getParamsMap().get("curefund")));
				fare.setIntegfund(Double.valueOf(request.getParamsMap().get("integfund")));
				fare.setPubfund(Double.valueOf(request.getParamsMap().get("pubfund")));
				fare.setSafefund(Double.valueOf(request.getParamsMap().get("safefund")));
				fare.setTax(Double.valueOf(request.getParamsMap().get("tax")));
				fare.setUpdtTime(new Date());
				welfareParamDAO.save(fare);
			}else{
				fare.setId(id);
				fare.setWorkday(Integer.valueOf(request.getParamsMap().get("workday")));
				fare.setWelfare(Double.valueOf(request.getParamsMap().get("welfare")));
				fare.setCurefund(Double.valueOf(request.getParamsMap().get("curefund")));
				fare.setIntegfund(Double.valueOf(request.getParamsMap().get("integfund")));
				fare.setPubfund(Double.valueOf(request.getParamsMap().get("pubfund")));
				fare.setSafefund(Double.valueOf(request.getParamsMap().get("safefund")));
				fare.setTax(Double.valueOf(request.getParamsMap().get("tax")));
				fare.setUpdtTime(new Date());
				welfareParamDAO.update(fare);
			}
			result = "{success:true,msg:'保存成功'}";
		}catch(Exception e){
			result = "{failure:true,msg:'保存失败,原因:"+e.getMessage()+"'}";
			logger.error("WeflareParamsVo的insertParams方法错误", e);
		}
		return result;
	}
	public WelfareParamDAO getWelfareParamDAO() {
		return welfareParamDAO;
	}
	public void setWelfareParamDAO(WelfareParamDAO welfareParamDAO) {
		this.welfareParamDAO = welfareParamDAO;
	}

}
