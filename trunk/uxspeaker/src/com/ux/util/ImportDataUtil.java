package com.ux.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.ux.dao.ParamsListDao;
import com.ux.dao.ParamsTypeDao;
import com.ux.entity.ArticleInfo;
import com.ux.entity.ParamsList;
import com.ux.entity.ParamsType;
import com.ux.entity.TagInfo;
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
		
		//导入iteye数据
		/*Map<String,String> type = new HashMap<String,String>();
		List<Object[]> ittype = paramsTypeDao.getHibernateTemplate().find("select id,typename from ParamsType where typeid='2'");
		for (Object[] o : ittype) {
			type.put(o[1].toString(), o[0].toString());
		}
		SimplePDFReader s = new SimplePDFReader();
		Map<String,PdfEntity> map = s.getTextFromPDF("F:\\workspace\\workspace-project\\uxspeaker\\rayln的博客文章.pdf");
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			PdfEntity e = map.get(key);
			ArticleInfo info = new ArticleInfo();
			info.setUserid("1");
			info.setBrower(0);
			info.setLove(0);
			info.setCurrentDate(new Date());
			info.setFirstDate(new Date());
			String text = "";
			if(e.getContent().replace("<", "").length()>250){
				text = e.getContent().replace("<", "").substring(0, 250)+"...";
			}else{
				text = e.getContent().replace("<", "");
			}
			info.setText(text);
			info.setContent(e.getContent());
			info.setTitle(e.getTitle());
			info.setStatus("13");
			info.setTag(e.getTag());
			info.setType(type.get(e.getType()));
			paramsTypeDao.getHibernateTemplate().save(info);
			String tag = e.getTag();
			if(!tag.equals("")){
				String[] taglist = tag.split(",");
				for (int i = 0; i < taglist.length; i++) {
					TagInfo tagInfo = new TagInfo();
					tagInfo.setArticleid(info.getId()+"");
					tagInfo.setCurrentDate(new Date());
					tagInfo.setTagname(taglist[i]);
					tagInfo.setUserid("1");
					paramsTypeDao.getHibernateTemplate().save(tagInfo);
				}
			}
		}*/
	}
}
