package com.ux.vo;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.util.CutImage;
import com.ux.dao.UserInfoDao;
import com.ux.entity.CutPhoto;
import com.ux.entity.UserInfo;
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
	public @ResponseBody String uploadphoto(CutPhoto cut) throws Exception{
		String name = System.getProperty("webapp.root")+"/upload/head/"+cut.getUploadname();
		System.out.println("cut.getX():"+cut.getX()+"   cut.getY():"+cut.getY()+"  cut.getW():"+cut.getW()+"  cut.getH():"+cut.getH());
		CutImage o = new CutImage(cut.getX(), cut.getY(), cut.getW(), cut.getH());
		o.setSrcpath(name);
		o.setSubpath(name);
		o.cut();
		return "success";
	}
}
