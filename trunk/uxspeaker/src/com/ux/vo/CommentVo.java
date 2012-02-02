package com.ux.vo;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
	public @ResponseBody String addComment(CommentInfo info,HttpSession session) throws IOException{
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
	@RequestMapping(value="/modify-comment.do",method=RequestMethod.GET)
	public @ResponseBody String editComment(CommentInfo info,HttpSession session) throws IOException{
		String userid = "";
		UserInfo userInfo = (UserInfo)session.getAttribute("userinfo");
		if(userInfo!=null){
			userid = userInfo.getId()+"";
		}
		info.setUserid(userid);
		List<CommentInfo> list = commentInfoDao.getCommentInfo(info);
		if(list.size()==0){
			return "failure";
		}else{
			CommentInfo entity = list.get(0);
			entity.setComment(info.getComment());
			commentInfoDao.update(entity);
		}
		return "success";
	}
	@RequestMapping(value="/delete-comment.do",method=RequestMethod.GET)
	public @ResponseBody String deleteComment(CommentInfo info,HttpSession session) throws IOException{
		String userid = "";
		UserInfo userInfo = (UserInfo)session.getAttribute("userinfo");
		userid = userInfo.getId()+"";
		info.setUserid(userid);
		List<CommentInfo> list = commentInfoDao.getCommentInfo(info);
		commentInfoDao.delete(list.get(0));
		return "success";
	}
}
