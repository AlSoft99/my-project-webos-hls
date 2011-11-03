package com.hrm.vo;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrm.control.Request;
import com.hrm.dao.OutUserListDAO;
import com.hrm.entity.OutUserList;
import com.hrm.entity.OutUserListId;
import com.hrm.entity.UserInfo;
import com.hrm.server.InitData;
import com.hrm.util.ClsFactory;
import com.hrm.util.StringUtil;

public class StoreOutuserVo implements BaseVo {
	private OutUserListDAO outUserListDAO;
	public Request execute(Request request) throws Exception {
		String result = "";
		String action = request.getParamsMap().get("action");
		final String tag = request.getParamsMap().get("tag");
		String outuser = request.getParamsMap().get("outuser");
		UserInfo userInfo = (UserInfo)request.getUserInfo();
		if (action.equals("insert")) {
			String userlist = "";
			String header = userInfo.getUserCode().substring(0, 5).toUpperCase()+tag;
			List<OutUserList> tmp = outUserListDAO.getHibernateTemplate().find("from OutUserList where id.tag='"+tag+"' order by id.userlist desc");
			if(tmp.size()==0){
				userlist = StringUtil.newInstance().createAccId(header, 1, 5);
			}else{
				OutUserList info = tmp.get(0);
				String id = info.getId().getUserlist();
				String idNumber = id.substring(13, id.length());
				int number = Integer.parseInt(idNumber);
				userlist = StringUtil.newInstance().createAccId(header, ++number, 5);
			}
			OutUserListId id = new OutUserListId();
			id.setOutuser(outuser);
			id.setTag(tag);
			id.setUserlist(userlist);
			OutUserList list = new OutUserList();
			list.setId(id);
			list.setUpdttime(new Date());
			list.setUpdtuser(userInfo.getUserId());
			outUserListDAO.save(list);
			result = "{success:true,msg:'"+userlist+"'}";
		}else if(action.equals("delete")){
			final String userlist = request.getParamsMap().get("userlist");
			boolean f = outUserListDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){

				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					int flag = session.createQuery("delete from OutUserList where id.tag='"+tag+"' and id.userlist='"+userlist+"'").executeUpdate();
					return flag>0;
				}
			});
			if(f){
				result = "{success:true,msg:'删除成功'}";
			}else{
				result = "{failure:true,msg:'删除失败'}";
			}
		}else if(action.equals("save")){
			//itemselector=36f7f35627b7ce470127b7d613ff0005%2C36f7f35627b7ce470127b7d543230004%2C36f7f35627b7ce470127b7d6b85e0006
			String[] itemselectors = request.getParamsMap().get("itemselector").split(",");
			final String userlist = request.getParamsMap().get("userlist");
			boolean f = outUserListDAO.getHibernateTemplate().execute(new HibernateCallback<Boolean>(){

				public Boolean doInHibernate(Session session)
						throws HibernateException, SQLException {
					int flag = session.createQuery("delete from OutUserList where id.tag='"+tag+"' and id.userlist='"+userlist+"'").executeUpdate();
					return flag>0;
				}
			});
			for (int i = 0; i < itemselectors.length; i++) {
				OutUserListId id = new OutUserListId();
				id.setOutuser(itemselectors[i]);
				id.setTag(tag);
				id.setUserlist(userlist);
				OutUserList user = new OutUserList();
				user.setId(id);
				user.setUpdttime(new Date());
				user.setUpdtuser(userInfo.getUserId());
				outUserListDAO.save(user);
			}
			result = "{success:true,msg:'保存成功'}";
		}
		BeanFactory factory = ClsFactory.newInstance().getFactory();
		InitData init = factory.getBean("initData", InitData.class);
		init.initOutuserInfo();
		request.setResponse(result);
		return request;
	}
	public OutUserListDAO getOutUserListDAO() {
		return outUserListDAO;
	}
	public void setOutUserListDAO(OutUserListDAO outUserListDAO) {
		this.outUserListDAO = outUserListDAO;
	}

}
