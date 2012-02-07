package com.ux.vo;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.util.CutImage;
import com.frame.util.Utils;
import com.ux.dao.UserInfoDao;
import com.ux.entity.CutPhoto;
import com.ux.entity.UserInfo;
import com.ux.util.FileOperate;
@Controller
public class UpdateUserInfoVo {
	@Resource
	private UserInfoDao userInfoDao;
	
	@RequestMapping(value="/userinfo.do",method=RequestMethod.GET, params="method=sign")
	public @ResponseBody String register(@RequestParam(value="sign",required=true) String sign,HttpSession session){
		UserInfo userinfo = (UserInfo)session.getAttribute("userinfo");
		userinfo.setSign(sign);
		userInfoDao.update(userinfo);
		return "success";
	}
	///uxspeaker/lib/swfupload/uploadphoto.do
	@RequestMapping(value="/cuthead.do",method=RequestMethod.GET)
	public @ResponseBody String uploadphoto(CutPhoto cut,HttpSession session) throws Exception{
		String name = System.getProperty("webapp.root")+"/tmp/head/";
		String out = System.getProperty("webapp.root")+"/upload/head/";
		if(cut.getW()!=0){
			CutImage o = new CutImage(cut.getX(), cut.getY(), cut.getW(), cut.getH());
			o.setSrcpath(name+cut.getUploadname());
			o.setSubpath(out+cut.getUploadname());
			o.cut(o.getType(cut.getUploadname()));
		}else{
			FileOperate o = new FileOperate();
			o.copyFile(name+cut.getUploadname(), out+cut.getUploadname());
		}
		
		UserInfo userinfo = (UserInfo)session.getAttribute("userinfo");
		userinfo.setPicture("upload/head/"+cut.getUploadname());
		userinfo.setCurrentdate(new Date());
		userInfoDao.update(userinfo);
		return "upload/head/"+cut.getUploadname();
	}
}
