package com.ux.util;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.ux.dao.ParamsListDao;
import com.ux.dao.ParamsTypeDao;
import com.ux.entity.ParamsList;
import com.ux.entity.ParamsType;
@Controller
public class ImportDataUtil {
	@Resource
	private ParamsListDao paramsListDao;
	@Resource
	private ParamsTypeDao paramsTypeDao;
	private boolean isReset;
	private List<ParamsList> paramsList;
	private List<ParamsType> paramsType;
	public List<ParamsList> getParamsList() {
		return paramsList;
	}
	public void setParamsList(List<ParamsList> paramsList) {
		this.paramsList = paramsList;
	}
	public List<ParamsType> getParamsType() {
		return paramsType;
	}
	public void setParamsType(List<ParamsType> paramsType) {
		this.paramsType = paramsType;
	}
	public boolean getIsReset() {
		return isReset;
	}
	public void setIsReset(boolean isReset) {
		this.isReset = isReset;
	}
	public void importData(){
		if (isReset) {
			paramsListDao.delete("");
			paramsTypeDao.delete("");
		}
		if(paramsListDao.isEmpty()){
			for (int i = 0; i < paramsList.size(); i++) {
				paramsListDao.save(paramsList.get(i));
			}
		}
		if(paramsTypeDao.isEmpty()){
			for (int i = 0; i < paramsType.size(); i++) {
				paramsTypeDao.save(paramsType.get(i));
			}
		}
	}
}
