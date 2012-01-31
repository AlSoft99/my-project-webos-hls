package com.ux.vo;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ux.dao.ArticleDao;
import com.ux.entity.ArticleInfo;

@Controller
public class IndexArticleVo {
	@Resource
	private ArticleDao articleDao;
	
	@RequestMapping(value="/add-love-logout.do",method=RequestMethod.GET)
	public @ResponseBody String addArticle(ArticleInfo info,@RequestParam(value="id") String id,HttpSession session) throws IOException{
		articleDao.updateLove(id);
		return "success";
	}
}
