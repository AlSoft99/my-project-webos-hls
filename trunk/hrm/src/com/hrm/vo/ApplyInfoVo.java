package com.hrm.vo;

import java.util.Date;

import com.hrm.control.Request;
import com.hrm.dao.StoreParamsInfoDAO;
import com.hrm.entity.StoreParamsInfo;

/**
 * 申报参数管理
 * @author Guanrl
 *
 */
public class ApplyInfoVo implements BaseVo{
	private StoreParamsInfoDAO storeParamsInfoDAO;

	public StoreParamsInfoDAO getStoreParamsInfoDAO() {
		return storeParamsInfoDAO;
	}

	public void setStoreParamsInfoDAO(StoreParamsInfoDAO storeParamsInfoDAO) {
		this.storeParamsInfoDAO = storeParamsInfoDAO;
	}

	public Request execute(Request request) throws Exception {
		String result = "";
		String action = request.getParamsMap().get("action");
		String outuser = request.getParamsMap().get("outuser");
		String min = request.getParamsMap().get("min");
		String max = request.getParamsMap().get("max");
		if ("insert".equals(action)) {
			StoreParamsInfo info = new StoreParamsInfo();
			info.setMax(Double.valueOf(max));
			info.setMin(Double.valueOf(min));
			info.setStocklevel(request.getParamsMap().get("stocklevel"));
			info.setOutuser(outuser);
			info.setUpdttime(new Date());
			storeParamsInfoDAO.save(info);
			result = "{success:true,msg:'记录添加成功,请看列表确认!'}";
		}else if("delete".equals(action)){
			String[] deleteId = request.getParamsMap().get("id").split(",");
			for (int i = 0; i < deleteId.length; i++) {
				StoreParamsInfo info = new StoreParamsInfo();
				info.setId(deleteId[i]);
				storeParamsInfoDAO.delete(info);
			}
			result = "{success:true,msg:'记录删除成功,请看列表确认!'}";
		}else if("update".equals(action)){
			String id = request.getParamsMap().get("id");
			StoreParamsInfo info = new StoreParamsInfo();
			info.setId(id);
			info.setMax(Double.valueOf(max));
			info.setMin(Double.valueOf(min));
			info.setStocklevel(request.getParamsMap().get("stocklevel"));
			info.setOutuser(outuser);
			info.setUpdttime(new Date());
			storeParamsInfoDAO.getHibernateTemplate().update(info);
			result = "{success:true,msg:'记录修改成功,请看列表确认!'}";
		}
		request.setResponse(result);
		return request;
	}
	
}
