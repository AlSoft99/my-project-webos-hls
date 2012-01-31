package com.ux.vo;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ux.dao.CommentInfoDao;
import com.ux.entity.CommentInfo;
import com.ux.entity.UserInfo;

@Controller
public class CommentVo {
	@Resource
	private CommentInfoDao commentInfoDao;
	
	@RequestMapping(value="/add-comment-logout.do",method=RequestMethod.GET)
	public @ResponseBody String addArticle(CommentInfo info,HttpSession session) throws IOException{
		String userid = "";
		UserInfo userInfo = (UserInfo)session.getAttribute("userinfo");
		if(userInfo!=null){
			userid = userInfo.getId()+"";
		}
		info.setApprove(0);
		info.setCurrentDate(new Date());
		info.setOppose(0);
		info.setUserid(userid);
		commentInfoDao.save(info);
		return "success";
	}
}
