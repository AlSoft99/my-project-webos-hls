package com.ux.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;
import com.frame.servlet.ServletFactory;
import com.ux.entity.ArticleInfo;
import com.ux.entity.TagInfo;

@Repository
public class TagInfoDao extends BaseDao{
	public void save(TagInfo entity){
		getHibernateTemplate().save(entity);
	}
	
	public ArticleInfo queryEntity(String id){
		List<ArticleInfo> list = getHibernateTemplate().find("from TagInfo where articleid='"+id+"'");
		return list.get(0);
	}
	public List<List<String>> getTagList(){
		QueryDao queryTips = ServletFactory.newInstant().getFactory().getBean("queryDao",QueryDao.class);
		List<Map<String,Object>> paramsTag = queryTips.queryByPage("SQL5", "",0,30);
		System.out.println("paramsTag:"+paramsTag);
		int[] lvMax = {15,15,18,18,18,20,20};
		List<List<String>> lv = new ArrayList<List<String>>();
		List<String> nowLv = new ArrayList<String>();
		int flag = 0;
		int actual = 0;
		for(int i = 0; i < paramsTag.size(); i++){
			Map<String,Object> map = paramsTag.get(i);
			String tagname = map.get("tagname").toString();
			actual += tagname.length()+1;
			System.out.println("actual:"+actual+"   tagname:"+tagname);
			if(flag>(lvMax.length-1)){
				break;
			}
			if(lvMax[flag]>=actual){
				nowLv.add(tagname);
				if(i==paramsTag.size()-1){
					lv.add(nowLv);
				}
			}else{
				System.out.println("add");
				flag++;
				lv.add(nowLv);
				nowLv = new ArrayList<String>();
				nowLv.add(tagname);
				actual = tagname.length()+1;
			}
			
		}
		return lv;
	}
}
