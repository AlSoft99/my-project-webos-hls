package com.ux.vo;

import java.io.IOException;
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
import com.ux.dao.ArticleDao;
import com.ux.dao.TagInfoDao;
import com.ux.entity.ArticleInfo;
import com.ux.entity.CutPhoto;
import com.ux.entity.TagInfo;
import com.ux.entity.UserInfo;
import com.ux.util.FileOperate;

@Controller
public class ArticleVo {
	@Resource
	private ArticleDao articleDao;
	@Resource
	private TagInfoDao tagInfoDao;
	
	@RequestMapping(value="/article-upload.do",method=RequestMethod.GET)
	public @ResponseBody String checkId(CutPhoto cut,HttpSession session) throws IOException{
		UserInfo userinfo = (UserInfo)session.getAttribute("userinfo");
		String name = System.getProperty("webapp.root")+"/tmp/head/";
		String out = System.getProperty("webapp.root")+"/upload/"+userinfo.getEmail()+"/article/";
		Utils.createFolder(out);
		if(cut.getW()!=0){
			CutImage o = new CutImage(cut.getX(), cut.getY(), cut.getW(), cut.getH());
			o.setSrcpath(name+cut.getUploadname());
			o.setSubpath(out+cut.getUploadname());
			o.cut();
		}else{
			FileOperate o = new FileOperate();
			o.copyFile(name+cut.getUploadname(), out+cut.getUploadname());
		}
		return "upload/"+userinfo.getEmail()+"/article/"+cut.getUploadname();
	}
	
	@RequestMapping(value="/article-add.do",method=RequestMethod.POST)
	public @ResponseBody String addArticle(ArticleInfo info,@RequestParam(value="tag") String tag,HttpSession session) throws IOException{
		UserInfo userinfo = (UserInfo)session.getAttribute("userinfo");
		info.setUserid(userinfo.getId()+"");
		info.setBrower(0);
		info.setLove(0);
		info.setCurrentDate(new Date());
		info.setFirstDate(new Date());
		articleDao.save(info);
		if(!tag.equals("")){
			String[] taglist = tag.split(",");
			for (int i = 0; i < taglist.length; i++) {
				TagInfo tagInfo = new TagInfo();
				tagInfo.setArticleid(info.getId()+"");
				tagInfo.setCurrentDate(new Date());
				tagInfo.setTagname(taglist[i]);
				tagInfo.setUserid(userinfo.getId()+"");
				tagInfoDao.save(tagInfo);
			}
			
		}
		
		
		return "success";
	}
}