package com.ux.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.frame.dao.BaseDao;
import com.ux.entity.ArticleInfo;
import com.ux.entity.TagInfo;

@Repository
public class ArticleDao extends BaseDao{
	public void save(ArticleInfo entity){
		getHibernateTemplate().save(entity);
	}
	public List<ArticleInfo> queryArticle(final int first, final int result){
		//List<ArticleInfo> list = getHibernateTemplate().find("from ArticleInfo where userid='"+userid+"' order by firstDate desc");
		List<ArticleInfo> list = getHibernateTemplate().executeFind(new HibernateCallback<List<ArticleInfo>>(){

			@Override
			public List<ArticleInfo> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query q = session.createQuery("from ArticleInfo order by firstDate desc").setFirstResult(first).setMaxResults(result);
				return q.list();
			}
			
		});
		return list;
	}
	public List<Map<String,Object>> queryArticleMap(final int first, final int result){
		//List<ArticleInfo> list = getHibernateTemplate().find("from ArticleInfo where userid='"+userid+"' order by firstDate desc");
		return queryArticleMap(first,result,"");
	}
	public List<Map<String,Object>> queryArticleMap(final int first, final int result,final String where){
		List<Map<String,Object>> list = getHibernateTemplate().executeFind(new HibernateCallback<List<Map<String,Object>>>(){

			@Override
			public List<Map<String,Object>> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query q = session.createQuery("select new map(a.id as id,a.userid as userid,a.type as type,c.typename as typename,a.title as title,a.picture as picture,a.content as content,a.text as text,a.tag as tag,a.love as love,a.brower as brower,a.firstDate as firstDate,a.currentDate as currentDate,b.email as email,b.username as username,b.sign as sign,b.job as job,b.intro as intro,b.phone as phone,b.picture as userpicture,(select count(*) from CommentInfo c where c.articleid=a.id) as commentsum) from ArticleInfo a,UserInfo b,ParamsType c where c.typeid='2' and c.id=a.type and a.userid = b.id "+where+" order by a.firstDate desc").setFirstResult(first).setMaxResults(result);
				List<Map<String,Object>> list = q.list();
				for (int i = 0; i < list.size(); i++) {
					String date = list.get(i).get("firstDate").toString().substring(0, 10);
					list.get(i).put("firstDate", date);
				}
				
				return list;
			}
			
		});
		return list;
	}
	
	public ArticleInfo queryEntity(String id){
		List<ArticleInfo> list = getHibernateTemplate().find("from ArticleInfo where id='"+id+"'");
		return list.get(0);
	}
	public Map<String,Object> queryMapById(String id){
		List<Map<String,Object>> list = getHibernateTemplate().find("select new map(a.id as id,a.userid as userid,a.type as type,c.typename as typename,a.title as title,a.picture as picture,a.content as content,a.text as text,a.tag as tag,a.love as love,a.brower as brower,a.firstDate as firstDate,a.currentDate as currentDate,b.email as email,b.username as username,b.sign as sign,b.job as job,b.intro as intro,b.phone as phone,b.picture as userpicture) from ArticleInfo a,UserInfo b,ParamsType c where c.typeid='2' and c.id=a.type and a.userid = b.id and a.id='"+id+"'");
		List<TagInfo> tag = getHibernateTemplate().find("from TagInfo where articleid='"+id+"'");
		String date = list.get(0).get("firstDate").toString().substring(0, 10);
		list.get(0).put("firstDate", date);
		String tagStr = "";
		for (TagInfo tagInfo : tag) {
			tagStr += tagInfo.getTagname()+",";
		}
		tagStr = tagStr.substring(0, tagStr.length()-1);
		list.get(0).put("tagname", tagStr);
		return list.get(0);
	}
	public long getCount(){
		List<Long> list = getHibernateTemplate().find("select count(*) from ArticleInfo");
		return list.get(0);
	}
	public void updateBrower(String id){
		ArticleInfo info = new ArticleInfo();
		info.setId(Integer.valueOf(id));
		info = getHibernateTemplate().get(ArticleInfo.class, Integer.valueOf(id));
		info.setBrower(info.getBrower()+1);
		getHibernateTemplate().update(info);
	}
	public void updateLove(String id){
		ArticleInfo info = new ArticleInfo();
		info.setId(Integer.valueOf(id));
		info = getHibernateTemplate().get(ArticleInfo.class, Integer.valueOf(id));
		info.setLove(info.getLove()+1);
		getHibernateTemplate().update(info);
	}
}
